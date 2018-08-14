package soyouarehere.imwork.speed;

import android.Manifest;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import soyouarehere.imwork.speed.app.BaseApplication;
import soyouarehere.imwork.speed.app.base.mvp.BaseActivity;
import soyouarehere.imwork.speed.app.base.mvp.BaseFragment;
import soyouarehere.imwork.speed.pager.find.FindFragment;
import soyouarehere.imwork.speed.pager.home.HomeFragment;
import soyouarehere.imwork.speed.pager.live.LiveFragment;
import soyouarehere.imwork.speed.pager.mine.MineFragment;
import soyouarehere.imwork.speed.util.log.LogUtil;

/**
 * @author li.xiaodong
 * @desc 主要的Activity 承载着四个fragment,home live find mine
 * @time 2018/7/30 8:56
 */
public class MainActivity extends BaseActivity {

    List<BaseFragment> fragmentList;
    @BindView(R.id.main_root_view)
    FrameLayout frameLayout;
    @BindView(R.id.main_tab_root_rb)
    RadioGroup radioGroup;
    @BindView(R.id.main_tab_home)
    RadioButton main_tab_home;
    private int mLastFgIndex = 0;

    @Override
    public void DataLoadError() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void create(Bundle savedInstanceState) {
        initFragment();
        initView();
        LogUtil.e("CPU核数：",Runtime.getRuntime().availableProcessors());
        getSDCardInfo();
        checkPermission();

    }






    private void initView() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.main_tab_home:
                        switchFragment(0);
                        break;
                    case R.id.main_tab_live:
                        switchFragment(1);
                        break;
                    case R.id.main_tab_find:
                        switchFragment(2);
                        break;
                    case R.id.main_tab_mine:
                        switchFragment(3);
                        break;
                    default:
                        break;
                }
            }
        });
        main_tab_home.setChecked(true);
        switchFragment(0);
    }

    private void initFragment() {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        HomeFragment homeFragment = HomeFragment.newInstance("", "");
        LiveFragment liveFragment = LiveFragment.newInstance("", "");
        FindFragment findFragment = FindFragment.newInstance("", "");
        MineFragment mineFragment = MineFragment.newInstance("", "");
        fragmentList.add(homeFragment);
        fragmentList.add(liveFragment);
        fragmentList.add(findFragment);
        fragmentList.add(mineFragment);
    }
    @Override
    protected boolean setVisibleToolbar() {
        return false;
    }

    /**
     * 切换fragment
     *
     * @param position 要显示的fragment的下标
     */
    private void switchFragment(int position) {
        if (position >= fragmentList.size()) {
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment targetFg = fragmentList.get(position);
        Fragment lastFg = fragmentList.get(mLastFgIndex);
        mLastFgIndex = position;
        ft.hide(lastFg);
        if (!targetFg.isAdded()) {
            ft.add(R.id.main_root_view, targetFg);
        }
        ft.show(targetFg);
        ft.commitAllowingStateLoss();
        targetFg.setUserVisibleHint(true);
    }

    /**
     *
     * 获取当前手机储存信息
     * */
    public void getSDCardInfo(){
        if (Environment.isExternalStorageEmulated()){
            LogUtil.e("getExternalCacheDir  getParentFile3",
                    BaseApplication.getInstance().getExternalCacheDir().getParentFile().getParentFile().getParent(),
                    Environment.getExternalStorageDirectory().getPath());
        }


        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT){
            File [] files = getExternalFilesDirs(Environment.MEDIA_MOUNTED);
            Observable.fromArray(files).subscribe(new Consumer<File>() {
                @Override
                public void accept(File file) throws Exception {
                    LogUtil.e("输出file信息",file.getName());
                }
            });
        }else {
          LogUtil.e("当前手机版本",Build.VERSION.SDK_INT);
        }

    }
    /**
     * 检查权限
     */
    private void checkPermission() {
        if (hasPermission(Manifest.permission.CHANGE_NETWORK_STATE,
                Manifest.permission.WRITE_SETTINGS)) {
//            getAppVersion();
        }
    }

    @Override
    protected String[] getPermission() {
        LogUtil.e("main","子类的 权限列表");
        return new String[]{
                Manifest.permission.CHANGE_NETWORK_STATE,
                Manifest.permission.WRITE_SETTINGS
        };
    }


    @Override
    protected void onPermissionsResult(String[] parms, boolean hasPermission) {
        super.onPermissionsResult(parms, hasPermission);
        LogUtil.e("==========" + hasPermission);
        if (hasPermission) {
//            getAppVersion();
        } else {
            requestPermission();
        }
    }


}

package soyouarehere.imwork.speed.pager.mine.download;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.base.mvp.BaseActivity;
import soyouarehere.imwork.speed.app.base.mvp.BaseFragment;
import soyouarehere.imwork.speed.pager.mine.download.all.AllFragment;
import soyouarehere.imwork.speed.pager.mine.download.complete.CompleteFragment;
import soyouarehere.imwork.speed.pager.mine.download.downloading.DownloadIngFragment;
import soyouarehere.imwork.speed.pager.mine.download.history.HistoryFragment;
import soyouarehere.imwork.speed.pager.mine.download.newtask.NewTaskConnectActivity;
import soyouarehere.imwork.speed.pager.mine.download.thread.DownLoadObserver;
import soyouarehere.imwork.speed.pager.mine.download.thread.DownloadInfo;
import soyouarehere.imwork.speed.pager.mine.download.thread.DownloadThreadManager;
import soyouarehere.imwork.speed.util.log.LogUtil;

/**
 * @desc  下载界面的activity  里面嵌套4个fragment  全部 下载中 下载完成 收藏/历史
 * @author li.xiaodong
 * @time 2018/7/30 15:04
 */
public class DownloadActivity extends BaseActivity {
    @BindView(R.id.tl_manager)
    public TabLayout tl_manager;

    @BindView(R.id.vp_manager)
    public ViewPager vp_manager;


    List<BaseFragment> fragmentList;
    List<String> stringListTitle;
    @Override
    public void DataLoadError() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_download;
    }

    @Override
    public void create(Bundle savedInstanceState) {
        initTabView();
        initFragment();
        initViewPager();
    }

    private void initViewPager() {
        ManagerViewPagerAdapter viewPagerAdapter = new ManagerViewPagerAdapter(getSupportFragmentManager(), fragmentList, stringListTitle);
        vp_manager.setAdapter(viewPagerAdapter);
    }
    AllFragment allFragment;
    DownloadIngFragment downloadIngFragment;
    CompleteFragment completeFragment;
    HistoryFragment historyFragment;
    private void initFragment() {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
            allFragment = AllFragment.newInstance("AllFragment", "AllFragment");
            downloadIngFragment = DownloadIngFragment.newInstance("DownloadFragment", "DownloadFragment");
            completeFragment = CompleteFragment.newInstance("CompleteFragment", "CompleteFragment");
            historyFragment = HistoryFragment.newInstance("HistoryFragment", "HistoryFragment");
            fragmentList.add(historyFragment);
            fragmentList.add(allFragment);
            fragmentList.add(downloadIngFragment);
            fragmentList.add(completeFragment);
        }
    }

    private void initTabView() {
        if (stringListTitle == null) {
            stringListTitle = new ArrayList<>();
            stringListTitle.add("全部");
            stringListTitle.add("下载中");
            stringListTitle.add("已完成");
            stringListTitle.add("收藏/历史");
        }
        tl_manager.addTab(tl_manager.newTab().setText(stringListTitle.get(0)));
        tl_manager.addTab(tl_manager.newTab().setText(stringListTitle.get(1)));
        tl_manager.addTab(tl_manager.newTab().setText(stringListTitle.get(2)));
        tl_manager.addTab(tl_manager.newTab().setText(stringListTitle.get(3)));
        tl_manager.setupWithViewPager(vp_manager);
    }
    @Override
    protected String setTvRightText() {
        return "+";
    }

    @Override
    protected boolean setTvRightVisible() {
        return true;
    }

    @Override
    protected void tvRightClick() {
        super.tvRightClick();
        ArrayList<String> options1Items = new ArrayList<>();
        options1Items.add("二维码下载");
        options1Items.add("新建下载链接");
        options1Items.add("新建BT任务");
        new CustomBottomDialog(this, options1Items, true, true, new CustomBottomDialog.OnclickItemListener() {
            @Override
            public void clickCallBack(int position) {
                Toast.makeText(mContext,"你选中了第"+position,Toast.LENGTH_SHORT).show();
                if (position==1){
                    Map<String,String> map = new HashMap<>();
                    launchStartActivityForResult(DownloadActivity.this,NewTaskConnectActivity.class,false,map,226);
                }
            }
        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==226){
            if (resultCode==261){
                String url = data.getStringExtra("url");
                LogUtil.e(url);
                startDownloadFile(url);
            }
        }
    }

    public void startDownloadFile(String url){
        DownloadThreadManager.getInstance().download(url, new DownLoadObserver() {
            @Override
            public void onComplete() {
                LogUtil.e("下载完成");

            }

            @Override
            public void onNext(DownloadInfo downloadInfo) {
                LogUtil.e("下载中"+downloadInfo.getProgress()/downloadInfo.getTotal());
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("下载错误"+e.getMessage());
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

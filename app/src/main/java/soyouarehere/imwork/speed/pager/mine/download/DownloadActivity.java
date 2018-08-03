package soyouarehere.imwork.speed.pager.mine.download;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.BaseApplication;
import soyouarehere.imwork.speed.app.base.mvp.BaseActivity;
import soyouarehere.imwork.speed.app.base.mvp.BaseFragment;
import soyouarehere.imwork.speed.pager.mine.download.all.AllFragment;
import soyouarehere.imwork.speed.pager.mine.download.complete.CompleteFragment;
import soyouarehere.imwork.speed.pager.mine.download.downloading.DownloadIngFragment;
import soyouarehere.imwork.speed.pager.mine.download.history.HistoryFragment;
import soyouarehere.imwork.speed.pager.mine.download.newtask.NewTaskConnectActivity;
import soyouarehere.imwork.speed.pager.mine.download.task.DownloadFileInfo;
import soyouarehere.imwork.speed.pager.mine.download.task.TaskCallBack;
import soyouarehere.imwork.speed.pager.mine.download.task.TaskRunnable;
import soyouarehere.imwork.speed.pager.mine.download.thread.DownLoadObserver;
import soyouarehere.imwork.speed.pager.mine.download.thread.DownloadInfo;
import soyouarehere.imwork.speed.pager.mine.download.thread.DownloadThreadManager;
import soyouarehere.imwork.speed.util.FileSizeUtil;
import soyouarehere.imwork.speed.util.FileUtil;
import soyouarehere.imwork.speed.util.UrlUtils;
import soyouarehere.imwork.speed.util.log.LogUtil;

/**
 * @author li.xiaodong
 * @desc 下载界面的activity  里面嵌套4个fragment  全部 下载中 下载完成 收藏/历史
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

    MyHandler myHandler;

    @Override
    public void create(Bundle savedInstanceState) {
        initTabView();
        initFragment();
        initViewPager();
        myHandler = new MyHandler(this);
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
            fragmentList.add(allFragment);
            fragmentList.add(downloadIngFragment);
            fragmentList.add(completeFragment);
            fragmentList.add(historyFragment);
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
                Toast.makeText(mContext, "你选中了第" + position, Toast.LENGTH_SHORT).show();
                if (position == 1) {
                    Map<String, String> map = new HashMap<>();
                    launchStartActivityForResult(DownloadActivity.this, NewTaskConnectActivity.class, false, map, 226);
                }
            }
        }).show();
    }

    public static class MyHandler extends Handler {
        private WeakReference<Activity> activityWeakReference;

        public MyHandler(Activity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Activity activity = activityWeakReference.get();
            if (activity == null) {
                return;
            }
            switch (msg.what) {
                case 360:
                    LogUtil.e("下载进度" + msg.obj.toString());
                    break;
                case 361:
                    LogUtil.e("下载完成" + msg.obj.toString());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 226) {
            if (resultCode == 261) {
                String url = data.getStringExtra("url");
                DownloadFileInfo downloadFileInfo = new Gson().fromJson(data.getStringExtra("DownloadFileInfo"),DownloadFileInfo.class);
                LogUtil.e("传过来的下载信息",downloadFileInfo.toString());
                // testDownloadFile(url,myHandler);//http://192.168.22.30:8080/static/file/download/lxd.jpg
                //  //http://192.168.22.30:8080/static/file/video/我不是药神纪录片.mp4
                executorRunable(downloadFileInfo);
            }
        }
    }
    public void executorRunable( DownloadFileInfo fileInfo) {
        new Thread(new TaskRunnable(fileInfo, new TaskCallBack() {
            @Override
            public void progress(DownloadFileInfo info) {
                LogUtil.e("进度" + info.getShowProgress() +"当前文件大小"+info.getShowProgressSize()+"文件总大小"+info.getShowSize());
            }

            @Override
            public void finish(DownloadFileInfo info) {
                LogUtil.e("下载完成" + info.toString());
            }

            @Override
            public void fail(String msg) {
                LogUtil.e("下载失败" + msg);
            }
        })).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myHandler!=null){
            myHandler.removeCallbacksAndMessages(null);
        }
    }
}

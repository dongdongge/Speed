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
                LogUtil.e(url);
//                startDownloadFile(url);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
//                        testDownloadFile(url,myHandler);//http://192.168.22.30:8080/static/file/download/lxd.jpg
                            //  //http://192.168.22.30:8080/static/file/video/我不是药神纪录片.mp4
                            executorRunable(url);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }

    public void startDownloadFile(String url) {
        DownloadThreadManager.getInstance().download(url, new DownLoadObserver() {
            @Override
            public void onComplete() {
                LogUtil.e("下载完成");

            }

            @Override
            public void onNext(DownloadInfo downloadInfo) {
                LogUtil.e("下载中" + downloadInfo.getProgress() / downloadInfo.getTotal());
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("下载错误" + e.getMessage());
            }
        });


    }

    public void testDownloadFile(String urlPath, Handler handler) throws Exception {
        String _filePath = BaseApplication.getInstance().getCacheDir().getPath();
        LogUtil.e("创建文件地址...", _filePath);

        String substringFileName = "";
        if (urlPath.contains("?")) {
            String tempUrl = urlPath.substring(0, urlPath.indexOf("?"));
            if (tempUrl.contains("/")) {
                substringFileName = tempUrl.substring(tempUrl.lastIndexOf("/"), tempUrl.length()).replaceAll("/", "").trim();
            }
        }

        File _file = new File(_filePath, substringFileName);
        if (_file.exists()) {
            _file.delete();
        }
        _file.createNewFile();
        LogUtil.e("创建文件成功...", _file.getAbsolutePath());
        URL url = new URL(urlPath);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.connect();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.connect();
        int httpStatus = 200;
        if (connection.getResponseCode() == httpStatus) {
            LogUtil.e("开始下载");
            // 文件大小
            long contentLength = connection.getContentLength();
            //文件 原始大小
            String showFileSize = FileSizeUtil.FormetFileSize(contentLength);
            LogUtil.e("文件大小" + "原始大小：" + contentLength + "\n 换算MB" + showFileSize);

            FileOutputStream fos = new FileOutputStream(_file);
            InputStream is = connection.getInputStream();
            int len = -1;
            byte[] bytes = new byte[4 * 1024];
            NumberFormat numberFormat = NumberFormat.getPercentInstance();
            numberFormat.setMinimumFractionDigits(2);
            long total = 0;
            while ((len = is.read(bytes)) != -1) {
                total += len;
                fos.write(bytes, 0, len);
                String result = numberFormat.format((float)total/contentLength);
                Message message = new Message();
                message.what = 360;
                message.obj = result;
                handler.sendMessage(message);
            }
            fos.flush();
            fos.close();
            is.close();
            connection.disconnect();
            Message message = new Message();
            message.what = 361;
            message.obj = "下载完成";
            handler.sendMessage(message);
        } else {
            LogUtil.e(connection.getResponseCode());
        }

    }

    public void formatHeadUrl(String urlString) {
        if (urlString.contains("?")) {
            String heardString = urlString.substring(urlString.indexOf("?"), urlString.length());
            String[] stringsKey = heardString.split("&");
            List<Map<String, String>> list = new ArrayList<>();
            for (String keyValue : stringsKey) {
                Map<String, String> map = new HashMap();
                String keyValueS[] = keyValue.split("=");
                map.put(keyValueS[0], keyValueS[1]);
                list.add(map);
            }
        }
    }

    public void executorRunable(String url){
        // 检查url
        DownloadFileInfo fileInfo = checkUrl(url);
        // 检查文件路径是否存在该文件-->不存在,创建并初始化,存在读取数据设置info
        //
        new Thread(new TaskRunnable(createDownInfo(url), new TaskCallBack() {
            @Override
            public void progress(DownloadFileInfo info) {
                LogUtil.e("进度"+info.getProgress());
            }

            @Override
            public void finish(DownloadFileInfo info) {
                LogUtil.e("下载完成"+info.toString());
            }

            @Override
            public void fail(String msg) {

            }
        })).start();
    }

    private DownloadFileInfo checkUrl(String url) {



        return null;
    }

    public DownloadFileInfo createDownInfo(String downloadUrl){
        DownloadFileInfo downloadFileInfo = new DownloadFileInfo();
        long total = getContentLength(downloadUrl);
        downloadFileInfo.setTotal(total);
        downloadFileInfo.setUrl(downloadUrl);
        downloadFileInfo.setProgress(0);
        String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
        downloadFileInfo.setFileName(fileName);
        LogUtil.e("创建DownInfo"+downloadFileInfo.toString());
        return downloadFileInfo;
    }
    /**
     * 获取下载长度
     *
     * @param downloadUrl
     * @return
     */
    private long getContentLength(String downloadUrl) {
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        OkHttpClient client = new OkHttpClient.Builder().build();
        try {
            Response response = client.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                long contentLength = response.body().contentLength();
                response.close();
                return contentLength == 0 ? -1 : contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacksAndMessages(null);
    }
}

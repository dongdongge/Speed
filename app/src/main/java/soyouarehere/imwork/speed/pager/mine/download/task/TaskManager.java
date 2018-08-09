package soyouarehere.imwork.speed.pager.mine.download.task;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import soyouarehere.imwork.speed.app.BaseApplication;
import soyouarehere.imwork.speed.util.FileSizeUtil;
import soyouarehere.imwork.speed.util.FileUtil;
import soyouarehere.imwork.speed.util.UrlUtils;
import soyouarehere.imwork.speed.util.log.LogUtil;

/**
 * Created by li.xiaodong on 2018/8/7.
 */

public class TaskManager {

    private OkHttpClient mClient;//OKHttpClient;

    public TaskManager() {
        mClient = new OkHttpClient.Builder().build();
    }

    public static TaskManager getInstance() {
        return TaskManagerHelp.INSTANCE;
    }

    private static class TaskManagerHelp {
        private static final TaskManager INSTANCE = new TaskManager();
    }

    /**
     * 执行下载任务,
     */
    public  void checkDownUrl(String urlString, CheckUrlCallBack checkUrlCallBack) {
        if (!UrlUtils.checkUrl(urlString)) {
            LogUtil.e("检查url合法失败", urlString);
            checkUrlCallBack.fail("检查url合法失败");
            return;
        }
        //创建FileName
        String fileName = UrlUtils.getFileNameFromUrl(urlString);
        if (fileName == null) {
            LogUtil.e("根据url创建文件名字失败");
            checkUrlCallBack.fail("根据url创建文件名字失败");
            return;
        }
        createNewFile(urlString, fileName, checkUrlCallBack);
    }

    private void createNewFile(String url, String fileName, CheckUrlCallBack checkUrlCallBack) {
        DownloadFileInfo fileInfo = new DownloadFileInfo(url);
        fileInfo.setFileName(fileName);
        //创建文件名 检测文件夹中是否存在该文件，
        File fileAdress = BaseApplication.getInstance().getExternalCacheDir();
//        Map<String, String> fileMap = FileUtil.findAllFiles(fileAdress.getPath());
        File file = new File(fileAdress, fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            LogUtil.e("创建文件失败");
            checkUrlCallBack.fail("创建文件失败");
            e.printStackTrace();
        }
        // 不存在就是新的文件
        fileInfo.setFilePath(fileAdress.getPath());
        fileInfo.setProgress(0);
        fileInfo.setShowProgressSize("0B");
        fileInfo.setShowProgress("0");
        String[] strings = getContentLength(fileInfo.getUrl());
        long fileLength = Long.parseLong(strings[0]);
        if (fileLength == -1) {
            LogUtil.e("获取文件大小失败");
            checkUrlCallBack.fail("获取文件大小失败"+strings[1]+strings[2]);
            return;
        }
        fileInfo.setTotal(fileLength);
        fileInfo.setShowSize(FileSizeUtil.FormetFileSize(fileLength));
        checkUrlCallBack.onSuccess(fileInfo);
    }

    /**
     * 获取下载长度
     *
     * @param downloadUrl
     * @return
     */
    private String[] getContentLength(String downloadUrl) {
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        OkHttpClient client = new OkHttpClient.Builder().build();
        try {
            Response response = client.newCall(request).execute();
            if (response != null && response.code() == 200) {
                long contentLength = response.body().contentLength();
                LogUtil.e("获取文件大小", contentLength);
                response.close();
                return contentLength == 0 ? new String[]{"-1","contentLength为零","未知"} : new String[]{String.valueOf(contentLength)};
            } else {
                LogUtil.e("code", response.code(), response.body().string());
                return new String[]{"-1", String.valueOf(response.code()),response.body().toString()};
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("IOException", e.getMessage());
            return new String[]{"-1","未知","失败"};
        }
    }

    public interface CheckUrlCallBack {
        void onSuccess(DownloadFileInfo fileInfo);

        void fail(String msg);
    }
}

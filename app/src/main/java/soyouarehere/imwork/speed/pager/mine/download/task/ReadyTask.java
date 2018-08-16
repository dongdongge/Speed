package soyouarehere.imwork.speed.pager.mine.download.task;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import soyouarehere.imwork.speed.app.BaseApplication;
import soyouarehere.imwork.speed.pager.mine.download.task.bean.DownloadFileInfo;
import soyouarehere.imwork.speed.util.FileSizeUtil;
import soyouarehere.imwork.speed.util.UrlUtils;
import soyouarehere.imwork.speed.util.log.LogUtil;

/**
 * 下载前的准备任务
 */
public class ReadyTask implements Runnable {
    private String url;
    private TaskManager.PrepareCallBack prepareCallBack;

    public ReadyTask(String url, TaskManager.PrepareCallBack prepareCallBack) {
        this.url = url;
        this.prepareCallBack = prepareCallBack;
    }

    @Override
    public void run() {
        checkDownUrl();
    }

    /**
     * 执行下载任务,
     */
    private void checkDownUrl() {
        if (!UrlUtils.checkUrl(url)) {
            LogUtil.e("检查url合法失败", url);
            prepareCallBack.fail("检查url合法失败");
            return;
        }
        //创建FileName
        String fileName = UrlUtils.getFileNameFromUrl(url);
        if (fileName == null) {
            LogUtil.e("根据url创建文件名字失败");
            prepareCallBack.fail("根据url创建文件名字失败");
            return;
        }
        createNewFile(url, fileName, prepareCallBack);
    }

    private void createNewFile(String url, String fileName, TaskManager.PrepareCallBack prepareCallBack) {
        LogUtil.e("createNewFile");
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
            prepareCallBack.fail("创建文件失败");
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
            prepareCallBack.fail("获取文件大小失败" + strings[1] + strings[2]);
            return;
        }
        fileInfo.setSupportInterrupt(strings[1].equals("1"));
        fileInfo.setTotal(fileLength);
        fileInfo.setShowSize(FileSizeUtil.FormetFileSize(fileLength));
        prepareCallBack.onSuccess(fileInfo);

    }

    /**
     * 获取下载长度
     *
     * @param downloadUrl
     * @return
     */
    private String[] getContentLength(String downloadUrl) {
        LogUtil.e("获取下载长度   下载地址为" + downloadUrl);
        Request request = new Request.Builder()
                .url(downloadUrl)
                .addHeader("content-type", "application/json;charset:utf-8")
                .build();
        OkHttpClient client = new OkHttpClient.Builder().build();
        try {
            Response response = client.newCall(request).execute();
            LogUtil.e("获取下载长度   response" + response.code());
            if (response != null && response.code() == 206) {
                LogUtil.e("获取下载长度   response" + response.code());
                Headers headers = response.headers();
                Map<String, List<String>> listMap = headers.toMultimap();
                long contentLength = 0;
                if (listMap.containsKey("content-range") && listMap.containsKey("accept-ranges") && listMap.containsKey("content-length")) {
                    // 可以断点续传
                    contentLength = Long.parseLong(listMap.get("content-length").get(0));
                } else {
                    contentLength = response.body().contentLength();
                }
                LogUtil.e("获取到的头部信息", listMap.toString());
                LogUtil.e("获取文件大小", contentLength);
                response.close();
                return contentLength == 0 ? new String[]{"-1", "contentLength为零", "未知"} : new String[]{String.valueOf(contentLength), "1"};
            } else {
                LogUtil.e("code", response.code(), response.body().string());
                return new String[]{"-1", String.valueOf(response.code()), response.body().toString()};
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("IOException", e.getMessage());
            return new String[]{"-1", "未知", "失败"};
        }
    }
}

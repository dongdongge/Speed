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
import soyouarehere.imwork.speed.util.PreferenceUtil;
import soyouarehere.imwork.speed.util.UrlUtils;
import soyouarehere.imwork.speed.function.log.LogUtil;

/**
 * 该任务是进行首次下载的任务  即是在数据库中不能查到该任务
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
            prepareCallBack.fail(0,"检查url合法失败");
            return;
        }
        //创建FileName
        String fileName = UrlUtils.getFileNameFromUrl(url);
        if (fileName == null) {
            LogUtil.e("根据url创建文件名字失败");
            prepareCallBack.fail(1,"根据url创建文件名字失败");
            return;
        }
        createNewFile(url, fileName);
    }

    private void createNewFile(String url, String fileName) {
        LogUtil.e("createNewFile");
        DownloadFileInfo fileInfo = new DownloadFileInfo(url);
        fileInfo.setFileName(fileName);
        //创建文件名 检测默认的下载文件夹中是否存在该文件，
        String filePosition =createFile(fileName);
        if (filePosition==null){
            return;
        }
        LogUtil.e("获取文件长度");
        // 不存在就是新的文件
        fileInfo.setFilePath(filePosition);
        fileInfo.setProgress(0);
        fileInfo.setShowProgressSize("0B");
        fileInfo.setShowProgress("0");
        fileInfo = getContentLength(fileInfo);
        if (fileInfo.getTotal() == 0) {
            prepareCallBack.fail(4,"获取文件大小失败");
            return;
        }
        fileInfo.setShowSize(FileSizeUtil.FormetFileSize(fileInfo.getTotal()));
        prepareCallBack.onSuccess(fileInfo);
    }

    //创建文件名 检测默认的下载文件夹中是否存在该文件，
    private String createFile(String fileName){
        String filePosition = PreferenceUtil.getDownloadPotion(BaseApplication.getInstance());
        LogUtil.e("默认的下载文件夹"+filePosition);
        if (PreferenceUtil._isExetisDownloadFile(fileName)){
            prepareCallBack.fail(2,"该任务已经在任务列表");
            return null;
        }
        File file = new File(filePosition, fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            prepareCallBack.fail(3,"创建文件失败");
            e.printStackTrace();
            return null;
        }
        return filePosition;
    }

    /**
     * 获取下载长度 判断服务端是否支持断点续传
     *
     * @param fileInfo
     * @return
     */
    private DownloadFileInfo getContentLength(DownloadFileInfo fileInfo) {
        Request request = new Request.Builder()
                .url(fileInfo.getUrl())
                .build();
        OkHttpClient client = new OkHttpClient.Builder().build();
        try {
            Response response = client.newCall(request).execute();
            if (response != null && response.code() == 206) {
                Headers headers = response.headers();
                Map<String, List<String>> listMap = headers.toMultimap();
                long contentLength = 0;
                if (listMap.containsKey("content-range") && listMap.containsKey("accept-ranges") && listMap.containsKey("content-length")) {
                    // 可以断点续传
                    contentLength = Long.parseLong(listMap.get("content-length").get(0));
                    fileInfo.setSupportInterrupt(true);
                } else {
                    fileInfo.setSupportInterrupt(false);
                    fileInfo.setReasonMsg("服务器不存在断点续传的headers等必要信息");
                    contentLength = response.body().contentLength();
                }
                response.close();
                fileInfo.setTotal(contentLength);
                return fileInfo;
            } else if (response.code() == 200){
                LogUtil.e("code", response.code(), response.body().string());
                fileInfo.setTotal(response.body().contentLength());
                fileInfo.setSupportInterrupt(false);
                fileInfo.setReasonMsg("不支持断点续传功能code码不是206 是200");
                return fileInfo;
            }else {
                LogUtil.e("code", response.code(), response.body().string());
                fileInfo.setTotal(0);
                fileInfo.setReasonMsg("未知错误"+response.code());
                return fileInfo;
            }
        } catch (IOException e) {
            e.printStackTrace();
            fileInfo.setTotal(0);
            fileInfo.setReasonMsg("请求length异常");
            return fileInfo;
        }
    }
}

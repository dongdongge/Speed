package soyouarehere.imwork.speed.pager.mine.download.task.broken;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Call;
import okhttp3.Response;
import soyouarehere.imwork.speed.app.BaseApplication;
import soyouarehere.imwork.speed.pager.mine.download.task.TaskHelp;
import soyouarehere.imwork.speed.pager.mine.download.task.bean.DownloadFileInfo;
import soyouarehere.imwork.speed.pager.mine.download.task.imp.TaskCallBack;
import soyouarehere.imwork.speed.util.CloseUtils;
import soyouarehere.imwork.speed.util.FileSizeUtil;
import soyouarehere.imwork.speed.util.PreferenceUtil;
import soyouarehere.imwork.speed.function.log.LogUtil;

/**
 * 断点续传和非 都可以使用该任务下载 任务类
 * 注意：指的是在文件从0开始下载，在下载过程中暂停 然后恢复下载，期间不能销毁该任务(不能销毁该activity)
 * 如果是销毁了该任务，再次进入到该任务界面时，需要启动ContinueTask 来继续下载任务；
 */
public class BrokenRunnable implements Runnable {
    private DownloadFileInfo info;
    private TaskCallBack callBack;
    private boolean isPause = false;
    private String name;

    public BrokenRunnable(DownloadFileInfo info, TaskCallBack callBack) {
        this.info = info;
        this.callBack = callBack;
        this.name = info.getFileName();
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        LogUtil.e("开始执行断点续传下载工具类");
        File file = TaskHelp.getFile(info.getFilePath(), info.getFileName());
        // 此处要去更新下文件的实际大小  不能以数据库中的大小为准
        if (PreferenceUtil._isExetisDownloadFile(info.getFileName())&&!info.getFileStatue().equals("stop")) {
            LogUtil.e("数据库中已经存在该 任务信息");
            callBack.fail("已经存在该任务");
            return;
        } else {
            info.setProgress(file.length());
            PreferenceUtil.putDownloadFileInfo(BaseApplication.getInstance(), info.getFileName(), info.toString());
            LogUtil.e("数据库中不存在该 任务信息  存放");
        }
        Call call = TaskHelp.getHttpClient().newCall(TaskHelp.getRequest(info));
        LogUtil.e("实例化call 成功");
        InputStream is = null;
        RandomAccessFile randomAccessFile = null;
        try {
            Response response = call.execute();
            if (response.code() == 206 || response.code() == 200) {
                LogUtil.e("开始下载文件", response.code());
                is = response.body().byteStream();
                if (callBack != null) {
                    info.setFileStatue("downloading");
                    PreferenceUtil.putDownloadFileInfo(BaseApplication.getInstance(), info.getFileName(), info.toString());
                    callBack.progress(info);
                }
                randomAccessFile = new RandomAccessFile(file, "rwd");
                randomAccessFile.seek(info.getProgress());
//                randomAccessFile.setLength(info.getTotal());
                // 在此处 进行判断 如果文件大小介于20~100 则每次读取1M 介于
                long readLength = TaskHelp.formatReadFileSize(info.getTotal());
                byte[] buffer = new byte[(int) readLength];
                long startTime = System.currentTimeMillis();
                long startFileSize = info.getProgress();
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    // 写入文件中去
                    randomAccessFile.write(buffer, 0, len);
                    // 设置 实际进度 long
                    info.setProgress(info.getProgress() + len);
                    // 设置 实际进度大小
                    info.setShowProgressSize(FileSizeUtil.FormetFileSize(info.getProgress()));
                    // 设置 展示进度
                    info.setShowProgress(TaskHelp.formatProgress(info.getProgress(), info.getTotal()));
                    if (info.getProgress() == info.getTotal()) {
                        if (callBack != null) {
                            info.setFileStatue("complete");
                            PreferenceUtil.putDownloadFileInfo(BaseApplication.getInstance(), info.getFileName(), info.toString());
                            callBack.finish(info);
                        }
                        return;
                    }
                    if (System.currentTimeMillis() - startTime > 500) {
                        // 时间差 s 为秒 differenceTime   文件差 differenceFileSize  获取结果
                        String[] strings = FileSizeUtil.formatProgressSpeed(info.getProgress() - startFileSize, System.currentTimeMillis() - startTime, info.getTotal() - info.getProgress());
                        info.setDownloadSpeed(strings[0] + strings[1]);
                        if (callBack != null) {
                            callBack.progress(info);
                        }
                        startTime = System.currentTimeMillis();
                        startFileSize = info.getProgress();
                    }
                    if (info.getFileStatue().equals("stop")){
                        LogUtil.e("暂停下载");
                        PreferenceUtil.putDownloadFileInfo(BaseApplication.getInstance(), info.getFileName(), info.toString());
                        callBack.finish(info);
                        return;
                    }
                }
            } else {
                LogUtil.e("code码错误" + response.code());
                if (callBack != null) {
                    info.setFileStatue("stop");
                    PreferenceUtil.putDownloadFileInfo(BaseApplication.getInstance(), info.getFileName(), info.toString());
                    callBack.fail("下载失败" + response.message());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("IO异常" + e.getMessage());
            if (callBack != null) {
                info.setFileStatue("stop");
                PreferenceUtil.putDownloadFileInfo(BaseApplication.getInstance(), info.getFileName(), info.toString());
                callBack.fail("下载失败" + e.getMessage());
            }
        } finally {
            CloseUtils.closeQuietly(is);
            CloseUtils.closeQuietly(randomAccessFile);
        }

    }
}

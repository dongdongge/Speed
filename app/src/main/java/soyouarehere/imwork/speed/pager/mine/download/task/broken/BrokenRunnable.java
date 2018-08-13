package soyouarehere.imwork.speed.pager.mine.download.task.broken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Response;
import soyouarehere.imwork.speed.pager.mine.download.task.TaskHelp;
import soyouarehere.imwork.speed.pager.mine.download.task.bean.DownloadFileInfo;
import soyouarehere.imwork.speed.pager.mine.download.task.imp.TaskCallBack;
import soyouarehere.imwork.speed.util.CloseUtils;
import soyouarehere.imwork.speed.util.FileSizeUtil;

/**
 * 断点续传 任务类
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

    /**
     * 暂停线程
     */
    public synchronized void onThreadPause() {
        isPause = true;
    }

    /**
     * 线程等待,不提供给外部调用
     */
    private void onThreadWait() {
        try {
            synchronized (this) {
                this.wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 线程继续运行
     */
    public synchronized void onThreadResume() {
        isPause = false;
        this.notify();
    }

    /**
     * 关闭线程
     */
    public synchronized void closeThread() {
        try {
            notify();
//            setClose(true);
//            interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Call call = TaskHelp.getHttpClient().newCall(TaskHelp.getRequest(info));
        InputStream is = null;
        FileOutputStream fileOutputStream = null;
        try {
            Response response = call.execute();
            if (response.code() == 206) {
                is = response.body().byteStream();
                File file = TaskHelp.getFile(info.getFileName());
                if (callBack!=null){
                    callBack.finish(info);
                }
                fileOutputStream = new FileOutputStream(file);
                // 在此处 进行判断 如果文件大小介于20~100 则每次读取1M 介于
                long readLength = TaskHelp.formatReadFileSize(info.getTotal());
                byte[] buffer = new byte[(int) readLength];
                int callBackFlag = 0;
                long startTime = 0;
                long startFileSize = info.getProgress();
                int len = 0;

                while ((len = is.read(buffer)) != -1) {
                    if (!isPause){
                        callBackFlag++;
                        if (callBackFlag == 1) {
                            startTime = System.currentTimeMillis();
                            startFileSize = info.getProgress();
                        }
                        // 写入文件中去
                        fileOutputStream.write(buffer, 0, len);
                        // 设置 实际进度 long
                        info.setProgress(info.getProgress() + len);
                        // 设置 实际进度大小
                        info.setShowProgressSize(FileSizeUtil.FormetFileSize(info.getProgress()));
                        // 设置 展示进度
                        info.setShowProgress(TaskHelp.formatProgress(info.getProgress(), info.getTotal()));
                        if (info.getProgress() == info.getTotal()) {
                            if (callBack!=null){
                                callBack.finish(info);
                            }
                        } else if (callBackFlag > 3000) {
                            // 时间差 s 为秒 differenceTime   文件差 differenceFileSize  获取结果
                            String[] strings = FileSizeUtil.formatProgressSpeed(info.getProgress() - startFileSize, System.currentTimeMillis() - startTime, info.getTotal() - info.getProgress());
                            info.setDownloadSpeed(strings[0] + strings[1]);
                            if (callBack!=null){
                                callBack.progress(info);
                            }
                            callBackFlag = 0;
                        }
                        fileOutputStream.flush();
                    }else {
                        onThreadWait();
                    }
                }
            } else {
                if (callBack != null) {
                    callBack.fail("下载失败" + response.message());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (callBack != null) {
                callBack.fail("下载失败" + e.getMessage());
            }
        } finally {
            CloseUtils.closeQuietly(is);
            CloseUtils.closeQuietly(fileOutputStream);
        }

    }
}

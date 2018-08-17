package soyouarehere.imwork.speed.pager.mine.download.task.single;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;

import okhttp3.Call;
import okhttp3.Response;
import soyouarehere.imwork.speed.pager.mine.download.task.imp.TaskCallBack;
import soyouarehere.imwork.speed.pager.mine.download.task.TaskHelp;
import soyouarehere.imwork.speed.pager.mine.download.task.bean.DownloadFileInfo;
import soyouarehere.imwork.speed.util.CloseUtils;
import soyouarehere.imwork.speed.util.FileSizeUtil;

/**
 * Created by li.xiaodong on 2018/8/7.
 */

public class TaskCallableTask implements Callable<String> {
    private DownloadFileInfo info;
    private TaskCallBack callBack;
    private String name;
    public TaskCallableTask(DownloadFileInfo info, TaskCallBack callBack) {
        this.info = info;
        this.callBack = callBack;
        this.name = info.getFileName();
    }
    public String getName(){
        return name;
    }

    @Override
    public String call() throws Exception {
        Call call = TaskHelp.getHttpClient().newCall(TaskHelp.getRequest(info));
        InputStream is = null;
        FileOutputStream fileOutputStream = null;
//        BufferedInputStream bis = null;
//        BufferedOutputStream bos = null;
        try {
            Response response = call.execute();
            if (response.code() == 200) {
                is = response.body().byteStream();
//                bis = new BufferedInputStream(response.body().byteStream());
                File file = TaskHelp.getFile(info.getFilePath(),info.getFileName());
                callBack.finish(info);
                fileOutputStream = new FileOutputStream(file);
//                bos = new BufferedOutputStream(fileOutputStream);
                // 在此处 进行判断 如果文件大小介于20~100 则每次读取1M 介于
                long readLength = TaskHelp.formatReadFileSize(info.getTotal());
                byte[] buffer = new byte[(int) readLength];
                int callBackFlag = 0;
                long startTime = 0;
                long startFileSize = info.getProgress();
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
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
                        callBack.finish(info);
                    } else if (callBackFlag > 3000) {
                        // 时间差 s 为秒 differenceTime   文件差 differenceFileSize  获取结果
                        String[] strings = FileSizeUtil.formatProgressSpeed(info.getProgress() - startFileSize, System.currentTimeMillis() - startTime, info.getTotal() -info.getProgress());
                        info.setDownloadSpeed(strings[0]+strings[1]);
                        callBack.progress(info);
                        callBackFlag = 0;
                    }
                    fileOutputStream.flush();
                }
            } else {
                callBack.fail("下载失败" + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
            callBack.fail("下载失败" + e.getMessage());
        } finally {
            CloseUtils.closeQuietly(is);
            CloseUtils.closeQuietly(fileOutputStream);
        }
        return name;
    }
}

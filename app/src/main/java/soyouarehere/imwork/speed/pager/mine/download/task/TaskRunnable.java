package soyouarehere.imwork.speed.pager.mine.download.task;

import com.facebook.common.file.FileUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import soyouarehere.imwork.speed.app.BaseApplication;
import soyouarehere.imwork.speed.util.BigDecimalUtils;
import soyouarehere.imwork.speed.util.CloseUtils;
import soyouarehere.imwork.speed.util.FileSizeUtil;
import soyouarehere.imwork.speed.util.TimeUtls;
import soyouarehere.imwork.speed.util.log.LogUtil;

/**
 * Created by li.xiaodong on 2018/8/2.
 */

public class TaskRunnable implements Runnable {
    private DownloadFileInfo info;
    private TaskCallBack callBack;

    public TaskRunnable(DownloadFileInfo info, TaskCallBack callBack) {
        this.info = info;
        this.callBack = callBack;
    }

    @Override
    public void run() {
        Request request = new Request.Builder()
                .addHeader("RANGE", "bytes=" + info.getProgress() + "-" + info.getTotal())
                .url(info.getUrl())
                .build();
        Call call = getHttpClient().newCall(request);
        InputStream is = null;
        FileOutputStream fileOutputStream = null;
//        BufferedInputStream bis = null;
//        BufferedOutputStream bos = null;
        try {
            Response response = call.execute();
            if (response.code() == 200) {
                is = response.body().byteStream();
//                bis = new BufferedInputStream(response.body().byteStream());
                File file = new File(BaseApplication.getInstance().getExternalCacheDir(), info.getFileName());
                if (!file.exists()) {
                    file.createNewFile();
                }
                callBack.finish(info);
                fileOutputStream = new FileOutputStream(file);
//                bos = new BufferedOutputStream(fileOutputStream);
                int len = 0;
                // 在此处 进行判断 如果文件大小介于20~100 则每次读取1M 介于
                long readLength = formatReadFileSize(info.getTotal());
                byte[] buffer = new byte[(int) readLength];
                int callBackFlag = 0;
                long startTime = 0;
                long startFileSize = info.getProgress();
                while ((len = is.read(buffer)) != -1) {
                    callBackFlag++;
                    if (callBackFlag == 1) {
                        startTime = System.currentTimeMillis();
                        startFileSize = info.getProgress();
                    }
                    fileOutputStream.write(buffer, 0, len);
                    info.setProgress(info.getProgress() + len);
                    info.setShowProgressSize(FileSizeUtil.FormetFileSize(info.getProgress()));
                    String tempShowProgress = formatProgress(info.getProgress(), info.getTotal());
                    info.setShowProgress(tempShowProgress);
                    if (info.getProgress() == info.getTotal()) {
                        callBack.finish(info);
                    } else if (callBackFlag > 3000) {
                        // 时间差 s 为秒
                        long differenceTime = (System.currentTimeMillis() - startTime);
                        // 文件差
                        long differenceFileSize = (info.getProgress() - startFileSize);
                        String[] strings = FileSizeUtil.formatProgressSpeed(differenceFileSize, differenceTime, info.getTotal() -info.getProgress());
                        info.setDownloadSpeed(strings[0]+strings[1]);
                        LogUtil.e("strings",info.getShowProgress());
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
        } finally {
            CloseUtils.closeQuietly(is);
            CloseUtils.closeQuietly(fileOutputStream);
        }
    }

    public OkHttpClient getHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        return client;
    }

    public static String formatProgress(long long1, long total) {
        if (long1 >= total) {
            return "100%";
        }
        if (long1 == 0 || total == 0) {
            return "0%";
        }
        NumberFormat nf   =   NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(2);
        return nf.format((double) long1/total);
    }
    private static long formatReadFileSize(long fileLength) {
        long read4KB = 4 * 1024;// 每次读取4KB
        long read8KB = 8 * 1024;// 每次读取8KB
        if (fileLength >= 1024 * 1024) {
            return read8KB;
        }
        return read4KB;
    }


}

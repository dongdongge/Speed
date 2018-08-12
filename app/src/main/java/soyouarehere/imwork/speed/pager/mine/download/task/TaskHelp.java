package soyouarehere.imwork.speed.pager.mine.download.task;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import soyouarehere.imwork.speed.app.BaseApplication;
import soyouarehere.imwork.speed.pager.mine.download.task.bean.DownloadFileInfo;

public class TaskHelp {
    public static File getFile(String fileName) {
        File file = new File(BaseApplication.getInstance().getExternalCacheDir(), fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static Request getRequest(DownloadFileInfo fileInfo) {
        return new Request.Builder()
                .addHeader("RANGE", "bytes=" + fileInfo.getProgress() + "-" + fileInfo.getTotal())
                .url(fileInfo.getUrl())
                .build();
    }

    public static OkHttpClient getHttpClient() {
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
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(2);
        return nf.format((double) long1 / total);
    }

    public static long formatReadFileSize(long fileLength) {
        long read4KB = 4 * 1024;// 每次读取4KB
        long read8KB = 8 * 1024;// 每次读取8KB
        if (fileLength >= 1024 * 1024) {
            return read8KB;
        }
        return read4KB;
    }

}

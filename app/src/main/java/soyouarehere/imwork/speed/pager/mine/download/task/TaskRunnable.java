package soyouarehere.imwork.speed.pager.mine.download.task;

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
import soyouarehere.imwork.speed.util.FileSizeUtil;

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
        try {
            Response response = call.execute();
            if (response.isSuccessful()){
                InputStream is = response.body().byteStream();
                File file = new File(BaseApplication.getInstance().getExternalCacheDir(),info.getFileName());
                if (!file.exists()){
                    file.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                int len = 0;
                byte[] buffer = new byte[4*1024];
                while ((len=is.read(buffer))!=-1){
                    fileOutputStream.write(buffer,0,len);
                    info.setProgress(info.getProgress()+len);
                    info.setShowProgressSize(FileSizeUtil.FormetFileSize(info.getProgress()));
                    info.setShowProgress(formatProgress(info.getProgress(),info.getTotal()));
                    callBack.progress(info);
                }
                callBack.finish(info);
            }else{
                callBack.fail("下载失败"+response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public OkHttpClient getHttpClient(){
        OkHttpClient client = new OkHttpClient.Builder().build();
        return client;
    }

    public static String formatProgress(long long1,long total){
        if (long1>=total){
            return "100%";
        }
        if (long1==0||total==0){
            return "0%";
        }
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat.format((double)long1/total);
    }


}

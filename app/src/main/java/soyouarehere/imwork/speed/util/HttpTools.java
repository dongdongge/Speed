package soyouarehere.imwork.speed.util;

import android.os.Handler;
import android.os.Message;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpTools {
    public static HttpTools getINSTANCE(){
        return HttpToolsHelp.httpTools;
    }

    private static class HttpToolsHelp{
     public static final  HttpTools httpTools = new HttpTools();
    }


    /**
     * HttpURLConnection method Get
     * params{
     * url : 服务端地址；
     * params： 参数；
     * handler : 回调结果；
     * }
     */
    public static String getMethod(String baseUrl) throws Exception {
        URL url = new URL(baseUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        if (httpURLConnection.getResponseCode() == 200) {
            InputStream bs = httpURLConnection.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = bs.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
                outputStream.flush();
            }
            return outputStream.toString("utf-8");
        }
        return "-1";
    }

    public  void postMethod(final String baseUrl, final Map<String, String> params, final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(baseUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
                    printWriter.print(JsonParseTools.mapParseJsonString(params));
                    printWriter.flush();
                    if (httpURLConnection.getResponseCode() == 200) {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        InputStream inputStream = httpURLConnection.getInputStream();
                        byte[] buffer = new byte[1024];
                        int len = 0;
                        while ((len = inputStream.read(buffer)) != -1) {
                            byteArrayOutputStream.write(buffer, 0, len);
                            byteArrayOutputStream.flush();
                        }
                        byteArrayOutputStream.close();
                        String result = byteArrayOutputStream.toString("utf-8");
                        Message message = new Message();
                        message.what = 200;
                        message.obj = result;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}

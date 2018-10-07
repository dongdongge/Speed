package soyouarehere.imwork.speed.function.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUrlConnectionUtils {


    public static void get(String url,Map<String,String>headerMap,CallBack callBack){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String s = HttpGetRequest(url,headerMap);
                callBack.success(s);
            }
        }).start();
    }

    public static void post(){

    }
    public static String HttpGetRequest(String url,Map<String,String>headerMap) {
        String result = "";
        try {
            //1 得到访问地址
            URL url1 = new URL(url);
            //2 得到网络访问对象 java.net.HttpUrlConnection
            HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
            //3 设置请求参数 （过期时间，输入，输出流。访问方式）
            //设置是否向HttpURLConnection输出
            httpURLConnection.setDoOutput(false);
            //设置是否向HttpURLConnection读入
            httpURLConnection.setDoInput(true);
            if(headerMap != null){
                setHeader(httpURLConnection,headerMap);
            }
            //设置请求方式
            httpURLConnection.setRequestMethod("GET");
            //设置是否使用缓存
            httpURLConnection.setUseCaches(true);
            //设置超时时间
            httpURLConnection.setConnectTimeout(3000);
            //连接
            httpURLConnection.connect();
            //4 得到响应状态码的返回值responseCode；
            int code = httpURLConnection.getResponseCode();
            //5 如果返回值正常，数据在网络中是以流的形式得到服务端返回的数据
            if (code == 200) {
//                result = readHttpTypeStringFromInputStream(httpURLConnection.getInputStream());
                result = readHttpTypeByteFromInputStream(httpURLConnection.getInputStream());
            }
            httpURLConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("lxd:\n" + result);
        return result;
    }
    /**
     * 设置请求头
     */
    private static void setHeader(HttpURLConnection conn, Map<String, String> headerMap) {
        if(headerMap != null){
            for (String key: headerMap.keySet()){
                conn.setRequestProperty(key, headerMap.get(key));
            }
        }
    }
    /**
     * description 该方法是字节为单位读取io流中的数据将其转化为String返回
     */
    public static String readHttpTypeByteFromInputStream(InputStream inputStream) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        byte[] bytes = new byte[1024];
        int length = -1;
        while ((length = inputStream.read(bytes)) != -1) {
            stringBuffer.append(new String(bytes, 0, length, "utf-8"));
        }
        inputStream.close();
        return stringBuffer.toString();
    }

    public interface CallBack{
        void success(String content);
        void fail(String msg);
    }

}

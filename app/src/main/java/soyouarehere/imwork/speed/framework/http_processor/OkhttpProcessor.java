package soyouarehere.imwork.speed.framework.http_processor;

import android.os.Handler;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpProcessor implements IHttpProcessor {


    private OkHttpClient mOkHttpClient;

    private Handler mHandler;

    public OkhttpProcessor() {
        mOkHttpClient = new OkHttpClient();
        mHandler = new Handler();
    }

    /**
     * 格式化参数
     *
     * @param params
     * @return
     */
    private RequestBody appendBody(Map<String, Object> params) {
        FormBody.Builder body = new FormBody.Builder();
        if (params == null || params.isEmpty()) {
            return body.build();
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            body.add(entry.getKey(), entry.getValue().toString());
        }

        return body.build();
    }

    @Override
    public void post(String url, Map<String, Object> params, ICallBack callBack) {
        RequestBody requestBody = appendBody(params);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure(e.toString(), "");
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            String result = null;
                            try {
                                result = response.body().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            callBack.onSuccess(result);
                        } else {
                            callBack.onFailure(response.message().toString(), "");
                        }
                    }
                });
            }
        });
    }


    @Override
    public void get(String url, Map<String, Object> params, ICallBack callBack) {

    }
}

package soyouarehere.imwork.speed.framework.http_processor;

import java.util.Map;

public interface IHttpProcessor {
    // 网络层访问 POST GET DEl UPDATE PUT

    /**
     *
     * @param url
     * @param params
     * @param callBack
     */
    void post(String url, Map<String,Object> params,ICallBack callBack);

    /**
     *
     * @param url
     * @param params
     * @param callBack
     */
    void get(String url, Map<String,Object> params,ICallBack callBack);
}

package soyouarehere.imwork.speed.framework.http_processor;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import soyouarehere.imwork.speed.function.log.LogUtil;

/**
 * 代理类  Proxy  拥有RealSubject对象 IHttpProcessor 其子类已经
 * 实现了 接口
 * 代理 交给真正工作的
 */
public class HttpHelper implements IHttpProcessor {

    private static IHttpProcessor mIHttpProcessor = null;
    private Map<String, Object> mParams;

    public HttpHelper() {
        mParams = new HashMap<>();
    }

    public static HttpHelper getInstance() {
        return Help.httpHelper;
    }

    public static void init(IHttpProcessor httpProcessor) {
        mIHttpProcessor = httpProcessor;
    }

    @Override
    public void post(String url, Map<String, Object> params, ICallBack callBack) {
        final String finalUrl = appendParams(url, params);
        mIHttpProcessor.post(finalUrl, params, callBack);
    }


    @Override
    public void get(String url, Map<String, Object> params, ICallBack callBack) {
        final String finalUrl = appendParams(url, params);
        mIHttpProcessor.post(finalUrl, params, callBack);
    }

    /**
     * 格式化请求url和参数
     *
     * @param url
     * @param params
     * @return
     */
    private String appendParams(String url, Map<String, Object> params) {
        if (TextUtils.isEmpty(url)) {
            throw new RuntimeException("url 为空");
        }
        if (params == null || params.isEmpty()) {
            return url;
        }
        StringBuilder urlBuilder = new StringBuilder(url);
        if (urlBuilder.indexOf("?") <= 0) {
            urlBuilder.append("?");
        } else {
            if (!urlBuilder.toString().endsWith("?")) {
                urlBuilder.append("&");
            }
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            urlBuilder.append(encode(entry.getKey())).append("=").append(encode(entry.getValue().toString()));
        }
        return urlBuilder.toString();
    }

    /**
     * 单例
     */
    private static class Help {
        private static final HttpHelper httpHelper = new HttpHelper();
    }


    /**
     * url 不允许为空的字符串，如果参数有空格，需要格式化
     *
     * @param str
     * @return
     */
    private static String encode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LogUtil.e("格式化参数异常");
            throw new RuntimeException(e);
        }
    }


}

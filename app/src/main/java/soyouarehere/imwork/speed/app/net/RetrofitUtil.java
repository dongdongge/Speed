package soyouarehere.imwork.speed.app.net;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import soyouarehere.imwork.speed.app.config.Config;
import soyouarehere.imwork.speed.util.log.LogUtil;

/**
 * retrofit
 * Created by Administrator on 2017/8/24 0024.
 */

public class RetrofitUtil {

    public static volatile RetrofitUtil retrofitUtil;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .client(OkHttpUtils.getInstance())
            .addConverterFactory(RsaGsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    public RetrofitUtil() {
    }

    public <T> T getApi(Class<T> clazz) {
        return retrofit.create(clazz);
    }

    /**
     * 服务接口单例
     *
     * @return Retrofit
     */
    public static synchronized RetrofitUtil build() {
        if (retrofitUtil == null) {//双重检测同步延迟加载
            synchronized (RetrofitUtil.class) {
                if (retrofitUtil == null) {
                    retrofitUtil = new RetrofitUtil();
                }
            }
        }
        return retrofitUtil;
    }


    /**
     * 获取URL  根据版本切换不同版本
     *
     * @return
     */
    public static String getBaseURL() {
        if (LogUtil.isDebug) {
            return "http://192.168.22.68/";
        } else {
            return "http://192.168.22.20:8083/";//正式
        }
    }
}

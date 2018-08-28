package soyouarehere.imwork.speed.app.net;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import soyouarehere.imwork.speed.app.BaseApplication;
import soyouarehere.imwork.speed.app.config.Config;
import soyouarehere.imwork.speed.util.log.LogUtil;

/**
 * retrofit
 */

public class RetrofitUtil {

    public static volatile RetrofitUtil retrofitUtil;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseApplication.BASE_URL)
            .client(OkHttpUtils.getInstance())
            /*支持String转换*/
            .addConverterFactory(StringConverterFactory.create())
            // GsonConverterFactory.create()
            .addConverterFactory(RsaGsonConverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
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
        //双重检测同步延迟加载
        if (retrofitUtil == null) {
            synchronized (RetrofitUtil.class) {
                if (retrofitUtil == null) {
                    retrofitUtil = new RetrofitUtil();
                }
            }
        }
        return retrofitUtil;
    }

}

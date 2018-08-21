package soyouarehere.imwork.speed.app;

import android.app.Application;

import soyouarehere.imwork.speed.util.FileUtil;
import soyouarehere.imwork.speed.util.PreferenceUtil;
import soyouarehere.imwork.speed.util.exception.CrashHandler;
import soyouarehere.imwork.speed.util.log.LogUtil;

public class BaseApplication extends Application {

    public static BaseApplication INSTANCE;
    public static final String LOG_TAG = "BaseApplication";
    public static String BASE_URL = "";
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        initAppStatus();
        initConfig();
    }

    /**
     * 初始化配置信息；
     */
    private void initConfig() {
        initConfigCrashHandler();
        initConfigBaseUrl();
        initConfigFilePosition();
    }


    /**
     *
     * 初始化全局异常 闪退 记录 锁定方案
     * */
    public void initConfigCrashHandler(){
        CrashHandler.getInstance().init(this);
//        CrashHandler.getInstance().init(this);

    }
    /**
     * 初始化baseUrl
     */
    public void initConfigBaseUrl() {
        BASE_URL = PreferenceUtil.getConfigBaseUrl(this);
        if (BASE_URL ==null){
            String temp = "http://192.168.3.2:8080";
            PreferenceUtil.putConfigBaseUrl(this,temp);
            BASE_URL = temp;
        }
    }
    /**
     * 初始化下载文件位置方案
     */
    private void initConfigFilePosition() {
        String filePosition = PreferenceUtil.getDownloadPotion(this);
        if (filePosition == null) {
            filePosition = FileUtil._getDefaultDownloadFilePosition();
            PreferenceUtil.putDownloadPotion(BaseApplication.getInstance(), filePosition);
        }
        LogUtil.e("程序开始，初始化下载文件夹的位置" + filePosition);

    }


    public static Application getInstance() {
        return INSTANCE;
    }

    //监听app运行的状态
    private void initAppStatus() {
//        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
//            @Override
//            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//                LogUtil.e(LOG_TAG,"onActivityCreated");
//            }
//
//            @Override
//            public void onActivityStarted(Activity activity) {
//                LogUtil.e(LOG_TAG,"onActivityStarted");
//            }
//
//            @Override
//            public void onActivityResumed(Activity activity) {
//                LogUtil.e(LOG_TAG,"APP当前状态",activity.getClass().getName(),"onActivityResumed","APP重新回到前台");
//            }
//
//            @Override
//            public void onActivityPaused(Activity activity) {
//                LogUtil.e(LOG_TAG,"onActivityPaused");
//            }
//
//            @Override
//            public void onActivityStopped(Activity activity) {
//                LogUtil.e(LOG_TAG,"APP当前状态",activity.getClass().getName(),"onActivityStopped","Activity在后台运行");
//            }
//
//            @Override
//            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//                LogUtil.e(LOG_TAG,"onActivitySaveInstanceState");
//            }
//
//            @Override
//            public void onActivityDestroyed(Activity activity) {
//                LogUtil.e(LOG_TAG,"onActivityDestroyed");
//                LogUtil.e(LOG_TAG,"APP当前状态",activity.getClass().getName(),"onActivityDestroyed","Activity被移除");
//            }
//        });

    }


}

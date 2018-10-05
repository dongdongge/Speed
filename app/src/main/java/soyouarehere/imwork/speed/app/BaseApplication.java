package soyouarehere.imwork.speed.app;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import soyouarehere.imwork.speed.util.FileUtil;
import soyouarehere.imwork.speed.util.PreferenceUtil;
import soyouarehere.imwork.speed.function.log.LogUtil;

public class BaseApplication extends Application {

    public static BaseApplication INSTANCE;
    public static final String LOG_TAG = "BaseApplication";
    public static String BASE_URL = "";
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

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
//        CrashHandler.getInstance().init(this);
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

    }


}

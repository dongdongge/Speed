package soyouarehere.imwork.speed.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import soyouarehere.imwork.speed.util.log.LogUtil;

public class BaseApplication extends Application {

    public static BaseApplication INSTANCE;
    public static final String LOG_TAG = "BaseApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        initAppStatus();
    }

    public static Application getInstance(){
        return INSTANCE;
    }

    //监听app运行的状态
    private void initAppStatus(){
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                LogUtil.e(LOG_TAG,"onActivityCreated");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                LogUtil.e(LOG_TAG,"onActivityStarted");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                LogUtil.e(LOG_TAG,"APP当前状态",activity.getClass().getName(),"onActivityResumed","APP重新回到前台");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                LogUtil.e(LOG_TAG,"onActivityPaused");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                LogUtil.e(LOG_TAG,"APP当前状态",activity.getClass().getName(),"onActivityStopped","Activity在后台运行");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                LogUtil.e(LOG_TAG,"onActivitySaveInstanceState");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                LogUtil.e(LOG_TAG,"onActivityDestroyed");
                LogUtil.e(LOG_TAG,"APP当前状态",activity.getClass().getName(),"onActivityDestroyed","Activity被移除");
            }
        });

    }



}

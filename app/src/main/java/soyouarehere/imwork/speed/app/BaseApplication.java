package soyouarehere.imwork.speed.app;

import android.app.Application;

public class BaseApplication extends Application {

    public static BaseApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }

    public static Application getInstance(){
        return INSTANCE;
    }
}

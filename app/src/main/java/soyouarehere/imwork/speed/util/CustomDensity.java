package soyouarehere.imwork.speed.util;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * 屏幕适配方案  默认以宽度为唯一的适配
 */
public class CustomDensity {

    //当然以上代码只是以设计图宽360dp去适配的，如果要以高维度适配，可以再扩展下代码即可。

    private  static float sNoncompatDensity;
    private static  float sNoncompatScaleDensity;
    private static void setCustomDensity(Activity activity, Application application){
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (sNoncompatDensity == 0){
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaleDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0){
                        sNoncompatScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }
                @Override
                public void onLowMemory() { }
            });
        }
        final float targetDensity = appDisplayMetrics.widthPixels/360;
        final float targetScaleDensity = targetDensity * (sNoncompatScaleDensity / sNoncompatDensity);
        final int targetDensityDpi = (int)(160*targetDensity);

        appDisplayMetrics.density =  targetDensity;
        appDisplayMetrics.scaledDensity = targetScaleDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics =  activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density =  targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

}

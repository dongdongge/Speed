package soyouarehere.imwork.speed.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import soyouarehere.imwork.speed.app.BaseApplication;

public class PhoneLauncherUtils {


    public static void launcher1() {
        Intent intent = BaseApplication.getInstance().getPackageManager().getLaunchIntentForPackage(BaseApplication.getInstance().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        BaseApplication.getInstance().startActivity(intent);
    }
    /**
     *
     * 此两个钟方法都能重启APP 但是 如果启动的activity不是以standard 的话 不能清除activity中的内存数据
     * */
    public static void launcher2(){
        Intent intent = BaseApplication.getInstance().getPackageManager().getLaunchIntentForPackage(BaseApplication.getInstance().getPackageName());
        PendingIntent restartIntent = PendingIntent.getActivity(BaseApplication.getInstance(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager)BaseApplication.getInstance().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 50, restartIntent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static void launcher3(){
        int [] arr = new int[]{1,2,3};
        System.out.print(arr[6]);
    }

}

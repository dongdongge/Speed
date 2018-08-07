package soyouarehere.imwork.speed.app.handle;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by li.xiaodong on 2018/8/7.
 */

public  class MyHandler extends Handler {
    private WeakReference<Activity> activityWeakReference;

    public MyHandler(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        Activity activity = activityWeakReference.get();
        if (activity != null) {

        }
    }
}

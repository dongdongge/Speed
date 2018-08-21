package soyouarehere.imwork.speed.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import soyouarehere.imwork.speed.MainActivity;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.BaseApplication;

/**
 * 通知工具类
 */
public class NotificationUtils {


    /**
     *
     * 系统默认通知栏
     * */
    public void showSystemDefaulNotification(Context context, int id, String title, String text) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        // 可以设置大图 P9上不会显示大的icon  魅族显示
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher));
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setAutoCancel(true);
        builder.setOnlyAlertOnce(true);
        // 需要VIBRATE权限
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setPriority(Notification.PRIORITY_DEFAULT);

        // Creates an explicit intent for an Activity in your app

        //自定义打开的界面
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());
    }

    /**
     *
     *  1 当内容多行显示 Android 4.1中官方推出的Notification.BigTextStyle  判断当前运行的环境
     *  2 builder.setStyle(new Notification.BigPictureStyle().bigPicture(aBigBitmap));
     * */

    public void showSystemDefaulNotificationSDK4(){

    }


    /**
     *自定义通知栏
     * 1小米通知栏左右都会又个pandding值  华为文字默认都是白色(文字看不到)
     * 2如果设置的高度大于通知栏的默认高度 需要设置bigContentView模式 否则显示不全(builder.setCustomBigContentView(remoteViews))
     * 3 用户还没有划出通知栏的时候需要提醒用户收到通知 显示smallIcon。(builder.setSmallIcon(R.mipmap.ic_icon);)
     * */
    public void showCoustomNotification(Context context){
//        Intent intent = new Intent(BaseApplication.getInstance(), MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent contentIntent = PendingIntent.getActivity(context 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        RemoteViews remoteViews = new RemoteViews(BaseApplication.getInstance().getPackageName(),
//                R.layout.layout_custom_notifycation);
//        remoteViews.setImageViewResource(R.id.iv_icon, R.mipmap.ic_launcher);
//        remoteViews.setTextViewText(R.id.tv_title, "我是一个有内涵的程序猿");
//        remoteViews.setTextViewText(R.id.tv_description,
//                "你懂我的，作为新时代的程序员，要什么都会，上刀山下火海，背砖搬砖都要会，不然哈哈哈哈哈哈哈哈哈你就惨了");
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setContent(remoteViews);
//        builder.setContentIntent(contentIntent);
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
//            builder.setCustomBigContentView(remoteViews);
//        }
//        NotificationManager notificationManager = (NotificationManager) BaseApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(111, builder.build());
    }

    /**
     *  (Android7.0)上是白色的字体，也就看不见。那怎么解决这个问题呢
     *  使用系统通知栏风格的方式,新建一个layout-v21存放5.0以上的布局，对TextView设置文字风格
     *  5.0及以上  android:textAppearance="@android:style/TextAppearance.Material.Notification.Info"
     *  5.0以下   android:textAppearance="@style/TextAppearance.StatusBar.EventContent.Info"
     *
     *
     * */


}

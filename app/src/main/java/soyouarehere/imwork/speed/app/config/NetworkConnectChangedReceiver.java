package soyouarehere.imwork.speed.app.config;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import soyouarehere.imwork.speed.util.NetUtils;
import soyouarehere.imwork.speed.util.log.LogUtil;

/**
 * @desc 全局监听网络变化的广播类
 * @time 2018/7/31 13:32
 */
public class NetworkConnectChangedReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkConnectChanged";

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = NetUtils.isNetworkConnected();
        LogUtil.e(TAG, "onReceive: 当前网络 " + isConnected+"          ==>action"+intent.getAction());
    }
}

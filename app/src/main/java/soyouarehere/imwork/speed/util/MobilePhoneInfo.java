package soyouarehere.imwork.speed.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import soyouarehere.imwork.speed.app.BaseApplication;

/**
 * @desc 当前手机信息类
 * @author li.xiaodong
 * @time 2018/8/8 9:37
 */

public class MobilePhoneInfo {


    public static String _getHardInfo(){
        StringBuffer phoneInfo = new StringBuffer();
        phoneInfo.append("Product: " + android.os.Build.PRODUCT);
        phoneInfo.append(",\n CPU_ABI: " + android.os.Build.CPU_ABI);
        phoneInfo.append(",\n TAGS: " + android.os.Build.TAGS);
        phoneInfo.append(", \nVERSION_CODES.BASE: " + android.os.Build.VERSION_CODES.BASE);
        phoneInfo.append(", \nMODEL: " + android.os.Build.MODEL);
        phoneInfo.append(", \nSDK: " + android.os.Build.VERSION.SDK);
        phoneInfo.append(", \nVERSION.RELEASE: " + android.os.Build.VERSION.RELEASE);
        phoneInfo.append(", \nDEVICE: " + android.os.Build.DEVICE);
        phoneInfo.append(", \nDISPLAY: " + android.os.Build.DISPLAY);
        phoneInfo.append(", \nBRAND: " + android.os.Build.BRAND);
        phoneInfo.append(", \nBOARD: " + android.os.Build.BOARD);
        phoneInfo.append(", \nFINGERPRINT: " + android.os.Build.FINGERPRINT);
        phoneInfo.append(", \nID: " + android.os.Build.ID);
        phoneInfo.append(", \nMANUFACTURER: " + android.os.Build.MANUFACTURER);
        phoneInfo.append(", \nUSER: " + android.os.Build.USER);
        return phoneInfo.toString();
    }


    /**
     * 获得系统总内存
     */
    private String getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("\\s+");
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return "总内存大小：" + Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }

    /**
     * 获取手机MAC地址
     * 只有手机开启wifi才能获取到mac地址
     */
    private String getMacAddress(BaseApplication context){
        String result = "";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        result = wifiInfo==null?null:wifiInfo.getMacAddress();
        return "手机macAdd:" + result;
    }

///*    *//**
//     * 获得手机屏幕宽高
//     * @return
//     *//*
//    public String getHeightAndWidth(BaseApplication context){
//        int width=context.getWindowManager().getDefaultDisplay().getWidth();
//        int heigth=getWindowManager().getDefaultDisplay().getHeight();
//        String str = "Width:" + width+"\nHeight:"+heigth+"";
//        return str;
//    }*/

}

package soyouarehere.imwork.speed.util;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by li.xiaodong on 2018/8/2.
 */

public class UrlUtils {

    /**
     * 判断url是否合法;
     */
    public static boolean checkUrl(@NonNull String url) {
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf('?'));
        }
        if (url.contains("//") && url.indexOf("/", 8) != -1) {
            int index = url.indexOf("/", 8);
            url = url.substring(0, index);
        }
        System.out.println(url);
        return url.matches("^((https|http|ftp|rtsp|mms)?://)"
                + "+(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?"
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" + "|" + "([0-9a-z_!~*'()-]+\\.)*"
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." + "[a-z]{2,6})" + "(:[0-9]{1,10})?"
                + "((/?)|" + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$");
    }


    public static String getFileNameFromUrl(@NonNull String url) {
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf('?'));
        }
        int index = url.lastIndexOf("/");
        if (index == -1) {
            Log.e("url地址错误", "" + url);
            return null;
        }
        url = url.substring(index + 1, url.length());
        return url;
    }

    public static boolean checkFileNameFromUrl(String url) {
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf('?'));
        }
        int index = url.lastIndexOf("/");
        if (index == -1) {
            Log.e("url地址错误", "" + url);
            return false;
        }
        return true;
    }

    public static String getFileNameFromUrls(String url) {
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf('?'));
        }
        int index = url.lastIndexOf("/");
        return url.substring(index + 1, url.length());
    }
}

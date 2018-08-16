package soyouarehere.imwork.speed.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by li.xiaodong on 2018/8/9.
 */

public class TimeUtls {

    /**
     * 获取当前时间戳
     */
    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间并格式化 2018-08-09.
     */
    public static String _getData1() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前时间并格式化 2018-08-09.
     */
    public static String _getData1(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date(time));
    }

    /**
     * 获取当前时间并格式化 2018/08/09.
     */
    public static String _getData2() {
        return _getData1().replaceAll("-", "/");
    }

    /**
     * 获取当前时间并格式化 2018/08/09.
     */
    public static String _getData2(Long time) {
        return _getData1(time).replaceAll("-", "/");
    }

    /**
     * 获取当前时间并格式化 2018-08-09 14:06:03.
     */
    public static String _getDataTime1() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前时间并格式化 2018-08-09 14:06:03.
     */
    public static String _getDataTime1(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date(time));
    }

    /**
     * 获取当前时间并格式化 2018/08/09 14:06:03.
     */
    public static String _getDataTime2() {
        return _getDataTime1().replaceAll("-", "/");
    }

    /**
     * 获取当前时间并格式化 2018/08/09 14:06:03.
     */
    public static String _getDataTime2(long time) {
        return _getDataTime1(time).replaceAll("-", "/");
    }

    /**
     * 将long 类型的时间差 转为小时分钟秒
     *
     * 59秒 1分59秒    59分1秒   1小时59分59秒  23小时59分59秒  1天23小时59分59秒
     */
    public static String _formatTime(long second) {
        //秒
        if (second < 60) {
            return String.valueOf(second) + "秒";
        }
        // 分
        if (second > 60 && second <= 3600) {
            long[] longs = BigDecimalUtils._divideAndRemainder(second, 60);
            return longs[0] + "分" + longs[1] + "秒";
        }
        // 小时
        if (second > 60 * 60 && second <= 86400) {
            long[] longs = BigDecimalUtils._divideAndRemainder(second, 3600);
            if (longs[1] < 60) {
                return longs[0] + "小时" + longs[1] + "秒";
            } else {
                long[] longsHours = BigDecimalUtils._divideAndRemainder(longs[1], 60);
                return longs[0] + "小时" + longsHours[0] + "分" + longsHours[1] + "秒";
            }
        }
        // 天
        if (second > 60 * 60 * 24) {
            long[] longs = BigDecimalUtils._divideAndRemainder(second, 86400);
            if (longs[1] < 3600) {
                long[] longsMunite = BigDecimalUtils._divideAndRemainder(longs[1], 60);
                return longs[0] + "天" + longsMunite[0] + "分" + longsMunite[1] + "秒";
            }else {
                long[] longsDay = BigDecimalUtils._divideAndRemainder(longs[1], 3600);
                if (longsDay[1] < 60) {
                    return longs[0] + "天" +longsDay[0]+"时"+ longsDay[1] + "秒";
                } else {
                    long[] longsHours = BigDecimalUtils._divideAndRemainder(longsDay[1], 60);
                    return longs[0]+"天"+ longsDay[0] + "小时" + longsHours[0] + "分" + longsHours[1] + "秒";
                }
            }
        }
        return "未知";
    }


}

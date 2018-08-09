package soyouarehere.imwork.speed;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.math.BigDecimal;
import java.text.NumberFormat;

import soyouarehere.imwork.speed.util.BigDecimalUtils;

/**
 * Created by li.xiaodong on 2018/8/2.
 */

public class Test {


    public static void main(String[] args) {

        System.out.println(_formatTime(60-1));// 59秒
        System.out.println(_formatTime(60*2-1));// 1分59秒
        System.out.println(_formatTime(60*60-59));// 59分1秒
        System.out.println(_formatTime(60*60-1));// 59分59秒
        System.out.println(_formatTime(60*60*2-1));// 1时59分59秒
        System.out.println(_formatTime(60*60*2-60));// 1时59分0秒
        System.out.println(_formatTime(60*60+59));// 1时0分59秒
        System.out.println(_formatTime(60*60*24-1));// 23时59分59秒
        System.out.println(_formatTime(60*60*23+59));// 23时0分59秒
        System.out.println(_formatTime(60*60*24-60));// 23时59分0秒
        System.out.println(_formatTime(60*60*24*2-1));// 1天23时59分59秒
    }


    /**
     * 将long 类型的时间差 转为小时分钟秒
     */
    public static String _formatTime(long second) {
        //秒
        if (second < 60) {
            return String.valueOf(second) + "秒";
        }
        // 分
        if (second > 60 && second <= 3600) {
            long[] longs = divideAndRemainder(second, 60);
            return longs[0] + "分" + longs[1] + "秒";
        }
        // 小时
        if (second > 60 * 60 && second <= 86400) {
            long[] longs = divideAndRemainder(second, 3600);
            if (longs[1] < 60) {
                return longs[0] + "小时" + longs[1] + "秒";
            } else {
                long[] longsHours = divideAndRemainder(longs[1], 60);
                return longs[0] + "小时" + longsHours[0] + "分" + longsHours[1] + "秒";
            }
        }
        // 天
        if (second > 60 * 60 * 24) {
            long[] longs = divideAndRemainder(second, 86400);
            if (longs[1] < 3600) {
                long[] longsMunite = divideAndRemainder(longs[1], 60);
                return longs[0] + "天" + longsMunite[0] + "分" + longsMunite[1] + "秒";
            }else {
                long[] longsDay = divideAndRemainder(longs[1], 3600);
                if (longsDay[1] < 60) {
                    return longs[0] + "天" +longsDay[0]+"时"+ longsDay[1] + "秒";
                } else {
                    long[] longsHours = divideAndRemainder(longsDay[1], 60);
                    return longs[0]+"天"+ longsDay[0] + "小时" + longsHours[0] + "分" + longsHours[1] + "秒";
                }
            }
        }
        return "";
    }

    /**
     * 求商取余
     * long[0] 为商 long[1] 为余
     */
    public static long[] divideAndRemainder(long number, long number2) {
        BigDecimal bigDecimal = new BigDecimal(number);
        BigDecimal[] bigDecimalArray = bigDecimal.divideAndRemainder(BigDecimal.valueOf(number2));
        long result1 = bigDecimalArray[0].longValue();
        long result2 = bigDecimalArray[1].longValue();
        return new long[]{result1, result2};
    }
}

package soyouarehere.imwork.speed.util;

import java.math.BigDecimal;

/**
 * Created by li.xiaodong on 2018/8/9.
 */

public class BigDecimalUtils {
    /**
     * 加法
     *
     * @param var1
     * @param var2
     * @return
     */
    public static double _add(double var1, double var2) {
        BigDecimal b1 = new BigDecimal(Double.toString(var1));
        BigDecimal b2 = new BigDecimal(Double.toString(var2));
        return b1.add(b2).doubleValue();

    }

    /**
     * 减法
     *
     * @param var1
     * @param var2
     * @return
     */

    public static double _sub(double var1, double var2) {
        BigDecimal b1 = new BigDecimal(Double.toString(var1));
        BigDecimal b2 = new BigDecimal(Double.toString(var2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 乘法
     *
     * @param var1
     * @param var2
     * @return
     */
    public static double _mul(double var1, double var2) {
        BigDecimal b1 = new BigDecimal(Double.toString(var1));
        BigDecimal b2 = new BigDecimal(Double.toString(var2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 除法
     *
     * @param v1
     * @param v2
     * @param scale 精度，到小数点后几位
     * @return
     */

    public static double _div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or ");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 四舍五入
     *
     * @param v
     * @param scale 精确位数
     * @return
     */
    public static double _round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    /**
     * 求商取余
     * long[0] 为商 long[1] 为余
     * */
    public static long[] _divideAndRemainder(long number, long number2) {
        BigDecimal bigDecimal = new BigDecimal(number);
        BigDecimal[] bigDecimalArray = bigDecimal.divideAndRemainder(BigDecimal.valueOf(number2));
        long result1 = bigDecimalArray[0].longValue();
        long result2 = bigDecimalArray[1].longValue();
        return new long[]{result1,result2};
    }
}

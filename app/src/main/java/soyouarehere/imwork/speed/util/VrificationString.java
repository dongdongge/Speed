package soyouarehere.imwork.speed.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by li.xiaodong on 2018/8/30.
 * 校验类
 * *各种字符的unicode编码的范围：
 * 汉字：[0x4e00,0x9fa5]（或十进制[19968,40869]）
 * 数字：[0x30,0x39]（或十进制[48, 57]）
 * 小写字母：[0x61,0x7a]（或十进制[97, 122]）
 * 大写字母：[0x41,0x5a]（或十进制[65, 90]
 */
public class VrificationString {


    /**
     * 是否为数字字母汉字的校验类  且必须为16位
     *
     * @param str
     * @return
     */
    public static boolean isLetterDigit(String str) {
        // 必填字母数字特殊字符
        String pattern = "^[A-Za-z0-9]{16}$";
        boolean b = str.matches(pattern);
        return b;
    }

}

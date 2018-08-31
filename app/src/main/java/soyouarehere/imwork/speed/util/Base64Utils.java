package soyouarehere.imwork.speed.util;

import android.annotation.SuppressLint;

import java.util.Base64;

/**
 * 在计算机中任何数据都是按ascii码存储的，而ascii码的128～255之间的值是不可见字符。而在网络上交换数据时，
 * 比如说从A地传到B地，往往要经过多个路由设备，由于不同的设备对字符的处理方式有一些不同，
 * 这样那些不可见字符就有可能被处理错误，这是不利于传输的。所以就先把数据先做一个Base64编码，统统变成可见字符，这样出错的可能性就大降低了。
 * <p>
 * 无论是编码还是解码都会有一个参数Flags，Android提供了以下几种
 * DEFAULT     这个参数是默认，使用默认的方法来加密
 * NO_PADDING  这个参数是略去加密字符串最后的”=”
 * NO_WRAP     这个参数意思是略去所有的换行符（设置后CRLF就没用了）
 * CRLF        这个参数看起来比较眼熟，它就是Win风格的换行符，意思就是使用CR LF这一对作为一行的结尾而不是Unix风格的LF
 * URL_SAFE    这个参数意思是加密时不使用对URL和文件名有特殊意义的字符来作为加密字符，具体就是以-和_取代+和/
 */
public class Base64Utils {
    /**
     * @param bytes
     * @return
     */
    @SuppressLint("NewApi")
    public static byte[] encode(byte[] bytes) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encode(bytes);
    }

    /**
     * @param bytes
     * @return
     */
    @SuppressLint("NewApi")
    public static byte[] decode(byte[] bytes) {
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(bytes);
    }

    /**
     *
     * @param md5SecretStr
     * @return
     */
    public static String encodeString(byte[] md5SecretStr) {
        return new String(encode(md5SecretStr));
    }
}

package soyouarehere.imwork.speed.function.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import soyouarehere.imwork.speed.util.Base64Utils;
import soyouarehere.imwork.speed.util.VrificationString;

/**
 * Created by li.xiaodong on 2018/8/30.
 * 对称加密
 * AES 是一种可逆加密算法，对用户的敏感信息加密处理 对原始数据进行AES加密后，在进行Base64编码转化；
 * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
 */
public class AESEncryption {

    String AESKey = "...";
    //偏移量,可自行修改
    String ivParameter = "0392039203920300";

    public static AESEncryption getInstance() {
        return Help.aesEncryption;
    }

    private static class Help {
        static final AESEncryption aesEncryption = new AESEncryption();
    }


    /**
     * 对称加密
     *
     * @param sSrc 待加密数据
     * @return 加密结果
     * @throws Exception
     */
    String encrypt(String sSrc) throws Exception {
        return _encrypt(sSrc, AESKey, ivParameter);
    }


    /**
     * 对称加密 - 解密
     *
     * @param sSrc 加密的数据
     * @return 原文
     */
    String decrypt(String sSrc) {
        return _decrypt(sSrc, AESKey, ivParameter);
    }

    /**
     * 对称加密 - 加密
     *
     * @param encData   待加密的数据
     * @param secretKey 加密用的秘钥  其中秘钥为16位的数据
     * @param vector    偏移量
     * @return 加密的结果
     * @throws Exception
     */
    private String _encrypt(String encData, String secretKey, String vector) throws Exception {
        if (!VrificationString.isLetterDigit(secretKey)){
            System.err.println("secretKey必须是字符和数字组成的16位的组合");
            return null;
        }

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = secretKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec(vector.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(encData.getBytes("utf-8"));
        // 此处使用BASE64做转码。
        String signResult = Base64Utils.encodeString(encrypted).replaceAll("\r\n","");
        return signResult;
    }

    /**
     * 对称加密 - 解密
     *
     * @param sSrc 待解密的内容
     * @param key  解密秘钥
     * @param ivs  偏移
     * @return 原文内容
     * @throws Exception
     */
    private String _decrypt(String sSrc, String key, String ivs) {
        try {
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            // 先用base64解密
            byte[] encrypted1 = Base64Utils.decode(sSrc.getBytes());
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

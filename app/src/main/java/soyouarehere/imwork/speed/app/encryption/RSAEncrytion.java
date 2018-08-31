package soyouarehere.imwork.speed.app.encryption;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import soyouarehere.imwork.speed.util.Base64Utils;

/**
 * Created by li.xiaodong on 2018/8/30.
 * <p>
 * 非对称加密 RSA、ECC（移动设备用）、Diffie-Hellman、El Gamal、DSA（数字签名用）
 * {非对称加密算法需要两个密钥：公开密钥（publickey）和私有密钥（privatekey）
 * 服务器生成一对密钥并将其中的一把作为公用密钥向其它方公开；得到该公用密钥的客户端使用该密钥对机密信息进行加密后再发送给服务器；
 * 服务器再用自己保存的另一把专用密钥对加密后的信息进行解密。服务器只能用其专用密钥解密由其公用密钥加密后的任何信息}
 *
 * 加密铭文大小为117 超过这异常;
 */
public class RSAEncrytion {

    public static String str_pubK = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqPvovSfXcwBbW8cKMCgwqNpsYuzF8RPAPFb7LGsnVo44JhM/xxzDyzoYtdfNmtbIuKVi9PzIsyp6rg+09gbuI6UGwBZ5DWBDBMqv5MPdOF5dCQkB2Bbr5yPfURPENypUz+pBFBg41d+BC+rwRiXELwKy7Y9caD/MtJyHydj8OUwIDAQAB";
    public static String str_priK = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKo++i9J9dzAFtbxwowKDCo2mxi7MXxE8A8VvssaydWjjgmEz/HHMPLOhi1182a1si4pWL0/MizKnquD7T2Bu4jpQbAFnkNYEMEyq/kw904Xl0JCQHYFuvnI99RE8Q3KlTP6kEUGDjV34EL6vBGJcQvArLtj1xoP8y0nIfJ2Pw5TAgMBAAECgYAGGB8IllMwxceLhjf6n1l0IWRH7FuHIUieoZ6k0p6rASHSgWiYNRMxfecbtX8zDAoG0QAWNi7rn40ygpR5gS1fWDAKhmnhKgQIT6wW0VmD4hraaeyP78iy8BLhlvblri2nCPIhDH5+l96v7D47ZZi3ZSOzcj89s1eS/k7/N4peEQJBAPEtGGJY+lBoCxQMhGyzuzDmgcS1Un1ZE2pt+XNCVl2b+T8fxWJH3tRRR8wOY5uvtPiK1HM/IjT0T5qwQeH8Yk0CQQC0tcv3d/bDb7bOe9QzUFDQkUSpTdPWAgMX2OVPxjdq3Sls9oA5+fGNYEy0OgyqTjde0b4iRzlD1O0OhLqPSUMfAkEAh5FIvqezdRU2/PsYSR4yoAdCdLdT+h/jGRVefhqQ/6eYUJJkWp15tTFHQX3pIe9/s6IeT/XyHYAjaxmevxAmlQJBAKSdhvQjf9KAjZKDEsa7vyJ/coCXuQUWSCMNHbcR5aGfXgE4e45UtUoIE1eKGcd6AM6LWhx3rR6xdFDpb9je8BkCQB0SpevGfOQkMk5i8xkEt9eeYP0fi8nv6eOUcK96EXbzs4jV2SAoQJ9oJegPtPROHbhIvVUmNQTbuP10Yjg59+8=";

    private RSAEncrytion() {
    }

    public static RSAEncrytion getInstance() {
        return Help.rsaEncrytion;
    }

    private static class Help {
        static final RSAEncrytion rsaEncrytion = new RSAEncrytion();
    }

    public String encrypt(String content) {
        System.out.println(content.getBytes().length);
        return _encrypt(str_pubK, content);
    }

    public String decrypt(String content) {
        return _decrypt(str_priK, content);
    }

    /**
     * RSA 非对称加密 - 加密
     *
     * @param str_pri 私钥字符串
     * @param content 待加密内容
     * @return 加密过后的内容
     * @throws Exception
     */
    private String _encrypt(String str_pri, String content){
        String signString = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, strConvertPubKey(str_pri));
            byte[] result = cipher.doFinal(content.getBytes());
            byte[] signatrue = Base64Utils.encode(result);
            signString = new String(signatrue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("加密结果 result:{" + signString + "}");
        return signString;
    }

    /**
     * RSA 非对称加密 - 解密
     *
     * @param str_pri 私钥字符串
     * @param content 已加密内容
     * @return 原文
     * @throws Exception
     */
    private String _decrypt(String str_pri, String content) {
        String result = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, strConvertPriKey(str_pri));
            byte[] i = Base64Utils.decode(content.getBytes());
            result = new String(cipher.doFinal(i));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("解密结果 " + result);
        return result;
    }

    /**
     * 将公钥字符串转为公钥
     *
     * @param str_pub 公钥字符串
     * @return 公钥
     */
    public PublicKey strConvertPubKey(String str_pub) throws Exception {
        byte[] keyBytes = Base64Utils.decode(str_pub.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 将私钥字符串转为私钥
     *
     * @param str_pri 私钥字符串
     * @return 私钥
     */
    public PrivateKey strConvertPriKey(String str_pri) throws Exception {
        byte[] keyBytes = Base64Utils.decode(str_pri.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }


    public static void rsa(String contextMsg) throws Exception {
        String context = contextMsg == null || contextMsg.isEmpty() ? "lxd" : contextMsg;
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("rsa");
        KeyPair gkp = kpg.generateKeyPair();

        PrivateKey privateKey = gkp.getPrivate();
        PublicKey publicKey = gkp.getPublic();
        byte[] encode1 = privateKey.getEncoded();
        byte[] encode2 = publicKey.getEncoded();

        byte[] key1 = Base64Utils.encode(encode1);
        byte[] key2 = Base64Utils.encode(encode2);

        System.out.println("编码后的私钥 " + new String(key1));

        System.out.println("编码后的公钥 " + new String(key2));

        System.out.println("私钥 privateKey:{" + privateKey + "}");
        System.out.println("公钥 publicKey:{" + publicKey + "}");

        Cipher cipher = Cipher.getInstance("rsa");
        cipher.init(1, privateKey);
        byte[] result = cipher.doFinal(context.getBytes());
        byte[] signatrue = Base64Utils.encode(result);
        System.out.println("加密结果 result:{" + new String(signatrue) + "}");
        byte[] i = Base64Utils.decode(signatrue);
        cipher.init(2, publicKey);
        System.out.println("解密结果 " + new String(cipher.doFinal(i)));

    }

}

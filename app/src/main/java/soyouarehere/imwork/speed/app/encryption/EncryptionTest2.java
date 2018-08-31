package soyouarehere.imwork.speed.app.encryption;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import soyouarehere.imwork.speed.util.Base64Utils;

/**
 * Created by li.xiaodong on 2018/8/30.
 */
public class EncryptionTest2 {
    /**
     * 对称加密  DES、3DES、Blowfish、IDEA、RC4、RC5、RC6和AES
     *{对称加密采用了对称密码编码技术，它的特点是文件加密和解密使用相同的密钥加密}
     *
     * 非对称加密 RSA、ECC（移动设备用）、Diffie-Hellman、El Gamal、DSA（数字签名用）
     * {非对称加密算法需要两个密钥：公开密钥（publickey）和私有密钥（privatekey）
     * 服务器生成一对密钥并将其中的一把作为公用密钥向其它方公开；得到该公用密钥的客户端使用该密钥对机密信息进行加密后再发送给服务器；
     * 服务器再用自己保存的另一把专用密钥对加密后的信息进行解密。服务器只能用其专用密钥解密由其公用密钥加密后的任何信息}
     *
     * 校验数据完整性 MD5 HASH
     * {Hash算法特别的地方在于它是一种单向算法，用户可以通过hash算法对目标信息生成一段特定长度的唯一hash值，
     * 却不能通过这个hash值重新获得目标信息。因此Hash算法常用在不可还原的密码存储、信息完整性校验等。}
     *
     * */


    /**
     * 对称加解密
     *
     * @param key     加解密秘钥
     * @param content 待加密内容
     */
    private static void DES(String key, String content) {
        if (key==null||key.isEmpty()){
            System.out.println("DES 对称加解密:  你的秘钥为空");
            return;
        }


    }
    /**
     * 对称加解密
     *
     * @param key     加解密秘钥 [加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。]
     * @param content 待加密内容
     */
    private static void AES(String key, String content) throws Exception {
        if (key==null||key.isEmpty()||key.length()<16){
            System.out.println("AES 对称加解密:  你的秘钥为空");
            return;
        }
        //aes 加密
        KeyGenerator aesKeyGenerator = KeyGenerator.getInstance("aes");
        SecretKey aesSecretKey = aesKeyGenerator.generateKey();

        System.out.println("aes 加密 秘钥: { " +aesSecretKey + " }");
        Cipher aesCipher = Cipher.getInstance("aes");
        aesCipher.init(Cipher.ENCRYPT_MODE, aesSecretKey);
        byte[] aseResultBytes = aesCipher.doFinal(content.getBytes());
        System.out.println("aes 加密 : { " + Base64Utils.encodeString(aseResultBytes)+ " }");

        //aes 解密
        aesCipher.init(Cipher.DECRYPT_MODE, aesSecretKey);
        aseResultBytes = aesCipher.doFinal(aseResultBytes);
        System.out.println("aes 解密: { " + new String(aseResultBytes) + " }");
    }
    /**
     * RSA非对称加密
     *
     * @param publickey  公钥
     * @param privatekey 私钥
     * @param content    待加密内容
     */
    private static void RSA(String publickey, String privatekey, String content) {
        System.out.println("RSA 非对称加密:  ");

    }

    /**
     * MD5 盐值加密
     * @param salt    指定盐值
     * @param content 待加密内容
     */
    private static void MD5(String salt, String content) {
        System.out.println("MD5 盐值加密:  ");

    }


}

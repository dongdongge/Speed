package soyouarehere.imwork.speed.function.encryption;


/**
 * Created by li.xiaodong on 2018/8/30.
 */
public class MD5Salt {
//    /**
//     * 生成含有随机盐的密码
//     */
//    public static String generate(String password) {
//        Random r = new Random();
//        StringBuilder sb = new StringBuilder(16);
//        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
//        int len = sb.length();
//        if (len < 16) {
//            for (int i = 0; i < 16 - len; i++) {
//                sb.append("0");
//            }
//        }
//        String salt = sb.toString();
//        System.out.println("根据待加密内容生成盐值: "+salt);
//        password = md5Hex(password + salt);
//        char[] cs = new char[48];
//        for (int i = 0; i < 48; i += 3) {
//            cs[i] = password.charAt(i / 3 * 2);
//            char c = salt.charAt(i / 3);
//            cs[i + 1] = c;
//            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
//        }
//        return new String(cs);
//    }
//
//    /**
//     * 校验密码是否正确
//     */
//    public static boolean verify(String password, String md5) {
//        char[] cs1 = new char[32];
//        char[] cs2 = new char[16];
//        for (int i = 0; i < 48; i += 3) {
//            cs1[i / 3 * 2] = md5.charAt(i);
//            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
//            cs2[i / 3] = md5.charAt(i + 1);
//        }
//        String salt = new String(cs2);
//        System.out.println("从加密后的数据中获取盐值: "+salt);
//        System.out.println("...: "+md5Hex(password + salt));
//        return md5Hex(password + salt).equals(new String(cs1));
//    }
//
//    /**
//     * 获取十六进制字符串形式的MD5摘要
//     */
//    public static String md5Hex(String src) {
//        try {
//            MessageDigest md5 = MessageDigest.getInstance("MD5");
//            byte[] bs = md5.digest(src.getBytes());
//            return new String(new Hex().encode(bs));
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public static void main(String[] args) {
//        String password = generate("admin");
//        System.out.println("加密过后的数据"+password);
//        System.out.println(verify("admin", password));
//    }
}

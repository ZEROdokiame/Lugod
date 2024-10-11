package com.ruoyi.common.core.utils;

import cn.hutool.core.codec.Base64;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.regex.Pattern;

/**
 * @program: JieYiHua-Cloud
 * @description: 加密解密
 * @author: LiYu
 * @create: 2021-07-23 17:02
 **/
@Component
public class EncryptUtil {

    public static final String MD5 = "MD5";
    public static final String SHA1 = "SHA1";
    public static final String HmacMD5 = "HmacMD5";
    public static final String HmacSHA1 = "HmacSHA1";
    public static final String DES = "DES";
    public static final String AES = "AES";
    public static final Charset CHARSET = StandardCharsets.UTF_8;

    public static final byte keyStrSize = 16;


    public static final byte ivStrSize = 16;

    public static final String AES_CBC_NOPADDING = "AES/CBC/NoPadding";
    public static final String DES_ECB_PKCS7PADDING = "DES/ECB/PKCS7Padding";
    public static final String AES_ECB_PKCS5PADDING = "AES/ECB/PKCS5Padding";

    /**
     * 编码格式；默认使用uft-8
     */
    public static String charset = "utf-8";
    /**
     * DES
     */
    public static int keysizeDES = 0;
    /**
     * AES
     */
    public static int keysizeAES = 128;

    public static EncryptUtil me;

    private EncryptUtil() {
        //单例
    }

    //双重锁
    public static EncryptUtil getInstance() {
        if (me == null) {
            synchronized (EncryptUtil.class) {
                if (me == null) {
                    me = new EncryptUtil();
                }
            }
        }
        return me;
    }

    /**
     * 使用MessageDigest进行单向加密（无密码）
     *
     * @param res       被加密的文本
     * @param algorithm 加密算法名称
     * @return
     */
    private static String messageDigest(String res, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] resBytes = charset == null ? res.getBytes() : res.getBytes(charset);
            return base64(md.digest(resBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用KeyGenerator进行单向/双向加密（可设密码）
     *
     * @param res       被加密的原文
     * @param algorithm 加密使用的算法名称
     * @param key       加密使用的秘钥
     * @return
     */
    private String keyGeneratorMac(String res, String algorithm, String key) {
        try {
            SecretKey sk = null;
            if (key == null) {
                KeyGenerator kg = KeyGenerator.getInstance(algorithm);
                sk = kg.generateKey();
            } else {
                byte[] keyBytes = charset == null ? key.getBytes() : key.getBytes(charset);
                sk = new SecretKeySpec(keyBytes, algorithm);
            }
            Mac mac = Mac.getInstance(algorithm);
            mac.init(sk);
            byte[] result = mac.doFinal(res.getBytes());
            return base64(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用KeyGenerator双向加密，DES/AES，注意这里转化为字符串的时候是将2进制转为16进制格式的字符串，不是直接转，因为会出错
     *
     * @param res       加密的原文
     * @param algorithm 加密使用的算法名称
     * @param key       加密的秘钥
     * @param keysize
     * @param isEncode
     * @return
     */
    private static String keyGeneratorES(String res, String algorithm, String key, int keysize, boolean isEncode) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(algorithm);
            if (keysize == 0) {
                byte[] keyBytes = charset == null ? key.getBytes() : key.getBytes(charset);
                kg.init(new SecureRandom(keyBytes));
            } else if (key == null) {
                kg.init(keysize);
            } else {
                byte[] keyBytes = charset == null ? key.getBytes() : key.getBytes(charset);
                kg.init(keysize, new SecureRandom(keyBytes));
            }
            SecretKey sk = kg.generateKey();
            SecretKeySpec sks = new SecretKeySpec(sk.getEncoded(), algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            if (isEncode) {
                cipher.init(Cipher.ENCRYPT_MODE, sks);
                byte[] resBytes = charset == null ? res.getBytes() : res.getBytes(charset);
                return parseByte2HexStr(cipher.doFinal(resBytes));
            } else {
                cipher.init(Cipher.DECRYPT_MODE, sks);
                return new String(cipher.doFinal(parseHexStr2Byte(res)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Deprecated
    private static String base64(byte[] res) {
        return Base64.encode(res);
    }

    /**
     * 将二进制转换成16进制
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * md5加密算法进行加密（不可逆）
     *
     * @param res 需要加密的原文
     * @return
     */
    @Deprecated
    public String MD5(String res) {
        return messageDigest(res, MD5);
    }

    /**
     * md5加密算法进行加密（不可逆）
     *
     * @param res 需要加密的原文
     * @param key 秘钥
     * @return
     */
    @Deprecated
    public String MD5(String res, String key) {
        return keyGeneratorMac(res, HmacMD5, key);
    }

    /**
     * 使用SHA1加密算法进行加密（不可逆）
     *
     * @param res 需要加密的原文
     * @return
     */
    public static String SHA1(String res) {
        return messageDigest(res, SHA1);
    }

    /**
     * 使用SHA1加密算法进行加密（不可逆）
     *
     * @param res 需要加密的原文
     * @param key 秘钥
     * @return
     */
    public String SHA1(String res, String key) {
        return keyGeneratorMac(res, HmacSHA1, key);
    }

    /**
     * 使用DES加密算法进行加密（可逆）
     *
     * @param res 需要加密的原文
     * @param key 秘钥
     * @return
     */
    public static String DESencode(String res, String key) {
        return keyGeneratorES(res, DES, key, keysizeDES, true);
    }

    /**
     * 对使用DES加密算法的密文进行解密（可逆）
     *
     * @param res 需要解密的密文
     * @param key 秘钥
     * @return
     */
    public String DESdecode(String res, String key) {
        return keyGeneratorES(res, DES, key, keysizeDES, false);
    }


    /**
     * 使用异或进行加密
     *
     * @param res 需要加密的密文
     * @param key 秘钥
     * @return
     */
    public String XORencode(String res, String key) {
        byte[] bs = res.getBytes();
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return parseByte2HexStr(bs);
    }

    /**
     * 使用异或进行解密
     *
     * @param res 需要解密的密文
     * @param key 秘钥
     * @return
     */
    public String XORdecode(String res, String key) {
        byte[] bs = parseHexStr2Byte(res);
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return new String(bs);
    }

    /**
     * 直接使用异或（第一调用加密，第二次调用解密）
     *
     * @param res 密文
     * @param key 秘钥
     * @return
     */
    public int XOR(int res, String key) {
        return res ^ key.hashCode();
    }

    /**
     * 使用Base64进行加密
     *
     * @param res 密文
     * @return
     */
    public String Base64Encode(String res) {
        return Base64.encode(res.getBytes());
    }

    /**
     * 使用Base64进行解密
     *
     * @param res
     * @return
     */
    public String Base64Decode(String res) {
        return new String(Base64.decode(res));
    }


    private static final int length = 128;

    /**
     * 加密
     *
     * @param content  需要加密的内容
     * @param password 加密密码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private static byte[] encrypt(String content, String password)
            throws Exception {

        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(password.getBytes());
        kgen.init(length, secureRandom);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
        byte[] byteContent = content.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(byteContent);
        return result; // 加密

    }

    /**
     * 解密
     *
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    private static byte[] decrypt(byte[] content, String password)
            throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(password.getBytes());
        kgen.init(length, secureRandom);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(content);
        return result; // 加密


    }

    /**
     * 加密
     *
     * @param content  需要加密的内容
     * @param password 加密密码
     * @return
     */
    public static byte[] encrypt2(String content, String password) {
        try {
            SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Deprecated
    public static String AESencode(String content, String password) {
        try {
            byte[] encryptResult = encrypt(content, password);
            return Base64.encode(encryptResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Deprecated
    public static String AESdecode(String content, String password) {
        try {
            byte[] decryptResult = decrypt(Base64.decode(content), password);
            return new String(decryptResult, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return content;
        }
    }

    public static boolean isBase64(String str) {
        String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        return Pattern.matches(base64Pattern, str);
    }

    public static void main(String[] args) {
//        String s = AESencode("430602200007025537", "wfwbkdyrdmr");
//        System.out.println(s);

//        String s = AESencode("18058743226", "gsdfeygasfw");
//        String s1 = AESencode("张三", "gsdfeygasfw");
//        System.out.println(s);
//        System.out.println(s1);
//        System.out.println(Arrays.toString(Base64.decode("5vpdaf8bTigPCRakqzIZXA==")));
//        String s = AESdecode("Lsz+2WDokzxEuAaoZYf0cQ==", "gsdfeygasfw");
//
//        String phone = AESdecode("j6rj21kehQqc4JJS4NxTug==", "fdsasdfsdds");
//        System.out.println("phone:" + phone);
//        String s = AESencode("18058743226", "gsdfeygasfw");
//        String s1 = AESdecode("CVr/+AgX/sHe00OQnXet9Q==", "wfwbkdyrdmr");许
//        System.out.println(s1);
//        String s = AESencode("13750869639", "gsdfeygasfw");
//        System.out.println(s);
//        System.out.println(AESdecode("O1ZWNkiAaIJLDGzwAaTfug==","gsdfeygasfw"));
//        String a = AESdecode("罗娜","wfwbkdyrdmr");
//        System.out.println(a);
//        System.out.println(MD5Utils.encrypt(a));

//        System.out.println(AESencode("17707051035","gsdfeygasfw"));
        System.out.println(AESdecode("W+/dxhwi5yBWiDnqtLKY+w==", "gsdfeygasfw"));
        System.out.println(AESdecode("J88FbYTTmTeKXfIBBedw1A==", "gsdfeygasfw"));

    }

    /***
     * 利用Apache的工具类实现SHA-256加密
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256Str(String str){
        MessageDigest messageDigest;
        String encdeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes(StandardCharsets.UTF_8));
            encdeStr = Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encdeStr;
    }

    /**
     * 用 AES 算法加密 inputStr。
     * 使用 secretStr 作为 key，ivStr作为 iv。
     * 并对加密后的字节数组调用 sun.misc.BASE64Encoder.encode 方法，
     * 转换成 base64 字符串返回。
     *
     * （仅作为测试用途，具体加密流程以接口文档为准）
     *
     * @param secretStr
     * @param inputStr
     * @return
     */
    public static String base64StrDecode(String secretStr, String ivStr, String inputStr){
        byte[] inputBytes;
        inputBytes = org.apache.commons.codec.binary.Base64.decodeBase64(inputStr);
        String outputStr = new String(decode(secretStr, ivStr, inputBytes), CHARSET);
        System.out.println("base64Decode > base64 decrypt " + outputStr);
        return outputStr.trim();
    }

    /**
     * 用 AES 算法解密 inputStr。
     * 使用 secretStr 作为 key，ivStr作为 iv。
     *
     * @param secretStr
     * @param ivStr
     * @return
     */
    public static byte[] decode(String secretStr, String ivStr, byte[] inputBytes){
        if (keyStrSize != secretStr.length() || ivStrSize != ivStr.length()) {
            return null;
        }
        byte[] secretKeyBytes = secretStr.getBytes(CHARSET);
        byte[] ivBytes = ivStr.getBytes(CHARSET);

        byte[] outputBytes = decryptCBCNoPadding(secretKeyBytes, ivBytes, inputBytes);
        return outputBytes;
    }

    /**
     * AES/CBC/NoPadding decrypt
     * 16 bytes secretKeyStr
     * 16 bytes intVector
     *
     * @param secretKeyBytes
     * @param intVectorBytes
     * @param input
     * @return
     */
    public static byte[] decryptCBCNoPadding(byte[] secretKeyBytes, byte[] intVectorBytes, byte[] input) {
        try {
            IvParameterSpec iv = new IvParameterSpec(intVectorBytes);
            SecretKey secretKey = new SecretKeySpec(secretKeyBytes, AES);

            Cipher cipher = Cipher.getInstance(AES_CBC_NOPADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] encryptBytes = cipher.doFinal(input);
            return encryptBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  DES 加密加密模式 ECB，填充方式 Pkcs7，输出方式 Base64，字符集 utf8
     * @param data
     * @param password
     * @return
     */
    public static String encryptECBPkcs7(String data, String password) {
        if (password== null || password.length() < 8) { throw new RuntimeException("加密失败，key不能小于8位"); }
        if(StringUtils.isBlank(data)){ return null; }

        try {
            //下面这行在进行PKCS7Padding加密时必须加上，否则报错
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            //根据传入的秘钥内容生成符合DES加密解密格式的秘钥内容
            DESKeySpec dks = new DESKeySpec(password.getBytes());
            //获取DES秘钥生成器对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            // 生成秘钥：key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            //获取DES/ECB/PKCS7Padding该种级别的加解密对象
            Cipher cipher = Cipher.getInstance(DES_ECB_PKCS7PADDING);
            //初始化加解密对象【opmode:确定是加密还是解密模式；secretKey是加密解密所用秘钥】
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(data.getBytes(CHARSET));
            return Base64.encode(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptECBPkcs7(String data, String password) {
        if (password== null || password.length() < 8) { throw new RuntimeException("解密失败，key不能小于8位"); }
        if(StringUtils.isBlank(data)){ return null; }

        try {
            //下面这行在进行PKCS7Padding加密时必须加上，否则报错
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            //根据传入的秘钥内容生成符合DES加密解密格式的秘钥内容
            DESKeySpec dks = new DESKeySpec(password.getBytes());
            //获取DES秘钥生成器对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            // 生成秘钥：key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            //获取DES/ECB/PKCS7Padding该种级别的加解密对象
            Cipher cipher = Cipher.getInstance(DES_ECB_PKCS7PADDING);
            //初始化加解密对象【opmode:确定是加密还是解密模式；secretKey是加密解密所用秘钥】
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.decode(data.getBytes(charset))), charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * AES加密
     * @param content 内容
     * @param password 密钥
     * @return 加密后数据
     */
    public static byte[] encryptECBPkcs5(byte[] content, byte[] password) {
        if (content == null || password == null)
            return null;
        try {
            Cipher cipher = Cipher.getInstance(AES_ECB_PKCS5PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(password, AES));
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * AES解密
     * @param content 加密内容
     * @param password 密钥
     * @return 解密后数据
     */
    public static byte[] decryptECBPkcs5(byte[] content, byte[] password) {
        if (content == null || password == null)
            return null;
        try {
            Cipher cipher = Cipher.getInstance(AES_ECB_PKCS5PADDING);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(password, AES));
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}

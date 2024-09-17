package com.ruoyi.common.core.utils;


import com.ruoyi.common.core.constant.HttpStatus;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * @author LiYu
 * @ClassName SecureUtil.java
 * @Description 加解密工具类
 * @createTime 2024年05月20日 11:04:00
 */
@Slf4j
public class SecureUtils {
    /**
     * md5加密
     */
    public static class Md5Util {

        /**
         * 判断是否为md5加密
         *
         * @param str 字符串
         * @return 结果
         */
        public static boolean isMd5(String str) {
            return str.matches("^[a-f0-9]{32}$");
        }

        /**
         * MD5加密并转大写
         *
         * @param str 字符串
         * @return 结果
         */
        public static String md5ToUpperCase(String str) {
            return StringUtils.hasText(str) ? isMd5(str) ? str : cn.hutool.crypto.SecureUtil.md5(str).toUpperCase() : null;
        }

        /**
         * MD5加密并转小写
         *
         * @param str 字符串
         * @return 结果
         */
        public static String md5ToLowerCase(String str) {
            return StringUtils.hasText(str) ? isMd5(str) ? str : cn.hutool.crypto.SecureUtil.md5(str).toLowerCase() : null;
        }

        /**
         * MD5加密
         *
         * @param str 字符串
         * @return 结果
         */
        public static String md5(String str) {
            return StringUtils.hasText(str) ? isMd5(str) ? str : cn.hutool.crypto.SecureUtil.md5(str) : null;
        }
    }

    /**
     * des加密
     */
    public static class DesUtil {
        /**
         * 加密key
         */
        public static final String KEY = "_@Ks`Y*9jLb.hvho}C;GwDpw";
        /**
         * 偏移量
         */
        public static final String IV = "2%8iTpSi";

        /**
         * 创建加密对象
         *
         * @param iv   偏移量
         * @param mode 模式
         * @return 结果
         */
        private static Cipher createCipher(String iv, int mode) throws NoSuchAlgorithmException, NoSuchPaddingException,
                InvalidKeyException, InvalidAlgorithmParameterException {
            byte[] key = KEY.getBytes();
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(mode, new SecretKeySpec(key, "DESede"), ivParameterSpec);
            return cipher;
        }

        /**
         * 加密
         *
         * @param data 数据
         * @param iv   偏移量
         * @return 结果
         */
        public static String encrypt(String data, String iv) {
            try {
                Cipher cipher = createCipher(iv, Cipher.ENCRYPT_MODE);
                return URLEncoder.encode(Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes())), "UTF-8");
            } catch (Exception e) {
                log.error("加密失败", e);
            }
            return null;
        }

        /**
         * 加密
         *
         * @param data 数据
         * @return 结果
         */
        public static String encrypt(String data) {
            return encrypt(data, IV);
        }

        /**
         * 解密
         *
         * @param data 数据
         * @param iv   偏移量
         * @return 结果
         */
        public static String decrypt(String data, String iv) {
            try {
                Cipher cipher = createCipher(iv, Cipher.DECRYPT_MODE);
                return new String(cipher.doFinal(Base64.getDecoder().decode(URLDecoder.decode(data, "UTF-8"))));
            } catch (Exception e) {
                log.error("解密失败", e);
            }
            return null;
        }

        /**
         * 解密
         *
         * @param data 数据
         * @return 结果
         */
        public static String decrypt(String data) {
            return decrypt(data, IV);
        }

    }

    /**
     * AES加解密
     */
    public static class AesUtil {
        /**
         * 加密模式之 ECB，算法/模式/补码方式
         */
        public static final String AES_ECB = "AES/ECB/PKCS5Padding";

        /**
         * 加密模式之 CBC，算法/模式/补码方式
         */
        public static final String AES_CBC = "AES/CBC/PKCS5Padding";

        /**
         * 加密模式之 CFB，算法/模式/补码方式
         */
        public static final String AES_CFB = "AES/CFB/PKCS5Padding";

        /**
         * AES 中的 IV 必须是 16 字节（128位）长
         */
        public static final Integer IV_LENGTH = 16;

        /***
         * 空校验
         * @param str 需要判断的值
         */
        public static boolean isEmpty(Object str) {
            return null == str || "".equals(str);
        }

        /***
         * String 转 byte
         * @param str 需要转换的字符串
         */
        public static byte[] getBytes(String str) {
            if (isEmpty(str)) {
                return null;
            }

            try {
                return str.getBytes(StandardCharsets.UTF_8);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        /***
         * 初始化向量（IV），它是一个随机生成的字节数组，用于增加加密和解密的安全性
         */
        public static String getIv() {
            String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < IV_LENGTH; i++) {
                int number = random.nextInt(str.length());
                sb.append(str.charAt(number));
            }
            return sb.toString();
        }


        /***
         * 获取一个 AES 密钥规范
         */
        public static SecretKeySpec getSecretKeySpec(String key) {
            return new SecretKeySpec(Objects.requireNonNull(getBytes(key)), "AES");
        }

        /**
         * 加密 - 模式 ECB
         *
         * @param text 需要加密的文本内容
         * @param key  加密的密钥 key
         */
        public static String encrypt(String text, String key) {
            if (isEmpty(text) || isEmpty(key)) {
                return null;
            }
            try {
                // 创建AES加密器
                Cipher cipher = Cipher.getInstance(AES_ECB);
                SecretKeySpec secretKeySpec = getSecretKeySpec(key);
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
                // 加密字节数组
                byte[] encryptedBytes = cipher.doFinal(Objects.requireNonNull(getBytes(text)));
                // 将密文转换为 Base64 编码字符串
                return Base64.getEncoder().encodeToString(encryptedBytes);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        /**
         * 解密 - 模式 ECB
         *
         * @param text 需要解密的文本内容
         * @param key  解密的密钥 key
         */
        public static String decrypt(String text, String key) {
            if (isEmpty(text) || isEmpty(key)) {
                return null;
            }
            // 将密文转换为16字节的字节数组
            byte[] textBytes = Base64.getDecoder().decode(text);
            try {
                // 创建AES加密器
                Cipher cipher = Cipher.getInstance(AES_ECB);
                SecretKeySpec secretKeySpec = getSecretKeySpec(key);
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
                // 解密字节数组
                byte[] decryptedBytes = cipher.doFinal(textBytes);
                // 将明文转换为字符串
                return new String(decryptedBytes, StandardCharsets.UTF_8);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * 加密 - 自定义加密模式
         *
         * @param text 需要加密的文本内容
         * @param key  加密的密钥 key
         * @param iv   初始化向量
         * @param mode 加密模式
         */
        public static String encrypt(String text, String key, String iv, String mode) {
            if (isEmpty(text) || isEmpty(key) || isEmpty(iv)) {
                return null;
            }
            try {
                // 创建AES加密器
                Cipher cipher = Cipher.getInstance(mode);
                SecretKeySpec secretKeySpec = getSecretKeySpec(key);
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(Objects.requireNonNull(getBytes(iv))));
                // 加密字节数组
                byte[] encryptedBytes = cipher.doFinal(Objects.requireNonNull(getBytes(text)));
                // 将密文转换为 Base64 编码字符串
                return Base64.getEncoder().encodeToString(encryptedBytes);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * 加密
         *
         * @param content  待加密内容
         * @param password 加密密钥
         * @return 加密后的内容
         */
        public static String AesEncode(String content, String password) {
            try {
                byte[] encryptResult = encryptByte(content, password);
                return Base64Util.encode(encryptResult);
            } catch (Exception e) {
                log.error("加密出现问题！", e);
                e.printStackTrace();
            }
            return null;
        }

        public static void main(String[] args) {

            String s = "{\"accumulationFund\":1,\"age\":18,\"car\":1,\"career\":1,\"city\":\"重庆\",\"cityCode\":1000,\"creditCard\":1,\"education\":1,\"guarantee\":1,\"hourse\":1,\"idCardMd5\":\"331d17d1ca8a091410e3238fab16a863\",\"monthlyIncome\":5000,\"nameMd5\":\"331d17d1ca8a091410e3238fab16a863\",\"phoneMd5\":\"331d17d1ca8a091410e3238fab16a863\",\"sex\":0,\"socialSecurity\":1,\"zhiMa\":600}";
            String s1 = "{\"accumulationFund\":1,\"age\":18,\"car\":1,\"career\":1,\"city\":\"重庆\",\"cityCode\":1000,\"creditCard\":1,\"education\":1,\"guarantee\":1,\"hourse\":1,\"idCard\":\"341202199306023511\",\"idCardMd5\":\"331d17d1ca8a091410e3238fab16a863\",\"monthlyIncome\":5000,\"name\":\"朱三\",\"nameMd5\":\"331d17d1ca8a091410e3238fab16a863\",\"phone\":\"15205600635\",\"phoneMd5\":\"331d17d1ca8a091410e3238fab16a863\",\"sex\":0,\"socialSecurity\":1,\"zhiMa\":600}";
            System.out.println(AesEncode(s,"LwLjtU1Bt8dcxxjY"));
        }
        /**
         * 解密
         *
         * @param content  待解密内容
         * @param password 解密密钥
         * @return 解密后的内容
         */
        public static String AesDecode(String content, String password) {
            try {
                byte[] decryptResult = decryptByte(Base64Util.decodeToByteArray(content), password);
                return new String(decryptResult, StandardCharsets.UTF_8);
            } catch (Exception e) {
                log.error("解密出现问题！", e);
                return null;
            }
        }

        /**
         * 加密 - 模式 CBC
         *
         * @param text 需要加密的文本内容
         * @param key  加密的密钥 key
         * @param iv   初始化向量
         * @return 加密后的内容
         */
        public static String encryptCbc(String text, String key, String iv) {
            return encrypt(text, key, iv, AES_CBC);
        }

        /**
         * 解密 - 自定义加密模式
         *
         * @param text 需要解密的文本内容
         * @param key  解密的密钥 key
         * @param iv   初始化向量
         * @param mode 加密模式
         */
        public static String decrypt(String text, String key, String iv, String mode) {
            if (isEmpty(text) || isEmpty(key) || isEmpty(iv)) {
                return null;
            }
            // 将密文转换为16字节的字节数组
            byte[] textBytes = Base64.getDecoder().decode(text);
            try {
                // 创建AES加密器
                Cipher cipher = Cipher.getInstance(mode);
                SecretKeySpec secretKeySpec = getSecretKeySpec(key);
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(Objects.requireNonNull(getBytes(iv))));
                // 解密字节数组
                byte[] decryptedBytes = cipher.doFinal(textBytes);
                // 将明文转换为字符串
                return new String(decryptedBytes, StandardCharsets.UTF_8);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * 解密
         *
         * @param content  待解密内容
         * @param password 解密密钥
         * @return 解密后的内容
         */
        private static byte[] decryptByte(byte[] content, String password)
                throws Exception {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            keyGenerator.init(128, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(content);
        }

        /**
         * 加密
         *
         * @param content  需要加密的内容
         * @param password 加密密码
         * @return 加密后的字节数组
         */
        private static byte[] encryptByte(String content, String password)
                throws Exception {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            keyGenerator.init(128, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(byteContent);
        }

        /**
         * 解密 - 模式 CBC
         *
         * @param text 需要加密的文本内容
         * @param key  加密的密钥 key
         * @param iv   初始化向量
         * @return 加密后的内容
         */
        public static String decryptCbc(String text, String key, String iv) {
            return decrypt(text, key, iv, AES_CBC);
        }




    }


    /**
     * Base64加解密
     */
    public static class Base64Util {
        /**
         * 编码字符串为Base64
         *
         * @param input 需要编码的字符串
         * @return 编码后的Base64字符串
         */
        public static String encode(String input) {
            return Base64.getEncoder().encodeToString(input.getBytes());
        }

        /**
         * 从Base64编码解码为字符串
         *
         * @param input Base64编码的字符串
         * @return 解码后的字符串
         */
        public static String decode(String input) {
            byte[] decodedBytes = Base64.getDecoder().decode(input);
            return new String(decodedBytes);
        }

        /**
         * 编码字节数组为Base64字符串
         *
         * @param input 需要编码的字节数组
         * @return 编码后的Base64字符串
         */
        public static String encode(byte[] input) {
            return Base64.getEncoder().encodeToString(input);
        }

        /**
         * 解码Base64字符串为字节数组
         *
         * @param input Base64编码的字符串
         * @return 解码后的字节数组
         */
        public static byte[] decodeToByteArray(String input) {
            return Base64.getDecoder().decode(input);
        }

        /**
         * 判断是否为Base64编码
         *
         * @param str 字符串
         * @return 结果
         */
        public static boolean isBase64(String str) {
            String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
            return Pattern.matches(base64Pattern, str);
        }
    }

    public static void main(String[] args) {

    }
}

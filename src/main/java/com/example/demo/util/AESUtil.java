package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES加解密工具类
 */
public class AESUtil {

    private static Logger logger = LoggerFactory.getLogger(AESUtil.class);

    private static final String encodeRules = "zhangyonghong";

    private static Cipher getCipher(int mode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        // 1.构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        // 2.根据encodeRules规则初始化密钥生成器
        // 生成一个128位的随机源，根据传入的字节数组
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(encodeRules.getBytes());
        keygen.init(128, random);
        // 3.产生原始对称密钥
        SecretKey original_key = keygen.generateKey();
        // 4.获得原始对称密钥的字节数组
        byte[] raw = original_key.getEncoded();
        // 5.根据字节数组生成AES密钥
        SecretKey key = new SecretKeySpec(raw, "AES");
        // 6.根据指定算法AES自成密码器
        Cipher cipher = Cipher.getInstance("AES");
        // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
        cipher.init(mode, key);
        return cipher;
    }

    /**
     * 加密
     */
    public static String encode(String content) {
        try {
            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
            // 将字符串转成原始字节数组
            byte[] byte_original = content.getBytes(StandardCharsets.UTF_8);
            // 加密，得到加密后的字节数组
            byte[] byte_encrypt = cipher.doFinal(byte_original);
            // 用 Base64 编码加密后的字节数组成字符串
            return Base64.getEncoder().encodeToString(byte_encrypt);
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(logger, e);
        }
        return null;
    }

    /**
     * 解密
     */
    public static String decode(String content) {
        try {
            Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
            // 用 Base64 解码得到加密后的字节数组
            byte[] byte_encrypt = Base64.getDecoder().decode(content);
            // 解密，得到原始字节数组
            byte[] byte_original = cipher.doFinal(byte_encrypt);
            // 将原始字节数组构造成解密后的字符串
            return new String(byte_original, StandardCharsets.UTF_8);
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(logger, e);
        }
        return null;
    }

    public static void main(String[] args) {
        String content = "abc";
        logger.info(">>>>> {}--------{}", content, encode(content));
        String encrypt = "zHFlOpVpD1DT1eL4psjNKg==";
        logger.info(">>>>> {}--------{}", encrypt, decode(encrypt));
    }

}

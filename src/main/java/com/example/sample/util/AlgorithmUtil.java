package com.example.sample.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AlgorithmUtil {

    private static Logger logger = LoggerFactory.getLogger(AlgorithmUtil.class);

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String key = "zhangyonghong";
        logger.info(">>>>> {}: {}", key, MD5(key));
        logger.info(">>>>> {}: {}", key, SHA(key));
    }

    public static String MD5(String key) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = md.digest(key.getBytes());
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static String SHA(String key) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA");
        byte[] bytes = md.digest(key.getBytes());
        return new String(bytes, StandardCharsets.UTF_8);
    }

}

package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	private static Logger logger = LoggerFactory.getLogger(MD5Util.class);
	
	public static String encode(byte[] bytes) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5"); 
        return bytes2HexString(md5.digest(bytes));
	}
	
    public static String bytes2HexString(byte[] bytes) {
    	String base = "0123456789ABCDEF";
    	char[] baseChars = new char[base.length()];
    	base.getChars(0, base.length(), baseChars, 0);
    	char[] chars = new char[bytes.length*2];
    	int k = 0;
		for (byte b : bytes) {
			chars[k++] = baseChars[b >>> 4 & 0xf];
			chars[k++] = baseChars[b & 0xf];
		}
    	return new String(chars);
    }
    
	public static void main(String[] args) throws NoSuchAlgorithmException {
		logger.info(">>>>> hello: {}", encode("hello".getBytes()));
	}
	
}

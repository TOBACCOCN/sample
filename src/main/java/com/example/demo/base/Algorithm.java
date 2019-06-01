package com.example.demo.base;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Algorithm {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		MD5("zhangyonghong");
		SHA("zhangyonghong");
	}

	public static void MD5(String key) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] bytes = md.digest(key.getBytes());
		for (byte b : bytes) {
			System.out.println(b);
		}
		System.out.println(new String(bytes, StandardCharsets.UTF_8));
	}
	
	public static void SHA(String key) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA");
		byte[] bytes = md.digest(key.getBytes());
		for (byte b : bytes) {
			System.out.println(b);
		}
		System.out.println(new String(bytes, StandardCharsets.UTF_8));
	}

}

package com.example.demo.base;

import java.io.UnsupportedEncodingException;

public class HexTest {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
//		System.out.println(System.currentTimeMillis());
//		Scanner scanner = new Scanner(System.in);
//		while (true) {
//			int a = scanner.nextInt();
//			byte x =(byte) (a & 0xFF);
//			byte y = (byte) (a >> 8);
//			byte z = (byte) (a >> 16);
//			byte w = (byte) (a >> 24);
//			byte[] bytes = new byte[]{w, z, y, x};
//			System.out.println(bytes2Hex(bytes));
//		}
		System.out.println(bytes2Hex("20180606111000".getBytes()));
		byte[] bytes = "20180606111000".getBytes();
		byte[] bs = new byte[bytes.length];
		for (int i = 0; i < bs.length; i++) {
			bs[i] = bytes[i];
			System.out.println(bytes[i]);
		}
		String s = new String(bs);
		System.out.println(s);
	}
	
	private static String bytes2Hex(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		for (byte b : bytes) {
			char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'e', 'e', 'f'};
			char[] temp = new char[2];
			temp[0] = chars[(b >>> 4) & 0X0F];
			temp[1] = chars[b & 0X0F];
			builder.append(new String(temp));
		}
		return builder.toString();
	}

}

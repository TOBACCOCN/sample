package com.example.demo.base;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.github.pagehelper.util.StringUtil;

public class LongTest {

	public static void main(String[] args) throws UnsupportedEncodingException {
		long a = 1L;
		String b = "a";
		System.out.println(a + b);
		System.out.println("abcdef".substring(0, 6-1));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("x", 2L);
		long y = (long) map.get("x");
		System.out.println(y);
		System.out.println(StringUtil.isNotEmpty(null));
		byte[] bytes = "你好".getBytes("ASCII");
		StringBuilder sb = new StringBuilder();
		for (byte c : bytes) {
			sb.append((char) c);
		}
		System.out.println(sb.toString());
	}
	
}
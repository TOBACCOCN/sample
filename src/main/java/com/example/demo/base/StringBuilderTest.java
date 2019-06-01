package com.example.demo.base;

import java.util.ArrayList;
import java.util.List;

public class StringBuilderTest {

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		sb.append(null == null ? "" : "hello");
		List<String> list = new ArrayList<String>();
		list.add(null);
		System.out.println(list.get(0));
	}
	
}

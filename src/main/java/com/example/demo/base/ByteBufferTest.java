package com.example.demo.base;

import java.nio.ByteBuffer;

public class ByteBufferTest {

	public static void main(String[] args) {
		System.out.println("before allocate, jvm memory left: " + Runtime.getRuntime().freeMemory());  
		
		// 如果分配的内存过小，调用Runtime.getRuntime().freeMemory()大小不会变化 
	    // 分配的内存大小超过多少JVM才能感觉到？
		ByteBuffer byteBuffer = ByteBuffer.allocate(102400);
//		byte[] bytes = new byte[byteBuffer.position()];
//		byteBuffer.get(bytes);
//		System.out.println(bytes.length);
		byteBuffer.put("sdafdasfasdfdasfdasf".getBytes());
		System.out.println("byteBuffer: " + byteBuffer);
		System.out.println("after allocate, jvm memory left: " + Runtime.getRuntime().freeMemory());
		
		// 这部分直接用的系统内存，所以对JVM的内存没有影响
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024000);
		System.out.println("buffer: " + buffer);
		System.out.println("after allocatDirect, jvm memory left: " + Runtime.getRuntime().freeMemory());
		
		byte[] bytes = new byte[32];
		ByteBuffer wrapperedBuffer = ByteBuffer.wrap(bytes, 10, 10);
		System.out.println("wrapperedBuffer: " + wrapperedBuffer);
	}
	
}

package com.example.sample;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public class ByteBufferTests {
    
    private static Logger logger= LoggerFactory.getLogger(ByteBufferTests.class);

    @Test
    public void test() {
        logger.info(">>>>> before allocate, jvm memory left: {}", Runtime.getRuntime().freeMemory());

        // 如果分配的内存过小，调用Runtime.getRuntime().freeMemory()大小不会变化 
        // 分配的内存大小超过多少JVM才能感觉到？
        ByteBuffer byteBuffer = ByteBuffer.allocate(102400);
        // byte[] bytes = new byte[byteBuffer.position()];
        // byteBuffer.get(bytes);
        // logger.info(">>>>> LENGTH: {}", bytes.length);
        logger.info(">>>>> POSITION: {}", byteBuffer.position());
        byteBuffer.put("sdafdasfasdfdasfdasf".getBytes());
        logger.info(">>>>> byteBuffer: {}", byteBuffer);
        logger.info(">>>>> after allocate, jvm memory left: {}", Runtime.getRuntime().freeMemory());

        // 这部分直接用的系统内存，所以对JVM的内存没有影响
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024000);
        logger.info(">>>>> buffer: {}", buffer);
        logger.info(">>>>> after allocatDirect, jvm memory left: {}", Runtime.getRuntime().freeMemory());

        byte[] bytes = new byte[32];
        ByteBuffer wrapperedBuffer = ByteBuffer.wrap(bytes, 10, 10);
        wrapperedBuffer.put("abd".getBytes());
        logger.info(">>>>> wrapperedBuffer: {}", wrapperedBuffer);
    }

}

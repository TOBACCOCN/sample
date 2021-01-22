package com.example.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.ByteBuffer;

@Slf4j
public class ByteBufferTest {

    // private static Logger logger = LoggerFactory.getLogger(ByteBufferTest.class);

    @Test
    public void test() {
        log.info(">>>>> before allocate, jvm memory left: [{}]", Runtime.getRuntime().freeMemory());

        // 如果分配的内存过小，调用Runtime.getRuntime().freeMemory()大小不会变化 
        // 分配的内存大小超过多少JVM才能感觉到？
        ByteBuffer byteBuffer = ByteBuffer.allocate(102400);
        // byte[] bytes = new byte[byteBuffer.position()];
        // byteBuffer.get(bytes);
        // log.info(">>>>> LENGTH: [{}]", bytes.length);
        log.info(">>>>> POSITION: [{}]", byteBuffer.position());
        byteBuffer.put("sdafdasfasdfdasfdasf".getBytes());
        log.info(">>>>> byteBuffer: [{}]", byteBuffer);
        log.info(">>>>> after allocate, jvm memory left: [{}]", Runtime.getRuntime().freeMemory());

        // 这部分直接用的系统内存，所以对JVM的内存没有影响
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024000);
        log.info(">>>>> buffer: [{}]", buffer);
        log.info(">>>>> after allocateDirect, jvm memory left: [{}]", Runtime.getRuntime().freeMemory());

        byte[] bytes = new byte[32];
        ByteBuffer wrappedBuffer = ByteBuffer.wrap(bytes, 10, 10);
        wrappedBuffer.put("abd".getBytes());
        log.info(">>>>> wrappedBuffer: [{}]", wrappedBuffer);
    }

    @Test
    public void get() {
        byte[] bytes = new byte[]{12, 13};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        log.info(">>>>> byte: [{}]", byteBuffer.get());
        log.info(">>>>> byte: [{}]", byteBuffer.get());
        log.info(">>>>> byteBuffer: [{}]", byteBuffer);
        // may throw BufferUnderflowException when the buffer's current position is not smaller than its limit
        // log.info(">>>>> byte: [{}]", byteBuffer.get());
    }

    @Test
    public void getFromIndex() {
        byte[] bytes = new byte[]{12, 13};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        log.info(">>>>> bytes: [{}]", byteBuffer.get(byteBuffer.limit() - 1));
        log.info(">>>>> byteBuffer: [{}]", byteBuffer);
        log.info(">>>>> byte: [{}]", byteBuffer.get());
    }

    @Test
    public void getBytes() {
        byte[] bytes = new byte[]{12, 13};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byte[] dest = new byte[2];
        byteBuffer.get(dest);
        log.info(">>>>> byte: [{}]", dest);
        log.info(">>>>> byteBuffer: [{}]", byteBuffer);
        // may throw BufferUnderflowException when the buffer's current position is not smaller than its limit
        // log.info(">>>>> byte: [{}]", byteBuffer.get());
    }

    @Test
    public void getBytesWithIndexAndLen() {
        byte[] bytes = new byte[]{12, 13};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byte[] dest = new byte[3];
        byteBuffer.get(dest, 0, 2);
        log.info(">>>>> byte: [{}]", dest);
        log.info(">>>>> byteBuffer: [{}]", byteBuffer);
        // may throw BufferUnderflowException when the buffer's current position is not smaller than its limit
        // log.info(">>>>> byte: [{}]", byteBuffer.get());
    }

    @Test
    public void getChar() {
        char src = 'C';
        byte[] bytes = new byte[2];
        bytes[1] = (byte) (src & 0xff);
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        char c = byteBuffer.getChar();
        log.info(">>>>> c: [{}]", c);
        log.info(">>>>> byteBuffer: [{}]", byteBuffer);
        // may throw BufferUnderflowException when the buffer's current position is not smaller than its limit
        // log.info(">>>>> byte: [{}]", byteBuffer.get());
    }

    @Test
    public void getCharFromIndex() {
        char src = 'C';
        byte[] bytes = new byte[4];
        bytes[1] = (byte) (src & 0xff);
        // src = 'X';
        bytes[3] = (byte) (src & 0xff);
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        char c = byteBuffer.getChar(2);
        log.info(">>>>> c: [{}]", c);
        log.info(">>>>> byteBuffer: [{}]", byteBuffer);
        log.info(">>>>> byte: [{}]", byteBuffer.get());
    }

    @Test
    public void getShort() {
        short src = 1234;
        byte[] bytes = new byte[2];
        bytes[1] = (byte) (src & 0xff);
        src = (short) (src >> 8);
        bytes[0] = (byte) (src & 0xff);
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        short s = byteBuffer.getShort();
        log.info(">>>>> s: [{}]", s);
        log.info(">>>>> byteBuffer: [{}]", byteBuffer);
        // may throw BufferUnderflowException when the buffer's current position is not smaller than its limit
        // log.info(">>>>> byte: [{}]", byteBuffer.get());
    }

    @Test
    public void getShortFromIndex() {
        short src = 1234;
        byte[] bytes = new byte[4];
        bytes[1] = (byte) (src & 0xff);
        src = (short) (src >> 8);
        bytes[0] = (byte) (src & 0xff);
        src = 3456;
        bytes[3] = (byte) (src & 0xff);
        src = (short) (src >> 8);
        bytes[2] = (byte) (src & 0xff);
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        short s = byteBuffer.getShort(2);
        log.info(">>>>> s: [{}]", s);
        log.info(">>>>> byteBuffer: [{}]", byteBuffer);
        log.info(">>>>> byte: [{}]", byteBuffer.get());
    }

    @Test
    public void put() {
        byte[] bytes = new byte[]{12, 13};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        log.info(">>>>> byteBuffer: [{}]", byteBuffer);
        // byteBuffer.mark();
        byteBuffer.put((byte) 14);
        // byteBuffer.reset();
        log.info(">>>>> byteBuffer: [{}]", byteBuffer);
        log.info(">>>>> byte: [{}]", byteBuffer.get());
    }

    @Test
    public void clear() {
        byte[] bytes = new byte[]{12, 13};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        log.info(">>>>> byte: [{}]", byteBuffer.get());
        byteBuffer.mark();
        log.info(">>>>> byteBuffer: [{}]", byteBuffer);
        byteBuffer.clear();
        log.info(">>>>> after clear, byteBuffer: [{}]", byteBuffer);
        log.info(">>>>> byte: [{}]", byteBuffer.get());
    }

    @Test
    public void reset() {
        byte[] bytes = new byte[]{12, 13};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        log.info(">>>>> byte: [{}]", byteBuffer.get());
        byteBuffer.mark();
        log.info(">>>>> byte: [{}]", byteBuffer.get());
        log.info(">>>>> byteBuffer: [{}]", byteBuffer);
        byteBuffer.reset();
        log.info(">>>>> after reset, byteBuffer: [{}]", byteBuffer);
        log.info(">>>>> byte: [{}]", byteBuffer.get());
    }

    @Test
    public void flip() {
        byte[] bytes = new byte[]{12, 13};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        log.info(">>>>> byte: [{}]", byteBuffer.get());
        log.info(">>>>> byteBuffer: [{}]", byteBuffer);
        byteBuffer.flip();
        log.info(">>>>> after flip, byteBuffer: [{}]", byteBuffer);
        log.info(">>>>> byte: [{}]", byteBuffer.get());
    }

    @Test
    public void rewind() {
        byte[] bytes = new byte[]{12, 13, 14};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        log.info(">>>>> byte: [{}]", byteBuffer.get());
        byteBuffer.mark();
        log.info(">>>>> byte: [{}]", byteBuffer.get());
        byteBuffer.reset();
        log.info(">>>>> after reset, byte: [{}]", byteBuffer.get());
        log.info(">>>>> byteBuffer: [{}]", byteBuffer);
        byteBuffer.rewind();

        // may throw InvalidMarkException as mark is set to -1
        // byteBuffer.reset();
        log.info(">>>>> after rewind, byteBuffer: [{}]", byteBuffer);
        log.info(">>>>> byte: [{}]", byteBuffer.get());
    }

}

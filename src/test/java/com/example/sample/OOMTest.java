package com.example.sample;

import javassist.CannotCompileException;
import javassist.ClassPool;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class OOMTest {

    @Test
    public void heap() {
        List<Object> list =new ArrayList<>();
        while(true) {
            list.add(new Object());
        }
    }

    private int count = 0;
    @Test
    public void stackOverflowError() {
        count++;
        stackOverflowError();
    }

    // 好像没有效果，即使设置了 -XX:MetaspaceSize=10m
    public static void main(String[] args) throws CannotCompileException {
        ClassPool classPool = ClassPool.getDefault();
        int i = 0;
        while(true) {
            classPool.makeClass(OOMTest.class.getName() + i++).toClass();
        }
    }

    // 在 windows 上目前没有效果
    @Test
    public void threadCountError() {
        while(true) {
            new Thread(() -> {
                try {
                    Thread.sleep(10000000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

}

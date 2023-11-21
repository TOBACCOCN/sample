package com.example.sample;

import javassist.CannotCompileException;
import org.junit.Test;

public class ErrorTest {

    // -Xmx5m
    // @Test
    public void healNotEnoughError() {
        byte[] bytes = new byte[10*1024*1024];      // java.lang.OutOfMemoryError: Java heap space
    }

    // -Xss1k
    // @Test
    public void stackOverflowError() throws InterruptedException {
        stackOverflowError();       // java.lang.StackOverflowError
    }

    // 在命令行中执行可复现，在 IDE 中无法复现
    public static void main(String[] args) throws CannotCompileException {
        while(true) {
            new Thread(() -> {
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();     // java.lang.OutOfMemoryError: unable to create new native thread
        }
    }

    // -XX:MaxMetaspaceSize=10m，没有复现
    // public static void main(String[] args) throws CannotCompileException {
    //     while (true) {
    //         Enhancer enhancer =new Enhancer();
    //         enhancer.setSuperclass(ErrorTest.class);
    //         enhancer.setUseCache(false);
    //         enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> methodProxy.invokeSuper(o, args));
    //         enhancer.create();
    //     }
    //
    //     // ClassPool classPool = ClassPool.getDefault();
    //     // int i = 0;
    //     // while(true) {
    //     //     classPool.makeClass(ErrorTest.class.getName() + i++).toClass();
    //     // }
    // }

    boolean finalized = false;
    @Test
    public void test() {
        Person p =new Person();
        p = null;
        while (true) {
            if (finalized) {
                break;
            }
            System.out.println("gc ...");
            System.gc();
        }
    }

    class Person {
        public void finalize() {
            System.out.println("我被回收了");
            finalized = true;
        }
    }

}


package com.example.sample;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class BaseTest {

    @Test
    public void test01() {
        User user = new User("admin", "super", "超级管理员");
        String json = JSON.toJSONString(user);
        log.info(">>>>> json: [{}]", json);
    }

    @Test
    public void test02() throws IOException, ClassNotFoundException {
        User user = new User("admin", "super", "超级管理员");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(byteArrayOutputStream);
        os.writeObject(user);
        os.flush();
        os.close();
        ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        user = (User) is.readObject();
        is.close();
        log.info(">>>>> s: [{}]", user);
    }


    @Test
    public void test03() {
        // HashSet<String> set = new LinkedHashSet<>();     // 存取顺序一致：遍历时，按存入的顺序取出
        HashSet<String> set = new HashSet<>();
        set.add("a");
        set.add("a");
        set.add("b");
        set.add("c");
        set.add("c");
        set.add("b");
        set.add("e");
        set.add("d");
        for (String s : set) {
            log.info(">>>> s: [{}]", s);
        }
    }

    private int sum;
    private AtomicInteger s = new AtomicInteger();
    private volatile int v;

    @Test
    public void test04() throws InterruptedException {
        for (int i = 1; i <= 100; i = i + 10) {
            MyThread myThread = new MyThread(i);
            myThread.start();
            myThread.join();
        }
        // log.info(">>>>> SUM: [{}]", sum);
        // log.info(">>>>> S: [{}]", s);
        log.info(">>>>> V: [{}]", v);

        int n = 0;
        for (int i = 1; i <= 100; ++i) {
            n += i;
        }
        log.info(">>>>> N: [{}]", n);

    }

    private synchronized void addSum(int n) {
        sum += n;
    }

    class MyThread extends Thread {
        private int start;

        public MyThread(int start) {
            this.start = start;
        }


        @Override
        public void run() {
            int sum = 0;
            for (int i = 0; i < 10; ++i) {
                sum += start++;
            }
            log.info(">>>>> THREAD_NAME: [{}], SUM: [{}]", Thread.currentThread().getName(), sum);
            // addSum(sum);
            // s.addAndGet(sum);
            // 这里有问题, 编译器都提示 += 不是原子操作, 所以即使 v 是 volatile, 某线程修改了 v, 会保证其他线程读到的 v 一定是修改过后的 v,
            // 但是 v += sum 是读后再加 sum, 有可能读到修改后的 v 后, 又被其他线程抢到 CPU 时间片, 且又修改了 v, 那么当前线程计算时的 v 跟主内存中的 v 不一致
            v += sum;
        }
    }

}

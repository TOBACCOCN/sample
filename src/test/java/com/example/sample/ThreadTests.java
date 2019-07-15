package com.example.sample;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadTests {

    private static Logger logger = LoggerFactory.getLogger(ThreadTests.class);

    private static final Object lock = new Object();
    private static final Object lock2 = new Object();

    @Test
    public void test() {
        new Thread(mm).start();
        new Thread(nn).start();
        new Thread(qq).start();
    }

    private static Runnable mm = () -> {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info(">>>>> mm running ...");
        }
    };

    private static Runnable nn = () -> {
        synchronized (lock) {
            synchronized (lock2) {
                try {
                    lock2.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info(">>>>> nn running ...");
                lock.notify();
            }
        }
    };

    private static Runnable qq = () -> {
        synchronized (lock2) {
            logger.info(">>>>> qq running ...");
            lock2.notify();
        }
    };

}



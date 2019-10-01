package com.example.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ThreadTests {

    // private static Logger logger = LoggerFactory.getLogger(ThreadTests.class);

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
            log.info(">>>>> mm running ...");
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
                log.info(">>>>> nn running ...");
                lock.notify();
            }
        }
    };

    private static Runnable qq = () -> {
        synchronized (lock2) {
            log.info(">>>>> qq running ...");
            lock2.notify();
        }
    };

}



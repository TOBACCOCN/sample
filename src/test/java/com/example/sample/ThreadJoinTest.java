package com.example.sample;

import com.example.sample.util.ErrorPrintUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadJoinTest {

    private Thread newThread(Thread joined) {
        return new Thread(() -> {
            try {
                if (joined != null) {
                    joined.join();
                }
                TimeUnit.SECONDS.sleep(2);
                String name = Thread.currentThread().getName();
                log.debug(">>>>> THREAD: [{}], RUN_FINISHED", name);
            } catch (InterruptedException e) {
                ErrorPrintUtil.printErrorMsg(log, e);
            }
        });
    }

    @Test
    public void join() throws InterruptedException {
        Thread t1 = newThread(null);
        t1.start();
        Thread t2  = newThread(t1);
        t2.start();
        Thread t3  = newThread(t2);
        t3.start();

        // 等待 t3 执行完
        t3.join();

        // 用于阻塞主线程不让主线程退出
        // Thread.currentThread().join();

        String name = Thread.currentThread().getName();
        log.debug(">>>>> THREAD: [{}], RUN_FINISHED", name);
    }

}

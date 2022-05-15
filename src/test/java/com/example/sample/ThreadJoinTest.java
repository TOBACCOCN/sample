package com.example.sample;

import com.example.sample.util.ErrorPrintUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadJoinTest {

    private void startChildThread() {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                String name = Thread.currentThread().getName();
                log.debug(">>>>> THREAD: [{}], RUN_FINISHED", name);
            } catch (InterruptedException e) {
                ErrorPrintUtil.printErrorMsg(log, e);
            }
        }).start();
    }

    @Test
    public void join() throws InterruptedException {
        startChildThread();
        // 如果不执行 thread.join()，那么基本会先执行主线程的程序，并且很快就退出了，子线程中的程序就不会被执行了
        // thread.join();
        // Thread.currentThread().join(); 用于阻塞主线程不让主线程退出
        Thread.currentThread().join();
        String name = Thread.currentThread().getName();
        log.debug(">>>>> THREAD: [{}], RUN_FINISHED", name);
    }

    @Test
    public void countdownLatch() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        startChildThread();
        // 如果不执行 thread.join()，那么基本会先执行主线程的程序，并且很快就退出了，子线程中的程序就不会被执行了
        // thread.join();
        // Thread.currentThread().join(); 用于阻塞主线程不让主线程退出
        countDownLatch.await();
        String name = Thread.currentThread().getName();
        log.debug(">>>>> THREAD: [{}], RUN_FINISHED", name);
    }

}

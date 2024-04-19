package com.example.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

// 一等多
@Slf4j
public class CountDownLatchTest {

    @Test
    public void test() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);

        for (int i = 0; i < 2; i++) {
            int finalI = i;
            new Thread(() -> {
                long id = Thread.currentThread().getId();
                log.info("[{}] running...", id);
                try {
                    int seconds = 3 * (finalI + 1);
                    log.info("[{}] sleep [{}] seconds", id, seconds);
                    TimeUnit.SECONDS.sleep(seconds);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                countDownLatch.countDown();     // 将 countDownLatch 的 count 减 1
                log.info("[{}] countDown, count: [{}]", id, countDownLatch.getCount());
            }).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                long id = Thread.currentThread().getId();
                try {
                    log.info("[{}] await", id);
                    // 等待 countDownLatch 的 count 为 0 才会被唤醒
                    countDownLatch.await();
                    log.info("[{}] waked", id);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

        Thread.currentThread().join();
    }

}

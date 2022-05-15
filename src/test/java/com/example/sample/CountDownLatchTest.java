package com.example.sample;

import com.example.sample.util.ErrorPrintUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class CountDownLatchTest {

    @Test
    public void test01() {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        new Thread(() -> {
            log.info(">>>>> CHILD_THREAD END");
            countDownLatch.countDown();     // 将 countDownLatch 的 count 减 1
        }).start();

        try {
            // 主线程阻塞, 等待 countDownLatch 的 count 为 0 才会被唤醒
            countDownLatch.await();
            log.info(">>>>> MAIN END");
        } catch (InterruptedException e) {
            ErrorPrintUtil.printErrorMsg(log, e);
        }
    }

    @Test
    public void test02() {
        CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(() -> {
            log.info(">>>>> CHILD_THREAD END");
            countDownLatch.countDown();     // 将 countDownLatch 的 count 减 1
        }).start();

        new Thread(() -> {
            log.info(">>>>> OTHER_THREAD END");
            countDownLatch.countDown();     // 再将 countDownLatch 的 count 减 1
        }).start();

        try {
            // 主线程阻塞, 等待 countDownLatch 的 count 为 0 才会被唤醒
            countDownLatch.await();
            log.info(">>>>> MAIN END");
        } catch (InterruptedException e) {
            ErrorPrintUtil.printErrorMsg(log, e);
        }
    }

}

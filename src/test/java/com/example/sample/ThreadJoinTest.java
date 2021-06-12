package com.example.sample;

import com.example.sample.util.ErrorPrintUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadJoinTest {

    @Test
    public void join() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                String name = Thread.currentThread().getName();
                log.debug(">>>>> THREAD: [{}], RUN_FINISHED", name);
            } catch (InterruptedException e) {
                ErrorPrintUtil.printErrorMsg(log, e);
            }
        });
        thread.start();
        // 如果不执行 thread.join()，那么基本会先执行主线程的程序，并且很快就退出了，子线程中的程序就不会被执行了
        thread.join();
        String name = Thread.currentThread().getName();
        log.debug(">>>>> THREAD: [{}], RUN_FINISHED", name);
    }

}

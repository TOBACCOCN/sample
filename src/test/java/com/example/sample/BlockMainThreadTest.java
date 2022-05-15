package com.example.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class BlockMainThreadTest {

    private void startChildThread() {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                log.info(">>>>> THREAD_NAME: [{}]", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @Test
    public void join() {
        startChildThread();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void countdownLatch() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        startChildThread();
        countDownLatch.await();
    }

    @Test
    public void lockCondition() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();
        new Thread(() -> {
            try {
                reentrantLock.lock();
                TimeUnit.SECONDS.sleep(5);
                log.info(">>>>> THREAD_NAME: [{}]", Thread.currentThread().getName());
                condition.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                reentrantLock.unlock();
            }
        }).start();
        reentrantLock.lock();
        condition.await();
        reentrantLock.unlock();
    }

    @Test
    public void waitNotify() throws InterruptedException {
        new Thread(() -> {
            try {
                synchronized (BlockMainThreadTest.this) {
                    TimeUnit.SECONDS.sleep(5);
                    log.info(">>>>> THREAD_NAME: [{}]", Thread.currentThread().getName());
                    BlockMainThreadTest.this.notify();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        synchronized (this) {
            this.wait();
        }
    }

}

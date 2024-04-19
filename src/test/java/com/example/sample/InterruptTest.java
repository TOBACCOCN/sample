package com.example.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class InterruptTest {

	@Test
	public void canNotBeInterruptedWhileSynchronizeTryingLocking() throws InterruptedException {
		Object object = new Object();
		Runnable runnable = () -> {
			synchronized (object) {
				try {
					log.info("thread [{}] running", Thread.currentThread().getName());  // InterruptedException 发生在 t2 的这一句 log 之后说明 synchronized 没获取到锁不会被中断，若发生在这一句之前说明可中断
					Thread.sleep(10000);
					log.info("thread finished");
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		};

		Thread t1 =new Thread(runnable, "t1");
		t1.start();
		Thread.sleep(1000);


		Thread t2 =new Thread(runnable, "t2");
		t2.start();
		log.info("before interrupt");
		t2.interrupt();
		log.info("after interrupt");

		Thread.currentThread().join();
	}

	@Test
	public void canInterruptedWhileLockTryLocking() throws InterruptedException {
		Thread t = new Thread(() -> {
			Lock lock = new ReentrantLock();
			try {
				lock.lockInterruptibly();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		t.start();
		t.interrupt();
		Thread t1 = new Thread(() -> {
			Lock lock = new ReentrantLock();
			lock.lock();
			log.info("lock got");
		});
		t1.start();
		t1.interrupt();
		t.join();
		t1.join();
	}

}

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
		Lock lock = new ReentrantLock();
		Condition condition1 = lock.newCondition();
		Condition condition2 = lock.newCondition();
		Condition condition3 = lock.newCondition();
		Condition condition4 = lock.newCondition();

		final int[] num = {0};

		Thread t = new Thread(() -> {
			lock.lock();
			try {
				num[0] = 1;
				condition1.signal();
			} finally {
				lock.unlock();
			}
			synchronized (InterruptTest.this) {
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			log.info("t synchronized released");

			lock.lock();
			try {
				while (num[0] != 3) {
					condition3.await();
				}
				num[0] = 4;
				condition4.signal();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} finally {
				lock.unlock();
			}
		});

		Thread t1 = new Thread(() -> {
			lock.lock();
			try {
				while (num[0] != 1) {
					condition1.await();
				}
				num[0] = 2;
				condition2.signal();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} finally {
				lock.unlock();
			}

			synchronized (InterruptTest.this) {
				log.info("t1 synchronized lock get success");
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		});

		t.start();
		t1.start();

		lock.lock();
		try {
			while (num[0] != 2) {
				condition2.await();
			}
			t1.interrupt();
			log.info("interrupt t1 before t1 synchronize try locking...");
			num[0] = 3;
			condition3.signal();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			lock.unlock();
		}

		lock.lock();
		try {
			while (num[0] != 4) {
				condition4.await();
			}
			t1.interrupt();
			log.info("interrupt t1 after t1 synchronize get lock success...");
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			lock.unlock();
		}

		t1.join();
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
			log.info("lock get");
		});
		t1.start();
		t1.interrupt();
		t.join();
		t1.join();
	}

}

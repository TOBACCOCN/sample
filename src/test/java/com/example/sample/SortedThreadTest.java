package com.example.sample;

import com.example.sample.util.ErrorPrintUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SortedThreadTest {

	private Thread newThread(Thread joined) {
		return new Thread(() -> {
			try {
				if (joined != null) {
					joined.join();
				}
				String name = Thread.currentThread().getName();
				log.debug(">>>>> THREAD: [{}], RUN_FINISHED", name);
			} catch (InterruptedException e) {
				ErrorPrintUtil.printErrorMsg(log, e);
			}
		});
	}

	@Test
	public void sortThreadByJoin() throws InterruptedException {
		// Thread t1 = newThread(null);
		// t1.start();
		// Thread t2 = newThread(t1);
		// t2.start();
		// Thread t3 = newThread(t2);
		// t3.start();
		// t3.join();
		Thread next = null;
		for (int i = 0; i < 1000; i += 2) {
			Thread pre = newThread(next);
			pre.start();
			next = newThread(pre);
			next.start();
		}
		next.join();
	}

	// can not sort
	@Test
	public void sortThreadByWait() throws InterruptedException {
		new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			synchronized (SortedThreadTest.this) {
				String name = Thread.currentThread().getName();
				log.debug(">>>>> THREAD: [{}], RUN_FINISHED", name);
				// 唤醒使用 this wait 过的线程
				this.notify();
			}
		}).start();
		for (int i = 0; i < 1000; i++) {
			new Thread(() -> {
				synchronized (SortedThreadTest.this) {
					try {
						wait();
						String name = Thread.currentThread().getName();
						log.debug(">>>>> THREAD: [{}], RUN_FINISHED", name);
						this.notify();
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			}).start();
		}
		// 2023-08-11 19:11:48,443 [DEBUG] [Thread-1000] [SortedThreadTest.java:58] >>>>> THREAD: [Thread-1000], RUN_FINISHED
		// 2023-08-11 19:11:48,443 [DEBUG] [Thread-988] [SortedThreadTest.java:58] >>>>> THREAD: [Thread-988], RUN_FINISHED
		// indicate "wait" can not control thread's order

		Thread.currentThread().join();
	}

	// not suggested, need too many CountDownLatch when too many Threads
	@Test
	public void sortThreadByCountDownLatch() throws InterruptedException {
		// CountDownLatch latch = new CountDownLatch(1);
		// CountDownLatch latch1 = new CountDownLatch(1);
		// Thread t1 = newThread(latch, null, 10);
		// Thread t2 = newThread(latch1, latch, 5);
		// Thread t3 = newThread(null, latch1, 2);
		// t3.start();
		// t2.start();
		// t1.start();

		List<CountDownLatch> countDownLatches = new ArrayList<>();
		for (int i = 0; i < 999; i++) {
			countDownLatches.add(new CountDownLatch(1));
		}
		for (int i = 0; i < 1000; i++) {
			newThread(i == 999 ? null : countDownLatches.get(i), i == 0 ? null : countDownLatches.get(i - 1), 100 - i / 10).start();
		}

		Thread.currentThread().join();
	}

	private Thread newThread(CountDownLatch countDown, CountDownLatch wait, int miniSeconds) {
		return new Thread(() -> {
			if (wait != null) {
				try {
					wait.await();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			try {
				TimeUnit.MILLISECONDS.sleep(miniSeconds);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			String name = Thread.currentThread().getName();
			log.debug(">>>>> THREAD: [{}], RUN_FINISHED", name);
			if (countDown != null) {
				countDown.countDown();
			}
		});
	}

}

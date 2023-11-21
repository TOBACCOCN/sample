package com.example.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Semaphore;

@Slf4j
public class SemaphoreTest {



	@Test
	public void test() throws InterruptedException {
		Semaphore semaphore = new Semaphore(3);

		for (int i = 0; i < 6; i++) {
			new Thread(() -> {
				try {
					semaphore.acquire();
					log.info(Thread.currentThread().getName() + ", acquired");
					Thread.sleep(5000);
					semaphore.release();
					log.info(Thread.currentThread().getName() + ", released");
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}).start();
		}

		Thread.currentThread().join();
	}
}

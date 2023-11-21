package com.example.sample;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SynchronousQueueTest.class);

    BlockingQueue<String> synchronousQueue = new SynchronousQueue<>();

    @Test
    public void add() {
        // queue size is zero
        // java.lang.IllegalStateException: Queue full
        synchronousQueue.add("a");
    }

    @Test
    public void offer() {
        boolean offerResult = synchronousQueue.offer("a");
        LOGGER.info("offerResult: " + offerResult);
    }

    @Test
    public void put() throws InterruptedException {
        // may be blocked when queue is full
        Thread thread = new Thread(() -> {
            try {
                synchronousQueue.put("a");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        synchronousQueue.take();
        thread.join();
    }

    @Test
    public void offerTimeout() throws InterruptedException {
        boolean offerResult = synchronousQueue.offer("a", 5, TimeUnit.SECONDS);
        LOGGER.info("offerTimeoutResult: " + offerResult);
    }

    @Test
    public void remove() {
        // may throw NoSuchElementException when queue is empty
        String removeResult = synchronousQueue.remove();
        LOGGER.info("removeResult: " + removeResult);
    }

    @Test
    public void poll() {
        String pollResult = synchronousQueue.poll();
        LOGGER.info("pollResult: " + pollResult);
    }

    @Test
    public void take() throws InterruptedException {
        // may blocked when queue is empty
        Thread thread = new Thread(() -> {
            try {
                String takeResult = synchronousQueue.take();
                LOGGER.info("takeResult: " + takeResult);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        synchronousQueue.put("a");
        thread.join();
    }

    @Test
    public void pollTimeout() throws InterruptedException {
        String pollTimeoutResult = synchronousQueue.poll(3, TimeUnit.SECONDS);
        LOGGER.info("pollTimeoutResult: " + pollTimeoutResult);
    }

    @Test
    public void element() {
        // may throw NoSuchElementException when queue is empty
        String elementResult = synchronousQueue.element();
        LOGGER.info("elementResult: " + elementResult);
    }

    @Test
    public void peek() {
        String peekResult = synchronousQueue.peek();
        LOGGER.info("peekResult: " + peekResult);
    }

}

package com.example.sample;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ArrayBlockingQueueTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArrayBlockingQueueTest.class);

    BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

    private void fullFillQueue() {
        blockingQueue.add("a");
        blockingQueue.add("b");
        blockingQueue.add("c");
    }

    @Test
    public void add() {
        fullFillQueue();
        // may throw IllegalStateException when queue is full
        blockingQueue.add("a");
    }

    @Test
    public void offer() {
        fullFillQueue();
        boolean offerResult = blockingQueue.offer("a");
        LOGGER.info("offerResult: " + offerResult);
    }

    @Test
    public void put() throws InterruptedException {
        fullFillQueue();
        // may be blocked when queue is full
        blockingQueue.put("a");
    }

    @Test
    public void offerTimeout() throws InterruptedException {
        fullFillQueue();
        boolean offerResult = blockingQueue.offer("a", 5, TimeUnit.SECONDS);
        LOGGER.info("offerTimeoutResult: " + offerResult);
    }

    @Test
    public void remove() {
        // may throw NoSuchElementException when queue is empty
        String removeResult = blockingQueue.remove();
        LOGGER.info("removeResult: " + removeResult);
    }

    @Test
    public void poll() {
        String pollResult = blockingQueue.poll();
        LOGGER.info("pollResult: " + pollResult);
    }

    @Test
    public void take() throws InterruptedException {
        // may blocked when queue is empty
        String takeResult = blockingQueue.take();
        LOGGER.info("takeResult: " + takeResult);
    }

    @Test
    public void pollTimeout() throws InterruptedException {
        String pollTimeoutResult = blockingQueue.poll(3, TimeUnit.SECONDS);
        LOGGER.info("pollTimeoutResult: " + pollTimeoutResult);
    }

    @Test
    public void element() {
        // may throw NoSuchElementException when queue is empty
        String elementResult = blockingQueue.element();
        LOGGER.info("elementResult: " + elementResult);
    }

    @Test
    public void peek() {
        String peekResult = blockingQueue.peek();
        LOGGER.info("peekResult: " + peekResult);
    }

}

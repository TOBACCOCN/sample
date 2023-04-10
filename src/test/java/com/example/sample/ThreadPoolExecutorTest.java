package com.example.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;

// https://segmentfault.com/a/1190000041151766
@Slf4j
public class ThreadPoolExecutorTest {

    @Test
    public void showField() {
        int COUNT_BITS = Integer.SIZE - 3;
        int CAPACITY = (1 << COUNT_BITS) - 1;

        // runState is stored in the high-order bits
        int RUNNING = -1 << COUNT_BITS;
        int SHUTDOWN = 0 << COUNT_BITS;
        int STOP = 1 << COUNT_BITS;
        int TIDYING = 2 << COUNT_BITS;
        int TERMINATED = 3 << COUNT_BITS;
        log.info(">>>>> CAPACITY: [{}], RUNNING: [{}], SHUTDOWN: [{}], STOP: [{}], TIDYING: [{}], TERMINATED: [{}]",
                Integer.toBinaryString(CAPACITY), Integer.toBinaryString(RUNNING), Integer.toBinaryString(SHUTDOWN),
                Integer.toBinaryString(STOP), Integer.toBinaryString(TIDYING), Integer.toBinaryString(TERMINATED));
        // SHUTDOWN:                                                                            [0],
        // CAPACITY:                                [11111111111111111111111111111],
        // RUNNING:             [11100000000000000000000000000000],         // 32 位
        // STOP:                         [100000000000000000000000000000],         // 30 位
        // TIDYING:                [1000000000000000000000000000000],         // 31 位
        // TERMINATED:        [1100000000000000000000000000000]           // 31 位
    }

    @Test
    public void execute() throws InterruptedException {
        // 最多任务数为 maximumPoolSize + workQueue.capacity
        int corePoolSize = 1;
        int maximumPoolSize = 2;
        long keepAliveTime = 0;
        TimeUnit unit = TimeUnit.NANOSECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
        // BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(2);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        // RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        // RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        // RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);

        Runnable runnable = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(">>>>> runnable: [{}]", Thread.currentThread().getName());
        };
        Runnable runnable2 = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(">>>>> runnable2: [{}]", Thread.currentThread().getName());
        };
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        // executor.execute(runnable);      // AbortPolicy: java.util.concurrent.RejectedExecutionException: Task com.example.sample.ThreadPoolExecutorTest$$Lambda$1/1466073198@161b062a rejected from java.util.concurrent.ThreadPoolExecutor@2d9d4f9d[Running, pool size = 2, active threads = 2, queued tasks = 2, completed tasks = 0]
        // executor.execute(runnable);      // CallerRunsPolicy: 在调用者线程中 执行该 runnable 的 run 方法
        // executor.execute(runnable2);      // DiscardOldestPolicy: runnable2 中的 run 方法会挤掉其中一 runnable 的 run 方法
        executor.execute(runnable2);      // DiscardPolicy: 丢弃 runnable2 中 的 run 方法，不执行

        executor.shutdown();

        // Thread thread = new Thread(() -> {
        //     int i = 2 / 0;
        // });
        // thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
        //     @Override
        //     public void uncaughtException(Thread t, Throwable e) {
        //         System.out.println(">>>"  + e.getMessage());
        //     }
        // });
        // thread.start();

        Thread.currentThread().join();
    }

}

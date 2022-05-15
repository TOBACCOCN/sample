package com.example.sample.base;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j
public class SimpleFutureTask {

    public static void main(String[] args) {
        FutureTask futureTask = new FutureTask(() -> "SIMPLE_FUTURE_TASK_RESULT");
        new Thread(futureTask).start();
        try {
            String result  = (String) futureTask.get();
            log.info("result: [{}]", result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}

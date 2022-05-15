package com.example.sample.base;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SingletonTest01 {

    private static final SingletonTest01 INSTANCE = new SingletonTest01();

    private SingletonTest01() {

    }

    public static SingletonTest01 getInstanceNoneLazy() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        SingletonTest01 instance = getInstanceNoneLazy();
        SingletonTest01 instance1 = getInstanceNoneLazy();
        log.info(">>>>> instance == instance1: [{}]", instance == instance1);
        assert instance == instance1;
    }
}

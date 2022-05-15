package com.example.sample.base;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SingletonTest02 {

    private static SingletonTest02 INSTANCE_LAZY;

    private SingletonTest02() {

    }

    public static SingletonTest02 getInstanceLazy() {
        if (INSTANCE_LAZY == null) {
            synchronized (SingletonTest02.class) {
                if (INSTANCE_LAZY == null) {
                    INSTANCE_LAZY = new SingletonTest02();
                }
            }
        }
        return INSTANCE_LAZY;
    }

    public static void main(String[] args) {
        SingletonTest02 instance = getInstanceLazy();
        SingletonTest02 instance1 = getInstanceLazy();
        log.info(">>>>> instance == instance1: [{}]", instance == instance1);
        assert instance == instance1;
    }

}

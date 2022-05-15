package com.example.sample.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class SingletonTest03 {

    private SingletonTest03() {

    }

    private static final class InstanceLazyHolder {
        static final SingletonTest03 INSTANCE_LAZY = new SingletonTest03();
    }

    public static SingletonTest03 getInstanceLazy() {
        return InstanceLazyHolder.INSTANCE_LAZY;
    }

    public static void main(String[] args) {
        SingletonTest03 instance = getInstanceLazy();
        SingletonTest03 instance1 = getInstanceLazy();
        // 编译阶段都能识别出两个对象地址相同，即为同一对象
        log.info(">>>>> instance == instance1: [{}]", instance == instance1);
        assert instance == instance1;
    }

}

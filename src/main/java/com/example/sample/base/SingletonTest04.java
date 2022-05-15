package com.example.sample.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class SingletonTest04 {

    private SingletonTest04() {

    }

    enum SingletonEnum {
        INSTANCE;
        private final SingletonTest04 singletonTest04;

        SingletonEnum() {
            singletonTest04 = new SingletonTest04();
        }

        private SingletonTest04 getInstanceLazy() {
            return singletonTest04;
        }
    }


    public static SingletonTest04 getInstanceLazy() {
        return SingletonEnum.INSTANCE.getInstanceLazy();
    }

    public static void main(String[] args) {
        SingletonTest04 instance = getInstanceLazy();
        SingletonTest04 instance1 = getInstanceLazy();
        // 编译阶段都能识别出两个对象地址相同，即为同一对象
        log.info(">>>>> instance == instance1: [{}]", instance == instance1);
        assert instance == instance1;
    }

}

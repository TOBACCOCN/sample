package com.example.sample.base;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SingletonTest02 {

    private static volatile SingletonTest02 INSTANCE_LAZY;

    private SingletonTest02() {

    }

    public static SingletonTest02 getInstanceLazy() {
        if (INSTANCE_LAZY == null) {
            synchronized (SingletonTest02.class) {
                if (INSTANCE_LAZY == null) {
                    // DCL，在多线程中不能完全保证其他线程在获取 SingletonTest02 实例时是有效的
                    // new 操作编译成汇编指令时分成三部分，memory = allocate();instance(memory);INSTANCE_LAZY = memory;
                    // 这三条指令有可能会被重排成 memory = allocate();INSTANCE_LAZY = memory;instance(memory);，这重排后没有违反最终语义一致性
                    // 那么有可能出现线程 a 执行到 INSTANCE_LAZY = memory; 时，切换到线程 b，执行到 第一个 INSTANCE_LAZY == null 时，就会拿到不为空但是没有初始化的实例
                    // INSTANCE_LAZY 被 volatile 修饰后会禁止指令重排，就避免了该问题
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

package com.example.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

@Slf4j
public class CyclicBarrierTest {

    String[] sportsMans = {"肥罗", "小罗", "亨利", "C罗", "梅西", "马拉多纳", "贝利", "贝克汉姆", "卡西利亚斯", "布冯", "卡卡",
            "莫德里奇", "齐达内", "菲戈", "舍普琴科", "里贝里", "内马尔", "巴乔", "克洛泽", "苏亚雷斯", "拉莫斯", "埃托奥"};
    String[] actions = {"高速带球连续急停变向", "牛尾巴过人", "假射真传", "背身控球", "挑球过人", "头球破门", "倒挂金钩", "香蕉任意球", "飞身扑救", "蜘蛛侠式扑救", "挑射",
            "大脚抽射", "马赛回旋", "蝎子摆尾", "凌空抽射", "世界波破门", "单刀直入", "贴地斩", "插花脚射门", "脚后跟射门", "跳球转身抽射", "360度转身射门"};

    @Test
    public void test() throws InterruptedException {
        CyclicBarrier barrier =new CyclicBarrier(22, () -> {
            log.info("运动员到齐，比赛开始");
        });

        for (int i = 0; i < 22; i++) {
            int finalI = i;
            new Thread(() -> {
                log.info(sportsMans[finalI] + "出场");
                try {
                    barrier.await();
                    // await() 后的代码需要等到 barrier 的 action 执行完才会触发
                    log.info(sportsMans[finalI] + actions[finalI]);
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

        Thread.currentThread().join();
    }

}

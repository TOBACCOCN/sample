package com.example.sample.base;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Recursion {

    private static Logger logger = LoggerFactory.getLogger(Recursion.class);

    /**
     * @param n           盘子的个数
     * @param origin      源座
     * @param assist      辅助座
     * @param destination 目的座
     */
    private long hanoi(int n, char origin, char assist, char destination) {
        if (n == 1) {
            return 1;
        } else {
            long hanoi = hanoi(n - 1, origin, destination, assist);
            long hanoi1 = hanoi(n - 1, assist, origin, destination);
            return hanoi + hanoi1 + 1;
        }
    }

    @Test
    public void hanoi() {
        int n = 32;
        char origin = 'a';
        char assist = 'b';
        char destination = 'c';
        long hanoi = hanoi(n, origin, assist, destination);
        logger.info(">>>>> HANOI: {}", hanoi);
    }

}

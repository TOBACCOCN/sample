package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Implement int sqrt(int x).
 * Compute and return the square root of x, where x is guaranteed to be a non-negative integer.
 * Since the return type is an integer, the decimal digits are truncated and only the integer part of the result is returned.
 * Example 1:
 * Input: 4
 * Output: 2
 * Example 2:
 * Input: 8
 * Output: 2
 * Explanation: The square root of 8 is 2.82842..., and since
 * the decimal part is truncated, 2 is returned.
 *
 * @author zhangyonghong
 * @date 2020.2.27
 */
@Slf4j
public class Sqrt {

    // public int sqrt(int input) {
    //     int n = input;
    //     while (n * n > input) {
    //         n = (n + input / n) / 2;
    //     }
    //     return n;
    // }

    public int sqrt(int input) {
        int low = 0, high = input;
        int n = (low + input) / 2;
        while (low <= high) {
            if (n * n > input) {
                high = n - 1;
            } else if (n * n < input) {
                low = n + 1;
            } else {
                return n;
            }
            n = (low + high) / 2;
        }
        return n;
    }

    @Test
    public void sqrt() {
        int input = 986896869;
        long start = System.currentTimeMillis();
        log.info(">>>>> RESULT: [{}]", sqrt(input));
        log.info(">>>>> COST:[{}] MS", System.currentTimeMillis() - start);
    }

}

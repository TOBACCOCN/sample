package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Given a 32-bit signed integer, reverse digits of an integer.
 * Example 1:
 * Input: 123
 * Output: 321
 * Example 2:
 * Input: -123
 * Output: -321
 * Example 3:
 * Input: 120
 * Output: 21
 * Note:
 * Assume we are dealing with an environment which could only store integers within
 * the 32-bit signed integer range: [−231,  231 − 1]. For the purpose of this problem,
 * assume that your function returns 0 when the reversed integer overflows.
 *
 * @author zhangyonghong
 * @date 2020.2.25
 */
@Slf4j
public class ReverseInteger {

    public int reverseInteger(int num) {
        long result = 0;
        for (; num != 0; num /= 10) {
            result = result * 10 + num % 10;
        }
        return result > Integer.MAX_VALUE || result < Integer.MIN_VALUE ? 0 : (int) result;
    }

    @Test
    public void reverseInteger() {
        int num = -1340;
        log.info(">>>>> REVERSE_RESULT: [{}]", reverseInteger(num));
    }

}

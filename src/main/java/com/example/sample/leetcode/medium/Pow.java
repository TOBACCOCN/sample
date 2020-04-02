package com.example.sample.leetcode.medium;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Implement pow(x, n), which calculates x raised to the power n (xn).
 * Example 1:
 * Input: 2.00000, 10
 * Output: 1024.00000
 * Example 2:
 * Input: 2.10000, 3
 * Output: 9.26100
 * Example 3:
 * Input: 2.00000, -2
 * Output: 0.25000
 * Explanation: 2^-2 = 1/2^2 = 1/4 = 0.25
 * Note:
 * -100.0 < x < 100.0
 * n is a 32-bit signed integer, within the range [−231, 231 − 1]
 *
 * @author zhangyonghong
 * @date 2020.3.6
 */
@Slf4j
public class Pow {

    public double pow(double x, int n) {
        if (n == 0) {
            return 1;
        }

        if (n == 1) {
            return x;
        }

        double result = 1D;

        int left = Math.abs(n);
        while (left > 0) {
            double pow = x;
            int used = 1;
            int temp = left;
            while (temp > 1) {
                pow = pow * pow;
                temp = temp >> 1;
                used = used << 1;
            }
            left = left - used;
            result *= pow;
        }

        return n > 0 ? result : 1 / result;
    }

    @Test
    public void pow() {
        double x = 56D;
        int n = 34;
        log.info(">>>>> RESULT: [{}]", pow(x, n));
    }

}

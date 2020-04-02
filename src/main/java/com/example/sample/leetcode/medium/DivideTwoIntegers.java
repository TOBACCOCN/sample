package com.example.sample.leetcode.medium;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Given two integers dividend and divisor, divide two integers without using multiplication, division and mod operator.
 * Return the quotient after dividing dividend by divisor.
 * The integer division should truncate toward zero.
 * Example 1:
 * Input: dividend = 10, divisor = 3
 * Output: 3
 * Example 2:
 * Input: dividend = 7, divisor = -3
 * Output: -2
 * Note:
 * Both dividend and divisor will be 32-bit signed integers.
 * The divisor will never be 0.
 * Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−231, 231 − 1]. For the purpose of this problem, assume that your function returns 231 − 1 when the division result overflows.
 *
 * @author zhangyonghong
 * @date 2020.3.6
 */
@Slf4j
public class DivideTwoIntegers {

    public int divideTwoIntegers(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        int sign = (dividend > 0 && divisor > 0) || (dividend < 0 && divisor < 0) ? 1 : -1;

        int absEnd = Math.abs(dividend), absSor = Math.abs(divisor);
        int i = 0, quotient = 0;
        while (absEnd >= absSor) {
            absSor = absSor << 1;
            ++i;
        }
        quotient += 1 << --i;

        while (i > 0) {
            i = 0;
            absEnd -= absSor >> 1;
            absSor = Math.abs(divisor);
            while (absEnd >= absSor) {
                absSor = absSor << 1;
                ++i;
            }
            quotient += 1 << --i;
        }

        return quotient * sign;
    }

    @Test
    public void divideTwoIntegers() {
        int dividend = 623423, divisor = 1;
        log.info(">>>>> QUOTIENT: [{}]", divideTwoIntegers(dividend, divisor));
    }

}

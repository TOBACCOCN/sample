package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Determine whether an integer is a palindrome. An integer is a palindrome when it reads the same backward as forward.
 * Example 1:
 * Input: 121
 * Output: true
 * Example 2:
 * Input: -121
 * Output: false
 * Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
 * Example 3:
 * Input: 10
 * Output: false
 * Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
 * Follow up:
 * <p>
 * Coud you solve it without converting the integer to a string?
 *
 * @author zhangyonghong
 * @date 2020.2.25
 */
@Slf4j
public class PalindromeNumber {

    public boolean palindromeNumber(int num) {
        if (num < 0) {
            return false;
        }

        int copy = num, reverse = 0;
        while (copy > 0) {
            reverse = reverse * 10 + copy % 10;
            copy /= 10;
        }
        return num == reverse;
    }

    @Test
    public void palindromeNumber() {
        int num = 1234321;
        log.info(">>>>> INPUT: [{}], IS_PALINDROME_NUMBER: [{}]", num,  palindromeNumber(num));
    }

}

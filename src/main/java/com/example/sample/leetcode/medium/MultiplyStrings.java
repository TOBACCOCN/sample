package com.example.sample.leetcode.medium;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Given two non-negative integers num1 and num2 represented as strings, return the product of num1 and num2, also represented as a string.
 * Example 1:
 * Input: num1 = "2", num2 = "3"
 * Output: "6"
 * Example 2:
 * Input: num1 = "123", num2 = "456"
 * Output: "56088"
 * Note:
 * The length of both num1 and num2 is < 110.
 * Both num1 and num2 contain only digits 0-9.
 * Both num1 and num2 do not contain any leading zero, except the number 0 itself.
 * You must not use any built-in BigInteger library or convert the inputs to integer directly.
 *
 * @author zhangyonghong
 * @date 2020.3.6
 */
@Slf4j
public class MultiplyStrings {

    public String multiplyStrings(String num1, String num2) {
        int l1 = num1.length();
        int l2 = num2.length();
        int[] answer = new int[l1 + l2];
        for (int i = 0; i < l1; ++i) {
            for (int j = 0; j < l2; ++j) {
                int p = (num1.charAt(l1 - 1 - i) - '0') * (num2.charAt(l2 - 1 - j) - '0');
                int n = answer[i + j] + p;
                answer[i + j] = n % 10;
                answer[i + j + 1] += n / 10;
            }
        }

        int carry = 0;  // 进位
        for (int x = answer.length - 1; x >= 0; --x) {
            int r = answer[x] + carry;
            if (r > 9) {
                carry += (r / 10);
                answer[x] = r % 10;
            }
        }

        StringBuilder sb = new StringBuilder();
        int i = -1;
        while (++i < answer.length) {
            sb.insert(0, answer[i]);
        }

        String result = sb.toString();
        while (result.startsWith("0")) {
            result = result.substring(1);
        }
        return result;
    }

    @Test
    public void multiplyStrings() {
        String num1 = "234234534534534543653465743523", num2 = "23";
        long start = System.currentTimeMillis();
        log.info(">>>>> RESULT: [{}]", multiplyStrings(num1, num2));
        log.info(">>>>> COST: [{}] MS", System.currentTimeMillis() - start);
    }

}

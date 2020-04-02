package com.example.sample.leetcode.medium;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Collections;

/**
 * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
 * Symbol       Value
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * For example, two is written as II in Roman numeral, just two one's added together. Twelve is written as, XII, which is simply X + II. The number twenty seven is written as XXVII, which is XX + V + II.
 * Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII. Instead, the number four is written as IV. Because the one is before the five we subtract it making four. The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:
 * I can be placed before V (5) and X (10) to make 4 and 9.
 * X can be placed before L (50) and C (100) to make 40 and 90.
 * C can be placed before D (500) and M (1000) to make 400 and 900.
 * Given an integer, convert it to a roman numeral. Input is guaranteed to be within the range from 1 to 3999.
 * Example 1:
 * Input: 3
 * Output: "III"
 * Example 2:
 * Input: 4
 * Output: "IV"
 * Example 3:
 * Input: 9
 * Output: "IX"
 * Example 4:
 * Input: 58
 * Output: "LVIII"
 * Explanation: C = 100, L = 50, XXX = 30 and III = 3.
 * Example 5:
 * Input: 1994
 * Output: "MCMXCIV"
 * Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
 *
 * @author zhangyonghong
 * @date 2020.3.4
 */
@Slf4j
public class IntegerToRoman {

    int n;

    public String integer2Roman(int num) {
        this.n = num;
        StringBuilder builder = new StringBuilder();
        int i = n / 1000;
        if (i > 0) {
            for (int x = 0; x < i; ++x) {
                builder.append('M');
            }
            n = n - 1000 * i;
        }

        handleDigit(builder, 100, 'M', 'D', 'C');
        handleDigit(builder, 10, 'C', 'L', 'X');
        handleDigit(builder, 1, 'X', 'V', 'I');

        return builder.toString();
    }

    private void handleDigit(StringBuilder builder, int divide, char ten, char five, char one) {
        int i;
        i = n / divide;
        if (i > 0) {
            if (i == 9) {
                builder.append(one).append(ten);
            } else if (i >= 5) {
                builder.append(five).append(Collections.nCopies((i - 5), one));
            } else if (i == 4) {
                builder.append(one).append(five);
            } else {
                builder.append(Collections.nCopies(i, one));
            }
            n = n - divide * i;
        }
    }

    @Test
    public void integer2Roman() {
        int num = 3494;
        log.info(">>>>> ROMAN: [{}]", integer2Roman(num));
    }

}

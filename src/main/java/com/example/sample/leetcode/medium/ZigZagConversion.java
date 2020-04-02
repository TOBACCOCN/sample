package com.example.sample.leetcode.medium;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: (you may want to display this pattern in a fixed font for better legibility)
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * And then read line by line: "PAHNAPLSIIGYIR"
 * Write the code that will take a string and make this conversion given a number of rows:
 * string convert(string s, int numRows);
 * Example 1:
 * Input: s = "PAYPALISHIRING", numRows = 3
 * Output: "PAHNAPLSIIGYIR"
 * Example 2:
 * Input: s = "PAYPALISHIRING", numRows = 4
 * Output: "PINALSIGYAHRPI"
 * Explanation:
 * P     I    N
 * A   L S  I G
 * Y A   H R
 * P     I
 *
 * @author zhangyonghong
 * @date 2020.3.3
 */
@Slf4j
public class ZigZagConversion {

    public String zigZagConversion(String s, int rows) {
        int len;
        if (s == null || (len = s.length()) == 0 || rows <= 1) {
            return s;
        }

        StringBuilder builder = new StringBuilder();
        int index;
        for (int i = 0; i < rows; i++) {
            index = i;
            while (index < len) {
                builder.append(s.charAt(index));
                // 下一个索引的规律
                index += 2 * (rows - i - 1);
                if (i != 0 && i != rows - 1 && index < len) {
                    builder.append(s.charAt(index));
                }
                index += 2 * i;
            }
        }
        return builder.toString();
    }

    @Test
    public void zigZagConversion() {
        String s = "helloworld";
        String base = "abcdefghijklmnopqrstuvwzxyABCDEFGHIJKLMNOPQRSTUVWZXY";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            builder.append(base.charAt((int) (Math.random() * 52)));
        }
        s = builder.toString();
        log.info(">>>>> [{}]", s);
        int rows = 5;
        long start = System.currentTimeMillis();
        log.info(">>>>> RESULT: [{}]", zigZagConversion(s, rows));
        log.info(">>>>> COST: [{}] MS", System.currentTimeMillis() - start);
    }

}

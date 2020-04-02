package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author zhangyonghong
 * @date 2020.2.27
 */
@Slf4j
public class AddBinary {

    public String addBinary(String a, String b) {
        int p1 = a.length() - 1, p2 = b.length() - 1;
        int sum = 0;
        StringBuilder builder = new StringBuilder();
        while (p1 >= 0 && p2 >= 0) {
            sum += a.charAt(p1--) - '0';
            sum += b.charAt(p2--) - '0';
            builder.insert(0, sum & 1);
            sum >>= 1;
        }

        while (p1 >= 0) {
            sum += a.charAt(p1--) - '0';
            builder.insert(0, sum & 1);
            sum >>= 1;
        }

        while (p2 >= 0) {
            sum += b.charAt(p2--) - '0';
            builder.insert(0, sum & 1);
            sum >>= 1;
        }

        if (sum != 0) {
            builder.insert(0, sum);
        }
        return builder.toString();
    }

    @Test
    public void addBinary() {
        String a = "10010";
        String b = "110000";
        log.info(">>>>> RESULT: [{}]", addBinary(a, b));
    }

}

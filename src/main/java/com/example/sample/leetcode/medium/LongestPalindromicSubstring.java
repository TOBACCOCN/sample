package com.example.sample.leetcode.medium;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.
 * Example 1:
 * Input: "babad"
 * Output: "bab"
 * Note: "aba" is also a valid answer.
 * Example 2:
 * Input: "cbbd"
 * Output: "bb"
 *
 * @author zhangyonghong
 * @date 2020.3.2
 */
@Slf4j

public class LongestPalindromicSubstring {

    public String longestPalindromicSubstring(String s) {
        Runtime runtime = Runtime.getRuntime();
        long freeMemory = runtime.freeMemory();
        int len = s.length();
        String str = "";
        // 时间复杂度 O(n^2 / 2)
        for (int i = 0; i < len; ++i) {
            for (int j = len - 1; j > i; --j) {
                if (s.charAt(i) == s.charAt(j)) {
                    StringBuilder builder = new StringBuilder(s.substring(i, j + 1));
                    if (builder.toString().equals(builder.reverse().toString())) {
                        if (builder.length() > str.length()) {
                            str = builder.toString();
                        }
                    }
                }
            }
        }
        log.info(">>>>> USE_MEMORY: [{}]", freeMemory - runtime.freeMemory());
        return str;

        // not passed
        // int len = s.length();
        // Map<Character, Integer> map = new HashMap<>();
        // int preP = 0;
        // String str = "";
        // for (int i = 0; i < len; ++i) {
        //     char c = s.charAt(i);
        //     if (map.containsKey(c) /*&& map.get(c) > preP*/) {
        //         preP = map.get(c);
        //         StringBuilder builder = new StringBuilder(s.substring(preP, i + 1));
        //         if (builder.toString().equals(builder.reverse().toString())
        //                 && (builder.length() > str.length())) {
        //             str = builder.toString();
        //         } else {
        //             map.put(c, i);
        //         }
        //     } else {
        //         map.put(c, i);
        //     }
        // }
        // return str;
    }

    @Test
    public void longestPalindromicSubstring() {
        String base = "abcdefghijklmnopqrstuvwzxyABCDEFGHIJKLMNOPQRSTUVWZXY";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 800; i++) {
            builder.append(base.charAt((int) (Math.random() * 52)));
        }
        String s = builder.toString();
        // String s = "abcdefgfedxcxdef";
        log.info(">>>>> [{}]", s);
        long start = System.currentTimeMillis();
        log.info(">>>>> RESULT: [{}]", longestPalindromicSubstring(s));
        log.info(">>>>> COST:[{}] MS", System.currentTimeMillis() - start);
    }

}

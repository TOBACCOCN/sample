package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Implement strStr().
 * Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
 * Example 1:
 * Input: haystack = "hello", needle = "ll"
 * Output: 2
 * Example 2:
 * Input: haystack = "aaaaa", needle = "bba"
 * Output: -1
 * Clarification:
 * What should we return when needle is an empty string? This is a great question to ask during an interview.
 * For the purpose of this problem, we will return 0 when needle is an empty string. This is consistent to C's strstr() and Java's indexOf().
 *
 * @author zhangyonghong
 * @date 2020.2.26
 */
@Slf4j
public class ImplementStrStr {

    public int implementStrStr(String haystack, String needle) {
        int l1 = haystack.length(), l2 = needle.length();
        if (l1 < l2) {
            return -1;
        }

        for (int i = 0; ; i++) {
            if (i + l2 > l1) {
                return -1;
            }

            for (int j = 0; ; j++) {
                if (j == l2) {
                    return i;
                }
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    break;
                }
            }
        }
    }

    @Test
    public void implementStrStr() {
        String haystack = "hello";
        String needle = "ll";
        log.info(">>>>> INDEX: [{}]", implementStrStr(haystack, needle));
    }

}

package com.example.sample.leetcode.hard;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'.
 * '.' Matches any single character.
 * '*' Matches zero or more of the preceding element.
 * The matching should cover the entire input string (not partial).
 * Note:
 * s could be empty and contains only lowercase letters a-z.
 * p could be empty and contains only lowercase letters a-z, and characters like . or *.
 * Example 1:
 * Input:
 * s = "aa"
 * p = "a"
 * Output: false
 * Explanation: "a" does not match the entire string "aa".
 * Example 2:
 * Input:
 * s = "aa"
 * p = "a*"
 * Output: true
 * Explanation: '*' means zero or more of the precedeng element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
 * Example 3:
 * Input:
 * s = "ab"
 * p = ".*"
 * Output: true
 * Explanation: ".*" means "zero or more (*) of any character (.)".
 * Example 4:
 * Input:
 * s = "aab"
 * p = "c*a*b"
 * Output: true
 * Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore it matches "aab".
 * Example 5:
 * Input:
 * s = "mississippi"
 * p = "mis*is*p*."
 * Output: false
 *
 * @author zhangyonghong
 * @date 2020.3.9
 */
@Slf4j
public class RegularExpressionMatching {

    public boolean regularExpressionMatching(String s, String p) {
        // official
        // boolean[][] dp = new boolean[text.length() + 1][pattern.length() + 1];
        // dp[text.length()][pattern.length()] = true;
        //
        // for (int i = text.length(); i >= 0; i--) {
        //     for (int j = pattern.length() - 1; j >= 0; j--) {
        //         boolean first_match = (i < text.length() &&
        //                 (pattern.charAt(j) == text.charAt(i) ||
        //                         pattern.charAt(j) == '.'));
        //         if (j + 1 < pattern.length() && pattern.charAt(j + 1) == '*') {
        //             dp[i][j] = dp[i][j + 2] || first_match && dp[i + 1][j];
        //         } else {
        //             dp[i][j] = first_match && dp[i + 1][j + 1];
        //         }
        //     }
        // }
        // return dp[0][0];

        if (p.isEmpty()) {
            return s.isEmpty();
        }

        if (p.length() == 1) {
            return s.length() == 1 && (p.charAt(0) == '.' || s.charAt(0) == p.charAt(0));
        }

        boolean firstMatch = !s.isEmpty() && (p.charAt(0) == '.' || p.charAt(0) == s.charAt(0));
        if (p.charAt(1) != '*') {
            return firstMatch && regularExpressionMatching(s.substring(1), p.substring(1));
        }

        return regularExpressionMatching(s, p.substring(2))
                || (firstMatch && regularExpressionMatching(s.substring(1), p));
    }

    @Test
    public void regularExpressionMatching() {
        String s = "baabcbc";
        // String p = ".*bc";
        String p = "aa.*bc*";
        log.info(">>>>> RESULT:[{}]", regularExpressionMatching(s, p));
    }

}

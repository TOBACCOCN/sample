package com.example.sample.leetcode.hard;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*'.
 * '?' Matches any single character.
 * '*' Matches any sequence of characters (including the empty sequence).
 * The matching should cover the entire input string (not partial).
 * Note:
 * s could be empty and contains only lowercase letters a-z.
 * p could be empty and contains only lowercase letters a-z, and characters like ? or *.
 * Example 1:
 * Input:
 * s = "aa"
 * p = "a"
 * Output: false
 * Explanation: "a" does not match the entire string "aa".
 * Example 2:
 * Input:
 * s = "aa"
 * p = "*"
 * Output: true
 * Explanation: '*' matches any sequence.
 * Example 3:
 * Input:
 * s = "cb"
 * p = "?a"
 * Output: false
 * Explanation: '?' matches 'c', but the second letter is 'a', which does not match 'b'.
 * Example 4:
 * Input:
 * s = "adceb"
 * p = "*a*b"
 * Output: true
 * Explanation: The first '*' matches the empty sequence, while the second '*' matches the substring "dce".
 * Example 5:
 * Input:
 * s = "acdcb"
 * p = "a*c?b"
 * Output: false
 *
 * @author zhangyonghong
 * @date 2020.3.13
 */
@Slf4j
public class WildcardMatching {

    public boolean wildcardMatching(String s, String p) {
        int si = 0, pi = 0, match = 0, star = -1, sl = s.length(), pl = p.length();
        char[] sc = s.toCharArray(), pc = p.toCharArray();
        while (si < sl) {
            if (pi < pl && (pc[pi] == '?' || sc[si] == pc[pi])) {
                ++si;
                ++pi;
            } else if (pi < pl && pc[pi] == '*') {
                match = si;
                star = ++pi;
            } else if (pi < pl && star != -1) {
                si = ++match;
                pi = star + 1;
            } else {
                return false;
            }
        }
        while (pi < pl && pc[pi] == '*') {
            ++pi;
        }
        return pi == pl;
    }

    @Test
    public void wildcardMatching() {
        String s = "abcbcdefg", p = "a*bc*e?g";
        log.info(">>>>> MATCHED: [{}]", wildcardMatching(s, p));
    }

}

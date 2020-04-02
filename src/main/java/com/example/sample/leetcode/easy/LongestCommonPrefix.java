package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Write a function to find the longest common prefix string amongst an array of strings.
 * If there is no common prefix, return an empty string "".
 * Example 1:
 * Input: ["flower","flow","flight"]
 * Output: "fl"
 * Example 2:
 * Input: ["dog","racecar","car"]
 * Output: ""
 * Explanation: There is no common prefix among the input strings.
 * Note:
 * All given inputs are in lowercase letters a-z.
 *
 * @author zhangyonghong
 * @date 2020.2.25
 */
@Slf4j
public class LongestCommonPrefix {

    public String longestCommonPrefix(String[] strings) {
        if (strings.length == 0) {
            return "";
        }

        String prefix = strings[0];
        for (int i = 1; i < strings.length; i++) {
            while (strings[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }
        }
        return prefix;
    }

    @Test
    public void longestCommonPrefix() {
        String[] strings = {"zyh", "zys", "zya", "zyb", "zyc", "zyd"};
        log.info(">>>>> LONGEST_COMMON_PREFIX: [{}]", longestCommonPrefix(strings));
    }

}

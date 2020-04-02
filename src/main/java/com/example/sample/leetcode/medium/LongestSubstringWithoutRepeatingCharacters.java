package com.example.sample.leetcode.medium;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Given a string, find the length of the longest substring without repeating characters.
 * Examples:
 * Given "abcabcbb", the answer is "abc", which the length is 3.
 * Given "bbbbb", the answer is "b", with the length of 1.
 * Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 *
 * @author zhangyonghong
 * @date 2020.3.2
 */
@Slf4j
public class LongestSubstringWithoutRepeatingCharacters {

    public int lengthOfLongestSubstringWithoutRepeatingCharacters(String s) {
        // int p = -1;
        // StringBuilder subString = new StringBuilder();
        // String max = "";
        // while (++p < s.length()) {
        //     char c = s.charAt(p);
        //     int index = subString.toString().indexOf(c);
        //     if (index == -1) {
        //         subString.append(c);
        //     } else {
        //         if (subString.length() > max.length()) {
        //             max = subString.toString();
        //         }
        //         subString = new StringBuilder(subString.substring(index + 1) + c);
        //     }
        // }
        //
        // log.info(">>>>> MAX: [{}]", max);
        // log.info(">>>>> SUBSTRING: [{}]", subString);
        // return Math.max(max.length(), subString.length());

        int len;
        if (s == null || (len = s.length()) == 0) {
            return 0;
        }

        int[] hash = new int[256];
        int preP = 0, max = 0;
        for (int i = 0; i < len; ++i) {
            char c = s.charAt(i);
            int index = hash[c];
            if (index > preP) {
                preP = index;
            }

            hash[c] = i + 1;
            int l = i - preP + 1;
            if (l > max) {
                max = l;
            }
        }
        return max;
    }

    @Test
    public void lengthOfLongestSubstringWithoutRepeatingCharacters() {
        // String s = "agoisdfighdagkl;dskgsdlfjg";
        // String s = "agoisdfighdagkl;dskgsdlfjgjaflasfjskdfjkasdfjlsdfjskjsdfljsazxcvbkfjasdkfjs";
        String s = "bcdecfgc";
        // String base = "abcdefghijklmnopqrstuvwzxyABCDEFGHIJKLMNOPQRSTUVWZXY";
        // StringBuilder builder = new StringBuilder();
        // for (int i = 0; i < 100; i++) {
        //     builder.append(base.charAt((int) (Math.random() * 52)));
        // }
        // s = builder.toString();
        // log.info(">>>>> [{}]", input);
        long start = System.currentTimeMillis();
        log.info(">>>>> LENGTH: [{}]", lengthOfLongestSubstringWithoutRepeatingCharacters(s));
        log.info(">>>>> COST: [{}] MS", System.currentTimeMillis() - start);
    }

}

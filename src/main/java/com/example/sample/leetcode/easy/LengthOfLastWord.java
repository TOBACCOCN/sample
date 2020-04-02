package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Given a string s consists of upper/lower-case alphabets and empty space characters ' ', return the length of last word (last word means the last appearing word if we loop from left to right) in the string.
 * If the last word does not exist, return 0.
 * Note: A word is defined as a maximal substring consisting of non-space characters only.
 * Example:
 * Input: "Hello World"
 * Output: 5
 *
 * @author zhangyonghong
 * @date 2020.2.26
 */
@Slf4j
public class LengthOfLastWord {

    public int lengthOfLastWord(String string) {
        int p = string.length() - 1;
        while (p >= 0 && string.charAt(p) == ' ') {
            p--;
        }

        int end = p;
        while (p >= 0 && string.charAt(p) != ' ') {
            p--;
        }
        return end - p;
    }

    @Test
    public void lengthOfLastWord() {
        String string = "Hello world ";
        log.info(">>>>> LENGTH: [{}]", lengthOfLastWord(string));
    }

}

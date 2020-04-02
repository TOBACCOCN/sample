package com.example.sample.leetcode.medium;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Given a digit string, return all possible letter combinations that the number could represent.
 * A mapping of digit to letters (just like on the telephone buttons) is given below.
 * Example:
 * Input: "23"
 * Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 * Note:
 * Although the above answer is in lexicographical order, your answer could be in any order you want.
 * img:
 * https://camo.githubusercontent.com/2ae58f3cbb766ae5254cd35e27b7772382ba88a6/68747470733a2f2f75706c6f61642e77696b696d656469612e6f72672f77696b6970656469612f636f6d6d6f6e732f7468756d622f372f37332f54656c6570686f6e652d6b6579706164322e7376672f32303070782d54656c6570686f6e652d6b6579706164322e7376672e706e67
 *
 * @author zhangyonghong
 * @date 2020.3.4
 */
@Slf4j
public class LetterCombinationsOfPhoneNumber {

    private String[] array = {"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    public List<String> letterCombinationsOfPhoneNumber(String s) {
        if (s.length() == 0) {
            return Collections.emptyList();
        }

        List<String> list = new ArrayList<>();
        letterCombinations(list, s, "");
        return list;
    }

    private void letterCombinations(List<String> list, String digits, String ans) {
        if (ans.length() == digits.length()) {
            list.add(ans);
            return;
        }
        for (char c : array[digits.charAt(ans.length()) - '2'].toCharArray()) {
            letterCombinations(list, digits, ans + c);
        }
    }

    @Test
    public void letterCombinationsOfPhoneNumber() {
        String s = "23456789";
        log.info(">>>>> RESULT: [{}]", letterCombinationsOfPhoneNumber(s));
    }

}

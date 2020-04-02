package com.example.sample.leetcode.hard;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

/**
 * You are given a string, s, and a list of words, words, that are all of the same length. Find all starting indices of substring(s) in s that is a concatenation of each word in words exactly once and without any intervening characters.
 * Example 1:
 * Input:
 * s = "barfoothefoobarman",
 * words = ["foo","bar"]
 * Output: [0,9]
 * Explanation: Substrings starting at index 0 and 9 are "barfoo" and "foobar" respectively.
 * The output order does not matter, returning [9,0] is fine too.
 * Example 2:
 * Input:
 * s = "wordgoodgoodgoodbestword",
 * words = ["word","good","best","word"]
 * Output: []
 *
 * @author zhangyonghong
 * @date 2020.3.13
 */
@Slf4j
public class SubstringWithConcatenationOfAllWords {

    public List<Integer> substringWithConcatenationOfAllWords(String s, List<String> words) {
        long free = Runtime.getRuntime().freeMemory();
        if (s == null || s.isEmpty() || words.size() == 0) {
            return new ArrayList<>();
        }

        Map<String, Integer> map = new HashMap<>();
        int p = words.size();
        while (--p >= 0) {
            String word = words.get(p);
            if (map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, 1);
            }
        }

        List<Integer> list = new ArrayList<>();
        int len = words.get(0).length();
        int start = 0, end = len;
        while (end < s.length()) {
            Map<String, Integer> temp = new HashMap<>(map);
            int index = -1;
            while (temp.size() > 0 && end < s.length()) {
                String word = s.substring(start, end);
                Integer count = temp.get(word);
                if (count != null) {
                    if (index == -1) {
                        index = start;
                    }

                    if (count > 1) {
                        temp.put(word, temp.get(word) - 1);
                    } else {
                        temp.remove(word);
                    }
                    start += len;
                    end += len;
                } else {
                    ++start;
                    ++end;
                }

            }

            if (temp.size() == 0) {
                list.add(index);
            }
        }

        log.info(">>>>> MEMORY_USE: [{}] BYTES", free - Runtime.getRuntime().freeMemory());
        return list;
    }

    @Test
    public void substringWithConcatenationOfAllWords() {
        String s = "cabcdabefghijklmnopqabcdefabghijklmnopq";
        List<String> words = Arrays.asList("ab", "ab", "ef", "cd");
        log.info(">>>>> RESULT: [{}]", substringWithConcatenationOfAllWords(s, words));

    }

}

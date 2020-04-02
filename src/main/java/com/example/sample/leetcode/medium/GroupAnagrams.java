package com.example.sample.leetcode.medium;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

/**
 * Given an array of strings, group anagrams together.
 * Example:
 * Input: ["eat", "tea", "tan", "ate", "nat", "bat"],
 * Output:
 * [
 * ["ate","eat","tea"],
 * ["nat","tan"],
 * ["bat"]
 * ]
 * Note:
 * All inputs will be in lowercase.
 * The order of your output does not matter.
 *
 * @author zhangyonghong
 * @date 2020.3.6
 */
@Slf4j
public class GroupAnagrams {

    public List<List<String>> groupAnagrams(String[] strings) {
        int i = strings.length;
        Map<String, List<String>> map = new HashMap<>();
        while (--i >= 0) {
            char[] chars = strings[i].toCharArray();
            Arrays.sort(chars);
            String s = String.valueOf(chars);
            if (!map.containsKey(s)) {
                List<String> sub = new ArrayList<>();
                sub.add(strings[i]);
                map.put(s, sub);
            } else {
                map.get(s).add(strings[i]);
            }
        }

        return new ArrayList<>(map.values());
    }

    @Test
    public void groupAnagrams() {
        String[] strings = {"abc", "cba", "eat", "tea", "ate", "bat"};
        log.info(">>>>> RESULT: [{}]", groupAnagrams(strings));
    }

}

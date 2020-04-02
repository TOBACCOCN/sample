package com.example.sample.leetcode.medium;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 * For example, given n = 3, a solution set is:
 * [
 * "((()))",
 * "(()())",
 * "(())()",
 * "()(())",
 * "()()()"
 * ]
 *
 * @author zhangyonghong
 * @date 2020.3.5
 */
@Slf4j
public class GenerateParentheses {

    public List<String> generateParentheses(int n) {
        List<String> list = new ArrayList<>();
        generate(list, "", 0, 0, n);
        return list;
    }

    private void generate(List<String> list, String cur, int open, int close, int max) {
        if (cur.length() == max * 2) {
            list.add(cur);
            return;
        }

        if (open < max) {
            generate(list, cur + "(", open + 1, close, max);
        }
        if (close < open) {
            generate(list, cur + ")", open, close + 1, max);
        }
    }

    @Test
    public void generateParentheses() {
        int n = 5;
        log.info(">>>>> RESULT: [{}]", generateParentheses(n));
    }

}

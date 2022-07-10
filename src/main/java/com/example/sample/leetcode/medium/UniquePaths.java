package com.example.sample.leetcode.medium;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
 * 问总共有多少条不同的路径？
 * 输入：m = 3, n = 7
 * 输出：28
 * 输入：m = 3, n = 2
 * 输出：3
 * 输入：m = 7, n = 3
 * 输出：28
 * 输入：m = 3, n = 3
 * 输出：6
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：<a href="https://leetcode.cn/problems/unique-paths">...</a>
 *
 * <a href="https://mp.weixin.qq.com/s?__biz=MzUxNjY5NTYxNA==&mid=2247497736&idx=2&sn=fc0b658cb197ba33daef75cd7cc88db6&scene=21#wechat_redirect">...</a>
 *
 * @author TOBACCO
 * @date 2022/5/26
 */
@Slf4j
public class UniquePaths {

    @Test
    public void uniquePaths() {
        int m = 3, n = 5;
        log.info(">>>>> uniquePaths: [{}]", uniquePathsByDP(m, n));
        log.info(">>>>> uniquePaths: [{}]", uniquePathsByCombination(m, n));
    }

    private int uniquePathsByDP(int m, int n) {
        int max = Math.max(m, n);
        int[] dp = new int[max];
        for (int i = 0; i < max; ++i) {
            dp[i] = 1;
        }
        for (int i = 1; i < m; ++i) {
            for (int j = 1; j < n; ++j) {
                dp[j] += dp[j - 1];
            }
        }
        return dp[max - 1];
    }

    private int uniquePathsByCombination(int m, int n) {
        int numerator = m + n - 2;
        int denominator = m - 1;
        int result = 1;
        int count = m - 1;
        while (count-- > 0) {
            result *= numerator--;
            while (denominator > 0 && result % denominator == 0) {
                result /= denominator--;
            }
        }
        return result;
    }

}

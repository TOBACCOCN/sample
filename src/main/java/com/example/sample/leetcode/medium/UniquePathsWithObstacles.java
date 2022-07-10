package com.example.sample.leetcode.medium;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Scanner;

/**
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish”）。
 * 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
 * 网格中的障碍物和空位置分别用 1 和 0 来表示。
 * 输入：obstacleGrid = [[0,1],[0,0]]
 * 输出：1
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：<a href="https://leetcode.cn/problems/unique-paths-ii">...</a>
 *
 * <a href="https://mp.weixin.qq.com/s?__biz=MzUxNjY5NTYxNA==&mid=2247497756&idx=2&sn=ae38ef57cc1a9db8f39d498ece775311&scene=21#wechat_redirect">...</a>
 *
 * @author TOBACCO
 * @date 2022/5/26
 */
@Slf4j
public class UniquePathsWithObstacles {

    @Test
    public void uniquePathsWithObstacles() {
        int[][] array = new int[][]{{0, 0, 0, 0, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}};
        log.info(">>>>> uniquePathsWithObstacles: [{}]", uniquePathsWithObstacles(array));
    }

    private int uniquePathsWithObstacles(int[][] array) {
        int m = array.length, n = array[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m && array[i][0] == 0; ++i) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n && array[0][j] == 0; ++j) {
            dp[0][j] = 1;
        }

        for (int i = 1; i < m; ++i) {
            for (int j = 1; j < n; ++j) {
                if (array[i][j] == 1) {
                    continue;
                }
                dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
            }
        }
        return dp[dp.length - 1][n - 1];
    }

}

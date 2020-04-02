package com.example.sample.leetcode.medium;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai). n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). Find two lines, which together with x-axis forms a container, such that the container contains the most water.
 * Note: You may not slant the container and n is at least 2.
 * https://leetcode-cn.com/problems/container-with-most-water/solution/sheng-zui-duo-shui-de-rong-qi-by-leetcode/
 *
 * @author zhangyonghong
 * @date 2020.3.4
 */
@Slf4j
public class ContainerOfMostWater {

    public int containerOfMostWater(int[] height) {
        int l = 0, r = height.length - 1, max = 0;
        while (l < r) {
            max = Math.max(max, Math.min(height[r], height[l]) * (r - l));
            if (height[l] < height[r]) {
                ++l;
            } else {
                --r;
            }
        }
        return max;
    }

    @Test
    public void containerOfMostWater() {
        // int[] height = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5, 8};
        int[] height = {3, 1, 4, 1, 5, 8, 100, 6, 5, 3, 5, 8};
        log.info(">>>>> RESULT: [{}]", containerOfMostWater(height));
    }

}

package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
 * Example:
 * Input: [-2,1,-3,4,-1,2,1,-5,4],
 * Output: 6
 * Explanation: [4,-1,2,1] has the largest sum = 6.
 * Follow up:
 * If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
 *
 * @author zhangyonghong
 * @date 2020.2.26
 */
@Slf4j
public class MaximumSubarray {

    public int maximumSubarray(int[] nums) {
        int len = nums.length, dp = nums[0], max = dp;
        for (int i = 1; i < len; ++i) {
            dp = nums[i] + (Math.max(dp, 0));
            if (dp > max) {
                max = dp;
            }
        }
        return max;
    }

    @Test
    public void maximumSubarray() {
        int[] nums = {1, 2, 3, -4, -5, 3, 6};
        log.info(">>>>> SUM: [{}]", maximumSubarray(nums));
    }

}

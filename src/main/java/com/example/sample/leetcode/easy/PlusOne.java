package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Given a non-empty array of digits representing a non-negative integer, plus one to the integer.
 * The digits are stored such that the most significant digit is at the head of the list, and each element in the array contain a single digit.
 * You may assume the integer does not contain any leading zero, except the number 0 itself.
 * Example 1:
 * Input: [1,2,3]
 * Output: [1,2,4]
 * Explanation: The array represents the integer 123.
 * Example 2:
 * Input: [4,3,2,1]
 * Output: [4,3,2,2]
 * Explanation: The array represents the integer 4321.
 *
 * @author zhangyonghong
 * @date 2020.2.27
 */
@Slf4j
public class PlusOne {

    public int[] plusOne(int[] nums) {
        int p = nums.length - 1;
        if (nums[p] < 9) {
            nums[p]++;
        } else {
            do {
                nums[p--] = 0;
            } while (p >= 0 && nums[p] == 9);

            if (nums[0] != 0) {
                nums[p]++;
            } else {
                nums = new int[nums.length + 1];
                nums[0] = 1;
            }

        }
        return nums;
    }

    @Test
    public void plusOne() {
        int[] nums = {8, 9, 9, 9, 9, 9};
        log.info(">>>>> RESULT:[{}]", plusOne(nums));
    }

}

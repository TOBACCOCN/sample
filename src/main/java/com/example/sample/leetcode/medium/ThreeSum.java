package com.example.sample.leetcode.medium;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.
 * Note:
 * The solution set must not contain duplicate triplets.
 * Example:
 * Given array nums = [-1, 0, 1, 2, -1, -4],
 * A solution set is:
 * [
 * [-1, 0, 1],
 * [-1, -1, 2]
 * ]
 *
 * @author zhangyonghong
 * @date 2020.3.4
 */
@Slf4j
public class ThreeSum {

    /**
     * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.
     * Note:
     * The solution set must not contain duplicate triplets.
     * Example:
     * Given array nums = [-1, 0, 1, 2, -1, -4],
     * A solution set is:
     * [
     * [-1, 0, 1],
     * [-1, -1, 2]
     * ]
     *
     * @param nums 整数数组
     * @return 三个元素和为 0  的多个数组集合
     */
    public List<int[]> threeSum(int[] nums) {
        Arrays.sort(nums);
        if (nums[0] >= 0) {
            return null;
        }

        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < nums.length; ++i) {
            int need = -nums[i];
            int l = i + 1, r = nums.length - 1;
            while (l < r) {
                int towSum = nums[l] + nums[r];
                if (towSum > need) {
                    --r;
                } else if (towSum < need) {
                    ++l;
                } else {
                    int[] array = {nums[i], nums[l], nums[r]};
                    list.add(array);
                    break;
                }
            }
        }
        return list;
    }

    /**
     * Given an array nums of n integers and an integer target, find three integers in nums such that the sum is closest to target. Return the sum of the three integers. You may assume that each input would have exactly one solution.
     * Example:
     * Given array nums = [-1, 2, 1, -4], and target = 1.
     * The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
     *
     * @param nums   整数数组
     * @param target 目标和值
     * @return 整数数组中三个元素之和最接近该目标和值的和
     */
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);

        int closest = 0;
        for (int i = 0; i < nums.length; ++i) {
            int need = -nums[i];
            int pre = 0, fix = 0;
            int l = i + 1, r = nums.length - 1;
            while (l < r) {
                int twoSum = nums[l] + nums[r];
                if (twoSum > need) {
                    --r;
                    fix = twoSum + nums[i];
                } else if (twoSum < need) {
                    ++l;
                    pre = twoSum + nums[i];
                } else {
                    log.info(">>>>> INDEX: [{}], [{}], [{}]", i, l, r);
                    return target;
                }
            }

            int temp = target - pre < fix - target ? pre : fix;
            int max = Math.max(temp, closest);
            int min = Math.min(temp, closest);
            if (target > max) {
                closest = max;
            } else if (target < min) {
                closest = min;
            } else {
                closest = target - min < max - target ? min : max;
            }
        }
        return closest;
    }

    @Test
    public void threeSum() {
        int[] nums = {3, 1, 4, 5, 9, 2, 6, 3, 8, -4, -5, -6, -3, 29, 83, 74, 23, 89, 47, 24, 88, 46, 22, 87, 46, 28, -22, -36, 58, -90};
        long start = System.currentTimeMillis();
        threeSum(nums).forEach(array -> log.info(">>>>> [{}]", array));
        log.info(">>>>> COST:[{}] MS", System.currentTimeMillis() - start);
    }

    @Test
    public void threeSumClosest() {
        int[] nums = {3, 1, 4, 5, 9, 2, 6, 8};
        int target = 5;
        long start = System.currentTimeMillis();
        log.info(">>>>> CLOSEST: [{}]", threeSumClosest(nums, target));
        log.info(">>>>> COST:[{}] MS", System.currentTimeMillis() - start);
    }

}

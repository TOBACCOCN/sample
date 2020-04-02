package com.example.sample.leetcode.medium;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an array nums of n integers and an integer target, are there elements a, b, c, and d in nums such that a + b + c + d = target? Find all unique quadruplets in the array which gives the sum of target.
 * Note:
 * The solution set must not contain duplicate quadruplets.
 * Example:
 * Given array nums = [1, 0, -1, 0, -2, 2], and target = 0.
 * A solution set is:
 * [
 * [-1,  0, 0, 1],
 * [-2, -1, 1, 2],
 * [-2,  0, 0, 2]
 * ]
 *
 * @author zhangyonghong
 * @date 2020.3.5
 */
@Slf4j
public class FourSum {

    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> list = new ArrayList<>();
        int len = nums.length;
        if (len < 4) {
            return list;
        }

        Arrays.sort(nums);

        for (int i = 0; i < len - 3; ++i) {
            for (int j = len - 1; j > i + 2; --j) {
                int l = i + 1, r = j - 1;
                int settled = nums[i] + nums[j];
                while (l < r) {
                    int unsettled = nums[l] + nums[r];
                    if (settled + unsettled > target) {
                        --r;
                    } else if (settled + unsettled < target) {
                        ++l;
                    } else {
                        list.add(Arrays.asList(nums[i], nums[l], nums[r], nums[j]));
                        --r;
                        ++l;
                    }
                }
            }
        }
        return list;

        // List<List<Integer>> res = new ArrayList<>();
        // int len = nums.length;
        // if (len < 4) return res;
        // Arrays.sort(nums);
        // int max = nums[len - 1];
        // if (4 * max < target) return res;
        // for (int i = 0; i < len - 3; ) {
        //     if (nums[i] * 4 > target) break;
        //     if (nums[i] + 3 * max < target) {
        //         while (nums[i] == nums[++i] && i < len - 3) ;
        //         continue;
        //     }
        //
        //     for (int j = i + 1; j < len - 2; ) {
        //         int subSum = nums[i] + nums[j];
        //         if (nums[i] + nums[j] * 3 > target) break;
        //         if (subSum + 2 * max < target) {
        //             while (nums[j] == nums[++j] && j < len - 2) ;
        //             continue;
        //         }
        //
        //         int left = j + 1, right = len - 1;
        //         while (left < right) {
        //             int sum = subSum + nums[left] + nums[right];
        //             if (sum == target) {
        //                 res.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
        //                 while (nums[left] == nums[++left] && left < right) ;
        //                 while (nums[right] == nums[--right] && left < right) ;
        //             } else if (sum < target) ++left;
        //             else --right;
        //         }
        //         while (nums[j] == nums[++j] && j < len - 2) ;
        //     }
        //     while (nums[i] == nums[++i] && i < len - 3) ;
        // }
        // return res;
    }

    @Test
    public void fourSum() {
        int[] nums = {3, 1, 4, 15, 9, 2, 6, 5, 36, 2, 8, 97, 96};
        int target = 44;
        long start = System.currentTimeMillis();
        log.info(">>>>> RESULT: [{}]", fourSum(nums, target));
        log.info(">>>>> COST: [{}] MS", System.currentTimeMillis() - start);
    }

}

package com.example.sample.leetcode.medium;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 * (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).
 * You are given a target value to search. If found in the array return its index, otherwise return -1.
 * You may assume no duplicate exists in the array.
 * Your algorithm's runtime complexity must be in the order of O(log n).
 * Example 1:
 * Input: nums = [4,5,6,7,0,1,2], target = 0
 * Output: 4
 * Example 2:
 * Input: nums = [4,5,6,7,0,1,2], target = 3
 * Output: -1
 *
 * @author zhangyonghong
 * @date 2020.3.6
 */
@Slf4j
public class SearchInRotatedSortedArray {

    public int searchInRotatedSortedArray(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) >> 1;
            if (nums[mid] == target) {
                return mid;
            }

            // 左边序列是有序的
            if (nums[l] <= nums[mid]) {
                if (target >= nums[l] && target <= nums[mid]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else {
                // 右边序列是有序的
                if (target >= nums[mid] && target <= nums[r]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return -1;
    }

    @Test
    public void searchInRotatedSortedArray() {
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        int target = 6;
        log.info(">>>>> INDEX:[{}]", searchInRotatedSortedArray(nums, target));
    }

}

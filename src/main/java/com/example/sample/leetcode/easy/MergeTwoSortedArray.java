package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.
 * Note:
 * The number of elements initialized in nums1 and nums2 are m and n respectively.
 * You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional elements from nums2.
 * Example:
 * Input:
 * nums1 = [1,2,3,0,0,0], m = 3
 * nums2 = [2,5,6],       n = 3
 * Output: [1,2,2,3,5,6]
 *
 * @author zhangyonghong
 * @date 2020.2.28
 */
@Slf4j
public class MergeTwoSortedArray {

    public int[] mergeTwoSortedArray(int[] nums1, int m, int[] nums2, int n) {
        int p = m-- + n-- - 1;
        while (m >= 0 && n >= 0)
            nums1[p--] = nums1[m] > nums2[n] ? nums1[m--] : nums2[n--];
        while (n >= 0)
            nums1[p--] = nums2[n--];
        // 针对 m >= 0 不用再做处理，此时 n < 0，nums2 中的元素全部按大小放到 nums1 中去了，
        // 且 nums1 中剩余的未重新排放的元素其实就是处于低索引位置处，而且是按从小到大顺序
        return nums1;
    }

    @Test
    public void mergeTwoSortedArray() {
        int[] nums1 = {2, 3, 0, 0, 0, 0, 0};
        int[] nums2 = {1, 4, 5, 6};
        log.info(">>>>> RESULT: [{}]", mergeTwoSortedArray(nums1, 2, nums2, 4));
    }

}

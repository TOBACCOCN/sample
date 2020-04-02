package com.example.sample.leetcode.hard;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
 * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
 * Example 1:
 * nums1 = [1, 3]
 * nums2 = [2]
 * The median is 2.0
 * Example 2:
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 * The median is (2 + 3)/2 = 2.5
 *
 * @author zhangyonghong
 * @date 2020.3.7
 */
@Slf4j
public class MedianOfTwoSortedArrays {

    public double medianOfTwoSortedArrays(int[] nums1, int[] nums2) {
        int l = nums1.length + nums2.length;
        if ((l & 1) == 1) {
            return kthOfTwoSortedArrays(nums1, 0, nums2, 0, (l >> 1) + 1);
        }
        return (kthOfTwoSortedArrays(nums1, 0, nums2, 0, (l >> 1))
                + kthOfTwoSortedArrays(nums1, 0, nums2, 0, (l >> 1) + 1)) / 2.0;
    }

    private int kthOfTwoSortedArrays(int[] nums1, int start1, int[] nums2, int start2, int k) {
        if (start1 >= nums1.length) {
            return nums2[k - 1 + start1];
        }
        if (start2 >= nums2.length) {
            return nums1[k - 1 + start2];
        }

        if (k == 1) {
            return Math.min(nums1[start1], nums2[start2]);
        }

        int p1 = start1 + (k >> 1) - 1;
        int p2 = start2 + (k >> 1) - 1;
        int mid1 = p1 < nums1.length ? nums1[p1] : Integer.MAX_VALUE;
        int mid2 = p2 < nums2.length ? nums2[p2] : Integer.MAX_VALUE;
        if (mid1 < mid2) {
            return kthOfTwoSortedArrays(nums1, start1 + (k >> 1), nums2, start2, k - (k >> 1));
        }
        return kthOfTwoSortedArrays(nums1, start1, nums2, start2 + (k >> 1), k - (k >> 1));
    }

    @Test
    public void medianOfTwoSortedArrays() {
        int[] nums1 = {1, 3, 5, 7, 9};
        int[] nums2 = {0, 2, 4, 6, 8};
        log.info(">>>>> RESULT: [{}]", medianOfTwoSortedArrays(nums1, nums2));
    }

}

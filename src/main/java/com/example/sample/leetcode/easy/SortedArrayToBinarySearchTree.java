package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Given an array where elements are sorted in ascending order, convert it to a height balanced BST.
 * For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.
 * Example:
 * Given the sorted array: [-10,-3,0,5,9],
 * One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:
 * 0
 * / \
 * -3   9
 * /   /
 * -10  5
 *
 * @author zhangyonghong
 * @date 2020.2.28
 */
@Slf4j
public class SortedArrayToBinarySearchTree {

    public TreeNode sortedArrayToBinarySearchTree(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        return buildTree(nums, 0, nums.length - 1);
    }

    private TreeNode buildTree(int[] nums, int low, int high) {
        if (low > high) {
            return null;
        }

        int mid = (low + high) >> 1;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = buildTree(nums, low, mid - 1);
        node.right = buildTree(nums, mid + 1, high);
        return node;
    }

    @Test
    public void sortedArrayToBinarySearchTree() {
        int[] nums = {0, 1, 3, 4, 6, 8, 9, 11};
        TreeNode treeNode = sortedArrayToBinarySearchTree(nums);
        log.info(">>>>> RESULT: [{}]", treeNode);
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

}

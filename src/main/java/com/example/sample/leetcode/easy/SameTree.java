package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Given two binary trees, write a function to check if they are the same or not.
 * Two binary trees are considered the same if they are structurally identical and the nodes have the same value.
 * Example 1:
 * Input:
 * 1         1
 * / \       / \
 * 2   3     2   3
 * [1,2,3],   [1,2,3]
 * Output: true
 * Example 2:
 * Input:
 * 1         1
 * /            \
 * 2                2
 * [1,2],     [1,null,2]
 * Output: false
 * Example 3:
 * Input:
 * 1         1
 * / \       / \
 * 2   1     1   2
 * [1,2,1],   [1,1,2]
 * Output: false
 *
 * @author zhangyonghong
 * @date 2020.2.28
 */
@Slf4j
public class SameTree {

    public boolean isSameTree(TreeNode tn1, TreeNode tn2) {
        if (tn1 == null || tn2 == null) {
            return tn1 == tn2;
        }

        if (tn1.val != tn2.val) {
            return false;
        }

        return isSameTree(tn1.left, tn2.left) && isSameTree(tn1.right, tn2.right);
    }

    @Test
    public void isSameTree() {
        TreeNode tn1 = new TreeNode(0);
        tn1.left = new TreeNode(1, new TreeNode(3), new TreeNode(5));
        tn1.right = new TreeNode(2, new TreeNode(4), new TreeNode(6));
        TreeNode tn2 = new TreeNode(0);
        tn2.left = new TreeNode(2, new TreeNode(3), new TreeNode(5));
        tn2.right = new TreeNode(2, new TreeNode(4), new TreeNode(6));
        log.info(">>>>> IS_SAME_TREE: [{}]", isSameTree(tn1, tn2));
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

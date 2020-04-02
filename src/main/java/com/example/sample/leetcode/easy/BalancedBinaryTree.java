package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Given a binary tree, determine if it is height-balanced.
 * For this problem, a height-balanced binary tree is defined as:
 * a binary tree in which the depth of the two subtrees of every node never differ by more than 1.
 * Example 1:
 * Given the following tree [3,9,20,null,null,15,7]:
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * Return true. Example 2:
 * Given the following tree [1,2,2,3,3,null,null,4,4]:
 * 1
 * / \
 * 2   2
 * / \
 * 3   3
 * / \
 * 4   4
 * Return false.
 *
 * @author zhangyonghong
 * @date 2020.2.28
 */
@Slf4j
public class BalancedBinaryTree {

    public boolean isBalance(TreeNode root) {
        return getHeight(root) != -1;
    }

    private int getHeight(TreeNode treeNode) {
        if (treeNode == null) {
            return 0;
        }

        int leftHeight = getHeight(treeNode.left);
        if (leftHeight == -1) {
            return -1;
        }

        int rightHeight = getHeight(treeNode.right);
        if (rightHeight == -1) {
            return -1;
        }

        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }
        return 1 + Math.max(leftHeight, rightHeight);
    }

    @Test
    public void isBalance() {
        TreeNode root = new TreeNode(0);
        root.left = new TreeNode(1, new TreeNode(3), null);
        // root.left = null;
        root.right = new TreeNode(2, new TreeNode(4), new TreeNode(6));
        log.info(">>>>> IS_BALANCE: [{}]", isBalance(root));
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

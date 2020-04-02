package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Given a binary tree, you need to compute the length of the diameter of the tree. The diameter of a binary tree is the length of the longest path between any two nodes in a tree. This path may or may not pass through the root.
 * Example: Given a binary tree
 * 1
 * / \
 * 2   3
 * / \
 * 4   5
 * Return 3, which is the length of the path [4,2,1,3] or [5,2,1,3].
 * Note: The length of path between two nodes is represented by the number of edges between them.
 *
 * @author zhangyonghong
 * @date 2020.2.29
 */
@Slf4j
public class DiameterOfBinaryTree {

    int max = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        getLongDistance(root);
        return max;
    }

    private int getLongDistance(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = getLongDistance(root.left);
        int right = getLongDistance(root.right);
        if (left + right > max) {
            max = left + right;
        }

        return 1 + Math.max(getLongDistance(root.left), getLongDistance(root.right));
    }

    @Test
    public void diameterOfBinaryTree() {
        TreeNode root = new TreeNode(0);
        root.left = new TreeNode(0,
                new TreeNode(0, new TreeNode(0), new TreeNode(0)),
                new TreeNode(0, new TreeNode(0), new TreeNode(0)));
        root.right = new TreeNode(0, new TreeNode(0), new TreeNode(0));
        log.info(">>>>> DIAMETER: [{}]", diameterOfBinaryTree(root));
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

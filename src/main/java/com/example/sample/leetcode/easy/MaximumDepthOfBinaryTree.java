package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author zhangyonghong
 * @date 2020.2.28
 */
@Slf4j
public class MaximumDepthOfBinaryTree {

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    @Test
    public void maxDepth() {
        TreeNode root = new TreeNode(0);
        root.left = new TreeNode(1, null, null);
        root.right = new TreeNode(2, null, null);
        log.info(">>>>> DEPTH: [{}]", maxDepth(root));
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

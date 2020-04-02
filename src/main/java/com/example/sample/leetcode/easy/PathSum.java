package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.
 * Note: A leaf is a node with no children.
 * Example:
 * Given the below binary tree and sum = 22,
 * 5
 * / \
 * 4   8
 * /   / \
 * 11  13  4
 * /  \      \
 * 7    2      1
 * return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.
 *
 * @author zhangyonghong
 * @date 2020.2.29
 */
@Slf4j
public class PathSum {

    public boolean hasPathSum(TreeNode treeNode, int sum) {
        if (treeNode == null) {
            return false;
        }

        if (treeNode.left == null && treeNode.right == null) {
            if (treeNode.val == sum) {
                return true;
            }
        }

        return hasPathSum(treeNode.left, sum - treeNode.val)
                || hasPathSum(treeNode.right, sum - treeNode.val);
    }

    @Test
    public void hasPathSum() {
        TreeNode treeNode = new TreeNode(0);
        treeNode.left = new TreeNode(1, new TreeNode(3), new TreeNode(5));
        // treeNode.right = new TreeNode(2, new TreeNode(4), new TreeNode(6));
        treeNode.right = new TreeNode(2);
        int sum = 2;
        log.info(">>>>> HAS_PATH_SUM: [{}]", hasPathSum(treeNode, sum));
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

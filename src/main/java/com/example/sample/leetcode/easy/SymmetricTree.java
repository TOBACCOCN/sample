package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
 * For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
 * 1
 * / \
 * 2   2
 * / \ / \
 * 3  4 4  3
 * But the following [1,2,2,null,3,null,3] is not:
 * 1
 * / \
 * 2   2
 * \   \
 * 3    3
 * Note:
 * Bonus points if you could solve it both recursively and iteratively.
 *
 * @author zhangyonghong
 * @date 2020.2.28
 */
@Slf4j
public class SymmetricTree {

    public boolean isSymmetricTree(TreeNode root) {
        if (root == null) {
            return true;
        }

        return isSymmetric(root.left, root.right);
    }

    public boolean isSymmetric(TreeNode tn1, TreeNode tn2) {
        if (tn1 == null || tn2 == null) {
            return tn1 == tn2;
        }

        if (tn1.val != tn2.val) {
            return false;
        }

        return isSymmetric(tn1.left, tn2.right) && isSymmetric(tn1.right, tn2.left);
    }
    
    @Test
    public void isSymmetricTree() {
        TreeNode root = new TreeNode(0);
        root.left = new TreeNode(1, new TreeNode(3), new TreeNode(5));
        root.right = new TreeNode(1, new TreeNode(5), new TreeNode(3));
        log.info(">>>>> IS_SYMMETRIC_TREE: [{}]", isSymmetricTree(root));
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

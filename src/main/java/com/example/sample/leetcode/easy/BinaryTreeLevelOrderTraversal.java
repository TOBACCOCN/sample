package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Given a binary tree, return the bottom-up level order traversal of its nodes' values. (ie, from left to right, level by level from leaf to root).
 * <p>
 * For example:
 * <p>
 * Given binary tree [3,9,20,null,null,15,7],
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * return its bottom-up level order traversal as:
 * [
 * [15,7],
 * [9,20],
 * [3]
 * ]
 *
 * @author zhangyonghong
 * @date 2020.2.28
 */
@Slf4j
public class BinaryTreeLevelOrderTraversal {

    public List<List<Integer>> levelOrderTraversal(TreeNode root) {
        List<List<Integer>> list = new LinkedList<>();
        LinkedList<TreeNode> nodes = new LinkedList<>();
        nodes.add(root);
        while (nodes.size() > 0) {
            List<Integer> sub = new LinkedList<>();
            int size = nodes.size();
            for (int i = 0; i < size; ++i) {
                TreeNode node = nodes.remove();
                sub.add(node.val);
                if (node.left != null) {
                    nodes.add(node.left);
                }
                if (node.right != null) {
                    nodes.add(node.right);
                }
            }
            list.add(0, sub);
        }
        return list;
    }

    @Test
    public void levelOrderTraversal() {
        TreeNode root = new TreeNode(0);
        root.left = new TreeNode(1, new TreeNode(3), new TreeNode(5));
        root.right = new TreeNode(2, new TreeNode(4), new TreeNode(6));
        log.info(">>>>> RESULT: [{}]", levelOrderTraversal(root));
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

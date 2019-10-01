package com.example.sample.base;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
public class BinaryTree {

    // private static Logger logger = LoggerFactory.getLogger(BinaryTree.class);

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            list.add(random.nextInt(101));
        }
        long begin = System.currentTimeMillis();
        for (Integer integer : list) {
            binaryTree.insert(integer);
        }
        binaryTree.sortArray();
        long end = System.currentTimeMillis();
        for (Integer integer : binaryTree.sortedList) {
            log.info("" + integer);
        }
        long cost = end - begin;
        log.info(">>>>> BinaryTree.sort(), cost: [{}] ms", cost);

        begin = System.currentTimeMillis();
        Arrays.sort(list.toArray());
        end = System.currentTimeMillis();
        cost = end - begin;
        log.info(">>>>> Arrays.sort(), cost: [{}] ms", cost);
    }

    private List<Integer> sortedList = new ArrayList<>();
    private Node root;

    private void insert(int node) {
        if (this.root == null) {
            this.root = new Node(node);
        } else {
            insertNode(this.root, node);
        }
    }

    private void sortArray() {
        sortedAdd(sortedList, root);
    }

    private class Node {
        private int key;
        private Node left;
        private Node right;

        Node(int key) {
            this.key = key;
            this.left = null;
            this.right = null;
        }
    }

    private void sortedAdd(List<Integer> list, Node node) {
        if (node.left == null) {
            list.add(node.key);
        } else {
            sortedAdd(list, node.left);
            list.add(node.key);
        }

        if (node.right != null) {
            sortedAdd(list, node.right);
        }
    }

    private void insertNode(Node node, int newNode) {
        if (node.key > newNode) {
            if (node.left == null) {
                node.left = new Node(newNode);
            } else {
                insertNode(node.left, newNode);
            }
        } else {
            if (node.right == null) {
                node.right = new Node(newNode);
            } else {
                insertNode(node.right, newNode);
            }
        }
    }
}

package com.example.demo.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BinaryTree {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		BinaryTree binaryTree = new BinaryTree();
		List<Integer> list = new ArrayList<Integer>();
		Random random = new Random();
		for (int i = 0; i < 10000000; i++) {
			list.add(random.nextInt(101));
		}
		// long begin = System.currentTimeMillis();
		// for (Integer integer : list) {
		// 	binaryTree.insert(integer);
		// }
		// binaryTree.sortArray();
		// long end = System.currentTimeMillis();
		// for (Integer integer : binaryTree.sortedList) {
		// 	System.out.println(integer);
		// }
		// System.out.println("size:" + binaryTree.sortedList.size());
		// long cost  = end - begin;
		// System.out.println("cost:"+cost+"毫秒");
		Object[] array = list.toArray();
		long begin1 = System.currentTimeMillis();
		Arrays.sort(array);
		long end1 = System.currentTimeMillis();
		long cost1  = end1 - begin1;
		System.out.println("size1:" + array.length);
		System.out.println("cost1:"+cost1+"毫秒");
	}
	private List<Integer> sortedList = new ArrayList<Integer>();
	private Node root;
	public void insert(int node) {
		if (this.root == null) {
			this.root = new Node(node);
		} else {
			insertNode(this.root, node);
		}
	}
	public void sortArray() {
		sortedAdd(sortedList, root);
	}
	private class Node {
		private int key;
		private Node left;
		private Node right;
		public Node(int key) {
			this.key = key;
			this.left = null;
			this.right = null;
		}
	}
	public void sortedAdd(List<Integer> list, Node node) {
		if (node.left == null) {
			list.add(node.key);
		} else if (node.left != null) {
			sortedAdd(list, node.left);
			list.add(node.key);
		}
		
		if (node.right != null) {
			sortedAdd(list, node.right);
		}
	}
	public void insertNode(Node node, int newNode) {
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

package com.example.demo.leetcode;

/**
 * @author zhangyonghong
 * @date 2018.9.2
 *
 */
public class AddTwoNumbers {
	
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis() / 1000);
//		ListNode l1 = new ListNode(1);
//		for (int i = 0; i  < 100; i++) {
//			ListNode node = new ListNode(l1.getVal() + i + 1);
//			ListNode next = null;
//			for (int j = 1; j <= i; j++) {
//				next = l1.getNext();
//			}
//			
//			l1.setNext(next);
//		}
//		ListNode l2 = new ListNode(2);
//		doAddTwoNumbers(l1, l2);
	}

//	You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
//
//			You may assume the two numbers do not contain any leading zero, except the number 0 itself.
//
//			Example:
//
//			Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
//			Output: 7 -> 0 -> 8
//			Explanation: 342 + 465 = 807.
	@SuppressWarnings("unused")
	private static ListNode doAddTwoNumbers(ListNode l1, ListNode l2) {
		return null;
	}

}

class ListNode {
	
	private int val;
	
	private ListNode next;

	public ListNode(int val) {
		super();
		this.val = val;
	}
	
	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}



	public ListNode getNext() {
		return next;
	}

	public void setNext(ListNode next) {
		this.next = next;
	}
	
}

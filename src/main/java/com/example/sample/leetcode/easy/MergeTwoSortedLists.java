package com.example.sample.leetcode.easy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the nodes of the first two lists.
 * Example:
 * Input: 1->2->4, 1->3->4
 * Output: 1->1->2->3->4->4
 *
 * @author zhangyonghong
 * @date 2020.2.25
 */
@Slf4j
public class MergeTwoSortedLists {

    public ListNode mergeTwoSortedLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode temp = head;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                temp.next = l1;
                l1 = l1.next;
            } else {
                temp.next = l2;
                l2 = l2.next;
            }
            temp = temp.next;
        }
        temp.next = l1 != null ? l1 : l2;
        return head.next;
    }

    @Test
    public void mergeTwoSortedLists() {
        ListNode listNode1 = new ListNode(1);
        listNode1.next = new ListNode(5);
        ListNode listNode2 = new ListNode(2);
        listNode2.next = new ListNode(3);
        log.info(">>>>> MERGED_LIST: [{}]", mergeTwoSortedLists(listNode1, listNode2));
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

}

package com.example.sample.leetcode.hard;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
 * Example:
 * Input:
 * [
 * 1->4->5,
 * 1->3->4,
 * 2->6
 * ]
 * Output: 1->1->2->3->4->4->5->6
 *
 * @author zhangyonghong
 * @date 2020.3.12
 */
@Slf4j
public class MergeKSortedLists {

    public ListNode mergeKSortedLists(List<ListNode> lists) {
        int r = lists.size() - 1;
        while (lists.size() > 1) {
            int l = 0;
            while (l < r) {
                lists.set(l, mergeTwoSortedLists(lists.remove(r), lists.get(l)));
                ++l;
                --r;
            }
        }

        return lists.get(0);
    }

    private ListNode mergeTwoSortedLists(ListNode l1, ListNode l2) {
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
    public void mergeKSortedLists() {
        List<ListNode> lists = new ArrayList<>();
        lists.add(new ListNode(1, new ListNode(4, new ListNode(7, new ListNode(10)))));
        lists.add(new ListNode(2, new ListNode(5, new ListNode(8, new ListNode(11)))));
        lists.add(new ListNode(3, new ListNode(6, new ListNode(9, new ListNode(12)))));
        log.info(">>>>>RESULT:[{}]", mergeKSortedLists(lists));
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            List<Integer> list = new LinkedList<>();
            ListNode temp = new ListNode(0);
            temp.next = this;
            while (temp.next != null) {
                list.add(temp.next.val);
                temp = temp.next;
            }
            return list.toString();
        }
    }

}

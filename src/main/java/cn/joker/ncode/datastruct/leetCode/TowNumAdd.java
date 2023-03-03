package cn.joker.ncode.datastruct.leetCode;


import cn.joker.ncode.datastruct.LinkedList.Node;

/**
 * 两数相加
 */
public class TowNumAdd {

    public ListNode addTowNumbers(ListNode l1, ListNode l2) {

        int carry = 0;
        ListNode head  = null;
        ListNode tail = null;
        //链表长度可能不一致
        while (l1 != null || l2 != null) {
            //当前数字的进位数
            int aNum = l1 == null ? 0 : l1.val;
            int bNum = l2 == null ? 0 : l2.val;
            int sumResult =   aNum + bNum + carry;;

            //每个节点处理的同时进行链表构造
            if (null == head) {
                //取余数
                head = tail = new ListNode(sumResult % 10);
            } else {
                tail.next = new ListNode(sumResult % 10);
                tail = tail.next;
            }
            //取高位
            carry = sumResult / 10;
            if(carry > 0){
                tail.next = new ListNode(carry);
            }
            if (null != l1) {
                l1 = l1.next;
            }
            if (null != l2) {
                l2 = l2.next;
            }
        }

        return head;
    }

}

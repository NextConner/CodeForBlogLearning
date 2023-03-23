package cn.joker.ncode.datastruct.leetCode;

public class DeleteLastNNode {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(-1, head);
        ListNode slow = dummy;
        ListNode fast = dummy;
        if (head != null) {
            if (head.next == null) {
                return null;
            }
        } else {
            return head;
        }

        while (n--> 0)  fast = fast.next;


        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }


    public static void main(String[] args) {

        ListNode head = new ListNode(1, new ListNode(2, null));
        int n = 2;
        print(removeNthFromEnd(head, n));
    }

    static void print(ListNode listNode) {
        StringBuffer val = new StringBuffer(listNode.val + " -> ");
        while (listNode.next != null) {
            listNode = listNode.next;
            val.append(listNode.val + " -> ");
        }
        System.out.println(val);
    }
}

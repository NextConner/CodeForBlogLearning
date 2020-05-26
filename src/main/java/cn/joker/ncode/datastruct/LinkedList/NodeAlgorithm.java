package cn.joker.ncode.datastruct.LinkedList;

/**
 * 链表类的算法和非算法问题解答
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/5/21 12:40
 */
public class NodeAlgorithm<E extends Comparable> {

    /**
     * 合并有序单链表
     */
    public static Node<Integer> mergeOrderedLink(Node<Integer> headA,Node<Integer> headB){

        Node<Integer> firstA = new Node<>(null);
        firstA.next=headA;
        Node<Integer> firstB = new Node<>(null);
        firstB.next=headB;
        Node<Integer> temp = headB;
        Node<Integer> tb = headA;

        while(temp!=null){
            //需要判断A链是否到了尾部
            if(tb.next !=null && tb.next.compare(temp)){
                // A 的节点大于 B 头节点，B需要插入到头部
                //保持B链不断
                firstB.next = temp.next;
                //B 插入A 链
                temp.next=tb.next;
                tb.next=temp;
            }else{
                // A 节点 < B
                if(tb.next==null && !tb.compare(temp)){
                    tb.next=temp;
                    break;
                }
                tb = tb.next;
            }
            temp = firstB.next;
        }
        return firstA;
    }


    /**
     * 反转单链表
     */
    //反转单链表
    public void reverseLinkList(Node head){

        Node first = new Node(null);
        first.next = head;
        while(true){
            if(head.next == null){
                break;
            }
            Node move = head.next;
            Node moveNext = head.next.next;
            //修改指针引用
            head.next = moveNext;
            move.next = first.next;//链接到第一个非空节点
            first.next = move; // move 成为第一个非空节点
        }

        //输出
        while(first.next!=null){
            System.out.println((first.next.value + " ---> "));
            first = first.next;
        }
        System.out.println("null");
    }


    /**
     * 删除倒数第N个节点
     */
    public static void delNNodeNew(Node head,int n){

        Node first = head;
        Node snode = head;
        while(true){
            //将 n 减少到负数
            if(head.next!=null){
                n--;
                head = head.next;
            }else{
                //得出正序的位置
                if(n <0){
                    n=-1*n;
                }
                while(n!=0){
                    first = first.next;
                    n--;
                }
                Node del = first.next;
                Node next = first.next.next;
                //删除节点
                first.next = next;
                del.next=null;
                break;
            }

        }
        System.out.println(snode);
    }


    /**
     *  删除倒数第N个节点，基于快慢指针
     */
    public static Node delLastNWithFastAndSlowPointer(Node head, int n) {
        if(n < 1 || head == null) {
            return head;
        }
        Node guard = new Node('/');
        guard.next = head;

        Node slow = guard;
        Node fast = guard;
        //先将快指针移动k-1次
        for(int i = 0; i < n; i++) {
            if(fast != null) {
                fast = fast.next;
            }
        }
        //快慢指针一起前进，当快指针到达队尾，慢指针所在位置就是的下一个节点就是需要删除的节点
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return guard.next;
    }

    /**
     * 检测链表环，这个基于快慢指针实现
     */
    public static void checkaLinkCycle(Node head){

        Node slow = head;
        Node fast = head.next;
        //如果链表存在环，快指针就会一直绕圈，直到在【某一圈】追上慢指针
        while(fast.next!=null){
            if(slow.next.equals(fast.next)){
                System.out.println(true);
                return;
            }else{
                slow=slow.next;
                fast=fast.next.next;
            }
        }
        System.out.println(false);
    }


    /**
     * 查找链表中点
     * 只能用于处理奇数长链表
     */
    public static int midpoint(Node head){
        int midCount = 0;
        Node slow = head;
        Node fast = head.next;

        while(fast!=null){
            midCount++;
            slow = slow.next;
            if(null!=fast && null != fast.next) {
                fast = fast.next.next;
            }else{
                return midCount;
            }
        }
        return midCount;
    }


    /**
     * LeetCode 第2题，两数之和
     * 给定 A,B 单链表, 逆序存储非负整数，返回相加表示的的逆序链表，例如: 1-2-3 , 4-5-6 , 输出 : 5-7-8
     */
    public static Node<Integer> linkSum(Node<Integer> headA, Node<Integer> headB){

        int sumResult= 0 ;
        int sq=0;
        while(headA!=null && headB!=null){
            //当前数字的位数
            int munumber = (int) Math.pow(10,sq);

            int anum = headA.value*munumber;
            int bnum = headB.value*munumber;
            sumResult=sumResult + anum+bnum;
            sq++;
            headA=headA.next;
            headB=headB.next;
        }
        System.out.println(sumResult);

        Node<Integer> head = new Node<>();
        Node<Integer> tail = new Node<>();
        int i=0;
        while(i<sq){
            int munumber = (int) Math.pow(10,i);
            Node<Integer> temp = new Node<>((sumResult%(munumber*10))/munumber);
            tail.next = temp;
            if(head.next==null){
                head.next=tail.next;
            }
            tail=tail.next;
            i++;
        }
        return head.next;
    }


    public static void main(String[] args) {
        Node<Integer> A = new Node<>(2);
        Node<Integer> B = new Node<>(4);
        Node<Integer> C = new Node<>(4);
        A.next= B;
        B.next=C;

        Node<Integer> D = new Node<>(2);
        Node<Integer> E = new Node<>(4);
        Node<Integer> F = new Node<>(1);
        D.next=E;
        E.next=F;

        Node<Integer> node = linkSum(A,D);
        System.out.println(node.value + " : "+node.next.value + " : "+node.next.next.value);




    }

}

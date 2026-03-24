package cn.joker.ncode.datastruct.LinkedList;

/**
 * 继承了Comparable 的泛型Node类，用来构造链表
 *
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/5/21 12:38
 */
public class Node<E extends Comparable<E>> {

    public E val;
    public Node<E> next;

    public Node() {
    }

    public Node(E val) {
        this.val = val;
    }


    public boolean compare(Node<E> node) {
        return this.val.compareTo(node.val) >= 0;
    }

    @Override
    public boolean equals(Object obj) {
        return this.val == ((Node) obj).val;
    }
}

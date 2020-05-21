package cn.joker.ncode.datastruct.LinkedList;

/** 继承了Comparable 的泛型Node类，用来构造链表
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/5/21 12:38
 */
public class Node<E extends Comparable<E>> {

    public E value;
    public Node<E> next;

    public Node() {
    }

    public Node(E value) {
        this.value = value;
    }

    public boolean compare(Node<E> node) {
        return this.value.compareTo(node.value) >= 0;
    }
}

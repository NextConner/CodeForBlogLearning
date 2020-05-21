package cn.joker.ncode.datastruct.Queue;

import cn.joker.ncode.datastruct.LinkedList.Node;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/5/21 13:04
 */
public class LinkQueue<E extends Comparable<E>> {

    /**
     * 基于链表实现的链式队列
     */
    //这里没有指定容量，默认实现为无界队列
    private Node<E> head;
    private Node<E> tail;

    LinkQueue() {
    }

    //enQueue
    public boolean enqueue(E value) {

        //队列为空时
        if (head == null) {
            head = new Node(value);
            head.next = null;
            tail = head;
            return true;
        }

        Node newNode = new Node(value);
        tail.next = newNode;
        tail = tail.next;
        return true;
    }


    //dequeue
    public E deQueue() {
        Node<E> out;
        //需要判断队列为空
        if (head.next == tail.next) {
            return null;
        }
        //头节点出
        out = head;
        head = head.next;
        return out.value;
    }

}


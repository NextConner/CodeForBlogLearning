package cn.joker.ncode.datastruct.Queue;

import cn.joker.ncode.datastruct.LinkedList.Node;

import java.util.concurrent.CountDownLatch;

public class LockFreeQueue<E extends Comparable<E>> {


    public Node<E> head;
    public Node<E> tail;

    LockFreeQueue() {

        Node<E> dummy = new Node<>();
        dummy.next = null;
        head = tail = dummy;

    }

    //enQueue
    public boolean enqueue(LockFreeQueue<E> queue, Integer value) {
//        System.out.print(Thread.currentThread().getName() + "---当前入队---" + value);
        System.out.print(value + ",");
        //队列为空时
        /*if (head == null) {
            Node<E> inNode = new Node(value);
            inNode.next = null;
            head = inNode;
            tail = inNode;
            return true;
        }*/
        //新的尾节点
        Node newNode = new Node(value);
        newNode.next = null;
        Node oldP;
        Node next;

        while (true) {
            //获取尾指针快照
            oldP = tail;
            next = oldP.next;

            //判断尾指针是否已经变更
            if (!oldP.equals(tail)) {
                continue;
            }

            //判断next指针是否已经变更，已经变更就重置尾节点
            if (next != null) {
                casNode(tail, oldP, next);
                continue;
            }
            //cas设置尾节点next
            if (oldP.next == next) {
                oldP.next = newNode;
                break;
            }
        }
        //cas设置尾节点
//        casNode(queue.tail,oldP,newNode);
        if (tail.equals(oldP)) {
            tail = newNode;
        }

        return true;
    }

    public boolean isNull() {
        return head.next == null;
    }

    //CAS 操作更新节点值
    boolean casNode(Node<E> node, Node<E> oldVal, Node<E> newVal) {
        if (node == oldVal || node.equals(oldVal)) {
            node = newVal;
            return true;
        }
        return false;
    }

    //并发的deDueue
    public E deQueue() {

        E out;
        //并发获取头节点
        Node<E> oldHead = head;
        Node<E> oldTail = tail;
        Node<E> next = oldHead.next;

        while (true) {
            //什么样的情况下认为head指针已经被移动了?
            if (oldHead != head) {
                continue;
            }

            //判断队列已空
            if (oldHead == tail && next == null) {
                return null;
            }
            //判断head指针是否先于tail指针移动过
            if (oldHead == tail && next != null) {
                //尾指针没有移动到正确的位置
                if (tail == oldTail) {
                    tail = next;
                }
                continue;
            }

            //正确获得了队列的快照
            if (head == oldHead) {
                head = next;
                out = next.val;
                break;
            }
        }
        oldHead = null;
        return out;
    }


    public void timeTask(int threadNum, final Runnable runnable) throws Exception {

        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(3);

        for (int i = 0; i < threadNum; i++) {
            Thread t = new Thread(System.currentTimeMillis() + "threadTest:" + String.valueOf(i)) {

                @Override
                public void run() {

                    try {
                        //等待开放闭锁打开
                        startGate.await();
                        try {
                            runnable.run();
                        } finally {
                            //统计已经创建的线程数
                            endGate.countDown();
                        }
                    } catch (InterruptedException e) {

                    }
                }
            };
            t.start();
        }

        startGate.countDown();
        endGate.await();

    }

    public static void main(String[] args) throws Exception {


        LockFreeQueue<Integer> queue = new LockFreeQueue<>();
        queue.enqueue(queue, 100);
        queue.enqueue(queue, 101);
        System.out.println("");
        while (!queue.isNull()) {
            System.out.print(queue.deQueue() + ",");
        }
//        TimeUnit.SECONDS.sleep(3);


        //使用三个子线程循环入队，打印各自的入队顺序
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                while (i < 5) {
                    queue.enqueue(queue, i);
                    i++;
                }
            }
        });

        try {
            queue.timeTask(3, t1);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        while (!queue.isNull()) {
//            System.out.print(queue.deQueue() + ",");
//        }

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!queue.isNull()) {
                    System.out.print(queue.deQueue() + ",");
                }
            }
        });

        System.out.println("");
        try {
            queue.timeTask(3, t2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

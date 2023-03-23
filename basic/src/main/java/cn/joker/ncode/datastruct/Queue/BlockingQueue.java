package cn.joker.ncode.datastruct.Queue;

import java.util.Date;

public class BlockingQueue {

    /**
     * 基于数组实现的阻塞队列
     */
    private String[] items;
    private int length;
    private int head;
    private int tail;

    private boolean isFull = false;
    private boolean isEmpty = true;

    BlockingQueue(int n) {
        this.length = n;
        this.items = new String[n];
    }

    private boolean isEmpty() {
        return isEmpty;
    }

    private boolean isFull() {
        return isFull;
    }

    //enQueue
    public boolean enQueue(String item) {
        if (tail < length) {
            items[tail] = item;
            tail++;
            isEmpty = false;
            return true;
        }
        isFull = true;
        isEmpty = false;
        return false;
    }

    //enQueueNew,deQueue时不立刻进行数据的移动操作，
    //而是改变头指针的位置，当enQueue容量不足时再一次性移动数据
    public synchronized boolean enQueueNew(String item) throws InterruptedException {

        while (isFull() || head>tail) {
            // 队列已满，但是head不指向头部的时候，进行0~head位置的填充
            // 将head~tail之间的元素移动到0~head之间
            int i = head>tail ? head=tail: head;
            for (; head < tail; ) {
                //这个head-i是我没有想到的，比使用i=0;i<tail-head;要巧妙
                items[head - i] = items[head];
                head++;
            }
            tail = tail > head ? tail -= i : i - tail;
            head = 0;
            wait();
        }

        if (tail < length) {
            items[tail] = item;
            tail++;
            isEmpty = false;
            notifyAll();
            return true;
        }
        isEmpty = false;
        notifyAll();
        return false;
    }

    //deQueue
    public synchronized String deQueue() throws InterruptedException {


        while (isEmpty || head >=tail) {
            wait();
        }
        String result = "";
        if (head < length) {
            result = items[head];
            head++;
            isFull = false;
        }else{
            isFull=true;
        }
        notifyAll();
        return result;

    }


    public static void main(String[] args) {

        BlockingQueue queue = new BlockingQueue(20);

        Thread producer = new Thread(() -> {

            while (true) {

                try {
//                    System.out.println("生产者线程被唤醒----------------");
                    String str = new Date().toString();
                    queue.enQueueNew(str);
                    System.out.println("生产 : " + str);
                    Thread.sleep(500);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }, "producer");


        Thread consumer = new Thread(() -> {

            while (true) {

                try {
                    String str = queue.deQueue();
                    System.out.println(new Date().toString() + "-----------消费者0消费------------- : " + str);
                    Thread.sleep(800);
                } catch (Exception e) {
                    if (e instanceof InterruptedException) {
                        System.out.println("消费者被唤醒-----------------");
                    } else {
                        e.printStackTrace();
                    }
                }
            }

        }, "producer");


        Thread consumer1 = new Thread(() -> {

            while (true) {


                try {
                    String str = queue.deQueue();
                    System.out.println(new Date().toString() + "---------------消费者1消费------------ : " + str);
                    Thread.sleep(600);
                } catch (Exception e) {
                    if (e instanceof InterruptedException) {
                        System.out.println(new Date().toString() + "消费者被唤醒-----------------");
                    } else {
                        e.printStackTrace();
                    }
                }

            }

        }, "producer");

        producer.start();
        consumer.start();
        consumer1.start();

    }

}

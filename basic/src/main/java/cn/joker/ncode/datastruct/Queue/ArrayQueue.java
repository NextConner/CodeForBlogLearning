package cn.joker.ncode.datastruct.Queue;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/5/21 13:03
 */
public class ArrayQueue {

    /**
     * 基于数组实现的顺序队列
     */
    private String[] items;
    private int length;
    private int tail = 0;
    private int head = 0;

    ArrayQueue(int length) {
        this.length = length;
        this.items = new String[this.length];
    }

    private boolean isFull() {
        if (this.tail >= this.length) {
            return true;
        }
        return false;
    }

    //enQueue
    public boolean enQueue(String item) {
        if (null != items && !isFull()) {
            items[tail] = item;
            tail++;
            return true;
        }
        return false;
    }

    //enQueueNew,deQueue时不立刻进行数据的移动操作，
    //而是改变头指针的位置，当enQueue容量不足时再一次性移动数据
    public boolean enQueueNew(String item) {

        if (isFull()) {
            // 队列已满，但是head不指向头部的时候，进行0~head位置的填充
            // 将head~tail之间的元素移动到0~head之间
            int i = head;
            for (; head < tail; ) {
                //这个head-i是我没有想到的，比使用i=0;i<tail-head;要巧妙
                items[head - i] = items[head];
                head++;
            }
            tail -= i;
            head = 0;
        }

        if (null != items && !isFull()) {
            items[tail] = item;
            tail++;
            return true;
        }
        return false;
    }

    //deQueue
    public String deQueue() {

        String result = "";
        if (head == tail) {
            return null;
        }
        if (null != this.items) {
            result = items[head];
            head++;
        }
        return result;
    }

}


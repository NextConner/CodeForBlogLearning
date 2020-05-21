package cn.joker.ncode.datastruct.Queue;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/5/21 13:11
 */
public class ArrayCycleQueue {


    /**
     *  基于数组实现的循环队列
     */

    //数组实现
    private String[] items;
    private int head;
    private int tail;
    private int length;

    ArrayCycleQueue(int n){
        this.items=new String[n];
        this.head=0;
        this.tail=0;
    }

    //入队
    public boolean enQueue(String item){
        //判断队满
        if((tail+1)%length==head){
            return false;
        }
        items[tail]=item;
        //计算tail位置
        tail = (tail+1)%length;
        return true;
    }

    //出队
    public String deQueue(){

        if(head==tail){
            return null;
        }
        String result = items[head];
        //和判断队满类似，始终 head<=length
        head=(head+1)%length;
        return result;
    }


}

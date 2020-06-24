package cn.joker.ncode.datastruct.Queue;

import java.util.concurrent.CountDownLatch;

public class LockFreeQueueWithRingBuffer {

    /**
     *  基于环状数组实现的无锁队列
     */

    private Integer start;
    private Integer end;
    private int length;
    private Integer[] array;
    private int validData = 0;


    public LockFreeQueueWithRingBuffer(int n){
        this.length = n;
        this.array = new Integer[length];
        start = 0;
        end = 0;
    }

    public synchronized void getByIndex(){
        int k = start;
        while(k!=end){
            System.out.print(array[k]+",");
            k=(k+1)%length;
        }
    }

    //判断数组为空
    public boolean isEmpty(){
        return validData == 0 || start.equals(end);
    }

    //判断数组为满
    public boolean isFull(){
        return validData == length || (end+1) == start;
    }


    //插入数据
    public boolean enQueue(int value){

        if(isFull()){
            return false;
        }

        //cas操作获取尾节点
        while(true){
            //获取当前尾节点
            int oldEnd = end;
            int oldValid = validData;
            if(!isEmpty() && (!array[oldEnd].equals(array[end]) || array[oldEnd+1]!=null)){
                continue;
            }
            //cas判断
            if(end != (oldEnd+1)%length){
                array[oldEnd] = value;
                if(end != (oldEnd+1)%length) {
                    end = (end + 1) % length;
                }else{
                    continue;
                }
                if(oldValid + 1 != validData){
                    validData++;
                }
                break;
            }else{
                continue;
            }
        }

        return true;
    }

    //删除数据
    public int del(){

        if(isEmpty()){
            throw new NullPointerException("数组为空！");
        }

        int val = array[start];
        start = (start+1)%length;
        validData--;
        return val;
    }


    public static void timeTask(int threadNum, final Runnable runnable) throws Exception {

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

    public static void main(String[] args) {


        LockFreeQueueWithRingBuffer buffer = new LockFreeQueueWithRingBuffer(6);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                while (i < 7) {
                    buffer.enQueue(i);
                    i++;
                }
                buffer.getByIndex();
            }
        });


        try{

            LockFreeQueueWithRingBuffer.timeTask(3,t1);

        }catch (Exception e){
            e.printStackTrace();
        }


    }


}

package cn.joker.ncode.datastruct.Queue;

import java.util.concurrent.CountDownLatch;

public class LockFreeQueueWithRingBuffer {

    /**
     * 基于环状数组实现的无锁队列
     */

    private Integer start;
    private Integer end;
    private int length;
    private Integer[] array;
    private int validData = 0;


    public LockFreeQueueWithRingBuffer(int n) {
        this.length = n;
        this.array = new Integer[length];
        start = 0;
        end = 0;
    }

    public synchronized void getByIndexNew() {
        int k = start;
        int count = 0;
        while (count != validData) {
            System.out.print(array[k] + ",");
            k = (k + 1) % length;
            count++;
        }
    }

    //判断数组为空
    public boolean isEmpty() {
        return validData == 0;
    }

    //判断数组为满
    public boolean isFull() {
        return validData == length - 1 || ((end + 1) % length) == start;
    }


    //插入数据
    public boolean enQueueWhenNotFull(int value) {


        // 队列满时拒绝写入新数据
        if (isFull()) {
            return false;
        }

        //cas操作获取尾节点
        while (true) {
            //获取当前尾节点
            int oldEnd = end;
            int oldValid = validData;
            //空数组
            if (isEmpty() && array[oldEnd] == null) {

                if (null == array[end] && validData == 0) {
                    array[oldEnd] = value;
                    validData += 1;
                    end = (end + 1) % length;
                    return true;
                }

            } else if (!isEmpty() && null != array[end]) {
                continue;
            }
            //cas判断
            if (end != (oldEnd + 1) % length) {
                array[oldEnd] = value;
                if (end != (oldEnd + 1) % length) {
                    end = (end + 1) % length;
                } else {
                    continue;
                }
                if (oldValid + 1 != validData) {
                    validData++;
                }
                break;
            } else {
                continue;
            }
        }
        return true;
    }


    public boolean enQueueWhenFull(int value) {

        System.out.println(Thread.currentThread().getName() + " : " + String.valueOf(value));

        //覆盖旧数据的方式是同时移动end和start指针，
        //确保从 start ~ end 为止的范围内存储的数据是顺序写入的
        //这种方式默认允许未读的已写入数据丢失
        // 缓冲区A[0~6]写入 0~6 ,start = 0,end = 6;继续写入,start = 1,end = 0

        //cas操作获取尾节点
        while (true) {
            //获取当前尾节点
            int oldEnd = end;

            //空数组
            if (isEmpty() && array[oldEnd] == null) {
                if (null == array[end] && validData == 0) {
                    array[oldEnd] = value;
                    validData += 1;
                    end = (end + 1) % length;
                    return true;
                }
            }

            //cas判断
            if (end != (oldEnd + 1) % length) {
                //写满了数组，先移动start指针
                if (end.equals(start)) {
                    start = (start + 1) % length;
                }

                //尾指针还没有被移动
                if (array[end] == null || !array[end].equals(value)) {
                    array[oldEnd] = value;
                    end = (end + 1) % length;
                } else {
                    continue;
                }

                if (length != validData) {
                    validData++;
                }
                break;
            } else {
                continue;
            }
        }
        return true;
    }


    //删除数据
    public int deQueue() {

        if (isEmpty()) {
            throw new NullPointerException("数组为空！");
        }

        //判断队空
        while (true) {
            int oldStart = start;
            int oldValid = validData;
            Integer val = array[start];
            if (!isEmpty() && null == val) {
                continue;
            }
            //判断start指针是否移动
            if (oldStart == (start + 1) % length) {
                continue;
            }
            if (val.equals(array[start]) && oldValid == validData) {
                array[start] = null;
                start = (start + 1) % length;
                oldValid--;
                return val;
            }
        }
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
                            Thread.currentThread().interrupt();
                        } finally {
                            //统计已经创建的线程数
                            endGate.countDown();
                        }
                    } catch (InterruptedException e) {
                        System.out.println(Thread.currentThread().getName() + " : 线程响应中断！");
                    }
                }
            };
            t.start();
        }

        startGate.countDown();
        endGate.await();

    }

    public static void main(String[] args) {


        LockFreeQueueWithRingBuffer buffer = new LockFreeQueueWithRingBuffer(18);

//        int i = 0;
//        while (i < 10) {
//            buffer.enQueueWhenFull(i);
//            i++;
//        }
//
//        buffer.getByIndexNew();

        System.out.println("");
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                while (i < 7) {
                    buffer.enQueueWhenFull(i);
                    i++;
                }
            }
        });

        try {
            buffer.timeTask(3, t1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("");

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                while (!buffer.isEmpty()) {
                    try {
                        System.out.println(buffer.deQueue());
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
                Thread.currentThread().interrupt();
            }
        });

        try {
            buffer.timeTask(3, t2);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}

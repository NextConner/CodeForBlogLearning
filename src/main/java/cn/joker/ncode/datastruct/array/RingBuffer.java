package cn.joker.ncode.datastruct.array;

/**
 * @author taogezou
 */

public class RingBuffer {


    /**
     * 环状数组：
     * 1. 通过四个指针或者两个指针加两个整型计数来实现、
     * 2. 当 start 指针等于 end 指针的时候，环型数组为空
     * 3. 满的判断条件？
     */

    private Integer start;
    private Integer end;
    private int length;
    private Integer[] array;
    private int validData = 0;


    public RingBuffer(int n){
        this.length = n;
        this.array = new Integer[length];
        start = 0;
        end = 0;
    }

    public void getByIndex(){
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
    public boolean insert(int value){

        if(isFull()){
            return false;
        }
        array[end] = value;
        end = (end+1)%length;
        validData++;
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



    public static void main(String[] args) {


        int n=6;
        int start = 0;
        int end=0;
        RingBuffer ringBuffer = new RingBuffer(n);
        int i=0;

        int j=20;
        while(j>13){
            //加一
            if(j>17) {
                ringBuffer.insert(i);
                i++;
//                end = (end + 1);
//                System.out.println("头指针：" + start);
//                System.out.println("尾指针：" + end);
                ringBuffer.getByIndex();
                System.out.println("");
            }else {
                ringBuffer.del();
//                start = (start + 1) % n;
//                System.out.println("头指针：" + start);
//                System.out.println("尾指针：" + end);
                ringBuffer.getByIndex();
                System.out.println("");
            }
            j--;
        }

    }


}

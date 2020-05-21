package cn.joker.ncode.datastruct.Stack;

/** 基于数组实现的顺序栈
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/5/21 12:51
 */
public class ArrayStack {

    // 差点写成泛型数组，这里限定为字符对象
    private String[] items;
    //栈容量
    private int length;
    //元素数量
    public int size = 0;

    ArrayStack(int length){
        length = length <=0 ? 8 :length;
        this.items = new String[length];
        this.length = length;
    }

    ArrayStack(){
        this.length = 8;
        this.items = new String[length];
    }

    public String getTop(){
        return this.items[size-1];
    }

    //压栈
    public void push(String item){

        if(!sizeCheck()){
            this.items = new ArrayStack().items;
        }
        //数组的拷贝扩容
        if(size >= length){
            String[] newItem = new String[length*2];
            for(int i = 0;i<length;i++){
                newItem[i] = items[i];
            }
            this.items = newItem;
        }
        items[size] = item;
        size++;
    }

    //弹栈
    public String pop(){
        if(size == 0) {
            return null;
        }
        String value = items[size-1];
        if(sizeCheck()){
            items[size-1] = null;
        }
        size-=1;
        return value;
    }

    //容量检查
    private  boolean sizeCheck(){
        if(null == this.items || length == 0){
            return false;
        }
        return true;
    }

}

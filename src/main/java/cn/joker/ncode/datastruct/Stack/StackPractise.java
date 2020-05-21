package cn.joker.ncode.datastruct.Stack;

import org.apache.commons.lang3.StringUtils;

/** 基于栈的应用场景
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/5/21 12:53
 */
public class StackPractise {

    /**
     *  基于两个栈实现的四则运算
     */
    public static int noBracketsStackArithmetic(String expression){

        final String operator = "*/+-";
        final String mdOperator = "*/";
        final String pdOperator = "+-";
        final String numStr = "0123456789";
        ArrayStack numberStack = new ArrayStack(10);
        ArrayStack operateStack = new ArrayStack(10);

        String[] numberOperator = expression.split("");
        // 将操作数和运算符压栈
        for(String str : numberOperator){

            if(StringUtils.isNotBlank(str)){
                //                System.out.println(str);
                if(numStr.indexOf(str)>-1){
                    // 操作数压栈
                    numberStack.push(str);
                }else if(operator.indexOf(str)>-1){
                    //运算符压栈
                    if(numberStack.size%2==1){

                        if(pdOperator.indexOf(str)>-1 && operateStack.size>1 && mdOperator.indexOf(operateStack.getTop()) > -1){
                            // 当前运算符优先级低于栈顶运算符，低于栈顶操作运算符
                            Integer num1 = Integer.valueOf(numberStack.pop());
                            Integer num2 = Integer.valueOf(numberStack.pop());
                            numberStack.push(al(num1,num2,operateStack.pop()).toString());
                        }
                    }
                    operateStack.push(str);
                }else{
                    System.out.println(str);
                }
            }
        }

        //顺序遍历栈内容
        while(true){
            Integer num1 = 0;
            Integer num2 = 0;
            String opera = "";
            if(numberStack.size<=0){
                break;
            }

            while(numberStack.size > 0){
                num1 = Integer.valueOf(numberStack.pop());
                num2 = Integer.valueOf(numberStack.pop());
                break;
            }

            //操作数栈
            while(operateStack.size > 0){
                opera = operateStack.pop();
                break;
            }

            // 一次弹栈之后根据下次弹栈的结果决定是否进行计算
            Integer result = 0;
            if(pdOperator.indexOf(opera) >-1){
                numberStack.push(String.valueOf(al(num1,num2,opera)));
            }
            if(numberStack.size==1){
                return Integer.valueOf(numberStack.pop());
            }
        }
        return 0;
    }

    //四则运算
    private static Integer al(Integer num1,Integer num2,String opera){
        Integer result = 0;
        switch(opera){
            case "*":
                result = num2 * num1;
                break;
            case "/":
                result = num2 / num1;
                break;
            case "+":
                result = num2 + num1;
                break;
            case "-":
                result = num2 - num1;
                break;
            default:break;
        }
        return result;
    }


    /**
     * LeetCode 算法第20题 ， 判断有效括号字符
     * TODO 这个实现的代码比较的杂乱，可读性差，需要重构
     */
    public static boolean isUsefulBrakes(String str) {

        int midLength = str.length() / 2;
        ArrayStack stackLeft = new ArrayStack(str.length());
        ArrayStack stackRight = new ArrayStack(midLength);

        String lit = "()";
        String mid = "[]";
        String big = "{}";
        String litL = "(";
        String litR = ")";
        String midL = "[";
        String midR = "]";
        String bigL = "{";
        String bigR = "}";

        String lk = "([{";
        String rk = ")]}";

        String[] brakeArray = str.split("");
        for (int i = 0; i < str.length(); i++) {
            if (StringUtils.isNotBlank(brakeArray[i])) {
                stackLeft.push(brakeArray[i]);
            }
        }
        //the odd number length can`t be true
        if (stackLeft.size % 2 == 1) {
            return false;
        }

        //start to pop
        while (stackLeft.size > 0) {
            //if the right stack`s size equals to the left stack,which means all the brackets int right stack is right brackets
            if(stackLeft.size==stackRight.size){
                while(stackLeft.size > 0){
                    String ltop = stackLeft.pop();
                    String rtop = stackRight.pop();
                    //the same to the left stack
                    if(rk.indexOf(ltop)>0){
                        return false;
                    }
                    //if the same brackets type
                    if(lit.indexOf(ltop)>-1 && lit.indexOf(rtop)>-1){
                        continue;
                    }else if(mid.indexOf(ltop)>-1 && mid.indexOf(rtop)>-1){
                        continue;
                    }else if(big.indexOf(ltop)>-1 && big.indexOf(rtop)>-1){
                        continue;
                    }
                    return false;
                }
            }
            String top1 = stackLeft.pop();
            String top2 = stackLeft.pop();

            //the first element of left stakc must be a right brackets
            if(lk.indexOf(top1)>-1 && stackRight.size >0){
                // if not , compare with the top of the right stack
                String rtop = stackRight.pop();
                // is they the same type and match eacher other
                if(top1.equals(litL) && rtop.equals(litR)){
                    // push top2 back to the left stack
                    stackLeft.push(top2);
                    continue;
                }else if(top1.equals(midL) && rtop.equals(midR)){
                    stackLeft.push(top2);
                    continue;
                }else if(top1.equals(bigL) && rtop.equals(bigR)){
                    stackLeft.push(top2);
                    continue;
                }
                return false;
            }else if(rk.indexOf(top1)>-1){
                if(rk.indexOf(top2)>-1){
                    //the first two element are all right brackets ,push into the right stack
                    stackRight.push(top1);
                    stackRight.push(top2);
                }else if(lk.indexOf(top2)>-1){
                    //which means the top2 must be the same type of the left brackets
                    if(lit.indexOf(top1) >-1 && lit.indexOf(top2)>-1){
                        continue;
                    }else if(mid.indexOf(top1) >-1 && mid.indexOf(top2)>-1){
                        continue;
                    }else if(big.indexOf(top1) >-1 && big.indexOf(top2)>-1){
                        continue;
                    }
                    return false;
                }
            }
        }
        return true;
    }

}

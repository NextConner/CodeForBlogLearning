package cn.joker.ncode.datastruct.leetCode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  整型转罗马字符
 */
public class NumToRoman {

     int[] array = new int[]{1,5,10,50,100,500,1000};
      String[] roman = new String[]{"I","V","X","L","C","D","M"};

    public String intToRoman(int num) {

        String res = "";
        int i=array.length-1;
        while(i>0){
            //直接相等
            if(num == array[i]){
                res += roman[i];
            }else if(num > 1 && num==array[i]*0.8){
                //数字倒排
                res += roman[i-1]+roman[i];
            }else{
                while(num/array[i]>0 && num >=5){
                    if((num>array[i] && num % array[i] > 0 ) || array[i]%num >= 0){
                        res += roman[i];
                    }
                    num = num-array[i];
                }
            }

            i--;
        }
        return res;
    }

    public static void main(String[] args) {
        //TOOD 3 不通过
        System.out.println(new NumToRoman().intToRoman(35));
//        System.out.println(new NumToRoman().intToRoman(400));

    }

}

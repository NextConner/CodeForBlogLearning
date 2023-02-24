package cn.joker.ncode.datastruct.leetCode;

import java.util.Arrays;

/**
 * 整型转罗马字符
 */
public class NumToRoman {

    int[] array = new int[]{1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
    String[] roman = new String[]{"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};

    public String intToRoman(int num) {

        StringBuilder res = new StringBuilder("");
        for (int i = array.length - 1; i >= 0; i--) {
            while (num >= array[i]) {
                res.append(roman[i]);
                num -= array[i];
                if(i > 0 &&num < array[i-1]){
                    i--;
                }
            }
        }
        return res.toString();
    }


    public static void main(String[] args) {
        //TOOD 3 不通过
//        System.out.println(new NumToRoman().intToRoman(35));
//        System.out.println(new NumToRoman().intToRoman(83));
        int[] array = new int[]{1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
        String[] roman = new String[]{"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};


        System.out.println(Arrays.binarySearch(array, 35));
        System.out.println(Arrays.binarySearch(array, 84));

    }

}

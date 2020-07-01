package cn.joker.ncode.datastruct.leetCode;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/6/5 17:21
 */
public class DivideIntoKSortedArray {

    /**
     *  Given an array of integers nums and a positive integer k,
     *  find whether it's possible to divide this array into sets of k consecutive(连续的) numbers
     *  Return True if its possible otherwise return False.
     *  for example :
     *      Input : nums = [1,2,3,3,4,4,5,6], k = 4
     *      Output : True , [1,2,3,4] and [3,4,5,6].
     */

    public boolean divideKConsecutiveArray(int[] a,int k){

        int n = a.length;
        if(n%k!=0 || n<<1 <k){
            System.out.println("the array can`t be divide into some  K-length  subarray");
            return false;
        }

        /**
         * divide array with sorted at the same time
         */
        int min = a[0];
        int subArrayNum = n%k;
        while(subArrayNum >0){
            int[] sub = new int[k];
            for(int j=0;j<k;j++){

            }
            for(int i=1;i<n;i++){
                if(min > a[i]){
                    int temp = a[i];
                    a[i] = min;
                    min = temp;
                }
            }

        }

        return true;
    }



    /**
     *  查找两个非空有序数组 A,B 的中位数
     *  1. 数组A,B都至少包含一个数据
     *  2. 数组A,B都按照一样的规律排序
     *  3. 分布情况 ：
     *   3.1 数组A完全在数组B之后
     *   3.2 数组B完全在数组A之后
     *   3.3 数组A,B数字可以进行交叉再排序
     *  4. 要求算法复杂度为 O(log n)
     */

    // m ,n ， k = (m+n)/2 , 重复/不重复数组 , 偶数和奇数总数，同时二分查找？

    public static int middleNum(int[] a,int[] b){

        //获取所有数字的总和
        int n = a.length + b.length;
        int al=a.length;
        int bl=b.length;
        //确定中位数的位置，偶数取中间，奇数取中间两位数的平均数
        boolean isEven = n%2==0;
        int k =0;
        if(!isEven){
            k = n/2 + 1;
        }else{
            //偶数长度和取两个数的平均数
            k = n/2;
        }



        //确定中点位置
        //先不考虑性能实现
//        for(int i=0;i<a.length;i++){
//
//            int na = a[i];
//            int nb = b[i];
//
//
//
//        }
        int ak =  al/2 ;
        int bk =  bl/2 ;
        System.out.println(ak + " : " + bk);
        //a的近似中位数小于b的近似中位数
        if(a[ak]<b[bk]){
            int a1 = a.length - ak;
            System.out.println(a1 + bk);
        } else if (a[ak] > b[bk]) {
            int b1 = b.length-bk;
            System.out.println(b1+ak);
        }else{

        }

        return n;
    }


    public static void main(String[] args) {

        int[] a = {3,4,5,6,7};
        int[] b = {1,2,5,10};
        // 1,2,3,3,4, 4,5,5,6,7
        int alength = a.length;
        int blength = b.length;

        int am = alength/2;
        int bm = blength/2;

        int tn = a.length + b.length;
        int mt = tn/2;
//        if(tn%2==0){
//            mt=mt-1;
//        }

        System.out.println("a长度为:" + alength + ";中位数下标为:" + am);
        System.out.println("b长度为:" + blength + ";中位数下标为:" + bm);

//        int[] c = new int[tn];
        ArrayList<Integer> li = new ArrayList<>(20);
        Integer[] c;

        if(a[am] > b[bm]) {

            for (int i = 0; i <= am; i++) {
//            System.out.print(a[i]+" ,");
                li.add(a[i]);
            }
            System.out.println("");
            for (int j = 0; j < blength; j++) {
//            System.out.print(b[j]+" ,");
                li.add(b[j]);
            }
            System.out.println("");
//        int[] c = new int[tn];
            li.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    if (o1 > o2) {
                        return 1;
                    } else if (o1 < o2) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
            System.out.println(li.toString());
    c = li.toArray(new Integer[0]);
            Arrays.sort(c);
//        System.out.println(c[mt-1] + " : " + c[mt]);


        }else if(a[am] < b[bm]){
            for (int i = 0; i <alength; i++) {
//            System.out.print(a[i]+" ,");
                li.add(a[i]);
            }
            System.out.println("");
            for (int j = 0; j <=bm; j++) {
//            System.out.print(b[j]+" ,");
                li.add(b[j]);
            }
            System.out.println("");
//        int[] c = new int[tn];
            li.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    if (o1 > o2) {
                        return 1;
                    } else if (o1 < o2) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
            System.out.println(li.toString());
             c = li.toArray(new Integer[0]);
            Arrays.sort(c);
        }else{

            for (int i = 0; i <=am; i++) {
//            System.out.print(a[i]+" ,");
                li.add(a[i]);
            }
            System.out.println("");
            for (int j = 0; j <=bm; j++) {
//            System.out.print(b[j]+" ,");
                li.add(b[j]);
            }
            System.out.println("");
//        int[] c = new int[tn];
            li.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    if (o1 > o2) {
                        return 1;
                    } else if (o1 < o2) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
            System.out.println(li.toString());
            c = li.toArray(new Integer[0]);
            Arrays.sort(c);

        }

        System.out.println("");
        if(tn%2 == 0){
            System.out.println("偶数总数 :" + c[mt-1] + " : " +c[mt]);
        }else{
            System.out.println("奇数 : " + c[mt]);
        }



    }

}

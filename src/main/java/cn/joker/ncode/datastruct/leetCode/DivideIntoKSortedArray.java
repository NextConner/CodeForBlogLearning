package cn.joker.ncode.datastruct.leetCode;

import java.util.Arrays;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/6/5 17:21
 */
public class DivideIntoKSortedArray {

    /**
     * Given an array of integers nums and a positive integer k,
     * find whether it's possible to divide this array into sets of k consecutive(连续的) numbers
     * Return True if its possible otherwise return False.
     * for example :
     * Input : nums = [1,2,3,3,4,4,5,6], k = 4
     * Output : True , [1,2,3,4] and [3,4,5,6].
     */

    public boolean divideKConsecutiveArray(int[] a, int k) {

        int n = a.length;
        if (n % k != 0 || n << 1 < k) {
            System.out.println("the array can`t be divide into some  K-length  subarray");
            return false;
        }

        /**
         * divide array with sorted at the same time
         */
        int min = a[0];
        int subArrayNum = n % k;
        while (subArrayNum > 0) {
            int[] sub = new int[k];
            for (int j = 0; j < k; j++) {

            }
            for (int i = 1; i < n; i++) {
                if (min > a[i]) {
                    int temp = a[i];
                    a[i] = min;
                    min = temp;
                }
            }

        }

        return true;
    }


    /**
     * 查找两个非空有序数组 A,B 的中位数
     * 1. 数组A,B都至少包含一个数据
     * 2. 数组A,B都按照一样的规律排序
     * 3. 分布情况 ：
     * 3.1 数组A完全在数组B之后
     * 3.2 数组B完全在数组A之后
     * 3.3 数组A,B数字可以进行交叉再排序
     * 4. 要求算法复杂度为 O(log n)
     */

    public static double middleNum(int[] a, int[] b) {

        int alength = a.length;
        int blength = b.length;

        int am = alength / 2;
        int bm = blength / 2;
        double n = 0.0;
        if (alength == 0) {

            if (blength % 2 == 0) {
                System.out.println("偶数总数 :");
                n = b[bm - 1] * 0.5 + b[bm] * 0.5;
            } else {
                System.out.println("奇数 : ");
                n = b[bm];
            }
            return n;
        } else if (blength == 0) {
            if (alength % 2 == 0) {
                System.out.println("偶数总数 :");
                n = a[am - 1] * 0.5 + a[am] * 0.5;
            } else {
                System.out.println("奇数 : ");
                n = a[am];
            }
            return n;
        }

        int tn = a.length + b.length;
        int mt = tn / 2;


        System.out.println("a长度为:" + alength + ";中位数下标为:" + am);
        System.out.println("b长度为:" + blength + ";中位数下标为:" + bm);
        
        int[] c;

        int k = 0;

        if (a[am] > b[bm]) {


            c = new int[am + 1 + blength];
            for (int i = 0; i <= am; i++) {
                c[k] = a[i];
                k++;
            }
            for (int j = 0; j < blength; j++) {
                c[k] = b[j];
                k++;
            }
            System.out.println("");
//        int[] c = new int[tn];
            Arrays.sort(c);

        } else if (a[am] < b[bm]) {
            c = new int[bm + 1 + alength];
            for (int i = 0; i < alength; i++) {
                c[k] = a[i];
                k++;
            }
            System.out.println("");
            for (int j = 0; j <= bm; j++) {
                c[k] = b[j];
                k++;
            }

            Arrays.sort(c);
        } else {

            c = new int[am + bm + 2];
            for (int i = 0; i <= am; i++) {
                c[k] = a[i];
                k++;
            }
            System.out.println("");
            for (int j = 0; j <= bm; j++) {
                c[k] = b[j];
                k++;
            }
            Arrays.sort(c);
        }

        System.out.println(mt);
        if (tn % 2 == 0) {
            System.out.println("偶数总数 :");
            n = c[mt - 1] * 0.5 + c[mt] * 0.5;
        } else {
            System.out.println("奇数 : ");
            n = c[mt];
        }

        return n;
    }


    public static void main(String[] args) {


        int[] a = {4};
        int[] b = {1, 2, 3};
        System.out.println(System.currentTimeMillis());
        System.out.println(middleNum(a, b));
        System.out.println(System.currentTimeMillis());

    }

}

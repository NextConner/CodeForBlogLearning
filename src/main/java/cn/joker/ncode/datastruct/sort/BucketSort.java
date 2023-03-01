package cn.joker.ncode.datastruct.sort;

import java.util.Objects;

public class BucketSort {


    /**
     * 计数排序：
     * 1.对一组待长度为 n 的待排序数组，如果数据最大值为K
     * 2.可以把数据划分成 k 个桶，每个桶的数据值都是相等的
     * 3.然后再依次取出数据放入新数组中，就完成了对数据的排序
     */

    public static void counterSort(int[] a) {

        /**
         * 根据计数排序的思路:
         * 1.确定用来划分桶个数的max值
         * 2.将 a 数组的数据依次放入 b[max+1]数组中
         * 3.如何将 b[max+1] 数组排序？
         */
        if (null == a || a.length <= 1) {
            return;
        }
        int n = a.length;
        int max = a[0];
        for (int i = 1; i < n; i++) {
            if (max < a[i]) {
                int temp = a[i];
                a[i] = max;
                max = temp;
            }
        }
        //声明b[max+1]数组,每个位置存储元素值的个数
        int[] b = new int[max + 1];

        //如何将数组a的数据按照大小值放入b[max+1]数组中
        for (int i = 0; i < n; i++) {
            b[a[i]]++;
        }

        for (int i = 1; i < b.length; i++) {
            b[i] = b[i - 1] + b[i];
        }

        //如何确定b数组中的元素在排序后输出数组中的位置
        for (int i = b.length - 1; i >= 0; i--) {
            int value = b[i];
            if (value > 0) {
                //包含 value 个值小于等于 i 的元素
                value--;
                a[value] = i;
                b[i] = value;
                i++;
            }
        }

        System.out.println(b);

    }


    /**
     * 基数排序
     * 1.假设一个需要比较手机号码的场景，将5万个手机号码从小到大排列
     * 2.这里由于手机位数11位，对应的数据范围太大，无法通过桶排序和计数排序(按照最大最小值进行数组划分，在每个分组进行排序)的思路进行
     * 3.因此考虑手机号码的特点：每一位都是可以比较的数字，如果两个手机号码之间，高位大于低位，就不需要再进行比较
     *
     * @param
     */
    public static void radisSort(String[] ph) {

        //首先基于每一位对手机号码进行排序
        int n = ph.length;
        //使用计数排序进行统计，这里的区别是统计的是某一位的手机号码
        String[] bucket0 = new String[n];
        int b0 = 0;
        int b1 = 0;
        int b2 = 0;
        int b3 = 0;
        int b4 = 0;
        int b5 = 0;
        int b6 = 0;
        int b7 = 0;
        int b8 = 0;
        int b9 = 0;
        String[] bucket1 = new String[n];
        String[] bucket2 = new String[n];
        String[] bucket3 = new String[n];
        String[] bucket4 = new String[n];
        String[] bucket5 = new String[n];
        String[] bucket6 = new String[n];
        String[] bucket7 = new String[n];
        String[] bucket8 = new String[n];
        String[] bucket9 = new String[n];
        int k = 10;

        //需要进行11次桶排序
        for (int j = 0; j < n; j++) {
            int bitNumber = Integer.valueOf(ph[j].split("")[k]);
            switch (bitNumber) {
                case 9:
                    bucket9[b9++] = ph[j];
                    break;
                case 8:
                    bucket8[b8++] = ph[j];
                    break;
                case 7:
                    bucket7[b7++] = ph[j];
                    break;
                case 6:
                    bucket6[b6++] = ph[j];
                    break;
                case 5:
                    bucket5[b5++] = ph[j];
                    break;
                case 4:
                    bucket4[b4++] = ph[j];
                    break;
                case 3:
                    bucket3[b3++] = ph[j];
                    break;
                case 2:
                    bucket2[b2++] = ph[j];
                    break;
                case 1:
                    bucket1[b1++] = ph[j];
                    break;
                case 0:
                    bucket0[b0++] = ph[j];
                    break;
            }
        }
        quiuckSort(bucket0, 0, b0, k - 1);
        k--;


        System.out.println(ph);
    }


    public static void quiuckSort(String[] nums, int start, int end, int bit) {

        if (null == nums || nums.length <= 1 || start >= end) {
            return;
        }
        int pindex = partition(nums, start, end, bit);
        quiuckSort(nums, start, pindex, bit);
        quiuckSort(nums, pindex + 1, end, bit);
    }

    public static int partition(String[] nums, int start, int end, int bit) {

        if (start >= end) {
            return start;
        }
        String p = nums[end - 1];
        if (Objects.nonNull(p)) {
            return start;
        }
        int k =9;
        int bitNumber = k/2;
        int i = 0;
        while (bit > 0) {
            while(bitNumber >0){
                for (int j = 0; j < nums.length; j++) {
                    if (Objects.nonNull(nums[j])) {
                        continue;
                    }
                    int nbitNumber = Integer.valueOf(nums[j].split("")[bit]);
                    if (nbitNumber < bitNumber) {
                        String temp = nums[j];
                        nums[j] = nums[i];
                        nums[i] = temp;
                        i++;
                    }
                }
                String temp1 = nums[i];
                nums[i] = nums[end - 1];
                nums[end - 1] = temp1;
                bit--;
                i = 0;
            }
            bitNumber = bitNumber/2;
        }
        return i;
    }

    //对字符类型的手机号码做按位排序
    public static void phoneSort(String[] nums) {
        int j = 10;
        while (j > 0) {
            for (int i = 0; i < nums.length - 1; i++) {
                int li = Integer.valueOf(nums[i].split("")[j]);
                int bi = Integer.valueOf(nums[i + 1].split("")[j]);
                if (li > bi) {
                    String temp = nums[i];
                    nums[i] = nums[i + 1];
                    nums[i + 1] = temp;
                }
            }
            j--;
        }
        System.out.println(nums);
    }


    public static void main(String[] args) throws Exception {

    }


}

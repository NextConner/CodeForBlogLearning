package cn.joker.ncode.datastruct.search;


/**
 * 二分查找算法
 */
public class BinarySearch {

    /**
     * 基于循环实现的二分查找
     *
     * @param a
     * @return
     */
    public static int binarySearch(int[] a, int target) {

        /**
         * 拆解一下二分查找的思路：
         * 1.先确定数组中点位置，取中间值进行比较
         * 2.如果不匹配，更改数组比较的范围再次匹配
         * 3.直到找到对应的元素下标或查找范围缩小至0
         */
        int index = -1;
        int n = a.length;
        int low = 0;
        int high = n - 1;
        int mid = low + ((high - low) >> 1);

        while (low < high) {
            int value = a[mid];
            if (target == value) {
                return mid;
            } else if (target < value) {
                //去前半段查找
                high = mid - 1;
                mid = low + ((high - low) >> 1);
            } else {
                //去右半段查找
                low = mid + 1;
                mid = low + ((high - low) >> 1);
            }
        }
        return index;
    }

    /**
     * 基于递归实现
     *
     * @param a
     * @param low
     * @param high
     * @param target
     * @return
     */
    public static int binarySearchRecursive(int a[], int low, int high, int target) {

        //先明确退出条件
        if (low >= high) {
            return -1;
        }
        int mid = low + ((high - low) >> 1);
        int value = a[mid];
        if (value == target) {
            return mid;
        } else if (value > target) {
            high = mid - 1;
            return binarySearchRecursive(a, low, high, target);
        } else {
            low = mid + 1;
            return binarySearchRecursive(a, low, high, target);
        }
    }

    public static double sq(int nums) {

        double n = nums / 10;
        while (Math.round(n * n) != nums) {
            n = 0.5 * (n + nums / n);
        }
        return n;
    }

    public static int binaryFirstIndex(int[] a, int target) {

        /**
         * 重新拆解一下二分的思路:
         * 1.仍然使用折半的方式进行查找
         * 2.当定位到一个符合目标值元素的时候，也就是 a[mid] = target
         * 3.考虑再次向前二分查找？low ~ mid 位置，如果仍有匹配数据，更新index;直到遍历完成 low ~ mid 区域
         */

        int index = -1;
        int n = a.length;
        int low = 0;
        int high = n - 1;
        int mid = low + ((high - low) >> 2);

        while (low <= high) {
            int value = a[mid];
            if (target == value) {
                //这里匹配到了目标值,先记录下来
                index = mid;
                //尝试往 low ~ mid-1 的区域进行二分,不用向后查找
                high = mid - 1;
                mid = low + ((high - low) >> 1);
            } else if (target < value) {
                //去前半段查找
                high = mid - 1;
                mid = low + ((high - low) >> 1);
            } else {
                //去右半段查找
                low = mid + 1;
                mid = low + ((high - low) >> 1);
            }
        }

        return index;
    }


    public static int binaryFirstMoreThanIndex(int[] a, int target) {


        /**
         *  当 mid ==0  || (mid !=0 && a[mid-1]<target) 时循环退出
         */
        int index = -1;
        int n = a.length;
        int low = 0;
        int high = n - 1;
        int mid = low + ((high - low) >> 2);

        while (low <= high) {
            int value = a[mid];
            if (value < target) {
                low = mid +1;
                mid = low + ((high - low) >> 1);
            } else if (value >= target) {
                if (mid == 0 || (mid != 0 && a[mid - 1] < target)) {
                    return mid;
                }
                //去前半段查找
                high = mid - 1;
                mid = low + ((high - low) >> 1);
            }
        }

        return index;
    }

    public static void main(String[] args) {

//        int[] a = new int[100];
//        int j = 0;
//        for (int i = 0; i < 230; i++) {
//            if (i % 2 == 0) {
//                a[j] = i;
//                j++;
//                if (j >= 100) {
//                    break;
//                }
//            }
//        }
//        long st1 = System.currentTimeMillis();
//        System.out.println(binarySearch(a, 56));
//        System.out.println(System.currentTimeMillis() - st1);
//
//        System.out.println("---------------");
//        long st2 = System.currentTimeMillis();
//        System.out.println(binarySearchRecursive(a, 0,a.length-1,56));
//        System.out.println(System.currentTimeMillis() - st2);
        int[] a = {1, 2, 3, 4, 5, 6, 6, 6, 7, 8, 8, 9, 11, 11, 11, 11, 11, 12, 12, 12, 14, 14, 15, 66, 67, 78, 89, 123, 145, 567};
//        int n = 6;
//        System.out.println(a[17] + ": " + a[18]);
        System.out.println(binaryFirstMoreThanIndex(a, 5));

    }


}

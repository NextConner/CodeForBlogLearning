package cn.joker.ncode.datastruct.search;

/**
 * 常见的普通排序算法
 */

public class NormalSearch {


    /**
     * 冒泡排序:
     * 1.每次之比只比较相邻元素
     * 2.依次排序移动一个元素到正确的位置
     * 3.可以对上一次元素最终移动的位置进行标记，减少下次遍历的次数
     * 4.从小到大排序
     */
    public static void bubbleSearch(int[] nums) {
        int count=0;
        int n = nums.length;
        for (int j = 0; j < nums.length; j++) {
            for (int i = 0; i < n - 1 - j; i++) {
                count++;
                if (nums[i] > nums[i + 1]) {
                    int temp = nums[i + 1];
                    nums[i + 1] = nums[i];
                    nums[i] = temp;
                }
            }

        }
//        System.out.println(count);
    }


    /**
     * 插入排序:
     * 1.将待排序数组分为有序和无序两个部分
     * 2.每次取无序数组的元素在有序数组中遍历寻找插入位置
     * 3.每一次插入完成都需要将插入点之后的元素后移一位
     * 4.从小大大排序
     *
     * @param a
     */
    public static void insertSorted(int[] a) {

        //向有序数组中插入元素，每次插入后保证数组依旧有序
        //先依照顺序思维实现一个插入排序
        int n = a.length;
        int count = 0;
        for (int i = 0; i < n - 1; i++) {

            // 2,1,3,4,5
            //假,设a[0]为已排序数组，后续均为需要比较移动的数据,
            for (int j = i + 1; j < n; j++) {

                //比较，将较大的数插入到较小数的后面
                if (a[i] > a[j]) {
                    //较小数
                    int temp = a[j];
                    //将已排序元素后移
                    while (j > i) {
                        int te = a[j - 1];
                        a[j - 1] = a[j];
                        a[j] = te;
                        j--;
                        count++;
                    }
                    a[i] = temp;
                    count++;
                }
            }
        }
        System.out.println(count);
    }


    // 插入排序，a表示数组，n表示数组大小
    public static int[] insertionSort(int[] a) {
        int count = 0;
        int n = a.length;

        for (int i = 1; i < n; ++i) {
            int temp = a[i];//未排序元素的第一个
            int j = i - 1;
            for (; j >= 0; j--) {
                count++;
                int lower = a[j];//已排序区间的最后一个
                //比较
                if (lower > temp) {//已排序区间的最后一个大于未排序区间的第一个，需要依次向前比较
                    a[j + 1] = a[j]; //
//                    count++;
                } else {
                    break;
                }
            }
            a[j + 1] = temp;
//            count++;
        }
        return a;
    }


    /**
     * 选择排序:
     * 1.将数据划分为已排序和未排序区间
     * 2.每次寻找未排序区间的最小数据插入到已排序区间的末尾
     * 3.重复1
     * 4.这里按照从小到大实现
     *
     * @param
     */
    public static void selectionSorted(int[] a) {

        int count = 0;
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            //先假定a[i]是最小的数据
            int mix = a[i];
            for (int j = i + 1; j < n; j++) {
                //确定最小的元素
                count++;
                if (mix > a[j]) {
                    int temp = a[j];
                    a[j] = a[i];
                    a[i] = temp;
                    mix = temp;
//                    count++;
                }
            }
        }
        System.out.println(count);
    }


    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6};
//        bubbleSearch(nums);
//        insertionSort(nums);
//        selectionSorted(nums);
        int[] a ={11,5};
        bubbleSearch(a);

//        for (int num : nums) {
//            System.out.print(num + ",");
//        }
    }

}

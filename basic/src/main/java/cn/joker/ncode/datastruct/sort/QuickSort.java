package cn.joker.ncode.datastruct.sort;

import java.util.Arrays;

public class QuickSort {


    /**
     * 快速排序
     * 1.对于待排序数组a[p...r],选择任意元素作为 “分区点” q
     * 2.将小于 a[q] 的元素放在左边，大于a[q]的元素放在右边
     * 3.最终将它们合并
     * 4.基于分治的思想来考虑的的话，思路大概是:
     * 1.将完整数组以分区点 q 进行区分
     * 2.对于各自划分出来的分区再次重复1的步骤
     * 3.对于无法再区分的数组进行合并
     * 5.这个思路其实很像归并排序
     */
    public static int[] quickSort(int[] a) {

        if (null == a || a.length <= 1) {
            return a;
        }
        int n = a.length;
        int q = a[n - 1];
        //先按照将数组拆分的思路实现一次
        //这里将临时数组的长度都声明为n-1原因是防止出现极端分布的情况
        int[] lower = new int[n-1];
        int[] bigger = new int[n-1];
        int j = 0;
        int k = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] < q) {
                lower[j] = a[i];
                j++;
            } else if (a[i] > q) {
                bigger[k] = a[i];
                k++;
            }
        }
        int[] lr = Arrays.copyOf(lower, j);
        int[] br = Arrays.copyOf(bigger, n - j - 1);
        //一次排序之后只能保证按照分区点元素a[q]将数组的数组排成了大于a[q]和小于a[q]两部分
        //递归
        return mergeArray(quickSort(lr), quickSort(br), q);
    }

    //基于原地分区排序实现的快排
    public static void quickSortNew(int[] a,int start,int end) {

        if (null == a || start >= end) {
            return;
        }
        int pindex = partition(a,start,end);

        //一次排序之后只能保证按照分区点元素a[q]将数组的数组排成了大于a[q]和小于a[q]两部分
        //递归
        quickSortNew(a,start,pindex);
        quickSortNew(a,pindex+1,end);
    }


    //合并拆分的数组,这里只做合并，不做排序
    public static int[] mergeArray(int[] lower, int[] bigger, int q) {

        int[] result = new int[lower.length + bigger.length + 1];
        int k = 0;
        for (int i = 0; i < lower.length; i++) {
            result[k] = lower[i];
            k++;
        }
        result[k] = q;
        k++;
        for (int i = 0; i < bigger.length; i++) {
            result[k] = bigger[i];
            k++;
        }
        return result;
    }


    //实现思路应该错了，快排中需要的先对数组进行一次排序，得出分区点在排序后的数组中的位置，再根据q ~ p-1,p~r 的区间去递归进行排序
    //实现一个分区函数
    //要达到原地排序的目的，不使用额外的存储空间
    public static int partition(int[]a,int start,int end){

        //假设默认选择数组的末尾作为分区元素
        if(start>=end){
            return start;
        }
        int p = a[end-1];
        // i 用来记录分区点的变化位置
        int i=0;
        for(int j=0;j<end-1;j++){
            if(a[j]<p){
                int temp = a[j];
                a[j]=a[i];
                a[i]=temp;
                //代表着start~i 的元素 <=p
                i++;
            }
        }
        //交换a[i] 和 a[end-1]
        int temp = a[i];
        a[i]=a[end-1];
        a[end-1]=temp;

        return i;
    }


    //查找数组中第K大个元素
    //思路转变为确定前面有k-1个大于它的元素的K位置元素
    public static int findKNum(int[] a,int k){
        int n=a.length;
        int i =0;
        int kmax = a[n-1];
        for(int j=0;j<n;j++){
            if(a[j]>kmax){
                int temp = a[j];
                a[j]=a[i];
                a[i]=temp;
                i++;
            }
        }
        //交换a[i] 和 a[end-1]
        int temp = a[i];
        a[i]=a[n-1];
        a[n-1]=temp;
        if(i+1==k){
            return a[i];
        }else{
            return  i < k ? findKNum(a,k) : findKNum(Arrays.copyOf(a,i),k);
        }
    }


    public static void main(String[] args) {

        int[] a = {8, 10, 2, 3, 6, 1, 5};

//        quickSortNew(a,0,a.length);

        int b = findKNum(a,6);
        System.out.println(b);
    }

}

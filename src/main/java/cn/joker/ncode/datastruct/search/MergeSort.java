package cn.joker.ncode.datastruct.search;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 归并排序
 */
public class MergeSort {

    /**
     * 1.归并排序的思路是当对一个无序数组进行排序时，将数组划分成均等的两份
     * 2.分别对左右进行排序
     * 3.然后合并排序结果
     * 4.分治法的思路，通过递归的方式来实现
     */
    static List<Integer> nums= new ArrayList<>();
    static int count = 0;

    public static int[] mergeSort(int[] a,int start,int end){

       if(start >= end){
           if(nums.contains(a[start])){
               return new int[]{};
           }else {
               nums.add(a[start]);
               return new int[]{a[start]};
           }
       }else{
           int a1[] = mergeSort(a,start,end/2 -1);
           int a2[] = mergeSort(a,end/2,end-1);
           return mergeArray(a1,a2);
       }
    }

    /**
     * 基于数组拷贝实现的归并
     */

    //                                                     0,    n-1
    public static int[] mergeSortByArrayCopy(int[] a,int start,int end){

        final int n = a.length-1;

        if(start == 0 && start==end){
            return new int[]{a[0]};
        }

        int[] b = Arrays.copyOfRange(a,start,end);
        int[] c = Arrays.copyOfRange(a,end,a.length);
        //数组返回的条件
        return mergeArray(mergeSortByArrayCopy(b,0,(b.length)/2),mergeSortByArrayCopy(c,0,(c.length)/2));
    }

    private static int[] mergeArray(int[] a,int[] b){

        if(null == a && null == b || (a.length==0 && b.length==0)){
            return new int[]{};
        }
        int[] c = new int[a.length + b.length];
        int k =0;
        while(k<c.length){

            for(int i = 0;i<a.length;i++){
                c[k] = a[i];
                k++;
            }
            for(int j = 0;j<b.length;j++){
                c[k] = b[j];
                k++;
            }
        }
        NormalSearch.bubbleSearch(c);
        count++;
        return c;
    }

    public static void main(String[] args) {
        int[] a ={11,8,3,9,7,1,2,5,23,45,12,13,76,54,32,24};
        int n=a.length;
        a = mergeSort(a,0,2*(n-1));
//        a = mergeSortByArrayCopy(a,0,n/2);
        System.out.println(count);
        System.out.println(a);
    }

}

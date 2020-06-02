package cn.joker.ncode.datastruct.sort;


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
        return mergeArrayWithSort(mergeSortByArrayCopy(b,0,(b.length)/2),mergeSortByArrayCopy(c,0,(c.length)/2));
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


    /**
     * 1.因为在对数组进行排序之后，要合并的已经是有序数组，可以做到快速合并
     * 2.考虑极端情况下，一个数组会完全插入到另一个数组的尾部
     * 3.除此之外的情况就需要进行遍历处理了
     * @param a
     * @param b
     * @return
     */
    private static int[] mergeArrayWithSort(int[] a,int[] b){


        int al = a.length;
        int bl = b.length;
        if(null == a && null == b || (al == 0 && bl==0)){
            return new int[]{};
        }
        int[] c = new int[a.length + b.length];
        int k =0;
        //判断极端情况下的插入
        int i = 0;
        int j = 0;
        //这种方式可以保证其中一个数组的数据全部移动到临时数组中
        while(i<al && j <bl){
            //在排序中a的 i位置大于b的 j位置
            if(a[i]<=b[j]){
                c[k++] = a[i++];
            }else{
                c[k++] = b[j++];
            }
        }
        //判断哪个数组还有剩余元素，直接进行复制
        if(i>al-1){
            //a数组遍历完成
            while(j<bl){
                c[k++]=b[j++];
            }
        }else{
            while(i<al){
                c[k++]=a[i++];
            }
        }

//        NormalSearch.bubbleSearch(c);
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

package cn.joker.ncode.datastruct.search;


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
    public static int[] mergeSort(int[] a,int start,int end){

       if(start >= end){
           return new int[] {a[start]};
       }else{
           int se = end/2 -1;
           int a1[] = mergeSort(a,start,se);
           int a2[] = mergeSort(a,se+1,end-1);
           return mergeArray(a1,a2);
       }
    }


    private static int[] mergeArray(int[] a,int[] b){

        int[] c = new int[a.length+b.length];
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
        return c;
    }

    public static void main(String[] args) {
        int[] a ={11,8,3,9,7,1,2,5};
        int n=a.length;
        a = mergeSort(a,0,n-1);

        System.out.println(a);
    }

}

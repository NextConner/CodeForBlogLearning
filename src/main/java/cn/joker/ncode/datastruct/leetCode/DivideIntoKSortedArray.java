package cn.joker.ncode.datastruct.leetCode;

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


}

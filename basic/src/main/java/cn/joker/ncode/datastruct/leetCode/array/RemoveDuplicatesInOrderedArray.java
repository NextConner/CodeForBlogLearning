package cn.joker.ncode.datastruct.leetCode.array;

/**
 * remove-duplicates-from-sorted-array
 */

public class RemoveDuplicatesInOrderedArray {


    /**
     * the solution by save the result in a new array
     */
    /*public int removeDuplicates(int[] nums) {

        //find the duplicates
        //record the count of only nos
        //try to move them to the end of array
        //how to find the duplicates ?
        int len = nums.length;
        int[] result = new int[len];
        int temp = nums[0];
        result[0] = temp;
        int count = 1;
        for (int i = 1; i < len; i++) {
            if(temp != nums[i]){
                result[count++] = nums[i];
                temp = nums[i];
            }
         }

        //
        for (int i = 0; i < count; i++) {
            nums[i] = result[i];
        }
        return count;
    }*/

    /**
     * the solution by update the old array
     */
   /* public int removeDuplicates(int[] nums) {

        //find the duplicates
        //record the count of only nos
        //try to move them to the end of array
        //how to find the duplicates ?
        int len = nums.length;
        int count = 0;
        int i=0;
        while(i < len-1){
            if(nums[i] != nums[++i]){
                nums[++count] = nums[i];
            }
        }
        return count;
    }
    */

    /**
     * remove duplicates from array and  then left two only
     */
    public int removeDuplicates(int[] nums) {

        int len = nums.length;
        if (len <= 2) {
            return len;
        }
        int left = 2;
        int right = 2;
        while (right < len) {
            // judge by two steps
            if (nums[left - 2] != nums[right]) {
                nums[left] = nums[right];
                left++;
            }
            right++;
        }

        return left;
    }


    public static void main(String[] args) {

        int[] nums = {1, 1, 1, 2, 2, 2, 3, 3, 4, 4, 4, 4, 5, 5};
//        int[] nums = {1,  2,  3,  4,  5};

        new RemoveDuplicatesInOrderedArray().removeDuplicates(nums);
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }
    }

}

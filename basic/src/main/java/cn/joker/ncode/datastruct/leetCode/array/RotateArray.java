package cn.joker.ncode.datastruct.leetCode.array;

/**
 * rotate array -  https://leetcode.cn/problems/rotate-array/?envType=study-plan-v2&envId=top-interview-150
 */
public class RotateArray {

    /* *//**
     * @param nums the array need to rotate
     * @param k    the step to rotate
     *//*
    public void rotate(int[] nums, int k) {

        // 1. calculate the rotated index of num
        int len = nums.length;

        int[] result = new int[len];

        for (int i = 0; i < nums.length; i++) {
            // try to find that the index is out of range
            int idx = i + k;
            int index = idx > len-1 ? idx%len : idx;
            result[index] = nums[i];
        }

        for (int i = 0; i < result.length; i++) {
            nums[i] = result[i];
        }
    }*/

    /**
     * solution by change the old array
     *
     * @param nums the array need to rotate
     * @param k    the step to rotate
     */
    public void rotate(int[] nums, int k) {

        k %= nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);

    }

    // reverse the target array form start index to end index
    void reverse(int[] nums , int start ,int end){
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start += 1;
            end -= 1;
        }
    }

    public static void main(String[] args) {

        int[] arr = {1, 2, 3, 4, 5, 6,7};
//        int[] arr = {-1};
        new RotateArray().rotate(arr, 3);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

}

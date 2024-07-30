package cn.joker.ncode.datastruct.leetCode.array;

/**
 * leetcode https://leetcode.cn/problems/majority-element/description/?envType=study-plan-v2&envId=top-interview-150
 */

public class MajorityElement {

    public int majorityElement(int[] nums) {

        int count = 0;
        Integer candidate = null;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }
        return candidate;
    }

    public static void main(String[] args) {

        int[] nums = {1,2,2,1,1,3,3,4,5,3,3,3,3,21,1,1};
        int element = new MajorityElement().majorityElement(nums);
        System.out.println(element);
    }

}

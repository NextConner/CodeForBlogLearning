package cn.joker.ncode.datastruct.leetCode;

/**
 *  动态规划 ： 跳跃游戏2
 */
public class JumpGameII {

    public int jump(int[] nums){


        int length = nums.length;
        int start =0;
        int end = 0;
        int minStep = 0;
        for(int i=0;i<length;i++){
            //重新定位起跳区间
            start = Math.max(nums[i] + i,start);
            if(i==end){
                end = start;
                minStep++;
            }

        }
        return minStep;
    }

    public static void main(String[] args) {

        int[] arr = {1,2,1,1,1};
        System.out.println(new JumpGameII().jump(arr));

    }

}

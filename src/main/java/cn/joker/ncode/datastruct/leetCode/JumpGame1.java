package cn.joker.ncode.datastruct.leetCode;

/**
 *  跳跃游戏1
 */
public class JumpGame1 {

    public boolean canJump(int[] nums) {

        if(nums[0]<1){
            return false;
        }

        for(int i=1;i<nums.length;i++){
            if(i+nums[i] >=nums.length-1){
                return true;
            }else if(i + nums[i] <=i) {
                return false;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        int[] nums = {3,2,1,0,4};
        System.out.println(new JumpGame1().canJump(nums));
    }

}

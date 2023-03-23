package cn.joker.ncode.datastruct.leetCode.dp.middle;

/**
 *  跳跃游戏1
 */
public class JumpGame1 {

    /**
     * 判断能否通过选取 nums[i] 的最大步数 + i 到达终点
     *
     * 1. dp[0] = 1 ; dp[1] = dp[0] + nums[i];  dp[2] = dp[1] + nums[i]
     *
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {

        int index = 0;
        if(nums.length == 1){
            return true;
        }
        for(int i=0;i<nums.length;i++){
          if(index < nums.length-1){
              index = Math.max(index , nums[i] + i);
              if(index == i && nums[i] == 0){
                  return false;
              }
          }else{
              return true;
          }
        }
        return false;
    }

    //求解最长连续递增子序列长度
    int maxArrayLen(int[] nums){

        int max = 1;
        int[] dp=new int[nums.length];
        dp[0] = 1;
        for(int i=1;i<nums.length;i++){
            if(nums[i] > nums[i-1]){
                dp[i] = dp[i-1]+1;
            }else{
                dp[i] = Math.max(dp[i-1],1);
            }
            max=Math.max(max,dp[i]);
        }


        return max;
    }

    public static void main(String[] args) {

        int[] nums = {0};
        System.out.println(new JumpGame1().canJump(nums));
    }

}

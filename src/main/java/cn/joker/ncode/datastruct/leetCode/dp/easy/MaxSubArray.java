package cn.joker.ncode.datastruct.leetCode.dp.easy;


/**
 *  最大子数组和
 */
public class MaxSubArray {

    public int maxSubArraySum(int[] nums){

        int len = nums.length;
        // dp[i] 表示：以 nums[i] 结尾的连续子数组的最大和
        int[] dp = new int[len];
        dp[0] = nums[0];

        for (int i = 1; i < len; i++) {
            if (dp[i - 1] > 0) {
                dp[i] = dp[i - 1] + nums[i];
            } else {
                dp[i] = nums[i];
            }
        }

        // 也可以在上面遍历的同时求出 res 的最大值，这里我们为了语义清晰分开写，大家可以自行选择
        int res = dp[0];
        for (int i = 1; i < len; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }


    int maxSubArray2(int[] nums){

        int len = nums.length;
        int[] dp = new int[len];
        dp[0] = nums[0];
        int max = -1;
        for(int i=1;i<len;i++){
            if(dp[i-1]<0){ // 判断前一个数结尾的数组和是否为负，如果为负数则表示不能作为数组的起点
                dp[i] = nums[i];
            }else{
                dp[i] = dp[i-1] + nums[i];
            }
            max = Math.max(max,dp[i]);
        }
        return max;
    }

    public static void main(String[] args) {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(new MaxSubArray().maxSubArraySum(nums));
        System.out.println(new MaxSubArray().maxSubArray2(nums));
        nums= new int[]{3,-4,2,-1,2,6,-5,4};
        System.out.println(new MaxSubArray().maxSubArraySum(nums));
        System.out.println(new MaxSubArray().maxSubArray2(nums));
    }

}

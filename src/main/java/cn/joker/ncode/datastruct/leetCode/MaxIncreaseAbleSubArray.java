package cn.joker.ncode.datastruct.leetCode;


/**
 *  int[] nums 数组中最大升序子数组长度
 */
public class MaxIncreaseAbleSubArray {

	int maxIncreaseAbleSubArray(int[] nums){

		int len = nums.length;
		//求解的子问题是以i为起点时，能够组成的符合条件的最大长度子数组
		int[] dp = new int[len];
		//处理dp[0]作为计算的初值
		dp[0] = 1;
		int max = 1;
		//先直接遍历数组
		for(int i=1;i<len;i++){
			dp[i] =1;
			// 遍历 0~i-1 的区间，判断是否需要递增最大len
			for(int j= 0;j<i;j++){
				if( nums[i] > nums[j]){
					dp[i] = Math.max(dp[i],dp[j]+1);
				}
			}
			max = Math.max(max,dp[i]);
		}
		return max;
	}


	public static void main(String[] args) {
		int[] nums = {0,1,0,3,2,3};
		System.out.println(new MaxIncreaseAbleSubArray().maxIncreaseAbleSubArray(nums));
	}

}
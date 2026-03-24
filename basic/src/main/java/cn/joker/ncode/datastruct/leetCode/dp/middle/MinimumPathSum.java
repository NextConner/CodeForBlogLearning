package cn.joker.ncode.datastruct.leetCode.dp.middle;

/**
 *  最小路径和
 */
public class MinimumPathSum {

    /**
     * 找到从grid 左上角到右下角路径最小数字总和
     *  1. dp[i][j] = Min(dp[i-1][j],dp[i][j-1])
     *  2. min = Min(min,dp[i][j])
     */
    public int minPathSum(int[][] grid) {

        int n =grid.length;
        int m = grid[0].length;

        int[] dp = new int[m+n];
        //TODO dp 状态转移方程


        return dp[n-1];
    }


    public static void main(String[] args) {

        int[][] grid = new int[3][3];
        grid[0] = new int[]{1,3,1};
        grid[1] = new int[]{1,5,1};
        grid[2] = new int[]{4,2,1};
        System.out.println(new MinimumPathSum().minPathSum(grid));
    }

}

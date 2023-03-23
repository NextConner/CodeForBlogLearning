package cn.joker.ncode.datastruct.leetCode.dp.middle;

public class UniquePath2 {

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {

        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][]dp = new int[m][n];
        if(obstacleGrid[0][0] == 1){
            return 0;
        }
        for(int i=0;i<m && obstacleGrid[i][0] == 0;i++){
            dp[i][0] =  1;
        }
        for(int i=0;i<n &&  obstacleGrid[0][i] == 0;i++){
            dp[0][i] =  1;
        }

        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                dp [i][j] = obstacleGrid[i][j] ==1 ? 0 :  (dp[i-1][j] + dp[i][j-1]);
            }
        }
        return dp[m-1][n-1];
    }

    public static void main(String[] args) {

        int[][] obstacleGrid = new int[3][2];
        obstacleGrid[0] = new int[]{0, 0};
        obstacleGrid[1] = new int[]{1, 1};
        obstacleGrid[2] = new int[]{0, 0};
        System.out.println(new UniquePath2().uniquePathsWithObstacles(obstacleGrid));
    }

}
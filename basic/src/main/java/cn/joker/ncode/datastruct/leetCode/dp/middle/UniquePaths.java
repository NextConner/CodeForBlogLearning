package cn.joker.ncode.datastruct.leetCode.dp.middle;

/**
 *  不同路径
 */
public class UniquePaths {

    /**
     *  1. dp[i][j] : 当前状态
     *  2. dp[i-1][j] || dp[i][j-1]
     *
     **/

    public int uniquePaths(int m, int n) {

        int[][] dp = new int[n][m];

        for(int i=0;i<n;i++){
            dp[i][0]=1;
        }
        for(int i=0;i<m;i++){
            dp[0][i]=1;
        }

        for(int i=1;i<n;i++){
            for(int j=1;j<m;j++){
                dp [i][j]= (dp[i-1][j] + dp[i][j-1]);
            }
        }

        return dp[n-1][m-1];

    }


    class Point{

        int x;
        int y;
        public Point(int x , int y){
            this.x = x;
            this.y = y;
        }
    }


    public static void main(String[] args) {
        System.out.println(new UniquePaths().uniquePaths(3,7));
    }

}

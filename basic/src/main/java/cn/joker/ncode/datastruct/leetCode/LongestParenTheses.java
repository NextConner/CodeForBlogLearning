package cn.joker.ncode.datastruct.leetCode;

/**
 *  最长有效括号
 */
public class LongestParenTheses {


    //动态规划
    public int longestValidParentheses(String s) {
        char LEFT = '(';
        char RIGHT = ')';
        int maxans = 0;
        int[] dp = new int[s.length()];
        for (int i = 1; i < s.length()-2; i++) {
            //当前为 ')'
            if (s.charAt(i) == RIGHT) {
                // 之后接'('
                if (s.charAt(i - 1) == LEFT) {
                    //一个合法位置,最大长度递增2
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == LEFT) {
                    //如果当前位置 i 之前的最大字符长度有效 , i=3，dp[2]=2,i-dp[2]>0 ,如果 dp[i-dp[i-1]-1 == right
                    dp[i] = dp[i - 1] + ((i - dp[i - 1] >= 2) ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
                maxans = Math.max(maxans, dp[i]);
            }
        }
        return maxans;
    }

    /**
     * 双指针解法
     * @param s
     * @return
     */
    public int longestValidParenthesesByDoublePointer(String s) {

        int max = 0;


        return max;
    }



    /**
     * 对于一组不同重量、不可分割的物品，
     * 我们需要选择一些装入背包，
     * 在满足背包最大重量限制(W)的前提下，背包中物品
     * 总重量 = (求总重量，意味着求出可能重量的解，因此记录可能重量的值到dp数组)
     * 的最大值是多少呢
     weight:物品重量，n:物品个数，w:背包可承载重量
     * @param items
     * @param n
     * @param w
     * @return
     */
    public static int knapsack2(int[] items, int n, int w) {

        //以可能的结果值作为下标，意味着数组长度为 w+1
        boolean[] dp = new boolean[w+1];
        //先放入第一个
        if(items[0]<=w){
            dp[items[0]]=true;
        }
        //从第一个开始放入
        for(int i=1;i<n;i++){
            //以 j 作为能够容纳的剩余重量
            for(int j= w-items[i];j>=0;j--){
                if(dp[j]) dp[j+items[i]]=true;
            }
        }
        //倒排找到最大的可容纳数量
        for(int j=dp.length;j>=0;j--){
            if(dp[j]){
                return j;
            }
        }
        return 0;
    }


    public static int knapsackTwo(int[] items, int n, int w) {
        boolean[] states = new boolean[w+1]; // 默认值false
        states[0] = true;  // 第一行的数据要特殊处理，可以利用哨兵优化
        if (items[0] <= w) {
            states[items[0]] = true;
        }
        for (int i = 1; i < n; ++i) { // 动态规划
            for (int j = w-items[i]; j >= 0; --j) {//把第i个物品放入背包
                if (states[j]==true) states[j+items[i]] = true;
            }
        }
        for (int i = w; i >= 0; --i) { // 输出结果
            if (states[i] == true) return i;
        }
        return 0;
    }


    public int[] clamb(int n){

        int[] dp = new int[n+1];
        //1阶，2阶
        dp[1]=1;
        dp[2]=2;
        //3阶以上
        for(int i=3;i<n;i++){
            //当前子问题的解可以从已存在的子问题的解中得出
            dp[i] = dp[i-1]+dp[i-2];
        }
        return dp;
    }

    public static void main(String[] args) {
        int n=4;
    }

}

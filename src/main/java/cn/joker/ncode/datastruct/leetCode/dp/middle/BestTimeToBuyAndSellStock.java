package cn.joker.ncode.datastruct.leetCode.dp.middle;

/**
 *  买卖股票最佳时机2
 */
public class BestTimeToBuyAndSellStock {

    /**
     *
     * 1. 贪心解法： 因为不限制买入卖出次数，只要当前卖出价格有利润，就卖出
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {

        int max = 0;
        for(int i=1;i<prices.length;i++){
            if(prices[i] > prices[i-1]){
                max += (prices[i]-prices[i-1]);
            }
        }
        return max;
    }

}

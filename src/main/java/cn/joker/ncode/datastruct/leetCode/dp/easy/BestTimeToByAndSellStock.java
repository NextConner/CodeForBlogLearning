package cn.joker.ncode.datastruct.leetCode.dp.easy;

import java.util.HashMap;
import java.util.Map;

/**
 *  买卖股票的最佳时机
 */
public class BestTimeToByAndSellStock {


    /**
     *  max = p[1]-p[2] , if p[3]-p[2] > max , max =
     * 状态: 股票在i天卖出的利润的max : [0,i] , Max(price[i],price[0~i])
     * 双重循环超出时间限制，放弃
     * @param prices : price[i] = 股票 在第 i 天的价格
     * @return
     */
    public int maxProfit(int[] prices) {

        int maxProfit=0;
        //状态转移 , 列举第 i 天卖出的最大利润
        int min = prices[0];
        for(int i=1;i<prices.length;i++){

            if(prices[i] < min){
                min = prices[i];
            }else{
                maxProfit = Math.max(maxProfit, prices[i] - min);
            }

        }
        return  maxProfit;
    }

    public static void main(String[] args) {

                    //-3,1,1,-4,1,3,1,1,2
        int[] price = {3,4,5, 1,2,5,6,7,9};
        System.out.println(new BestTimeToByAndSellStock().maxProfit(price));

    }

}

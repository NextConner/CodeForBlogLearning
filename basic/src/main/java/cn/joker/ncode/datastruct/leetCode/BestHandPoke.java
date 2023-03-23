package cn.joker.ncode.datastruct.leetCode;

/**
 *  LeetCode ： 最佳手牌
 */
public class BestHandPoke {

    public static void main(String[] args) {

    }

    public String bestHand(int[] ranks, char[] suits) {

        int[] num = new int[14];
        boolean isFlush = true;
        int maxCount = 0;
        for (int i = 0; i < ranks.length; i++) {
            //处理花色
            if (i + 1 < suits.length && suits[i] != suits[i + 1]) {
                isFlush = false;
            }
            num[ranks[i]]++;
            //处理数字
            maxCount = Math.max(maxCount, num[ranks[i]]);
        }
        if (isFlush) {
            return "Flush";
        } else {
            if (maxCount >= 3)
                return "Three of a Kind";
            else if (maxCount >= 2)
                return "Pair";
        }
        return "High Card";
    }

}

package cn.joker.ncode.datastruct.leetCode;

/**
 * 整数反转
 */
public class RevertX {

    public static int reverse(int x) {

        int result = 0;
        int flag = x > 0 ? 1 : -1;
        x = x * flag;

        while (x > 0) {
            int carry = x % 10;
            if (result  >= Integer.MAX_VALUE /10) {
                return 0;
            }
            result = result * 10 + carry;
            x = x / 10;
        }
        result *= flag;
        if (result <= Integer.MIN_VALUE) {
            return 0;
        }
        return result;
    }

    public static void main(String[] args) {

        int a =1534236469;
        System.out.println(reverse(a));
    }

}

package cn.joker.ncode.datastruct.leetCode;

/**
 * 回文数字
 */
public class PalindromeNum {

    /**
     * 不把数字转字符的前提下实现
     *
     * @param x
     * @return
     */
    public boolean isPalindrome(int x) {

        //结束状态
        if(x<0 || (x%10==0 && x != 0)){
            return false;
        }
        int carry = 0;
        //例子: 12321
        while (x > carry) {
            //余数进位，接近数字反转一半的值
            carry = carry * 10 + x % 10;
            x = x / 10;
        }
       return carry == x || carry/10 == x;
    }

    public static void main(String[] args) {

        System.out.println(new PalindromeNum().isPalindrome(121));
        System.out.println(new PalindromeNum().isPalindrome(12321));
        System.out.println(new PalindromeNum().isPalindrome(4444));
        System.out.println(new PalindromeNum().isPalindrome(-121));

    }

}

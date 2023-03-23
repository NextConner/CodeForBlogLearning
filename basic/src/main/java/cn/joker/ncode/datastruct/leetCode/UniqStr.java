package cn.joker.ncode.datastruct.leetCode;

/**
 * . 判定字符是否唯一
 */
public class UniqStr {

    public static boolean isUnique(String astr) {


        return intArrayCalculate(astr);
//        return doubleCycle(astr);
//        return recursion(astr.charAt(0),astr.substring(1));

    }

    /**
     * 位运算计算:
     * 1. 以字符 A 作为起点，计算其他字符与 A 的 ‘距离’
     * 2. 以 long 类型变量存储位值
     *
     * @param astr
     * @return
     */
    public static boolean bitCalculate(String astr) {

        long bit = 0l;

        for (int i = 0; i < astr.length(); i++) {
            int move = astr.charAt(i) - 'A';
            // 使用1位移当前字符与 'A' 的距离，再进行位运算，如果位存在，表示是重复字符
            if ((bit & (1L << move)) != 0) {
                return false;
            } else {
                // 保存当前字符位值
                bit |= (1L << move);
            }
        }
        return true;
    }

    /**
     * 数组保存字符是否存在
     *
     * @param astr
     * @return
     */
    public static boolean intArrayCalculate(String astr) {

        int[] arr = new int[128];
        for (int i = 0; i < astr.length(); i++) {
            char c = astr.charAt(i);
            if (arr[c] != 0) {
                return false;
            }
            arr[c] = 1;
        }
        return true;
    }

    /**
     * 双重循环解法，内存消耗多
     *
     * @param astr
     * @return
     */
    private static boolean doubleCycle(String astr) {
        int length = astr.length();
        while (length-- >= 0) {
            for (int i = length - 1; i >= 0; i--) {
                if (astr.charAt(i) == astr.charAt(length)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {

        String[] strs = {"abc", "abbc", "abcc"};
        for (String str : strs) {
        }
        System.out.println('z' - 'A');
        System.out.println('a' - 'A');
        System.out.println('a' & 128);
        System.out.println('z' & 128);

        long bit = 0;
        System.out.println((bit & (1 << 64)));
        bit |= (1 << 64);
        System.out.println((bit & (1 << 64)));

    }

}

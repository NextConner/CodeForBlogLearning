package cn.joker.ncode.datastruct.leetCode;

/**
 * 最长公共前缀
 */
public class LongestCommonPrefix {

    public String longestCommonPrefix(String[] strs) {

        String result = strs[0];
        for (int i = 1; i < strs.length; i++) {
            int index = 0;
            String temp = strs[i];
            while (++index < Math.min(result.length(), temp.length())) {
                if (result.charAt(index) != temp.charAt(index)) {
                    break;
                }
            }
            result = result.substring(0, index);
        }
        return result;
    }



    public static void main(String[] args) {

        String[] array = {"hello", "hello123", "hello11212", "hel"};

        System.out.println(new LongestCommonPrefix().longestCommonPrefix(array));

    }

}

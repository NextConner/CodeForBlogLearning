package cn.joker.ncode.datastruct.leetCode;

/**
 * 有限状态机字符转换数字
 */
public class Atio {

    public int myAtio(String s) {

        long result = 0L;
        s = s.trim();
        //处理符号位
        if (!Character.isDigit(s.charAt(0)) &&
                '+'!=(s.charAt(0)) && '-'!=(s.charAt(0))) {
            return 0;
        }
        boolean neg = s.charAt(0)=='-';
        int j = 0;
        if ((neg || '+' == s.charAt(j)) && !Character.isDigit(s.charAt(j))) {
            j++;
        }
        while (j<s.length() && Character.isDigit(s.charAt(j))) {

            result = result * 10 + Character.getNumericValue(s.charAt(j));
            //整型溢出
            if (neg && -1 * result <= Integer.MIN_VALUE) {
                result =  Integer.MIN_VALUE;
                break;
            } else if(result>= Integer.MAX_VALUE) {
                result=  Integer.MAX_VALUE;
                break;
            }
            j++;
        }
        return neg ?  -1 * (int) (result) : (int) result;
    }

    public static void main(String[] args) {
        String s = " -12";
        System.out.println(new Atio().myAtio(s));
        System.out.println(new Atio().myAtio("+42"));
        System.out.println(new Atio().myAtio("42"));
    }

}

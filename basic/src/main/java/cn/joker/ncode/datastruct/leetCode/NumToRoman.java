package cn.joker.ncode.datastruct.leetCode;

/**
 * 整型转罗马字符
 * 罗马字符转整型
 */
public class NumToRoman {

//    int[] array = new int[]{1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
//    String[] roman = new String[]{"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};

    int[] array = new int[]{1, 5, 10, 50, 100, 500, 1000};
    String[] roman = new String[]{"I", "V", "X", "L", "C", "D", "M"};
    String romanStr = "IVXLCDM";
    // MCMXCIV

    public String intToRoman(int num) {
        StringBuilder res = new StringBuilder("");
        for (int i = array.length - 1; i >= 0; i--) {
            while (num >= array[i]) {
                res.append(roman[i]);
                num -= array[i];
            }
        }
        return res.toString();
    }

    public int romanToInt(String romanNum) {
        int result = 0;
        String[] split = romanNum.split("");
        for (int i = 0; i < split.length; i++) {

            int lowerIndex = romanStr.indexOf(split[i]);
            int higherIndex = i+1 < split.length ?  romanStr.indexOf(split[i+1]) : -1;
            if(lowerIndex < higherIndex){
                result+= (array[higherIndex]-array[lowerIndex]);
                i++;
            }else{
                result+= array[lowerIndex];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        //TOOD 3 不通过/
//        System.out.println(new NumToRoman().intToRoman(58));

        System.out.println(new NumToRoman().romanToInt("IV"));
        System.out.println(new NumToRoman().romanToInt("MCMXCIV"));
        System.out.println(new NumToRoman().romanToInt("LVII"));

    }

}

package cn.joker.ncode.datastruct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Recursive {


    /**
     * 输入字符串 abc, 求解出由这三个字母构成的所有排列组合结果
     * 1. a 开头, bc，cb 的输出
     * 2. b 开头, ac，ca 的输出
     * 3. c 开头, ab，ba 的输出
     * 4. 实现的思路是: 第一次遍历，保证所有的元素都会有移动到头部的机会；然后递归进行下次遍历，会实现当每个元素头部时
     */
    public static void pai(int[] t, int k, int n) {
        if (k == n - 1) {//输出这个排列
            for (int i = 0; i < n; i++) {
                System.out.print(t[i] + " ");
            }
            System.out.println();
        } else {
            for (int i = k; i < n; i++) {
                int tmp = t[i];
                t[i] = t[k];
                t[k] = tmp;//一次挑选n个字母中的一个,和前位置替换
                pai(t, k + 1, n);                      //再对其余的n-1个字母一次挑选
                tmp = t[i];
                t[i] = t[k];
                t[k] = tmp;    //再换回来
            }
        }

    }


    /**
     * 角谷定理:
     * 1. 输入一个自然数，
     * 2. 偶数除以2
     * 3. 奇数则乘3加1
     * 4.多少次运算之后可以得到1
     *
     * @param
     */
    public static int natureNumCount(int num, int count) {

        //递归退出条件
        if (num == 1) {
            return count;
        }

        //判断分支条件
        if (num % 2 == 0) {
            num = num / 2;
        } else {
            num = num * 3 + 1;
        }
        count++;
        return natureNumCount(num, count);
    }


    /**
     * 1.电话号码的数字对应着字符组合，例如 2 对应着 ABC; 7 对应着 PQRS;
     * 2.数字串 27 对应的字符组合有 3*4 =12 种
     * 3.输入 3-11 位长度的电话号码，输出所有可能组合和组合数
     *
     * @param
     */
    public static List<String> numStr(String[] numbers, int start, ArrayList<String> result) {

        if (start >= numbers.length) {
            System.out.println("共有" + result.size() + "种排列方法！");
            return result;
        }
        // 数字按键对应的字符串
        String[] numKeyStr = {"", "", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ"};
        //每次只取一组
        int firstIndex = Integer.valueOf(numbers[start]);
        while (firstIndex == 0 || firstIndex == 1) {
            firstIndex = Integer.valueOf(numbers[++start]);
        }

        String[] strs = numKeyStr[firstIndex].split("");
        if (result.size() == 0) {
            result.addAll(Arrays.asList(strs));
            return numStr(numbers, start + 1, result);
        } else {
            ArrayList<String> list = new ArrayList<>();
            for (String arrStr : strs) {
                for (String resStr : result) {
                    list.add(resStr + arrStr);
                }
            }
            return numStr(numbers, start + 1, list);
        }
    }


    public static void main(String[] args) {

//        pai(new int[]{1,2,3,4,5}, 0, 5);

        int num =17;
        System.out.println(natureNumCount(num,1));

//          String numberCode = "13570351393";
//        ArrayList<String> list = new ArrayList<>();
//        System.out.println(numStr(numberCode.split(""),0,list).toString());

    }

}

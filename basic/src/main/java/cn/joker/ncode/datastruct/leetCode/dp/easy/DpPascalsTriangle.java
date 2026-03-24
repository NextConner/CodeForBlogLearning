package cn.joker.ncode.datastruct.leetCode.dp.easy;


import java.util.ArrayList;
import java.util.List;

/**
 * 杨辉三角
 */
public class DpPascalsTriangle {

    String s1 = "";

    {
        System.out.println(s1);
    }

    /**
     *  List<List<Integer>> ret = new ArrayList<List<Integer>>();
     *         for (int i = 0; i < numRows; ++i) {
     *             List<Integer> row = new ArrayList<Integer>();
     *             for (int j = 0; j <= i; ++j) {
     *                 if (j == 0 || j == i) {
     *                     row.add(1);
     *                 } else {
     *                     row.add(ret.get(i - 1).get(j - 1) + ret.get(i - 1).get(j));
     *                 }
     *             }
     *             ret.add(row);
     *         }
     **/

    public List<List<Integer>> solution(int numRows) {

        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        for (int i = 0; i < numRows; ++i) {
            List<Integer> row = new ArrayList<Integer>();
            for (int j = 0; j <= i; ++j) {
                if (j == 0 || j == i) {
                    row.add(1);
                } else {
                    row.add(ret.get(i - 1).get(j - 1) + ret.get(i - 1).get(j));
                }
            }
            ret.add(row);
        }
        return ret;
    }


    /**
     * 输出第 numRow 层的杨辉三角数组
     * 1. 状态: 求解目标行中的每个值
     * 2. 状态转移:
     * 1.nums[0] = 1 , nums[row-1]=1
     * 2.nums[1,0,0,0,0] -> nums[1,1,0,0,0] -> nums[1,2,1,0,0] -> nums[1,3,3,1,0] -> nums[1,4,6,4,1]
     * 3. 只使用一个数组保存数据，滚动数组更新
     */
    public List<Integer> getNRowPascaleTriangle(int numRow) {
        List<Integer> list = new ArrayList<>(numRow);
        list.add(1);
        for (int i = 1; i < numRow; i++) {
            list.add(0);
            for (int j = i; j > 0; j--) {
                list.set(j,list.get(j) + list.get(j-1));
            }
        }
        return list;
    }

    public static void main(String[] args) {

        int nums = 7;
        List<List<Integer>> solution = new DpPascalsTriangle().solution(nums);
        for (List<Integer> list : solution) {
            System.out.println(list);
        }

        while (--nums > 0) {
            System.out.println(new DpPascalsTriangle().getNRowPascaleTriangle(nums));
        }

    }

}

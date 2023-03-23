package cn.joker.ncode.datastruct.leetCode;

/**
 * 11.盛水容器最大面积
 */
public class MaxArea {

    public int maxArea(int[] height) {

        //头尾指针
        int i = 0, j = height.length - 1;
        //计算当前面积
        int res =0;
        //判断头尾指针移动相遇
        while (i < j) {
            //判断长短边
            res = height[i] < height[j] ?
                    Math.max(res,(j - i) * height[i++] ):
                    Math.max(res, (j - i) * height[j--] );
        }
        return res;
    }

    public static void main(String[] args) {

        int[] array = new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println(new MaxArea().maxArea(array));
    }

}

package cn.joker.ncode.datastruct.leetCode;

import java.util.*;

/**
 * 三数之和
 */
public class SumThreeNums {

    /**
     * 返回 Nums 数组中三个下标不重复但和为0的数组
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {

        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        boolean isZero=false;

        for (int i = 0; i < nums.length - 1; i++) {
            int left = 1;
            if (nums[left] > 0) {
                return result;
            }
            int right = nums.length - 1;
            while (left < right) {
                if (i == left || i == right) {
                    break;
                }
                int sum = nums[left] + nums[right] + nums[i];
                if(!isZero && nums[left] == nums[i] && nums[i] == nums[right] && nums[right]==0){
                     isZero=true;
                }else if(isZero){
                     break;
                }
                if (sum == 0) {
                    result.add(Arrays.asList(nums[left],nums[right],nums[i]));
                    break;
                } else if (sum > 0) {
                    right--;
                } else {
                    left++;
                }
            }
        }
        return result;
    }

    /*List<List<Integer>> result = new ArrayList<>();
    int index = 0;
    int sum = 0 - nums[index];
    Set<String> set = new HashSet<>();
        for (int i = 1; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            if (sum - nums[i] - nums[j]==0) {
                String string = new StringBuilder().append(index).append(i).append(j).toString();
                if (!set.contains(string)) {
                    set.add(string);
                    result.add(Arrays.asList(nums[index], nums[i], nums[j]));
                    continue;
                }
                index++;
                sum = 0 - nums[index];
            }
        }
    }*/

    public static void main(String[] args) {

        int[] array = {-1,0,1,2,-1,-4};
        int[] array1 = {0,0,0,0,0};
        System.out.println(new SumThreeNums().threeSum(array1));
    }

}

package cn.joker.ncode.datastruct.leetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2020/6/23 12:40
 */
public class BinaryTreeLevelOrderTraversal {


    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }
        TreeNode(int val) {
            this.val = val;
        }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     *  二叉树的按层遍历
     *  1. 如何确定树的层？
     *  2. 树的遍历一般是从根节点开始的，将相同层级的节点放到每个数组中？
     */

    //按层输出，需要确定层数
    public static void getNodeLevel(TreeNode node,int level,List<List<Integer>> list){

        if(null == node){
            return;
        }else{
//            System.out.println("当前节点:" + level);
            if( level  > list.size()-1){
                list.add(level,new ArrayList<>());
            }
            list.get(level).add(node.val);
        }

        TreeNode left = node.left;
        TreeNode right = node.right;
        if(null != left || null != right){
            level++;
        }else{
            return;
        }
        getNodeLevel(left,level,list);
        getNodeLevel(right,level,list);
    }

//    public static void getNodeLevel(TreeNode node,int level,List<List<Integer>> list){
//
//
//        if(null == node){
//            if( level  > list.size()-1){
//                list.add(level,new ArrayList<>());
//            }
//            list.get(level).add(null);
//            return;
//        }else{
//            System.out.println("当前节点:" + level);
//            if( level  > list.size()-1){
//                list.add(level,new ArrayList<>());
//            }
//            list.get(level).add(node.val);
//        }
//
//        TreeNode left = node.left;
//        TreeNode right = node.right;
//        if(null != left || null != right){
//            level++;
//        }else{
//            return;
//        }
//        getNodeLevel(left,level,list);
//        getNodeLevel(right,level,list);
//    }

    public static void main(String[] args) {

        TreeNode root = new TreeNode(0);
        TreeNode left = new TreeNode(1);
        TreeNode right = new TreeNode(1);
        root.left = left;
        root.right=right;
        TreeNode ll = new TreeNode(2);
        TreeNode rl = new TreeNode(2);
        left.left=ll;
//        left.right=null;
        right.left=rl;
//        right.right=null;
        List<List<Integer>> list = new ArrayList<>();
        getNodeLevel(null,0,list);

        System.out.println(list.toString());

    }


}

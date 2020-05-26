package cn.joker.ncode.datastruct.tree;

public class TreePractise {

    //树结构
    static class TreeNode {
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
     * 判断一棵树是否是平衡二叉树：各子树的高度差<=1
     */
    public static boolean isBlanacedTree(TreeNode root) {
        return treeHeight(root) >-1;
    }

    //计算当前节点的高度
    private static int treeHeight(TreeNode node){

        //根节点高度差为0
        if(null == node){
            return 0;
        }

        return 0;

    }


    public static void main(String[] args) {

        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(2);
        TreeNode ll = new TreeNode(3);
        TreeNode lr = new TreeNode(3);
        TreeNode rl = new TreeNode(4);
        TreeNode rr = new TreeNode(4);
        TreeNode root1 = new TreeNode(7);
        TreeNode root2 = new TreeNode(8);

        root.right = right;
        root.left = left;
        left.left = ll;
        left.right = lr;
//        ll.left=rl;
//        ll.right=rr;

        System.out.println(isBlanacedTree(root));


    }

}

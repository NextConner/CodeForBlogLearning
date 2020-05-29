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
     * 判断一棵树是否是高度平衡树：
     * 1.各子树的高度差<=1
     * 2.各子树也是高度平衡树
     */
    public static boolean isBlanacedTree(TreeNode root) {

        //空根节点或无子节点
        if (null == root) {
            return true;
        }
        TreeNode left = root.left;
        TreeNode right = root.right;
        return Math.abs(treeHeight(left) - treeHeight(right)) <= 1  && isBlanacedTree(left) && isBlanacedTree(right);
    }

    //计算当前节点左右子树高度差
    private static int treeHeight(TreeNode root) {

        //空根节点
        if (null == root) {
            return 0;
        } else {
            return 1 + Math.max(treeHeight(root.left),treeHeight(root.right));
        }
        
    }


    public static void main(String[] args) {

        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(2);
        TreeNode ll = new TreeNode(3);
        TreeNode lr = new TreeNode(3);
        TreeNode rl = new TreeNode();
        TreeNode rr = new TreeNode(3);
        TreeNode lll = new TreeNode(4);
        TreeNode llr = new TreeNode(4);
        TreeNode rll = new TreeNode();
        TreeNode rrr = new TreeNode(4);


        root.right = right;
        root.left = left;
        left.left = ll;
        // left.right=lr;
        right.right=rr;
        ll.left = lll;
        // ll.right=llr;
        rr.right=rrr;
//
//        System.out.println(treeHeight(root));
//        System.out.println(treeHeight(left));
//        System.out.println(treeHeight(right));
//        System.out.println(treeHeight(left.left));
//        System.out.println(treeHeight(left.right));
//        System.out.println(treeHeight(right.left));
//        System.out.println(treeHeight(right.right));


        System.out.println(isBlanacedTree(root));
                System.out.println(isBlanacedTree(left));
                System.out.println(isBlanacedTree(right));


    }

}

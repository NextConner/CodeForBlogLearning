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
     * 在一颗二叉查找树中插入元素
     */
    public TreeNode insertTreeNode(TreeNode root,int value){

        if(null == root){
            root = new TreeNode(value);
            return root;
        }else if(value > root.val){
            //如果插入值大于当前节点的值，判断是否有右子树
            if(null == root.right){
                TreeNode right = new TreeNode(value);
                root.right = right;
                return right;
            }else{
                insertTreeNode(root.right,value);
            }
        }else{
            //同理
            if(null == root.left){
                TreeNode left = new TreeNode(value);
                root.left=left;
                return left;
            }else{
                insertTreeNode(root.left,value);
            }
        }
        return null;
    }


    /**
     * 在一颗二叉查找树中定位元素
     */
    public TreeNode getNodeInSearchTree(TreeNode root,int value){

        if(null == root){
            return null;
        }

        if(root.val == value){
            return root;
        }else  if(root.val < value){
            getNodeInSearchTree(root.right,value);
        }else{
            getNodeInSearchTree(root.left,value);
        }
        return null;
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
        return Math.abs(treeHeight(left) - treeHeight(right)) <= 1 && isBlanacedTree(left) && isBlanacedTree(right);
    }

    //计算当前节点左右子树高度差
    private static int treeHeight(TreeNode root) {

        //空根节点
        if (null == root) {
            return 0;
        } else {
            return 1 + Math.max(treeHeight(root.left), treeHeight(root.right));
        }

    }

    /**
     * 树的前序遍历
     *
     * @param root 根节点
     * @return
     */
    public static void frontTraverse(TreeNode root) {

        if (null == root) {
            return;
        }
        System.out.print(root.val + " ");
        TreeNode left = root.left;
        TreeNode right = root.right;
        frontTraverse(left);
        frontTraverse(right);
    }

    //中序遍历
    public static void middleTraverse(TreeNode root) {

        if (null == root) {
            return;
        }
        frontTraverse(root.left);
        System.out.print(root.val + " ");
        frontTraverse(root.right);
    }

    //后序遍历
    public static void backTraverse(TreeNode root) {

        if (null == root) {
            return;
        }
        frontTraverse(root.left);
        frontTraverse(root.right);
        System.out.print(root.val + " ");
    }


    public void levelTraverse(TreeNode root) {

        if (null == root) {
            return;
        }
        TreeNode left = root.left;
        TreeNode right = root.right;


    }

    public static void main(String[] args) {

        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        TreeNode ll = new TreeNode(4);
        TreeNode lr = new TreeNode(5);
        TreeNode rl = new TreeNode(6);
        TreeNode rr = new TreeNode(7);
//        TreeNode lll = new TreeNode(4);
//        TreeNode llr = new TreeNode(4);
//        TreeNode rll = new TreeNode();
//        TreeNode rrr = new TreeNode(4);


        root.right = right;
        root.left = left;
        left.left = ll;
        left.right = lr;
        right.right = rr;
        right.left = rl;
//        ll.right = llr;
//        rr.right = rrr;

        backTraverse(root);

    }

}

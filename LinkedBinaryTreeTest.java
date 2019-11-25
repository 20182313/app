public class LinkedBinaryTreeTest
{
    public static void main(String args[]) {
        //构建结点
        BinaryTreeNode node1 = new BinaryTreeNode(12);
        BinaryTreeNode node2 = new BinaryTreeNode(34);
        BinaryTreeNode node3 = new BinaryTreeNode(56);
        BinaryTreeNode node4 = new BinaryTreeNode(10);


        //构建二叉树
        LinkedBinaryTree tree1 = new LinkedBinaryTree(node1.getElement());
        LinkedBinaryTree tree2 = new LinkedBinaryTree(node2.getElement());
        LinkedBinaryTree tree3 = new LinkedBinaryTree(node3.getElement(),tree1,tree2);
        LinkedBinaryTree tree4 = new LinkedBinaryTree(node4.getElement(), tree2, tree3);


//        node2.setLeft(node4);
//        node1.setLeft(node2);
//        node1.setRight(node3);
        System.out.println("node1是否为叶子节点" + node1.isLeaf(node1));
        System.out.println("node2是否为叶子节点" + node2.isLeaf(node2));
        System.out.println("node3是否为叶子节点" + node3.isLeaf(node3));
        System.out.println("node4是否为叶子节点" + node4.isLeaf(node4));

        System.out.println("此树是否为空：" + tree4.isEmpty());

        System.out.println(tree4.getHeight());
        System.out.println("此树是否含有‘10’" + tree4.contains("10"));

        tree4.removeRightSubtree(tree4);
        System.out.println("删除右子树后：");
        System.out.println(tree4.toString());

        tree4.removeAllElement();
        System.out.println("删除所有元素后： ");
        System.out.println(tree4.toString());


    }
}
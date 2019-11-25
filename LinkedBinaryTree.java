import java.util.*;

import shiyan888.ArrayUnorderedList;
import shiyan888.UnorderedListADT;
//import Unit6.*;
import shiyan888.ElementNotFoundException;
import shiyan888.EmptyCollectionException;

public class LinkedBinaryTree<T> implements BinaryTreeADT<T>, Iterable<T>
{
    protected BinaryTreeNode<T> root;
    protected int modCount;

    protected LinkedBinaryTree<T> left,right;


    /**
     * Creates an empty binary tree.
     */
    public LinkedBinaryTree()
    {
        root = null;
    }

    /**
     * Creates a binary tree with the specified element as its root.
     *
     * @param element the element that will become the root of the binary tree
     */
    public LinkedBinaryTree(T element)
    {
        root = new BinaryTreeNode<T>(element);
    }

    /**
     * Creates a binary tree with the specified element as its root and the
     * given trees as its left child and right child
     *
     * @param element the element that will become the root of the binary tree
     * @param left the left subtree of this tree
     * @param right the right subtree of this tree
     */
    public LinkedBinaryTree(T element, LinkedBinaryTree<T> left,
                            LinkedBinaryTree<T> right)
    {
        root = new BinaryTreeNode<T>(element);
        root.setLeft(left.root);
        root.setRight(right.root);
        this.left = left;
        this.right = right;

    }

    /**
     * Returns a reference to the element at the root
     *
     * @return a reference to the specified target
     * @throws EmptyCollectionException if the tree is empty
     */
    @Override
    public T getRootElement() throws EmptyCollectionException
    {
        if (isEmpty())
            throw new EmptyCollectionException("tree");
        return root.element;
    }

    /**
     * Returns a reference to the node at the root
     *
     * @return a reference to the specified node
     * @throws EmptyCollectionException if the tree is empty
     */
    protected BinaryTreeNode<T> getRootNode() throws EmptyCollectionException
    {
        if (isEmpty())
            throw new EmptyCollectionException("tree");
        return root;
    }

    /**
     * Returns the left subtree of the root of this tree.
     *
     * @return a link to the left subtree fo the tree
     */
    public LinkedBinaryTree<T> getLeft()
    {
        return left;
    }

    /**
     * Returns the right subtree of the root of this tree.
     *
     * @return a link to the right subtree of the tree
     */
    public LinkedBinaryTree<T> getRight()
    {
        return right;
    }

    /**
     * Returns true if this binary tree is empty and false otherwise.
     *
     * @return true if this binary tree is empty, false otherwise
     */
    @Override
    public boolean isEmpty()
    {
        return (root == null);
    }

    /**
     * Returns the integer size of this tree.
     *
     * @return the integer size of the tree
     */
    @Override
    public int size()
    {
        int size = 0;
        BinaryTreeNode temp = root;
        BinaryTreeNode tempp = root;
        while (temp.getLeft() != null)
        {
            size++;
            temp = temp.left;
        }
        while (tempp.getRight() != null)
        {
            size++;
            tempp = tempp.right;
        }
        return size;
    }

    /**
     * Returns the height of this tree.
     *
     * @return the height of the tree
     */
    //根据递归树深度计算算法，计算决策树的深度.
    public int getHeight()
    {
        return height(root);
    }

    /**
     * Returns the height of the specified node.
     *
     * @param node the node from which to calculate the height
     * @return the height of the tree
     */
    private int height(BinaryTreeNode<T> node)
    {
        int height = 0;
        if(node != null){
            height = 1 +Math.max(height(node.getLeft()), height(node.getRight()));
        }
        return height;

    }
    //计算叶子节点个数
    public int CountLeaf()
    {
        return LeafNumber(root);
    }

    private int LeafNumber(BinaryTreeNode node){
        int num1 = 0,num2 = 0;
        if (node == null){
            return 0;
        }

        if (node.getRight() == null && node.getLeft() == null){
            return 1;
        }

        if (node.getLeft() != null || node.getRight() != null){
            num1 = LeafNumber(node.getLeft());
            num2 = LeafNumber(node.getRight());
        }
        return (num1 + num2);
    }

    /**
     * Returns true if this tree contains an element that matches the
     * specified target element and false otherwise.
     *
     * @param targetElement the element being sought in this tree
     * @return true if the element in is this tree, false otherwise
     */
    @Override
    public boolean contains(T targetElement)
    {
        return findNode(targetElement,root)==null;
    }

    /**
     * Returns a reference to the specified target element if it is
     * found in this binary tree.  Throws a ElementNotFoundException if
     * the specified target element is not found in the binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @return a reference to the specified target
     * @throws ElementNotFoundException if the element is not in the tree
     */
    @Override
    public T find(T targetElement) throws ElementNotFoundException
    {
        BinaryTreeNode<T> current = findNode(targetElement, root);

        if (current == null) {
            throw new ElementNotFoundException("LinkedBinaryTree2");
        }

        return (current.getElement());
    }

    /**
     * Returns a reference to the specified target element if it is
     * found in this binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @param next the element to begin searching from
     */
    private BinaryTreeNode<T> findNode(T targetElement,
                                       BinaryTreeNode<T> next)
    {
        if (next == null) {
            return null;
        }

        if (next.getElement().equals(targetElement)) {
            return next;
        }

        BinaryTreeNode<T> temp = findNode(targetElement, next.getLeft());

        if (temp == null) {
            temp = findNode(targetElement, next.getRight());
        }

        return temp;
    }

    /**
     * Returns a string representation of this binary tree showing
     * the nodes in an inorder fashion.
     *
     * @return a string representation of this binary tree
     */
    @Override
    public String toString()
    {
        UnorderedListADT<BinaryTreeNode<T>> nodes =
                new ArrayUnorderedList<BinaryTreeNode<T>>();
        UnorderedListADT<Integer> levelList =
                new ArrayUnorderedList<Integer>();
        BinaryTreeNode<T> current;
        String result = "";
        int printDepth = this.getHeight();
        int possibleNodes = (int)Math.pow(2, printDepth + 1);
        int countNodes = 0;

        nodes.addToRear((BinaryTreeNode<T>) root);
        Integer currentLevel = 0;
        Integer previousLevel = -1;
        levelList.addToRear(currentLevel);

        while (countNodes < possibleNodes)
        {
            countNodes = countNodes + 1;
            current = nodes.removeFirst();
            currentLevel = levelList.removeFirst();
            if (currentLevel > previousLevel)
            {
                result = result + "\n\n";
                previousLevel = currentLevel;
                for (int j = 0; j < ((Math.pow(2, (printDepth - currentLevel))) - 1); j++)
                    result = result + " ";
            }
            else
            {
                for (int i = 0; i < ((Math.pow(2, (printDepth - currentLevel + 1)) - 1)) ; i++)
                {
                    result = result + " ";
                }
            }
            if (current != null)
            {
                result = result + (current.getElement()).toString();
                nodes.addToRear(current.getLeft());
                levelList.addToRear(currentLevel + 1);
                nodes.addToRear(current.getRight());
                levelList.addToRear(currentLevel + 1);
            }
            else {
                nodes.addToRear(null);
                levelList.addToRear(currentLevel + 1);
                nodes.addToRear(null);
                levelList.addToRear(currentLevel + 1);
                result = result + " ";
            }

        }

        return result;
    }

    /**
     * Returns an iterator over the elements in this tree using the
     * iteratorInOrder method
     *
     * @return an in order iterator over this binary tree
     */
    @Override
    public Iterator<T> iterator()
    {
        return iteratorInOrder();
    }

    /**
     * Performs an inorder traversal on this binary tree by calling an
     * overloaded, recursive inorder method that starts with
     * the root.
     *
     * @return an in order iterator over this binary tree
     */
    @Override
    public Iterator<T> iteratorInOrder()
    {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        inOrder(root, tempList);

        return new TreeIterator(tempList.iterator());
    }

    /**
     * Performs a recursive inorder traversal.
     *
     * @param node the node to be used as the root for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    protected void inOrder(BinaryTreeNode<T> node,
                           ArrayUnorderedList<T> tempList)
    {
        if (node != null)
        {
            inOrder(node.getLeft(), tempList);
            tempList.addToRear(node.getElement());
            inOrder(node.getRight(), tempList);
        }
    }

    /**
     * Performs an preorder traversal on this binary tree by calling
     * an overloaded, recursive preorder method that starts with
     * the root.
     *
     * @return a pre order iterator over this tree
     */
    @Override
    public Iterator<T> iteratorPreOrder()
    {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        preOrder(root, tempList);
        return new TreeIterator(tempList.iterator());
    }

    /**
     * Performs a recursive preorder traversal.
     *
     * @param node the node to be used as the root for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    protected void preOrder(BinaryTreeNode<T> node,
                            ArrayUnorderedList<T> tempList)
    {
        if (node != null) {
            tempList.addToRear(node.getElement());
            inOrder(node.getLeft(), tempList);
            inOrder(node.getRight(), tempList);
        }
    }

    /**
     * Performs an postorder traversal on this binary tree by calling
     * an overloaded, recursive postorder method that starts
     * with the root.
     *
     * @return a post order iterator over this tree
     */
    @Override
    public Iterator<T> iteratorPostOrder()
    {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        postOrder(root, tempList);
        return new TreeIterator(tempList.iterator());
    }

    /**
     * Performs a recursive postorder traversal.
     *
     * @param node the node to be used as the root for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    protected void postOrder(BinaryTreeNode<T> node,
                             ArrayUnorderedList<T> tempList)
    {
        if (node != null) {
            postOrder(node.getLeft(),tempList);
            postOrder(node.getRight(),tempList);
            tempList.addToRear(node.getElement());
        }
    }

    /**
     * Performs a levelorder traversal on this binary tree, using a
     * templist.
     *
     * @return a levelorder iterator over this binary tree
     */
    @Override
    public Iterator<T> iteratorLevelOrder()
    {
        ArrayUnorderedList<BinaryTreeNode<T>> nodes =
                new ArrayUnorderedList<BinaryTreeNode<T>>();
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        BinaryTreeNode<T> current;

        nodes.addToRear(root);

        while (!nodes.isEmpty())
        {
            current = nodes.removeFirst();

            if (current != null)
            {
                tempList.addToRear(current.getElement());
                if (current.getLeft() != null) {
                    nodes.addToRear(current.getLeft());
                }
                if (current.getRight() != null) {
                    nodes.addToRear(current.getRight());
                }
            }
            else {
                tempList.addToRear(null);
            }
        }

        return new TreeIterator(tempList.iterator());
    }

    /**
     * Inner class to represent an iterator over the elements of this tree
     */
    private class TreeIterator implements Iterator<T>
    {
        private int expectedModCount;
        private Iterator<T> iter;

        /**
         * Sets up this iterator using the specified iterator.
         *
         * @param iter the list iterator created by a tree traversal
         */
        public TreeIterator(Iterator<T> iter)
        {
            this.iter = iter;
            expectedModCount = modCount;
        }

        /**
         * Returns true if this iterator has at least one more element
         * to deliver in the iteration.
         *
         * @return  true if this iterator has at least one more element to deliver
         *          in the iteration
         * @throws  ConcurrentModificationException if the collection has changed
         *          while the iterator is in use
         */
        @Override
        public boolean hasNext() throws ConcurrentModificationException
        {
            if (!(modCount == expectedModCount)) {
                throw new ConcurrentModificationException();
            }

            return (iter.hasNext());
        }

        /**
         * Returns the next element in the iteration. If there are no
         * more elements in this iteration, a NoSuchElementException is
         * thrown.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iterator is empty
         */
        @Override
        public T next() throws NoSuchElementException
        {
            if (hasNext()) {
                return (iter.next());
            } else {
                throw new NoSuchElementException();
            }
        }

        /**
         * The remove operation is not supported.
         *
         * @throws UnsupportedOperationException if the remove operation is called
         */
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    public void removeRightSubtree(LinkedBinaryTree<T> node)
    {

        if (getRootNode() == null) {
            throw new IllegalArgumentException("tree is empty");
        }

        root.right = null;
    }

    public void removeAllElement()
    {
        root=null;
    }
    //中序输出
    public void toInString(){
        inOrder(root);
    }
    private void inOrder(BinaryTreeNode root) {
        if (null != root) {
            inOrder(root.getLeft());
            System.out.print(root.getElement() + "\t");
            inOrder(root.getRight());
        }
    }

    //前序输出
    public void toPreString(){
        preOrder(root);
    }
    private void preOrder(BinaryTreeNode root){
        if(null!= root){
            System.out.print(root.getElement() + "\t");
            preOrder(root.getLeft());
            preOrder(root.getRight());
        }
    }

    //后序输出
    public void toPostString(){
        postOrder(root);
    }
    private void postOrder(BinaryTreeNode root) {
        if (null != root) {
            postOrder(root.getLeft());
            postOrder(root.getRight());
            System.out.print(root.getElement() + "\t");
        }
    }
    //递归实现层次遍历，并按照层次顺序输出每个节点内容.
    public void Levelprinttree()
    {
        if(root == null)
            return;
        int height = getHeight();
        for(int i = 1; i <= height; i++){
            levelOrdertraversal(root,i);
        }
    }
    //层序遍历
    private void levelOrdertraversal(BinaryTreeNode root, int level){
        if(root == null || level < 1)
        {
            return;
        }
        if(level == 1)
        {
            System.out.print(root.getElement() + "\t");
            return;
        }
        levelOrdertraversal(root.getLeft(),level - 1);
        levelOrdertraversal(root.getRight(),level - 1);
    }
    //非递归实现层次遍历，并按照层次顺序输出每个节点内容.
    public void Levelprinttree2()
    {
        BinaryTreeNode temp;
        //队列存储二叉树
        Queue<BinaryTreeNode> queue = new LinkedList<BinaryTreeNode>();
        queue.offer(root);
        while(!queue.isEmpty()){
            temp=queue.poll();
            System.out.print(temp.getElement()+"\n");
            //如果该元素有左孩子，则左孩子入队；
            if(null!=temp.getLeft())
                queue.offer(temp.getLeft());
            //如果该元素有右孩子，则右孩子入队；
            if(null!=temp.getRight()){
                queue.offer(temp.getRight());
            }
        }
    }

}
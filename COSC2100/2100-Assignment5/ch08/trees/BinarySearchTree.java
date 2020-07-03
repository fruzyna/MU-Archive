//----------------------------------------------------------------------------
// BinarySearchTree.java          by Dale/Joyce/Weems                Chapter 8
//
// Defines all constructs for a reference-based BST
//----------------------------------------------------------------------------

package ch08.trees;

import ch05.queues.*;
import ch03.stacks.*;     

public class BinarySearchTree<T extends Comparable<T>> 
             implements BSTInterface<T>
{
  protected BSTNode<T> root;      // reference to the root of this BST

  boolean found;   // used by remove
  
  // for traversals
  protected LinkedUnbndQueue<T> inOrderQueue;    // queue of info
  protected LinkedUnbndQueue<T> preOrderQueue;   // queue of info
  protected LinkedUnbndQueue<T> postOrderQueue;  // queue of info

  public BinarySearchTree()
  // Creates an empty BST object.
  {
    root = null;
  }

  public boolean isEmpty()
  // Returns true if this BST is empty; otherwise, returns false.
  {
    return (root == null);
  }

  private int recSize(BSTNode<T> tree)
  // Returns the number of elements in tree.
  {
    if (tree == null)    
      return 0;
    else
      return recSize(tree.getLeft()) + recSize(tree.getRight()) + 1;
  }

  public int size()
  // Returns the number of elements in this BST.
  {
    return recSize(root);
  }

  public int size2()
  // Returns the number of elements in this BST.
  {
    int count = 0;
    if (root != null)
    {
      LinkedStack<BSTNode<T>> hold = new LinkedStack<BSTNode<T>>();
      BSTNode<T> currNode;
      hold.push(root);
      while (!hold.isEmpty())
      {
        currNode = hold.top();
        hold.pop();
        count++;
        if (currNode.getLeft() != null)
          hold.push(currNode.getLeft());
        if (currNode.getRight() != null)
          hold.push(currNode.getRight());
      }
    }
    return count;
  }

  private boolean recContains(T element, BSTNode<T> tree)
  // Returns true if tree contains an element e such that 
  // e.compareTo(element) == 0; otherwise, returns false.
  {
    if (tree == null)
      return false;       // element is not found
    else if (element.compareTo(tree.getInfo()) < 0)
      return recContains(element, tree.getLeft());   // Search left subtree
    else if (element.compareTo(tree.getInfo()) > 0)
      return recContains(element, tree.getRight());  // Search right subtree
    else
      return true;        // element is found
  }

  public boolean contains (T element)
  // Returns true if this BST contains an element e such that 
  // e.compareTo(element) == 0; otherwise, returns false.
  {
    return recContains(element, root);
  }
  
  private T recGet(T element, BSTNode<T> tree)
  // Returns an element e from tree such that e.compareTo(element) == 0;
  // if no such element exists, returns null.
  {
    if (tree == null)
      return null;             // element is not found
    else if (element.compareTo(tree.getInfo()) < 0)
      return recGet(element, tree.getLeft());          // get from left subtree
    else
    if (element.compareTo(tree.getInfo()) > 0)
      return recGet(element, tree.getRight());         // get from right subtree
    else
      return tree.getInfo();  // element is found
  }

  public T get(T element)
  // Returns an element e from this BST such that e.compareTo(element) == 0;
  // if no such element exists, returns null.
  {
    return recGet(element, root);
  }

  private BSTNode<T> recAdd(T element, BSTNode<T> tree)
  // Adds element to tree; tree retains its BST property.
  {
    if (tree == null)
      // Addition place found
      tree = new BSTNode<T>(element);
    else if (element.compareTo(tree.getInfo()) <= 0)
      tree.setLeft(recAdd(element, tree.getLeft()));    // Add in left subtree
    else
      tree.setRight(recAdd(element, tree.getRight()));   // Add in right subtree
    return tree;
  }

  public void add (T element)
  // Adds element to this BST. The tree retains its BST property.
  {
    root = recAdd(element, root);
  }

  private T getPredecessor(BSTNode<T> tree)
  // Returns the information held in the rightmost node in tree
  {
    while (tree.getRight() != null)
      tree = tree.getRight();
    return tree.getInfo();
  }

  private BSTNode<T> removeNode(BSTNode<T> tree)
  // Removes the information at the node referenced by tree.
  // The user's data in the node referenced by tree is no
  // longer in the tree.  If tree is a leaf node or has only
  // a non-null child pointer, the node pointed to by tree is
  // removed; otherwise, the user's data is replaced by its
  // logical predecessor and the predecessor's node is removed.
  {
    T data;

    if (tree.getLeft() == null)
      return tree.getRight();
    else if (tree.getRight() == null) 
      return tree.getLeft();
    else
    {
      data = getPredecessor(tree.getLeft());
      tree.setInfo(data);
      tree.setLeft(recRemove(data, tree.getLeft()));  
      return tree;
    }
  }

  private BSTNode<T> recRemove(T element, BSTNode<T> tree)
  // Removes an element e from tree such that e.compareTo(element) == 0
  // and returns true; if no such element exists, returns false. 
  {
    if (tree == null)
      found = false;
    else if (element.compareTo(tree.getInfo()) < 0)
      tree.setLeft(recRemove(element, tree.getLeft()));
    else if (element.compareTo(tree.getInfo()) > 0)
      tree.setRight(recRemove(element, tree.getRight()));
    else  
    {
      tree = removeNode(tree);
      found = true;
	 }
    return tree;
  }

  public boolean remove (T element)
  // Removes an element e from this BST such that e.compareTo(element) == 0
  // and returns true; if no such element exists, returns false. 
  {
    root = recRemove(element, root);
    return found;
  }

  private void inOrder(BSTNode<T> tree)
  // Initializes inOrderQueue with tree elements in inOrder order.
  {
    if (tree != null)
    {
      inOrder(tree.getLeft());
      inOrderQueue.enqueue(tree.getInfo());
      inOrder(tree.getRight());
    }
  }

  private void preOrder(BSTNode<T> tree)
  // Initializes preOrderQueue with tree elements in preOrder order.
  {
    if (tree != null)
    {
      preOrderQueue.enqueue(tree.getInfo());
      preOrder(tree.getLeft());
      preOrder(tree.getRight());
    }
  }

  private void postOrder(BSTNode<T> tree)
  // Initializes postOrderQueue with tree elements in postOrder order.
  {
    if (tree != null)
    {
      postOrder(tree.getLeft());
      postOrder(tree.getRight());
      postOrderQueue.enqueue(tree.getInfo());
    }
  }

  public int reset(int orderType)
  // Initializes current position for an iteration through this BST
  // in orderType order. Returns current number of nodes in the BST.
  {
    int numNodes = size();

    if (orderType == INORDER)
    {
      inOrderQueue = new LinkedUnbndQueue<T>();
      inOrder(root);
    }
    else
    if (orderType == PREORDER)
    {
      preOrderQueue = new LinkedUnbndQueue<T>();
      preOrder(root);
    }
    if (orderType == POSTORDER)
    {
      postOrderQueue = new LinkedUnbndQueue<T>();
      postOrder(root);
    }
    return numNodes;
  }

  public T getNext (int orderType)
  // Preconditions: The BST is not empty
  //                The BST has been reset for orderType
  //                The BST has not been modified since the most recent reset
  //                The end of orderType iteration has not been reached
  //
  // Returns the element at the current position on this BST for orderType
  // and advances the value of the current position based on the orderType. 
  {
    if (orderType == INORDER)
      return inOrderQueue.dequeue();
    else
    if (orderType == PREORDER)
      return preOrderQueue.dequeue();
    else
    if (orderType == POSTORDER)
      return postOrderQueue.dequeue();
    else return null;
  }
  
  public void printSideways(){
	  printSideways(root,"");
  }

  private void printSideways(BSTNode<T> tree, String indent) {
	  if(tree != null) {
		  printSideways(tree.getRight(),indent+"    ");
		  System.out.println(indent+tree.getInfo());
		  printSideways(tree.getLeft(),indent+"    ");
	  }

  }
  

  //finds the second largest number in a sorted binary tree
  public T secondLargest() {
	  BSTNode<T> node = root;
	  boolean found = false;
	  while(!found) {
		  node = node.getRight();
		  //second to last far right is checked for
		  if(node.getRight().getRight() == null){
			  found = true;
		  }
	  }
	  return node.getInfo();
  }

  //prints the path on the way to each leaf node
  public void printPaths() {
	  //starts recursion
	  findPaths("", root);
  }
  
  //looks for the next node in the path
  public void findPaths(String path, BSTNode<T> node) {
	  //adds an arrow to the string
	  if(!path.equals("")) {
		  path = path + " > ";
	  }
	  //adds the current node to the path string
	  path = path + node.getInfo().toString();
	  
	  //if there is a left add that to the path
	  if(node.getLeft() != null) {
		  findPaths(path, node.getLeft());
	  }
	  
	  //if there is a right add that to the path
	  if(node.getRight() != null) {
		  findPaths(path, node.getRight());
	  }
	  
	  //if this is a leaf node print out the path
	  if(node.getLeft() == null && node.getRight() == null) {
		  System.out.println("New leaf at: " + path);
	  }
  }
  
  //used to switch all lefts and rights in a tree
  public BinarySearchTree<T> reverse() {
	  BinarySearchTree<T> reversed = new BinarySearchTree<>();
	  
	  //roots are the same
	  reversed.root = new BSTNode<T>(root.getInfo());
	  //begins flipping children
	  flip(reversed.root, root);
	  
	  //returns end result
	  return reversed;
  }
  
  //recursive method to switch the left and right nodes
  public void flip(BSTNode<T> flip, BSTNode<T> node) {
	  //if the right is null set the left null and stop that path
	  if(node.getRight() == null) {
		  flip.setLeft(null);
	  }
	  else {
		  //give the left node the info of the right
		  flip.setLeft(new BSTNode<T>(node.getRight().getInfo()));
		  //continue down that line
		  flip(flip.getLeft(), node.getRight());
	  }
	  
	  //if the left is null set the right null and stop that path
	  if(node.getLeft() == null) {
		  flip.setRight(null);
	  }
	  else {
		  //give the right node the info of the left
		  flip.setRight(new BSTNode<T>(node.getLeft().getInfo()));
		  //continue down that line
		  flip(flip.getRight(), node.getLeft());
	  }
  }
  
  public int height() {
	  return findHeight(root);
  }
  
  //recursive method used to determine the maximum height of the tree
  public int findHeight(BSTNode<T> node) {
	  int left = 1;
	  int right = 1;
	  
	  //add one to both the left and right if they are not null
	  if(node.getLeft() != null) {
		  left += findHeight(node.getLeft());
	  }
	  if(node.getRight() != null) {
		  right += findHeight(node.getRight());
	  }
	  
	  //start with one at any leaf node
	  if(node.getRight() == null && node.getLeft() == null) {
		  return 1;
	  }
	  
	  //determine the bigger side and continue with that
	  if(left >= right) {
		  return left;
	  }
	  else {
		  return right;
	  }
  }
  
  public int count(T element) {
	  int count = 0;
	  
	  try {
		  while(true) {
			  if(getNext(INORDER).equals(element)) {
				  count++;
			  }
		  }
	  }
	  catch(QueueUnderflowException e) {
		  
	  }
	  return count;
  }

}
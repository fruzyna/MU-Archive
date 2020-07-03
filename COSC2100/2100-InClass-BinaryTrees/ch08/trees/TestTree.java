package ch08.trees;

import ch08.trees.BinarySearchTree;

public class TestTree {

	public static void main(String[] args) {
		
		BinarySearchTree<Integer> tree= new BinarySearchTree<Integer>();
		tree.add(50);
		tree.add(40);
		tree.add(30);
		tree.add(45);
		tree.add(60);
		tree.add(90);
		tree.add(80);
		tree.add(100);
		
		tree.printSideways();
		
		int treeSize = tree.reset(BinarySearchTree.INORDER);
        System.out.println("The tree in Inorder is:");
        for (int count = 1; count <= treeSize; count++)
        {
          int element = (Integer) tree.getNext(BinarySearchTree.INORDER);
          System.out.println(element);
        }
		
        tree.printLeaves();
		
	}

}
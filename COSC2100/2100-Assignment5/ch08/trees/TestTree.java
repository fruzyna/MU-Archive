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
		System.out.println("-----------------");
		// #5
		tree.reverse().printSideways();
		
		// #4
        tree.printPaths();
		
        // #3
        System.out.println("Height: " + tree.height());
        
        // #2
        System.out.println("Second largest: " + tree.secondLargest());
	}

}
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		System.out.println("Test One:");
		testOne();
		System.out.println("Test Two:");
		testTwo();
	}

	public static void testOne() {
		//establishment of test nodes
		Node zero = new Node(0);
		Node one = new Node(1);
		Node two = new Node(2);
		Node three = new Node(3);
		Node four = new Node(4);
		Node five = new Node(5);
		Node six = new Node(6);
		Node seven = new Node(7);
		//adding links to nodes
		five.add(seven);
		six.add(seven);
		four.add(seven);
		one.add(five);
		one.add(two);
		four.add(six);
		zero.add(three);
		zero.add(four);
		//list of all head nodes
		ArrayList<Node> list = new ArrayList<Node>();
		list.add(zero);
		list.add(one);
		//starts sort with graph
		Graph graph = new Graph(list);
		graph.topSort();
	}
	
	public static void testTwo() {
		//establishment of test nodes
		Node zero = new Node(0);
		Node one = new Node(1);
		Node two = new Node(2);
		Node three = new Node(3);
		Node four = new Node(4);
		Node five = new Node(5);
		Node six = new Node(6);
		Node seven = new Node(7);
		//adding links to nodes
		five.add(seven);
		four.add(seven);
		three.add(five);
		one.add(three);
		one.add(two);
		//list of all head nodes
		ArrayList<Node> list = new ArrayList<Node>();
		list.add(zero);
		list.add(one);
		list.add(four);
		list.add(six);
		//starts sort with graph
		Graph graph = new Graph(list);
		graph.topSort();
	}
}
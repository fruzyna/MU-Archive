import java.util.ArrayList;
import java.util.Stack;

public class Graph {
	ArrayList<Node> nodes;
	
	public Graph(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	
	/* Runs recursive function to sort node*/
	public void topSort() {
		//tracks the current stack
		Stack<Node> running = new Stack<Node>();
		//and the current sorted state
		ArrayList<Node> sorted = new ArrayList<Node>();
		//for every head node it starts a step
		for(Node node : nodes) {
			step(sorted, running, node);
		}
		
		//prints out result in reverse order
		for(int i = sorted.size(); i > 0; i--) {
			System.out.println(sorted.get(i - 1).value);
		}
	}
	
	/*Recursive step used to order each node*/
	public void step(ArrayList<Node> sorted, Stack<Node> running, Node node) {
		//puts the node in the running stack
		running.push(node);
		//steps for every linked node
		for(Node nnode : node.links) {
			//unless it already has been visited
			if(!sorted.contains(nnode) && !running.contains(nnode)) {
				step(sorted, running, nnode);
			}
		}
		//moves the node from the running stack to the result
		sorted.add(running.pop());
	}
}
package assign4;
/**
 * DoubleLL.java
 * A class for containing and using a double linked list of any type
 * @author Liam Fruzyna
 *
 * @param <T>	Generic type for whatever method to be used in list
 */
public class DoubleLL<T> {
	private Node<T> head;
	
	/**
	 * Adds a new generic node to the end of the list
	 * @param node New generic node
	 */
	public void add(Node<T> node) {
		if(head == null) {
			//If the list doesn't have a first node make this one first
			head = node;
		}
		else {
			//Otherwise find the last node and put it after that one
			Node<T> current = head;
			while(current.getNext() != null)
			{
				current = current.getNext();
			}
			current.setNext(node);
			node.setLast(current);
		}
	}
	
	/**
	 * Gets a generic node at a given specific place in the list
	 * @param inst Location of the node in the list
	 * @return The node in the given location
	 */
	public Node<T> get(int inst) {
		Node<T> current = head;
		for(int i = 0; i < inst; i++) {
			//goes to given location and returns what is there
			current = current.getNext();
		}
		return current;
	}
	
	/**
	 * Determines and returns the size of the list
	 * @return Amount of items in the list
	 */
	public int size() {
		int count = 0;
		Node<T> current = head;
		while(current != null)
		{
			//counts every item
			count++;
			current = current.getNext();
		}
		return count;
	}
}

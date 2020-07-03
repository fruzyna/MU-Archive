package assign4;
/**
 * Node.java
 * Generic node class for use in the double linked list
 * @author Liam Fruzyna
 *
 * @param <T> Generic type to use for the node
 */
public class Node<T> {
	
	private Node<T> next;
	private Node<T> last;
	private T obj;
	
	/**
	 * Constructor that sets the object in the list to a given one
	 * @param obj Generic object to set in node
	 */
	public Node(T obj) {
		this.obj = obj;
	}
	
	/**
	 * Updates the next node link to the new next
	 * @param next New next node
	 */
	public void setNext(Node<T> next) {
		this.next = next;
	}
	
	/**
	 * Updates the last node link to the new last
	 * @param last New previous node
	 */
	public void setLast(Node<T> last) {
		this.last = last;
	}
	
	/**
	 * Updates the generic object in the node to a new one
	 * @param obj New generic object
	 */
	public void set(T obj) {
		this.obj = obj;
	}
	
	/**
	 * Returns the next linked node
	 * @return Next node
	 */
	public Node<T> getNext() {
		return next;
	}
	
	/**
	 * Returns the last linked node
	 * @return Last linked node
	 */
	public Node<T> getLast() {
		return last;
	}
	
	/**
	 * Returns the stored generic object
	 * @return Stored generic object
	 */
	public T get() {
		return obj;
	}
}

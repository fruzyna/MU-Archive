/**
 * 
 */

import support.*;

/**
 * @author Praveen Madiraju
 *
 */
public class DList<T> {
	
  protected DLLNode<T> header;   
  protected DLLNode<T> trailer;

  protected int size;
    
/**
 * 
 */
public DList() {
	header = null;
	trailer = null;
	size = 0;

}

/**
 * @return the header
 */
public DLLNode<T> getHeader() {
	return header;
}

/**
 * @return the trailer
 */
public DLLNode<T> getTrailer() {
	return trailer;
}


/**
 * @return the size
 */
public int getSize() {
	return size;
}

// add element to the front of the list
public void addToFront(T elem) {
	DLLNode<T> newNode = new DLLNode<T>(elem);
	
	if (header == null) {
		header = newNode;
	}
	if (trailer == null)
		trailer = newNode;
	else {
		newNode.setLink(header);
		header.setBack(newNode);
		header = newNode;
	}
	size++;

}

//add element to the end of the list
public void addToLast(T elem) {
	DLLNode<T> newNode = new DLLNode<T>(elem);
	newNode.setLink(null);
	
	if (trailer == null) {
		trailer = newNode;
		header = newNode;
	}
	else {
		trailer.setLink(newNode);
		newNode.setBack(trailer);
		trailer = newNode;		
	}
	
	size++;
}

public boolean isEmpty()
{
    return header == null;
}

public void print() {
	DLLNode<T> v = header;
	while (v != null) {
		System.out.println(v.getInfo());
		v = (DLLNode)v.getLink();
	}
		
}

public void remove(DLLNode<T> node)
{
	DLLNode<T> current = header;
	for(int i = 0; i < size; i++)
	{
		if(node == current)
		{
			current.getParent().setNode(current.getLink());
			current.getNode().setParent(current.getParent());
		}
	}
}
public void removeLast()
{
	trailer.getParent().setNode(trailer.getLink());
	trailer.getNode().setParent(trailer.getParent());
	trailer = trailer.getParent();
}



}

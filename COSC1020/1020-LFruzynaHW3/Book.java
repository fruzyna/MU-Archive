/**
 * Book.java
 * A container for a book's title and price
 * 
 * @author mail929
 *
 */
public class Book {
	String name;
	double price;
	
	/**
	 * Constructor for Book class, initializes variables
	 * 
	 * @param name Title of the book
	 * @param price Price of the book
	 */
	public Book(String name, double price) {
		this.name = name;
		this.price = price;
	}
}

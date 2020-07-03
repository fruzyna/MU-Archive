import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

/**
 * ShoppingCart.java
 * Main class for the bookstore. 
 * 
 * @author mail929
 * @version 1 2/29/2016
 */
public class ShoppingCart implements ActionListener{
	JComboBox forSale;
	JButton add = new JButton("Add to Cart");
	JList cart;
	JButton remove = new JButton("Remove from Cart");
	JButton clear = new JButton("Clear Cart");
	JButton checkout = new JButton("Checkout");
	JLabel subtotal = new JLabel("Subtotal:");
	JLabel tax = new JLabel("Tax:");
	JLabel total = new JLabel("Total:");
	ArrayList<Book> booksList;
	DefaultListModel cartList;
	
	/**
	 * Main method for program.
	 * 
	 * @param args unused
	 */
	public static void main(String[] args) {
		new ShoppingCart();
	}
	
	/**
	 * Constructor for testing, no window
	 * 
	 * @param boolean Used to differentiate constructors
	 */
	public ShoppingCart(boolean b) {
		
	}
	
	/**
	 * Constructor for ShoppingCart, creates and fills out window and sets listeners. 
	 */
	public ShoppingCart() {
		cartList = new DefaultListModel();
		
		//creates and sets up the window
		JFrame frame = new JFrame("Bookstore");
		frame.setSize(500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new GridLayout(4, 1));

		//prepares books for listing
		try {
			booksList = getBooks();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] names = new String[booksList.size()];
		for(int i = 0; i < booksList.size(); i++) {
			names[i] = booksList.get(i).name + " $" + booksList.get(i).price;
		}
		//sets up top row
		forSale = new JComboBox(names);
		add.addActionListener(this);
		JPanel books = new JPanel(new GridLayout(1, 2));
		books.add(forSale);
		books.add(add);
		frame.add(books);
		
		//sets up 2nd row
		cart = new JList(cartList);
		cart.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		cart.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		cart.setVisibleRowCount(-1);
		frame.add(cart);
		
		//sets up 3rd row
		remove.addActionListener(this);
		clear.addActionListener(this);
		checkout.addActionListener(this);
		JPanel buttons = new JPanel(new GridLayout(1, 3));
		buttons.add(remove);
		buttons.add(clear);
		buttons.add(checkout);
		frame.add(buttons);
		
		//sets up final row
		JPanel outputs = new JPanel(new GridLayout(1, 3));
		outputs.add(subtotal);
		outputs.add(tax);
		outputs.add(total);
		frame.add(outputs);
		
		//makes the window appear
		frame.setVisible(true);
	}
	
	/**
	 * Reads all the books in the store from a file (book-prices.txt).
	 * 
	 * @return A list of books that are available to purchase
	 * @throws Exception From BufferedReader
	 */
	public ArrayList<Book> getBooks() throws Exception {
		ArrayList<Book> books = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader("book-prices.txt"));
		String line = "";
		while((line = br.readLine()) != null) {
			//parses all data from a line, separated by ;
			String[] parts = line.split(";");
			books.add(new Book(parts[0], Double.parseDouble(parts[1])));
		}
		return books;
	}
	
	/**
	 * Responds to all button presses
	 * 
	 * @param e Event that caused got caused by an action.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source.equals(add)) {
			//if the add button is pressed add the current item to cart
			String name = forSale.getSelectedItem().toString();
			cartList.addElement(name);
		}
		else if(source.equals(remove)) {
			//if the remove button is pressed remove the selected item from cart
			cartList.remove(cart.getSelectedIndex());
		}
		else if(source.equals(clear)) {
			//if the clear button is pressed clear the cart
			cartList.clear();
		}
		else if(source.equals(checkout)) {
			//if the checkout button is pressed
			double total = 0;
			//find all the books in the cart
			for(int i = 0; i < cartList.size(); i++) {
				String currentBook = ((String) cartList.getElementAt(i));
				currentBook = currentBook.substring(0, currentBook.indexOf(" $"));
				for(Book book : booksList) {
					System.out.println("Comparing " + currentBook + " and " + book.name);
					if(book.name.equals(currentBook)) {
						//add up their prices
						total += book.price;
					}
				}
			}
			//put the price info on the window
			subtotal.setText("Subtotal: $" + round(total, 2));
			tax.setText("Tax: $" + round(total * .06, 2));
			this.total.setText("Total: $" + round(total * 1.06, 2));
		}
	}
	
	/**
	 * Used to round a double value to a set amount of decimal places.
	 * 
	 * @param value The value to be rounded
	 * @param places The amount of decimal places to round to
	 * @return The rounded value
	 */
	public static double round(double value, int places) {
	    BigDecimal bd = new BigDecimal(value);
	    //round to however many decimal places is given
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}

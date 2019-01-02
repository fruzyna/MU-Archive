import java.util.ArrayList;
import java.util.List;

/**
 * Checkout.java
 * Class that computes the totals and makes a receipt
 * 
 * @author mail929
 *
 */
public class Checkout {
	List<DessertItem> items;
	
	/**
	 * Constructor for checkout class
	 * initializes items arraylist
	 */
	public Checkout() {
		items = new ArrayList<>();
	}
	
	/**
	 * Returns the number of items in the cart
	 * 
	 * @return Size of items array
	 */
	public int numberOfItems() {
		return items.size();
	}
	
	/**
	 * Adds a given item to the list of items
	 * 
	 * @param item Item to be added to cart
	 */
	public void enterItem(DessertItem item) {
		items.add(item);
	}
	
	/**
	 * Clears the arraylist of items
	 */
	public void clear() {
		items = new ArrayList<>();
	}
	
	/**
	 * Calculates the total cost of all the items
	 * 
	 * @return Total cost of all the items
	 */
	public int totalCost() {
		int total = 0;
		for(DessertItem item : items) {
			total += item.getCost();
		}
		return total;
	}
	
	/**
	 * Calculates the total tax charged on all the items
	 * 
	 * @return Tax that will be charged
	 */
	public int totalTax() {
		return (int) (totalCost() * (DessertShoppe.TAX_RATE / 100));
	}
	
	/**
	 * Produces a receipt in form of a String
	 * 
	 * @return A receipt
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		//creates header of receipt
		sb.append("     " + DessertShoppe.STORE_NAME + "\n");
		sb.append("     --------------------\n\n");
		
		//goes through all the items
		for(DessertItem item : items) {
			String output = item.toString();
			String[] words = output.split(" ");
			
			//sorts through all the words in the string
			int count = 0;
			for(int j = 0; j < words.length; j++) {
				//determines if the text will go to the next line
				if(count + words[j].length() + 1 > DessertShoppe.MAX_ITEM_NAME_SIZE) {
					sb.append("\n");
					count = 0;
				}
				
				sb.append(words[j] + " ");
				count += words[j].length() + 1;
			}
			
			//adds the price
			String price = DessertShoppe.cents2dollarsAndCents(item.getCost());
			for(int i = 0; i < DessertShoppe.MAX_ITEM_NAME_SIZE - count + 4 - price.length(); i++) {
				//evens the spacing
				sb.append(" ");
			}
			sb.append(price + "\n");
		}
		
		//adds the total tax
		sb.append("\nTax");
		String tax = DessertShoppe.cents2dollarsAndCents(totalTax());
		for(int i = 0; i < 26 - tax.length(); i++) {
			//evens the spacing
			sb.append(" ");
		}
		sb.append(tax);
		
		//adds the total cost
		sb.append("\nTotal Cost");
		String total = DessertShoppe.cents2dollarsAndCents(totalCost());
		for(int i = 0; i < 17 - tax.length(); i++) {
			//evens the spacing
			sb.append(" ");
		}
		sb.append(total);
		
		return sb.toString();
	}
}

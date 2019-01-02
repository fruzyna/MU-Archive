/**
 * Sundae.java
 * Object of a Ice Cream Sundae
 * 
 * @author mail929
 *
 */
public class Sundae extends IceCream {
	String topping;
	int toppingCost;
	
	/**
	 * Constructor for a Sundae
	 * 
	 * @param name Name of the DessertItem
	 * @param cost Cost of the DessertItem
	 * @param topping Topping for on the Sundae
	 * @param toppingCost Cost of the topping on the Sundae
	 */
	public Sundae(String name, int cost, String topping, int toppingCost) {
		super(name, cost);
		this.toppingCost = toppingCost;
		this.topping = topping;
	}
	
	/**
	 * Returns the cost of the sundae from a combination of the cost and the toppings cost
	 * 
	 * @return The total cost of the Sundae
	 */
	@Override
	public int getCost() {
		//returns cost and the topping cost
		return cost + toppingCost;
	}
	
	/**
	 * Returns a string for the sundae of its topping and name
	 * 
	 * @return The sundae's topping and name
	 */
	@Override
	public String toString() {
		return topping + " Sundae with " + name;
	}
}
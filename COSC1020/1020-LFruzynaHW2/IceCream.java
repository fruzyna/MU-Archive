/**
 * IceCream.java
 * 
 * IceCream object type of DessertItem
 * 
 * @author mail929
 *
 */
public class IceCream extends DessertItem {
	int cost;
	
	/**
	 * Constructor for ice cream
	 * 
	 * @param name Name of the ice cream
	 * @param cost Cost of the ice cream
	 */
	public IceCream(String name, int cost) {
		super(name);
		this.cost = cost;
	}
	
	/**
	 * Returns the cost of the ice cream
	 * 
	 * @return Cost of ice cream
	 */
	@Override
	public int getCost() {
		//returns the standard cost of ice cream
		return cost;
	}

	/**
	 * Returns the name of the ice cream
	 * 
	 * @return Description of the ice cream
	 */
	@Override
	public String toString() {
		return name;
	}
}
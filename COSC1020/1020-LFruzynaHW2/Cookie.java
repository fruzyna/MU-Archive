/**
 * Cookie.java
 * Cookie object type of Dessert Item
 * 
 * @author mail929
 *
 */
public class Cookie extends DessertItem {
	int number;
	int pricePerDozen;
	
	/**
	 * Constructor for the Cookies
	 * 
	 * @param name Name of the Cookies
	 * @param number Number of Cookies
	 * @param pricePerDozen Price Per Dozen Cookies
	 */
	public Cookie(String name, int number, int pricePerDozen) {
		super(name);
		this.number = number;
		this.pricePerDozen = pricePerDozen;
	}
	
	/**
	 * Returns the calculated cost of the candy
	 * 
	 * @return Cost of the candy
	 */
	@Override
	public int getCost() {
		//calculates cost in cents
		double price = (double) number / 12 * (double) pricePerDozen;
		//if it should be rounded up increase cost by 1 cent
		if(price - ((int) price) >= .5)
		{
			price++;
		}
		return (int) price;
	}

	/**
	 * Returns a string with the number of cookies, price per dozen, and name
	 * 
	 * @return Description of the cookies
	 */
	@Override
	public String toString() { 
		return number + " @ " + pricePerDozen + " /dz. " + name;
	}
}
/**
 * Candy.java
 * Object for the Candy type of DessertItem contains a weight and price per pound of candy
 * 
 * @author mail929
 *
 */
public class Candy extends DessertItem {
	double weight;
	int pricePerPound;
	
	/**
	 * Constructor for Candy
	 * 
	 * @param name Name of the Candy
	 * @param weight Weight of the candy
	 * @param pricePerPound Price per pound of candy in cents
	 */
	public Candy(String name, double weight, int pricePerPound) {
		super(name);
		this.weight = weight;
		this.pricePerPound = pricePerPound;
	}
	
	/**
	 * Returns the calculated price of the candy based off weight and price per pound
	 * 
	 * @return Cost of the candy
	 */
	@Override
	public int getCost() {
		//calculates cost in cents
		int price = (int) (pricePerPound * weight);
		//rounds the cost up a cent if it should be
		if((pricePerPound * weight) - price >= .5)
		{
			price++;
		}
		return price;
	}
	
	/**
	 * Returns a string with the weight, price per pound, and name of the candy
	 * 
	 * @return String describing the candy
	 */
	@Override
	public String toString() { 
		return weight + " lbs. @ " + pricePerPound + " /lb. " + name;
	}
}
package assign4;
/**
 * Container object for a company to be stored in node for double linked lists
 * @author Liam Fruzyna
 *
 */
public class Company {
	
	private String name;
	private String symbol;
	private DoubleLL<Integer> owned;
	private DoubleLL<Integer> sold;
	
	/**
	 * Constructor to give the company a name and stock symbol
	 * @param name Name of the company
	 * @param symbol Stock symbol for the company
	 */
	public Company(String name, String symbol) {
		this.name = name;
		this.symbol = symbol;
		owned = new DoubleLL<Integer>();
		sold = new DoubleLL<Integer>();
	}
	
	/**
	 * Returns the name of the company
	 * @return Name of the company
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the stock symbol of the company
	 * @return Stock symbol of the company
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Returns the list of all currently and previously owned shares of the company
	 * @return List of all owned shares
	 */
	public DoubleLL<Integer> getOwned() {
		return owned;
	}
	
	/**
	 * Returns the list of sold shares of the company
	 * @return List of sold shares
	 */
	public DoubleLL<Integer> getSold() {
		return sold;
	}
}

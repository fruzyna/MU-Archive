/**
 * Bank.java
 * Highest level of bank, contains type of bank and address
 * 
 * @author Liam Fruzyna
 * @version 1 2/23/16
 */
public class Bank {
	private String type;
	private String address;
	
	/**
	 * Constructor for Bank
	 * 
	 * @param type The kind of bank
	 * @param address The location of the bank
	 */
	public Bank(String type, String address) {
		this.type = type;
		this.address = address;
	}

	/**
	 * Returns the type of bank
	 * 
	 * @return type of bank
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type of bank
	 * 
	 * @param type The kind of bank
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns the address of the bank
	 * 
	 * @return address of bank
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address of bank
	 * 
	 * @param address The address of the bank
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Returns a string with the type of bank and address of the bank
	 */
	@Override
	public String toString() {
		return type + " bank located at " + address;
	}
}

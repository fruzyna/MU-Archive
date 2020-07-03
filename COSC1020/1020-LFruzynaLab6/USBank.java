/**
 * USBank.java
 * a type of investment bank with no fee
 * 
 * @author Liam Fruzyna
 * @version 1 2/23/16
 */
public class USBank extends InvestmentBank {

	/**
	 * Constructor for usbank
	 * @param address The location of the bank
	 * @param mortgageRate The mortgage rate from the bank
	 * @param loanTenure The tenure of the loan
	 */
	public USBank(String address, double mortgageRate, int loanTenure) {
		super(address, mortgageRate, loanTenure);
	}


	/**
	 * Calculates the monthly amount due
	 * 
	 * @param loanAmount amount of money requested
	 * @param year years the loan is active
	 * @return the amount due for the month
	 */
	@Override
	public double calcMonthlyAmount(int loanAmount, int year) {
		int rate = 0;
		if(year == 15) {
			rate = 5;
		}
		else {
			rate = 8;
		}
		double interest = ((rate / 100) * loanAmount) * year;
		return (loanAmount + interest) / (year * 12);
	}

}

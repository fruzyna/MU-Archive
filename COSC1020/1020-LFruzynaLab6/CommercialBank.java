/**
 * CommercialBank.java
 * object for commercial banks also contains mortgage rate and loan tenure
 * 
 * @author Liam Fruzyna
 * @version 1 2/23/16
 */
public class CommercialBank extends Bank {
	double mortgageRate;
	int loanTenure;
	
	public CommercialBank(String address, double mortgageRate, int loanTenure) {
		super("commercial", address);
		this.mortgageRate = mortgageRate;
		this.loanTenure = loanTenure;
	}

	/**
	 * Calculates the monthly amount due
	 * 
	 * @param loanAmount amount of money requested
	 * @param year years the loan is active
	 * @return the amount due for the month
	 */
	public double calcMonthlyAmount(int loanAmount, int year) {
		int rate = 0;
		if(year == 15) {
			rate = 5;
		}
		else {
			rate = 8;
		}
		double interest = ((rate / 100) * loanAmount) * year;
		return (loanAmount + interest + 2000) / (year * 12);
	}
	
	/**
	 * Returns a string about the bank
	 * 
	 * @return tenure, mortgagerate and monthly payment
	 */
	@Override
	public String toString() {
		return loanTenure + " year fixed at " + mortgageRate + " per month: " + calcMonthlyAmount(100000, loanTenure);
	}
	
	/**
	 * returns mortgage rate of bank
	 * 
	 * @return mortgage rate of bank
	 */
	public double getMortgageRate() {
		return mortgageRate;
	}

	/**
	 * sets the mortgage rate of the bank
	 * @param mortgageRate the mortgage rate of the bank
	 */
	public void setMortgageRate(double mortgageRate) {
		this.mortgageRate = mortgageRate;
	}

	/**
	 * returns the loan tenure of the bank
	 * @return tenure of the loan
	 */
	public int getLoanTenure() {
		return loanTenure;
	}

	/**
	 * sets the loan tenure of the bank
	 * @param loanTenure the tenure of the loan
	 */
	public void setLoanTenure(int loanTenure) {
		this.loanTenure = loanTenure;
	}
	
}

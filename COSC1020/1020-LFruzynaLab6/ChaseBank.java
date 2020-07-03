/**
 * ChaseBank.java
 * a type of commercial bank with a 2% rebate
 * @author mail929
 *
 */
public class ChaseBank extends CommercialBank {

	/**
	 * Constructor for chasebank
	 * @param address The location of the bank
	 * @param mortgageRate The mortgage rate from the bank
	 * @param loanTenure The tenure of the loan
	 */
	public ChaseBank(String address, double mortgageRate, int loanTenure) {
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
		double rate = 0;
		if(year == 15) {
			rate = 5;
		}
		else {
			rate = 8;
		}
		rate *= .98;
		double interest = ((rate / 100) * loanAmount) * year;
		return (loanAmount + interest + 2000) / (year * 12);
	}
}

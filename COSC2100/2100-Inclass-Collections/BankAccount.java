package wk6;


/**
   A bank account has a balance that can be changed by
   deposits and withdrawals.
*/
public class BankAccount implements Comparable<Object> {

	private double balance;

   /**
      Constructs a bank account with a zero balance.
   */
   public BankAccount()
   {
      balance = 0;
   }

   /**
      Constructs a bank account with a given balance.
      @param initialBalance the initial balance
   */
   public BankAccount(double initialBalance)
   {
      balance = initialBalance;
   }

   /**
      This constructor sets the starting balance
      to the value in the String argument.
      @param str The starting balance, as a String.
   */
   public BankAccount(String str)
   {
      balance = Double.parseDouble(str);
   }

   /**
      Deposits money into the bank account.
      @param amount the amount to deposit
   */
   public void deposit(double amount)
   {
      balance = balance + amount;
   }


   /**
      The deposit method makes a deposit into
      the account.
      @param str The amount to add to the
                 balance field, as a String.
   */
   public void deposit(String str)
   {
      balance += Double.parseDouble(str);
   }

   /**
      Withdraws money from the bank account.
      @param amount the amount to withdraw
   */
   public void withdraw(double amount)
   {
      balance = balance - amount;
   }

  /**
      The withdraw method withdraws an amount
      from the account.
      @param str The amount to subtract from
                 the balance field, as a String.
   */

   public void withdraw(String str)
   {
      balance -= Double.parseDouble(str);
   }

   /**
      Gets the current balance of the bank account.
      @return the current balance
   */
   public double getBalance()
   {
      return balance;
   }

   /**
      Transfers money from the bank account to another account
      @param amount the amount to transfer
      @param other the other account
   */
   public void transfer(double amount, BankAccount other)
   {
   	  withdraw(amount);
      other.deposit(amount);
   }
   
   /**
         checks if two objects are equal based on their balance.
         @param otherObject the BankAccount object to be compared
         @return true if both objects have equal balances, otherwise return false
   */
   public boolean equals (Object otherObject)
   {
     if (otherObject == null)
        return false;
     else if (getClass() != otherObject.getClass())
        return false;
     else
     {
        BankAccount otherAccount = (BankAccount)otherObject;
        return (balance == otherAccount.balance);
     }
   } // end of equals

   /**
       a string representation of BankAccount object
        @return the string representation of this object
    */
   public String toString ()
   {
	   return getClass().getName()+"[balance="+balance+"]";
   } // end of toString()

   public int compareTo(Object o) {
		BankAccount otherAccount = (BankAccount) o;
		if (this.balance < otherAccount.getBalance())
			return -1;
		else if (this.balance > otherAccount.getBalance())
			return 1;
		else
			return 0;
	}

}
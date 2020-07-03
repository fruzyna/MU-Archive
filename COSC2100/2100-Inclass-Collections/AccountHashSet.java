package wk6;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class AccountHashSet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Set<BankAccount> accountSet = new HashSet<BankAccount>();

		BankAccount johnAccount = new BankAccount(1000);
		BankAccount sallyAccount = new BankAccount(2000);
		BankAccount peterAccount = new BankAccount(3000);
		BankAccount testAccount = new BankAccount(10000);

		accountSet.add(johnAccount);
		accountSet.add(sallyAccount);
		accountSet.add(peterAccount);
		accountSet.add(testAccount);

		if (accountSet.contains(testAccount))
			System.out.println("testAccount already exists");

		Iterator<BankAccount> accountIterator = accountSet.iterator();

		while (accountIterator.hasNext()) {
			BankAccount account = accountIterator.next();
			System.out.println(account);
		}
	}

}
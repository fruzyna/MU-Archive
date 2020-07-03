package assign4;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * CapitalGains.java
 * Class that reads in data from 2 files, stocks.txt and transactions.txt in order to calculate the capital gain from the stocks.
 * @author Liam Fruzyna
 *
 */
public class StockTransaction {

	File stocks;
	File transactions;
	DoubleLL<Company> companies;
	
	public static void main(String[] args) {
		new StockTransaction();
	}

	/**
	 * Constructor reads files then waits for input to respond
	 */
	public StockTransaction() {
		stocks = new File("stocks.txt");
		transactions = new File("transactions.txt");
		companies = new DoubleLL<>();
		
		try {
			readFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		listenForInput();
	}
	
	/**
	 * Read both files, stocks and transactions, then puts them in double linked lists
	 * @throws IOException
	 */
	public void readFiles() throws IOException {
		//sets up reader for stocks.txt
		BufferedReader br = new BufferedReader(new FileReader(stocks));
		String line = "";
		//goes through every line
		while((line = br.readLine()) != null) {
			//splits into parts
			String[] parts = line.split(";");
			//saves parts into a new company in a node in the companies list
			companies.add(new Node<Company>(new Company(parts[1], parts[0])));
		}
		br.close();
		
		//sets up reader for transactions.txt
		br = new BufferedReader(new FileReader(transactions));
		line = "";
		//goes through every line
		while((line = br.readLine()) != null) {
			//splits into parts
			String[] parts = line.split(";");
			//puts the parts in easier forms to process
			boolean buy = false;
			if(parts[1].equals("buy"))
			{
				buy = true;
			}
			int amount = Integer.parseInt(parts[2]);
			int value = Integer.parseInt(parts[3].replace("$", ""));
			
			//adds every share transacted to a bought or sold list
			for(int i = 0; i < companies.size(); i++) {
				if(companies.get(i).get().getSymbol().equals(parts[0])) {
					for(int j = 0; j < amount; j++)
					{
						//inserts value of share into node in the appropriate list in the company
						if(buy)
						{
							companies.get(i).get().getOwned().add(new Node<Integer>(value));
						}
						else
						{
							companies.get(i).get().getSold().add(new Node<Integer>(value));
						}
					}
				}
			}
		}
		br.close();
	}

	/**
	 * Prompts for a stocks ticker symbol, then responds with how much realized gain or loss there is
	 */
	public void listenForInput() {
		//prompts for a symbol and waits
		Scanner kb = new Scanner(System.in);
		System.out.println("Please enter a stock quote for realized gain(or loss) of the stock: ");
		String input = kb.nextLine();
		
		boolean found = false;
		//looks for it in all companies
		for(int i = 0; i < companies.size(); i++) {
			Company c = companies.get(i).get();
			
			//when found
			if(c.getSymbol().equals(input)) {
				found = true;
				
				//get all owned and sold
				DoubleLL<Integer> owned = c.getOwned();
				DoubleLL<Integer> sold = c.getSold();
				int total = 0;
				
				//adds them up
				if(owned.size() > sold.size()) {
					for(int j = 0; j < sold.size(); j++) {
						int sell = sold.get(j).get();
						int buy = owned.get(j).get();
						int net = sell - buy;
						total += net;
					}
					
					//gives an appropriate output
					if(total > 0) {
						System.out.println("Congratulations, your realized gain for " + c.getName() + " is: $" + total);
					}
					else if(total < 0) {
						System.out.println("Sorry, your realized loss for " + c.getName() + " is: $" + (-total));
					}
					else {
						System.out.println("Sorry, no realized gain(or loss) reported for " + c.getName());
					}
				}
				else {
					//or an error if too many are sold
					System.out.println("Sorry, there is an error condition associated with " + c.getName() + "\nThe number of sold shares exceeds the total buy quantity.");
				}
			}
		}
		
		//if the stock isn't found give an error
		if(!found) {
			System.out.println("Sorry, that stock quote does not exist in the system.");
		}
	}
}

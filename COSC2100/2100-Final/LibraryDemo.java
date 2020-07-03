import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

//COSC 2100 Final by Liam Fruzyna

public class LibraryDemo {

	HashMap<String, String> borrowers = new HashMap<>();
	HashMap<String, Book> books = new HashMap<>();
	ArrayList<Loan> loans = new ArrayList<>();
	
	public static void main(String[] args) {
		//Starts main class
		try {
			new LibraryDemo();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public LibraryDemo() throws IOException {
		//Reads borrowers file
		BufferedReader br = new BufferedReader(new FileReader(new File("borrowers.txt")));
		String line = br.readLine();
		String[] split = line.split(",");
		while((line = br.readLine()) != null) {
			//for every line splits up the data points and put in hashmap
			split = line.split(",");
			borrowers.put(split[0], split[1]);
		}
		br.close();

		//Reads books file
		br = new BufferedReader(new FileReader(new File("books.txt")));
		line = br.readLine();
		split = line.split(",");
		while((line = br.readLine()) != null) {
			//for every line splits up the data points and put in hashmap
			split = line.split(",");
			books.put(split[0], new Book(split[1], split[2]));
		}
		br.close();

		//Reads book loans file
		br = new BufferedReader(new FileReader(new File("book-loan.txt")));
		line = br.readLine();
		split = line.split(",");
		while((line = br.readLine()) != null) {
			//for every line splits up the data points and put in hashmap
			split = line.split(",");
			loans.add(new Loan(split[0], split[1], split[2], split[3]));
		}
		br.close();
		
		//Prompts and listens for card number
		Scanner kb = new Scanner(System.in);
		System.out.print("Please enter a borrower card number: ");
		String borrower = kb.nextLine();
		
		//Outputs all check out books by that card holder
		System.out.println("Books borrowed by " + borrower + " (" + borrowers.get(borrower) + ") are as follows:");
		for(int i = 0; i < loans.size(); i++) {
			Loan loan = loans.get(i);
			//Checks the borrower
			if(loan.borrower.equals(borrower))
			{
				//Outputs book loan info
				Book book = books.get(loan.id);
				System.out.println(book.name + ", due date : " + loan.dueDate);
			}
		}
	}
}

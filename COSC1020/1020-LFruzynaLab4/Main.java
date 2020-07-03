import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author mail929
 *
 */
public class Main {
	boolean running = true;
	ArrayList<Contact> contacts;
	Scanner kb;
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		try {
			contacts = load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		kb = new Scanner(System.in);
		while(running)
		{
			System.out.println("\n---------------");
			System.out.println("Contact Menu:");
			System.out.println("1) Add");
			System.out.println("2) Remove");
			System.out.println("3) Save");
			System.out.println("4) Load");
			System.out.println("5) Show All");
			System.out.println("6) Search");
			System.out.println("7) Exit");
			
			int choice = kb.nextInt();
			kb.nextLine();

			System.out.println();
			
			switch(choice) {
			case 1:
				//add contact
				System.out.println("New Contact:");
				System.out.print("Full Name: ");
				String[] name = kb.nextLine().split(" ");
				System.out.print("Phone Number: ");
				String phone = kb.nextLine();
				System.out.print("Email Address: ");
				String email = kb.nextLine();
				if(name.length < 2) {
					contacts.add(new Contact(name[0], "", phone, email));
				}
				else {
					contacts.add(new Contact(name[0], name[1], phone, email));
				}
				break;
			case 2:
				//remove contact
				printAll(contacts);
				System.out.print("Remove which number: ");
				contacts.remove(kb.nextInt());
				kb.nextLine();
				break;
			case 3:
				//save contacts
				try {
					save();
					System.out.println("Contacts Saved!");
				} catch (FileNotFoundException e) {
					System.out.println("Save Failed!");
					e.printStackTrace();
				}
				break;
			case 4:
				//load contacts from file
				try {
					contacts = load();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
			case 5:
				//print all contacts
				System.out.println("All Contacts:");
				printAll(contacts);
				break;
			case 6:
				//search contacts
				System.out.println("Search: ");
				String search = kb.nextLine();
				ArrayList<Contact> results = new ArrayList<>();
				for(Contact contact : contacts) {
					if((contact.getFirstName() + " " + contact.getLastName()).contains(search)
							|| contact.getPhone().contains(search)
							|| contact.getEmail().contains(search)) {
						results.add(contact);
					}
				}
				System.out.println("Results: ");
				printAll(results);
				break;
			case 7:
				//quit
				try {
					save();
					System.out.println("Contacts Saved!");
				} catch (FileNotFoundException e) {
					System.out.println("Save Failed!");
					e.printStackTrace();
				}
				System.out.println("Bye!");
				running = false;
				break;
			}
		}
	}
	
	public void printAll(ArrayList<Contact> contacts) {
		for(Contact contact : contacts) {
			System.out.println(contacts.indexOf(contact) + ") " + contact.getFirstName() + " " + contact.getLastName());
			System.out.println("-" + contact.getPhone());
			System.out.println("-" + contact.getEmail());
		}
	}
	
	public void save() throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new FileOutputStream(new File("contacts.txt")));
		for(Contact contact : contacts) {
			pw.println(contact.getFirstName()  + "%" + contact.getLastName() + "%" + contact.getPhone() + "%" + contact.getEmail());
		}
		pw.close();
	}
	
	public ArrayList<Contact> load() throws IOException {
		File file = new File("contacts.txt");
		if(!file.exists()) {
			file.createNewFile();
		}
		ArrayList<Contact> contacts = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = "";
		while((line = br.readLine()) != null) {
			String[] parts = line.split("%");
			contacts.add(new Contact(parts[0], parts[1], parts[2], parts[3]));
		}
		return contacts;
	}
}

import java.util.Scanner;

public class Driver {
	
	static Scanner kb = new Scanner(System.in); 
	
	public static void main(String [] args){
		LinkedList list = new LinkedList();
		
		while(true){
			int choice  = menu();
			int intData = 0;
			String stringData = "";
			
			switch(choice){
			case 1:
				System.out.println("Enter int data: ");
				intData = kb.nextInt();
				kb.nextLine();
				System.out.println("Enter string data: ");
				stringData = kb.nextLine();
				list.insert(intData, stringData);
				break;
			case 2:
				System.out.println("Enter int data: ");
				intData = kb.nextInt();
				kb.nextLine();
				System.out.println("Enter string data: ");
				stringData = kb.nextLine();
				if(list.delete(intData, stringData)){
					System.out.println("Data deleted...");
				}
				else System.out.println("Error: Data not found.");
				
				break;
			case 3:
				System.out.println("Enter int data: ");
				intData = kb.nextInt();
				kb.nextLine();
				System.out.println("Enter string data: ");
				stringData = kb.nextLine();
				if(list.search(intData, stringData)){
					System.out.println("Data found...");
				}
				else System.out.println("Error: Data not found.");
				
				break;
			case 4:
				list.print();
				break;
			case 5:
				System.out.println("Good bye...");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid input!");
			}
			
		}
	}
	
	public static int menu(){
		System.out.println("\nLinked List Operations:\n1.Insert\n2.Delete\n3.Search\n4.Display\n5.Exit\nPlease enter your choice:...");
		int choice = kb.nextInt();
		
		return choice;
	}

}

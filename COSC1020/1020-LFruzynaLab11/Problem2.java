import java.util.Scanner;

public class Problem2 {

	public static void main(String[] args) {
		//init scanner to read keyboard
		Scanner kb = new Scanner(System.in);
		
		//loops program
		while(true){
			System.out.print("Enter a whole number: ");
			
			//get input
			String input = kb.nextLine();
			try {
				try {
					//output input if int
					int in = Integer.parseInt(input);
					System.out.println("You entered: " + in);
				}
				catch(NumberFormatException e) {
					//catch is the number isn't an int
					throw new InvalidInputException();
				}
			}
			catch(InvalidInputException e) {
				//print the error message
				System.out.println(e.getMessage());
			}
		}
	}
}
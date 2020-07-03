import java.util.Scanner;

public class Problem1 {

	public static void main(String[] args) {
		//init scanner to read keyboard
		Scanner kb = new Scanner(System.in);
		
		//loops program
		while(true){
			System.out.print("Enter a number: ");
			
			//get input
			String input = kb.nextLine();
			try {
				try {
					//output square of input
					Double in = Double.parseDouble(input);
					System.out.println("The square of the number is: " + (in * in));
				}
				catch(NumberFormatException e) {
					//catch is the number isn't a double
					throw new InvalidInputException(input);
				}
			}
			catch(InvalidInputException e) {
				//print the error message
				System.out.println(e.getMessage());
			}
		}
	}
}


public class InvalidInputException extends Exception {
	
	public InvalidInputException() {
		super("Incorrect input. Please try again!");
	}
	
	public InvalidInputException(String input) {
		super("You provided " + input + " which is not a valid input!");
	}
}

import java.util.Scanner;

public class LetterCount {

	public static void main(String[] args) {
		/*
		//get the word and character
		Scanner kb = new Scanner(System.in);
		System.out.print("Enter word: ");
		String s = kb.nextLine().toLowerCase();
		System.out.print("Enter character: ");
		char c = kb.nextLine().toLowerCase().charAt(0);
		
		//print out the result
		System.out.println("There are " + countLetter(s, c) + " " + c +" in " + s);*/
		
		System.out.println("There are " + countLetter("hi there", 'h') + " h in hi there");
		System.out.println("There are " + countLetter("Mississippi", 'i') + " i in Mississippi");
		System.out.println("There are " + countLetter("Marquette", 's') + " s in Marquette");
	}

	public static int countLetter(String s, char c) {
		if(!s.contains(Character.toString(c))) {
			//if the character isn't there finish the sequence
			return 0;
		}
		else {
			//remove the next instance of c and add 1 to the count
			return countLetter(s.substring(s.indexOf(c) + 1), c) + 1;
		}
	}
}

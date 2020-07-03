import java.util.Scanner;

public class Main {

	public static void main(String[] args)
	{
		String input = "default";
		while(input.length() != 0)
		{	
			Scanner kb = new Scanner(System.in);
			System.out.print("Enter a phrase: ");
			input = kb.nextLine();
			if(input.length() != 0)
			{
				System.out.println("your input contains: " + input.length() + " characters");
				System.out.println("including:");
				int vowels = 0;
				int consonants = 0;
				int spaces = 0;
				for(int i = 0; i < input.length(); i++)
				{
					switch(input.charAt(i))
					{
						case 'a':
						case 'e':
						case 'i':
						case 'o':
						case 'u':
							vowels++;
							break;
						case ' ':
							spaces++;
							break;
						default:
							consonants++;
							break;
					}
				}
				System.out.println(vowels + " vowels");
				System.out.println(consonants + " consonants");
				System.out.println(spaces + " spaces");
			}
		}
		System.out.println("Bye");
	}
}

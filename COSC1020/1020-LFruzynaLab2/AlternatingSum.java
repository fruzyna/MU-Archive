import java.util.ArrayList;
import java.util.Scanner;

//calculates the alternating sum of all inputted numbers
//created by Liam Fruzyna 1/26/2016
public class AlternatingSum
{
	public static void main(String[] args)
	{
		//initialization of variables
		Scanner kb = new Scanner(System.in);
		String input = "";
		ArrayList<Integer> ints = new ArrayList<>();
		
		//runs until the latest input is done
		while(!input.equals("done"))
		{
			System.out.print("Please enter value or enter done: ");
			input = kb.nextLine();
			
			//only save to array if it isn't done
			if(!input.equals("done"))
			{
				int i = Integer.parseInt(input);
				ints.add(new Integer(i));
			}
		}
		
		//creates the alternating sum
		int sum = 0;
		for(int i = 0; i < ints.size(); i++)
		{
			//if it is divisible by 0 add
			if(i % 2 == 0)
			{
				sum += ints.get(i);
			}
			else
			{
				//otherwise subtract
				sum -= ints.get(i);
			}
		}
		
		//output the alternating sum
		System.out.println("The alternating sum of all elements is: " + sum);
	}
}

import java.util.ArrayList;
import java.util.Scanner;

//calculates a diver's score based off of 7 judges' scores
//created by Liam Fruzyna 1/26/2016
public class DivingScore
{
	public static void main(String[] args)
	{
		//init variables
		Scanner kb = new Scanner(System.in);
		ArrayList<Double> scores = new ArrayList<>();
		
		//get dive difficulty
		System.out.println("Enter the degree of difficulty for the dive (1.2-3.8).");
		double diff = kb.nextDouble();
		kb.nextLine();
		
		//get all scores from user
		for(int i = 1; i < 8; i++)
		{
			System.out.println("Enter score for judge " + i + " (0-10).");
			scores.add(kb.nextDouble());
			kb.nextLine();
		}
		
		//find and remove lowest score
		Double lowest = new Double(10);
		for(int i = 0; i < scores.size(); i++)
		{
			if(scores.get(i) < lowest)
			{
				lowest = scores.get(i);
			}
		}
		scores.remove(lowest);
		
		//find and remove highest score
		Double highest = new Double(0);
		for(int i = 0; i < scores.size(); i++)
		{
			if(scores.get(i) > highest)
			{
				highest = scores.get(i);
			}
		}
		scores.remove(highest);

		//total up all the scores
		double total = 0;
		for(int i = 0; i < scores.size(); i++)
		{
			total += scores.get(i);
		}
		
		//multiply total by difficulty and .6
		total *= diff;
		total *= .6;
		
		//output final score
		System.out.println("The diver's final score is " + total);
	}
}

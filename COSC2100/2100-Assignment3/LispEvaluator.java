/**
 * Class to contain methods for evaluating a Lisp expression
 * 
 * @author Liam Fruzyna
 *
 */
public class LispEvaluator
{
	
	/**
	 * Takes a String representation of a Lisp math expression and evaluates it using a stack.
	 * 
	 * @param lisp String representation of Lisp math expression
	 * @return Answer of given Lisp math expression
	 */
	public static double calculate(String lisp)
	{
		System.out.println("Calculating: " + lisp);
		
		//Replaces ( with spaces and makes sure there is a space before ) so that the string can be separated cleanly
		String function = lisp.replace("(", "").replace(")" , " )");
		
		//Splits the formatted string by spaces so numbers and types are in an array
		String[] parts = function.split(" ");
		
		StringLL stack = new StringLL();
	
		//Goes through every object in the array
		for(int i = 0; i < parts.length; i++)
		{
			String part = parts[i];
			if(part.equals(")"))
			{
				//If the next value is a ) calculate the equation it is closing
				double subAnswer = calcPart(stack);
				if(subAnswer != 0)
				{
					//If the equation didn't compute to 0 add it to the above equation
					stack.push("" + subAnswer);
				}
			}
			else
			{
				//Add the value to the stack
				stack.push(part);
			}
			
			//Print out current status of stack
			stack.outputStack();
		}
		
		//Print out and return answer when complete
		System.out.println("\n \n" + lisp + " = " + stack.top().getInfo() + "\n \n");
		return Double.parseDouble(stack.top().getInfo());
	}
	
	/**
	 * Calculates a single equation of Lisp math and returns the answer.
	 * 
	 * @param stack The stack being used to evaluate the lisp expression
	 * @return Answer to the uppermost equation currently in the stack
	 */
	public static double calcPart(StringLL stack)
	{
		LLStringNode last = stack.top();
		
		//Total value of the equation
		double total = 0;
		
		//New stack to hold equation
		StringLL temp = new StringLL();

		try {
			while(true)
			{
				//Move the last found equation to the new temp stack
				temp.push(last.getInfo());
				
				//Knows the equation is over because the last part wasn't a number
				Double.parseDouble(temp.top().getInfo());
				stack.pop();
				last = stack.top();
			}
		}
		catch(NumberFormatException e)
		{
			//The type of equation it is (+, -, *, /)
			String type = last.getInfo();
			System.out.println("Broke on " + type);
			
			//Make sure the equation has values
			if(temp.top().getParent() != null)
			{
				//Gets the first number in the equation adds it to the total and removes it from the stack
				LLStringNode next = temp.top().getParent();
				temp.pop();
				total = Double.parseDouble(next.getInfo());
				System.out.println(total);
				
				//If it is just a negative number not a subtraction make the total negative
				if(type.equals("-") && next.getParent() == null)
				{
					total = -total;
				}
				
				//If it is only dividing one value to do 1/value
				if(type.equals("/") && next.getParent() == null)
				{
					total = 1/total;
				}
				
				//Parse through all the values in the equation
				while((next = next.getParent()) != null)
				{
					System.out.println(next.getInfo());
					
					//Remove the last used value
					temp.pop();
					
					if(type.equals("+"))
					{
						//Add if addition
						total += Double.parseDouble(next.getInfo());
					}
					else if(type.equals("-"))
					{
						//Subtract if subtraction
						total -= Double.parseDouble(next.getInfo());
					}
					else if(type.equals("*"))
					{
						//Multiply if multiplication
						total *= Double.parseDouble(next.getInfo());
					}
					else if(type.equals("/"))
					{
						//Divide if division
						total /= Double.parseDouble(next.getInfo());
					}
				}
			}
			else if(type.equals("*"))
			{
				total = 1;
			}
			
			//Remove the last value (normally the operand)
			stack.pop();
		}
		System.out.println("Sum at: " + total);
		
		//Return the answer
		return total;
	}
}

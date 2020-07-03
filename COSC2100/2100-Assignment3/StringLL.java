
public class StringLL
{
	LLStringNode end;

	/**
	 * Constructor to create Linked List Stack with an initial node
	 * @param start First node in the Linked List
	 */
	public StringLL(String start)
	{
		push(start);
	}
	
	/**
	 * Blank constructor to create an empty stack
	 */
	public StringLL(){}
	
	/**
	 * Returns the top/last added item in the stack
	 * @return Top or last added item in the stack
	 */
	public LLStringNode top()
	{
		return end;
	}
	
	/**
	 * Removes the top/last added item in the stack
	 */
	public void pop()
	{
		if(end != null)
		{
			if(end.getParent() != null)
			{
				end.getParent().setLink(null);
				end = end.getParent();
			}
			else
			{
				end = null;
				System.out.println("Stack now empty");
			}
		}
		else
		{
			System.out.println("Nothing to pop!");
		}
	}
	
	/**
	 * Adds a new item to the top of the stack
	 * @param element New data for the new node at the top of the stack
	 */
	public void push(String element)
	{
		if(end != null)
		{
			LLStringNode child = new LLStringNode(element);
			child.setParent(end);
			end.setLink(child);
			end = child;
		}
		else
		{
			end = new LLStringNode(element);
		}
	}
	
	/**
	 * Prints out the status of the stack
	 */
	public void outputStack()
	{
		LLStringNode p = top();
		while(p.getParent() != null)
		{
			p = p.getParent();
		}
		
		System.out.print("Stack: ");
		while(p.getLink() != null)
		{
			System.out.print(p.getInfo() + " ");
			p = p.getLink();
		}
		System.out.println();
	}
}

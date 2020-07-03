/**
 * Simple String node for Linked Lists with ability to go backwards and forwards
 * @author Liam Fruzyna
 *
 */
public class LLStringNode
{
	  private LLStringNode link;
	  private LLStringNode parent;
	  private String info;
	  
	  /**
	   * Constructor requests the base info String for the node
	   * @param info Main piece of data for the node
	   */
	  public LLStringNode(String info)
	  {
		  this.info = info;
		  link = null;
	  }
	 
	  /**
	   * Updates the info of the node with a new String
	   * @param info New String to set info as
	   */
	  public void setInfo(String info)
	  {
		  this.info = info;
	  }

	  /**
	   * Returns the info String of the node
	   * @return Info assigned to node
	   */
	  public String getInfo()
	  {
		  return info;
	  }

	  /**
	   * Updates the link to the next node
	   * @param link Next node in the Linked List
	   */
	  public void setLink(LLStringNode link)
	  {
		  this.link = link;
	  }
	  
	  /**
	   * Updates the parent link to the previous node
	   * @param parent Previous node in the Linked List
	   */
	  public void setParent(LLStringNode parent)
	  {
		  this.parent = parent;
	  }

	  /**
	   * Returns the next node in the Linked List
	   * @return Next node in the Linked List
	   */
	  public LLStringNode getLink()
	  {
		  return link;
	  }

	  /**
	   * Returns the previous node in the Linked List
	   * @return Previous node in the Linked List
	   */
	  public LLStringNode getParent()
	  {
		  return parent;
	  }
}

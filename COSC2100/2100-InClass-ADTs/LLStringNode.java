//----------------------------------------------------------------------------
// LLStringNode.java            by Dale/Joyce/Weems                  Chapter 2
//
// Implements String nodes for a Linked List.
//----------------------------------------------------------------------------

public class LLStringNode
{
  private String info;
  private LLStringNode link;
  
  public LLStringNode(String info)
  {
    this.info = info;
    link = null;
  }
 
  public void setInfo(String info)
  // Sets info string of this LLStringNode.
  {
    this.info = info;
  }

  public String getInfo()
  // Returns info string of this LLStringNode.
  {
    return info;
  }
 
  public void setLink(LLStringNode link)
  // Sets link of this LLStringNode.
  {
    this.link = link;
  }

  public LLStringNode getLink()
  // Returns link of this LLStringNode.
  {
    return link;
  }
}
 
 
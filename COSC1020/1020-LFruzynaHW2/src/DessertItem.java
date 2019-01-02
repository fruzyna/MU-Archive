// DessertItem.java - Dessert Item abstract superclass
// Suzanne Balik, 25 Jun 2002


/**
 * Abstract superclass for Dessert Item hierarchy
 * @author Suzanne Balik
 */
public abstract class DessertItem {
  
  protected String name;
  
/**
 * Null constructor for DessertItem class
 */
  public DessertItem() {
    this("");
  }
/**
 * Initializes DessertItem data
 */   
  public DessertItem(String name) {
    if (name.length() <= DessertShoppe.MAX_ITEM_NAME_SIZE)
      this.name = name;
    else 
      this.name = name.substring(0,DessertShoppe.MAX_ITEM_NAME_SIZE);
  }
/**
 * Returns name of DessertItem
 * @return name of DessertItem
 */  
  public final String getName() {
    return name;
  }
/**
 * Returns cost of DessertItem
 * @return cost of DessertItem
 */  
  public abstract int getCost();

}
   
  
  
   
   

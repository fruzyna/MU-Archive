// DessertShoppe.java - constants and method to format cents as dollars and cents
// Suzanne Balik, 25 Jun 2002

public class DessertShoppe {

  
  public final static double TAX_RATE = 6.5;        // 6.5%
  public final static String STORE_NAME = "M & M Dessert Shoppe";
  public final static int MAX_ITEM_NAME_SIZE = 25;
  public final static int COST_WIDTH = 6;
  
  public static String cents2dollarsAndCents(int cents) {
 
    String s = "";
     
    if (cents < 0) {
      s += "-";
      cents *= -1;
    }
    
    int dollars = cents/100;
    cents = cents % 100;
    
    if (dollars > 0)
      s += dollars;
    
    s +=".";
      
    if (cents < 10)
      s += "0";
      
    s += cents;
    return s;
  } 
}

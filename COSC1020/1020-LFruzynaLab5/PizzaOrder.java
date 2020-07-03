
public class PizzaOrder {
	Pizza one;
	Pizza two;
	Pizza three;
	int total;
	
	//constructor if the order has 1 pizza
	public PizzaOrder(Pizza one) {
		this.one = one;
		total = 1;
	}

	//constructor if the order has 2 pizzas
	public PizzaOrder(Pizza one, Pizza two) {
		this.one = one;
		this.two = two;
		total = 2;
	}

	//constructor if the order has 3 pizzas
	public PizzaOrder(Pizza one, Pizza two, Pizza three) {
		this.one = one;
		this.two = two;
		this.three = three;
		total = 3;
	}
	
	public void setNumPizzas(int numPizzas) {
		total = numPizzas;
	}
	
	public int getNumPizzas() {
		return total;
	}
	
	public void setPizza1(Pizza one) {
		this.one = one;
	}
	
	public void setPizza2(Pizza two) {
		this.two = two;
	}
	
	public void setPizza3(Pizza three) {
		this.three = three;
	}
	
	public Pizza getPizza1() {
		return one;
	}
	
	public Pizza getPizza2() {
		return two;
	}
	
	public Pizza getPizza3() {
		return three;
	}
	
	//calculates the total cost of the order
	public double calcTotal() {
		double cost = 0;
		switch(total) {
		case 3:
			cost += three.calcCost();
		case 2:
			cost += two.calcCost();
		case 1:
			cost += one.calcCost();
		}
		return cost;
	}
}

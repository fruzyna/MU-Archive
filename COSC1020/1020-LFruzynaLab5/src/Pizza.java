
public class Pizza {
	private String size;
	private int cheeses;
	private int pepperonis;
	private int hams;
	
	//constructor sets all the instance variables
	public Pizza(String size, int cheeses, int pepperonis, int hams) {
		this.size = size;
		this.cheeses = cheeses;
		this.pepperonis = pepperonis;
		this.hams = hams;
	}

	//determines the price of the pizza
	public double calcCost() {
		double cost = 0;
		//sets the initial price based off the size
		if(size.equals("Small")) {
			cost = 10;
		}
		else if(size.equals("Medium")) {
			cost = 12;
		}
		else if(size.equals("Large")) {
			cost = 14;
		}
		//calculates the price of the toppings and adds it
		int totalTops = cheeses + pepperonis + hams;
		cost += totalTops * 2;
		return cost;
	}
	
	//returns an informative string about the pizza
	public String getDescription() {
		return size + " pizza with " + cheeses + " cheeses " + pepperonis + " pepperonis and " + hams + " hams, costing " + calcCost();
	}
	
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getCheeses() {
		return cheeses;
	}

	public void setCheeses(int cheeses) {
		this.cheeses = cheeses;
	}

	public int getPepperonis() {
		return pepperonis;
	}

	public void setPepperonis(int pepperonis) {
		this.pepperonis = pepperonis;
	}

	public int getHams() {
		return hams;
	}

	public void setHams(int hams) {
		this.hams = hams;
	}
}

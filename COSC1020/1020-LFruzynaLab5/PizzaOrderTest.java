import static org.junit.Assert.*;

import org.junit.Test;

public class PizzaOrderTest {

	@Test
	public void testPizzaOrderOne() {
		Pizza p = new Pizza("Medium", 1, 3, 2);
		PizzaOrder o = new PizzaOrder(p);
		testPizza(o.getPizza1(), "Medium", 1, 3, 2);
		assertSame(1, o.getNumPizzas());
	}
	
	@Test
	public void testPizzaOrderTwo() {
		Pizza p = new Pizza("Medium", 1, 3, 2);
		Pizza p2 = new Pizza("Large", 2, 1, 0);
		PizzaOrder o = new PizzaOrder(p, p2);
		testPizza(o.getPizza1(), "Medium", 1, 3, 2);
		testPizza(o.getPizza2(), "Large", 2, 1, 0);
		assertSame(2, o.getNumPizzas());
	}
	
	@Test
	public void testPizzaOrderThree() {
		Pizza p = new Pizza("Medium", 1, 3, 2);
		Pizza p2 = new Pizza("Large", 2, 1, 0);
		Pizza p3 = new Pizza("Small", 0, 0, 0);
		PizzaOrder o = new PizzaOrder(p, p2, p3);
		testPizza(o.getPizza1(), "Medium", 1, 3, 2);
		testPizza(o.getPizza2(), "Large", 2, 1, 0);
		testPizza(o.getPizza3(), "Small", 0, 0, 0);
		assertSame(3, o.getNumPizzas());
	}
	
	@Test
	public void calcTotal() {
		Pizza p = new Pizza("Medium", 1, 3, 2);
		Pizza p2 = new Pizza("Large", 2, 1, 0);
		Pizza p3 = new Pizza("Small", 0, 0, 0);
		PizzaOrder o = new PizzaOrder(p, p2, p3);
		assertTrue(new Double(p.calcCost() + p2.calcCost() + p3.calcCost()) == o.calcTotal());
	}
	
	@Test
	public void testSetNumPizzas() {
		Pizza p = new Pizza("Medium", 1, 3, 2);
		Pizza p2 = new Pizza("Large", 2, 1, 0);
		Pizza p3 = new Pizza("Small", 0, 0, 0);
		PizzaOrder o = new PizzaOrder(p, p2, p3);
		o.setNumPizzas(1);
		assertSame(1, o.getNumPizzas());
	}
	
	@Test
	public void testSetPizza1() {
		Pizza p = new Pizza("Medium", 1, 3, 2);
		Pizza p2 = new Pizza("Large", 2, 1, 0);
		Pizza p3 = new Pizza("Small", 0, 0, 0);
		PizzaOrder o = new PizzaOrder(p, p2, p3);
		o.setPizza1(new Pizza("Small", 4, 5, 6));
		testPizza(o.getPizza1(), "Small", 4, 5, 6);
	}
	
	@Test
	public void testSetPizza2() {
		Pizza p = new Pizza("Medium", 1, 3, 2);
		Pizza p2 = new Pizza("Large", 2, 1, 0);
		Pizza p3 = new Pizza("Small", 0, 0, 0);
		PizzaOrder o = new PizzaOrder(p, p2, p3);
		o.setPizza2(new Pizza("Small", 4, 5, 6));
		testPizza(o.getPizza2(), "Small", 4, 5, 6);
	}
	
	@Test
	public void testSetPizza3() {
		Pizza p = new Pizza("Medium", 1, 3, 2);
		Pizza p2 = new Pizza("Large", 2, 1, 0);
		Pizza p3 = new Pizza("Small", 0, 0, 0);
		PizzaOrder o = new PizzaOrder(p, p2, p3);
		o.setPizza3(new Pizza("Small", 4, 5, 6));
		testPizza(o.getPizza3(), "Small", 4, 5, 6);
	}
	
	public void testPizza(Pizza p, String size, int cheeses, int pepperonis, int hams) {
		assertEquals(size, p.getSize());
		assertSame(cheeses, p.getCheeses());
		assertSame(pepperonis, p.getPepperonis());
		assertSame(hams, p.getHams());
	}

}

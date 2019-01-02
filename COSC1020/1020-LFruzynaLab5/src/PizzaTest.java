import static org.junit.Assert.*;

import org.junit.Test;

public class PizzaTest {

	@Test
	public void testPizza() {
		Pizza p = new Pizza("Medium", 1, 3, 2);
		assertEquals("Medium", p.getSize());
		assertSame(1, p.getCheeses());
		assertSame(3, p.getPepperonis());
		assertSame(2, p.getHams());
	}

	@Test
	public void testCalcCost() {
		Pizza p = new Pizza("Small", 2, 2, 2);
		double cost = 22;
		assertTrue(cost == p.calcCost());
	}
	
	@Test
	public void testGetDescription() {
		Pizza p = new Pizza("Large", 1, 3, 2);
		assertEquals("Large" + " pizza with " + 1 + " cheeses " + 3 + " pepperonis and " + 2 + " hams, costing " + 26.0, p.getDescription());
	}
	
	@Test
	public void testGetSize() {
		Pizza p = new Pizza("Large", 1, 3, 2);
		assertEquals("Large", p.getSize());
	}
	
	@Test
	public void testSetSize() {
		Pizza p = new Pizza("Large", 1, 3, 2);
		p.setSize("Small");
		assertEquals("Small", p.getSize());
	}
	
	@Test
	public void testGetCheeses() {
		Pizza p = new Pizza("Large", 1, 3, 2);
		assertSame(1, p.getCheeses());
	}
	
	@Test
	public void testSetCheeses() {
		Pizza p = new Pizza("Large", 1, 3, 2);
		p.setCheeses(5);
		assertSame(5, p.getCheeses());
	}
	
	@Test
	public void testGetPepperonis() {
		Pizza p = new Pizza("Large", 1, 3, 2);
		assertSame(3, p.getPepperonis());
	}
	
	@Test
	public void testSetPepperonis() {
		Pizza p = new Pizza("Large", 1, 3, 2);
		p.setPepperonis(5);
		assertSame(5, p.getPepperonis());
	}
	
	@Test
	public void testGetHams() {
		Pizza p = new Pizza("Large", 1, 3, 2);
		assertSame(2, p.getHams());
	}
	
	@Test
	public void testSetHams() {
		Pizza p = new Pizza("Large", 1, 3, 2);
		p.setHams(5);
		assertSame(5, p.getHams());
	}

}

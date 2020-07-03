import static org.junit.Assert.*;

import org.junit.Test;

public class TestSundae {

	Sundae c = new Sundae("Test IC", 300, "Test Topping", 50);

	@Test
	public void IceCream() {
		assertEquals(c.name, "Test IC");
		assertTrue(c.cost == 300);
		assertEquals(c.topping, "Test Topping");
		assertTrue(c.toppingCost == 50);
	}
	
	@Test
	public void getCost() {
		assertTrue(c.getCost() == 350);
	}
	
	@Test
	public void testToString() {
		assertEquals(c.toString(), "Test Topping Sundae with Test IC");
	}
}

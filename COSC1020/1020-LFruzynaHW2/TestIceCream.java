import static org.junit.Assert.*;

import org.junit.Test;

public class TestIceCream {

	IceCream c = new IceCream("Test IC", 300);

	@Test
	public void IceCream() {
		assertEquals(c.name, "Test IC");
		assertTrue(c.cost == 300);
	}
	
	@Test
	public void getCost() {
		assertTrue(c.getCost() == 300);
	}
	
	@Test
	public void testToString() {
		assertEquals(c.toString(), "Test IC");
	}
}

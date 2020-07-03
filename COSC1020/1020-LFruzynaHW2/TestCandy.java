import static org.junit.Assert.*;

import org.junit.Test;

public class TestCandy {

	@Test
	public void Candy() {
		Candy c = new Candy("Test Candy", 2, 50);
		assertEquals(c.name, "Test Candy");
		assertTrue(c.weight == 2.0);
		assertSame(c.pricePerPound, 50);
	}
	
	@Test
	public void getCost() {
		Candy c = new Candy("Test Candy", 2, 50);
		assertSame(c.getCost(), 100);
	}
	
	@Test
	public void testToString() {
		Candy c = new Candy("Test Candy", 2, 50);
		assertEquals(c.toString(), "2.0 lbs. @ .50 /lb. Test Candy");
	}

}

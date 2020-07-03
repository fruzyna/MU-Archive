import static org.junit.Assert.*;

import org.junit.Test;

public class TestCookie {

	@Test
	public void Cookie() {
		Cookie c = new Cookie("Test Cookie", 6, 100);
		assertEquals(c.name, "Test Cookie");
		assertSame(c.number, 6);
		assertSame(c.pricePerDozen, 100);
	}
	
	@Test
	public void getCost() {
		Cookie c = new Cookie("Test Cookie", 6, 100);
		assertSame(c.getCost(), 50);
	}
	
	@Test
	public void testToString() {
		Cookie c = new Cookie("Test Cookie", 6, 100);
		assertEquals(c.toString(), "6 @ 1.00 /dz. Test Cookie");
	}

}

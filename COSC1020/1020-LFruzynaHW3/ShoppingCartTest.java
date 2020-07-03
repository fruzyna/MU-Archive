import static org.junit.Assert.*;

import org.junit.Test;

public class ShoppingCartTest {

	@Test
	public void getBooks() {
		try {
			assertTrue(new ShoppingCart(true).getBooks().size() > 0);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void round() {
		assertTrue(new ShoppingCart(true).round(2.256, 2) == 2.26);
	}

}

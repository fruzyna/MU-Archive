import static org.junit.Assert.*;

import org.junit.Test;

public class BookTest {

	@Test
	public void Book() {
		Book b = new Book("Test Book", 12.99);
		assertEquals(b.name, "Test Book");
		assertTrue(b.price == 12.99);
	}

}

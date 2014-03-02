package worms.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WormTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private static Worm worm1;
	
	@Before
	public void setUp() throws Exception {
		worm1 = new Worm(0, 0, 0, 5, null);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_setMaxActionPoints_ValidActionPoints() {
		worm1.setMaxActionPoints();
		assertEquals(worm1.getMaxActionPoints(),(double) (1062*(4/30*Math.PI)*5*5*5));		
	}

}

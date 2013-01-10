package testcode;

import static org.junit.Assert.*;

import org.junit.*;

import data.DismissList;

import java.util.Date;

/**
 * This class provides test methods for the DismissList class
 * @author  Fernando
 */
public class DismissListTest {

	private DismissList dismissList;
	private Date date;
	
	@Before
	/**
	 * Creates a DismissList object and a date to be used in the test methods
	 */
	public void setUp() {
		dismissList = new DismissList();
		date = new Date();
	}
	
	@Test
	/**
	 * This method checks that all the elements in the array are false when they are initially created
	 */
	public void testGetDismissed() {
		date.getTime();
		boolean [] list = dismissList.getDismissed(date);
		assertFalse(list[0]);
		assertFalse(list[1]);
		assertFalse(list[2]);
		assertFalse(list[3]);
		assertFalse(list[4]);
	}
}

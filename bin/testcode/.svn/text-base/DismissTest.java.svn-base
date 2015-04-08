package testcode;

import static org.junit.Assert.*;

import org.junit.*;

import java.util.Date;

import data.Dismiss;

/**
 * This class provides test methods for the Dismiss class
 * @author  Fernando
 */
public class DismissTest {

	private Dismiss dismiss;
	private Date date;
	
	@Before
	/**
	 * Creates a date object to be used in the test methods
	 */
	public void setUp() {
		date = new Date();
	}
	
	@Test
	/**
	 * This method first checks if all of the elements in a Dismiss object are false, then
	 * sends all of the possible parameters to set them all true, and checks if they are indeed true 
	 */
	public void testSetDismissed() {
		date.getTime();
		dismiss = new Dismiss(date);
		boolean[] list = dismiss.getDismissed();
		assertFalse(list[0]);
		assertFalse(list[1]);
		assertFalse(list[2]);
		assertFalse(list[3]);
		assertFalse(list[4]);
		dismiss.setDismissed("image");
		dismiss.setDismissed("diary");
		dismiss.setDismissed("topic");
		dismiss.setDismissed("place");
		dismiss.setDismissed("mood");
		list = dismiss.getDismissed();
		assertTrue(list[0]);
		assertTrue(list[1]);
		assertTrue(list[2]);
		assertTrue(list[3]);
		assertTrue(list[4]);
	}
	
	@Test
	/**
	 * This method first gets all of the elements of a Dismiss object and checks if one is false
	 * then sets the element to be true, and checks if it was indeed changed.
	 */
	public void testGetDismissed() {
		date.getTime();
		dismiss = new Dismiss(date);
		boolean[] list = dismiss.getDismissed();
		assertFalse(list[0]);
		dismiss.setDismissed("diary");
		list = dismiss.getDismissed();
		assertTrue(list[0]);
	}
	
	@Test
	/**
	 * This method first creates a new Dismiss object, then creates a Date object and
	 * checks if the date returned by Dismiss is the same as the one added earlier.
	 */
	public void testGetDate() {
		date.getTime();
		dismiss = new Dismiss(date);
		assertEquals(date, dismiss.getDate());
		Date date2 = new Date();
		date2.setTime(10000);
		assertNotSame(date2, dismiss.getDate());
	}
}

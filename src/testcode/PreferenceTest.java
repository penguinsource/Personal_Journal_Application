package testcode;

import static org.junit.Assert.*;

import org.junit.*;

import data.Preference;

import java.util.ArrayList;

/**
 * This class provides test methods for the Preference class
 * @author  Fernando
 */
public class PreferenceTest {

	private Preference preference;
	private ArrayList<String> order;
	private ArrayList<String> reminder;
	
	@Before
	/**
	 * Creates objects for the testing methods
	 */
	public void setUp() {
		preference = new Preference();
		order = new ArrayList<String> ();
		reminder = new ArrayList<String>();
	}
	
	@Test
	/**
	 * Adds elements and expects the elements in the entry to be equal
	 * to the ones just added
	 */
	//tests both setOrder and getOrder
	public void testOrder() {
		order.add("image");
		order.add("diary");
		preference.setOrder(order);
		assertEquals("image",preference.getOrder().get(0));
		assertEquals("diary",preference.getOrder().get(1));
	}
	
	@Test
	/**
	 * Adds elements and expects the elements in the entry to be
	 * equal to the ones just added
	 */
	//tests both setReminders and getReminders
	public void testReminders() {
		reminder.add("image");
		reminder.add("topic");
		preference.setReminders(reminder);
		assertEquals("image",preference.getReminders().get(0));
		assertEquals("topic",preference.getReminders().get(1));
		
	}

}

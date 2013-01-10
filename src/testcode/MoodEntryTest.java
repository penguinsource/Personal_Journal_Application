package testcode;

import static org.junit.Assert.*;

import org.junit.*;

import data.MoodEntry;

import java.util.Date;

/**
 * This class provides test methods for the MoodEntry class
 * @author  Fernando
 */
public class MoodEntryTest {
	
	private MoodEntry moodEntry;
	private Date date;
	
	@Before
	/**
	 * Creates a date for the testing methods
	 */
	public void setUp() {
		date = new Date();
	}
	
	@Test
	/**
	 * Creates a MoodEntry object, then expects the smiley
	 * to be equal to the one initially in the constructor
	 */
	public void testGetSmiley() {
		date.getTime();
		moodEntry = new MoodEntry(1, date);
		assertSame(1, moodEntry.getSmiley());
	}
	
	@Test
	/**
	 * Creates an entry and checks if the date corresponds
	 * to the one in the constructor
	 */
	public void testGetDate() {
		date.getTime();
		moodEntry = new MoodEntry(2, date);
		assertEquals(date, moodEntry.getDate());
	}
	
	@Test
	/**
	 * Creates an entry, then changes the smiley and expects
	 * the smiley in the entry to be changed.
	 */
	public void testSetSmiley() {
		date.getTime();
		moodEntry = new MoodEntry(2, date);
		assertEquals(2, moodEntry.getSmiley());
		moodEntry.setSmiley(5);
		assertEquals(5, moodEntry.getSmiley());
	}
	
	@Test
	/**
	 * Creates an entry, then changes the date and expects
	 * the date in the entry to be changed.
	 */
	public void testSetDate() {
		date.getTime();
		moodEntry = new MoodEntry(4, date);
		Date date2 = new Date();
		date2.getTime();
		moodEntry.setDate(date2);
		assertEquals(date2, moodEntry.getDate());
	}
	
	@Test
	/**
	 * Creates an entry and searches for the text representing the entry. It expects it to be true,
	 * then searches for some random text and expects it to be false.
	 */
	public void testSearch() {
		date.getTime();
		moodEntry = new MoodEntry(4, date);
		assertTrue(moodEntry.search("meh"));
		assertFalse(moodEntry.search("furious"));
	}
}

package testcode;

import static org.junit.Assert.*;

import org.junit.*;

import data.MoodEntryList;

import java.util.Date;

/**
 * This class provides test methods for the MoodEntryList class
 * @author  Fernando
 */
public class MoodEntryListTest {
	/**
	 */
	private MoodEntryList moodEntryList;
	private Date date;
	
	@Before
	/**
	 * Creates objects for the testing methods
	 */
	public void setUp() {
		moodEntryList = new MoodEntryList();
		date = new Date();
	}
	@Test
	/**
	 * Checks if an imaginary entry exists, expects it be null,
	 * then adds an entry and checks if the entry with the date
	 * just added exists.
	 */
	public void testAddEntry() {
		date.getTime();
		assertNull(moodEntryList.getEntry(date));
		moodEntryList.addEntry(1, date);
		assertNotNull(moodEntryList.getEntry(date));
	}
	
	@Test 
	/**
	 * Adds an entry and checks if the entry with that date exists
	 * 
	 */
	public void testGetEntry() {
		date.getTime();
		moodEntryList.addEntry(2, date);
		Date date2 = new Date();
		date2.setTime(10000);
		assertNull(moodEntryList.getEntry(date2));
		assertNotNull(moodEntryList.getEntry(date));
	}
	
	@Test
	/**
	 * Adds an entry, then deletes it and expects that
	 * entry to be null now.
	 */
	public void testDeleteEntry() {
		date.getTime();
		moodEntryList.addEntry(3, date);
		assertNotNull(moodEntryList.getEntry(date));
		moodEntryList.deleteEntry(date);
		assertNull(moodEntryList.getEntry(date));
	}
	
	@Test
	/**
	 * Inserts an entry, tries to search an existent string in the entry expecting it to be true,
	 * then tries to search a random string, expects it to be false
	 */
	public void testSearch() {
		date.getTime();
		moodEntryList.addEntry(4, date);
		assertTrue(moodEntryList.searchEntry("meh", date));
		assertFalse(moodEntryList.searchEntry("happy", date));
	}
}

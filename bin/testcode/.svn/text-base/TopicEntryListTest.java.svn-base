package testcode;

import static org.junit.Assert.*;
import org.junit.*;

import data.TopicEntryList;

import java.util.Date;

import org.junit.Test;

/**
 * This class provides test methods for the TopicEntryList class
 * @author  Fernando
 */
public class TopicEntryListTest {

	private TopicEntryList topicEntryList;
	private Date date;
	
	@Before
	/**
	 * Creates a date and a topicEntryList object for the testing methods
	 */
	public void setUp() {
		date = new Date();
		topicEntryList = new TopicEntryList();
	}
	
	@Test
	/**
	 * Adds and entry and it expects an entry with the same date not to be null
	 */
	public void testAddEntry() {
		date.getTime();
		assertEquals("", topicEntryList.getAllTopics());
		topicEntryList.addEntry("xmas", date, 1);
		assertNotNull(topicEntryList.getEntry(date));
	}
	
	@Test
	/**
	 * Adds an entry, then the entry is checked to see if it indeed exists
	 * and also checks if an entry with a date just created is null
	 */
	public void testGetEntry() {
		date.getTime();
		topicEntryList.addEntry("snow",date, 1);
		assertNotNull(topicEntryList.getEntry(date));
		Date date2 = new Date();
		date2.setTime(40000);
		assertNull(topicEntryList.getEntry(date2));
	}
	
	@Test
	/**
	 * Adds an entry, deletes it, then checks if the entry still exists
	 */
	public void testDeleteEntry() {
		date.getTime();
		topicEntryList.addEntry("santa", date, 1);
		assertNotNull(topicEntryList.getEntry(date));
		topicEntryList.deleteEntry(date);
		assertNull(topicEntryList.getEntry(date));
	}
	
	@Test
	/**
	 * Adds an entry, then edits the information and expects the information in the
	 * entry to be changed.
	 */
	public void testEditEntry() {
		date.getTime();
		topicEntryList.addEntry("music", date, 1);
		topicEntryList.editEntry("sports", date, 1);
		assertEquals("sports", topicEntryList.getEntry(date).getTopic());
	}
	
	@Test
	/**
	 * Adds an entry to the list, searches for the string added, expects it to return true,
	 * then searches for a random string, expects it to return false
	 */
	public void testSearch() {
		date.getTime();
		topicEntryList.addEntry("music", date, 1);
		assertTrue(topicEntryList.searchEntry("music", date));
		assertFalse(topicEntryList.searchEntry("something", date));
	}

}

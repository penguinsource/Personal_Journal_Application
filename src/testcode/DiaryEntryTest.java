package testcode;

import static org.junit.Assert.*;
import org.junit.*;

import org.junit.Test;
import java.util.Date;
import data.DiaryEntry;

/**
 * This class provides tests methods for the DiaryEntry class
 * @author  Fernando
 */
public class DiaryEntryTest {

	private DiaryEntry diaryEntry;
	private Date date;
	
	@Before
	/**
	 * Instantiate a date before each test method is called
	 */
	public void setUp() {
		date = new Date();
	}
	@Test
	/**
	 * This method first adds an entry, then checks if the content stored is equal to the one added
	 */
	public void testGetContent() {
		date.getTime();
		diaryEntry = new DiaryEntry("Title", "Content", date); //add diary entry
		assertEquals("Content", diaryEntry.getContent()); //test if content was added
	}
	
	@Test
	/**
	 * This method adds an entry, and expects the Title string to be equal to the one added
	 */
	public void testGetTitle() {
		date.getTime();
		diaryEntry = new DiaryEntry("Title", "Content", date);
		assertEquals("Title", diaryEntry.getTitle()); 
	}
	
	@Test
	/**
	 * This method adds an entry, and then expects the date stored to be equal to the one just added
	 */
	public void testGetDate() {
		diaryEntry = new DiaryEntry("Title", "Content", date);
		assertEquals(date, diaryEntry.getDate()); //test if date is the same date that was added
	}
	
	@Test
	/**
	 * This method adds an entry, then sets a new title, and checks if the title was indeed changed.
	 */
	public void testSetTitle() {
		diaryEntry = new DiaryEntry("Title", "Content", date);
		String string = "new title";
		diaryEntry.setTitle(string);
		assertEquals(string, diaryEntry.getTitle()); //test if the new title was set
	}
	
	@Test
	/**
	 * This method adds an entry, then sets another content, and checks if the content was indeed changed
	 */
	public void testSetContent() {
		diaryEntry = new DiaryEntry("Title", "Content", date);
		String string = "new content";
		diaryEntry.setContent(string);
		assertEquals(string, diaryEntry.getContent()); //test if the new content was set
	}
	
	@Test
	/**
	 * This method adds an entry, then sets another date, and checks if the date was indeed changed.
	 */
	public void testSetDate() {
		date.getTime();
		diaryEntry = new DiaryEntry("Title", "Content", date);
		Date date2 = new Date();
		date2.setTime(10000);
		diaryEntry.setDate(date2);
		assertEquals(date2, diaryEntry.getDate()); //test if the new date was set
	}
	
	@Test
	/**
	 * This method tries to search a string within the diary entry. It first searches for a string
	 * that is on the diary entry and it expects to return true, then it searches for a non-existent
	 * string in the entry, and it expects to return false.
	 */
	public void testSearch() {
		date.getTime();
		diaryEntry = new DiaryEntry("", "", date);
		diaryEntry.setContent("the cat");
		diaryEntry.setTitle("yay");
		assertTrue(diaryEntry.search("the"));
		assertFalse(diaryEntry.search("dog"));
	}

}

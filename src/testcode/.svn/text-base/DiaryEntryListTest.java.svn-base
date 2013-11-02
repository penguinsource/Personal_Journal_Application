package testcode;

import static org.junit.Assert.*;

import org.junit.*;

import java.util.Date;
import data.DiaryEntryList;

/**
 * This class provides test methods for the DiaryEntryList class
 * @author  Fernando
 */
public class DiaryEntryListTest {

	private DiaryEntryList diaryEntryList;
	private Date date;
	
	@Before
	/**
	 * create a DiaryEntryList before each test method is called.
	 */
	public void setUp() {
		diaryEntryList = new DiaryEntryList();
		date = new Date();
	}
	
	@Test
	/**
	 * This method first compares if the list is empty, then an entry is added and that information
	 * is compared to see if it's equal, then the list is checked to see that an element was indeed added.
	 */
	public void testAddEntry() {
		date.getTime();
		assertSame(0, diaryEntryList.getDiaryEntryList().size()); //list is empty
		diaryEntryList.addEntry("today was a good day","it was awesome", date);
		assertEquals("today was a good day", diaryEntryList.getEntry(date).getTitle());
		assertEquals("it was awesome", diaryEntryList.getEntry(date).getContent());
		assertEquals(date, diaryEntryList.getEntry(date).getDate());
		assertSame(1, diaryEntryList.getDiaryEntryList().size());
	}
	
	@Test
	/**
	 * This method first adds an entry, then checks if the title is the same as the one added,
	 * then the entry is edited, and finally the new information is checked to see if it's equal.
	 */
	public void testEditEntry() {
		date.getTime();
		diaryEntryList.addEntry("good day", "it was great", date);
		assertEquals("good day", diaryEntryList.getEntry(date).getTitle());
		diaryEntryList.editEntry("today was bad", "it sucked", date);
		assertEquals("today was bad", diaryEntryList.getEntry(date).getTitle());
		assertEquals("it sucked", diaryEntryList.getEntry(date).getContent());
		assertEquals(date, diaryEntryList.getEntry(date).getDate());	
	}
	
	@Test
	/**
	 * This method first adds an entry, checks that the entry was added, then deletes
	 * that entry and finally checks if the entry still exists
	 */
	public void testDeleteEntry() {
		date.getTime();
		diaryEntryList.addEntry("hello", "fun day", date);
		assertNotNull(diaryEntryList.getEntry(date));
		diaryEntryList.deleteEntry(date);
		assertSame(0, diaryEntryList.getDiaryEntryList().size());
		assertNull(diaryEntryList.getEntry(date)); 	
	}
	
	@Test
	/**
	 * This method first adds a diary entry, then expects to find the strings inserted. Finally
	 * it tries to search for an unexistent string in the entry and expects it to return false.
	 */
	public void testSearchTest() {
		date.getTime();
		diaryEntryList.addEntry("hello", "fun day", date);
		assertTrue(diaryEntryList.searchEntry("hello", date));
		assertTrue(diaryEntryList.searchEntry("fun day", date));
		assertTrue(diaryEntryList.searchEntry("fun", date));
		assertTrue(diaryEntryList.searchEntry("day", date));
		assertFalse(diaryEntryList.searchEntry("fun ray", date));
		
	}
}

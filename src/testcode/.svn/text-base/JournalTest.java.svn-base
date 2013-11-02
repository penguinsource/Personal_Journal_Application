package testcode;

import static org.junit.Assert.*;

import org.junit.*;

import data.Journal;

import java.util.Date;
import java.util.Vector;

/**
 * This class provides test methods for the Journal class
 * @author  Fernando
 */
public class JournalTest {

	private Journal journal;
	private Date date;
	
	@Before
	/**
	 * Creates objects for the testing methods
	 */
	public void setUp() {
		journal = new Journal();
		date = new Date();
	}
	
	@Test
	/**
	 * Creates an entry, then expects the content of that entry 
	 * to be the same as the one just added
	 */
	public void testAddDiaryEntry() {
		date.getTime();
		assertNull(journal.getDiaryEntry(date));
		journal.addDiaryEntry("today was nice", "yay", date);
		assertNotNull(journal.getDiaryEntry(date));
		assertEquals("yay",journal.getDiaryEntry(date).getContent());
	}
	
	@Test
	/**
	 * Creates an entry, edits it and expects the information in the entry to be changed
	 */
	public void testEditDiaryEntry() {
		date.getTime();
		journal.addDiaryEntry("today was nice", "yay", date);
		assertEquals("yay",journal.getDiaryEntry(date).getContent());
		journal.editDiaryEntry("one", "two", date);
		assertEquals("two", journal.getDiaryEntry(date).getContent());
	}
	
	@Test
	/**
	 * Creates an entry, expects it to exist, then deletes that entry and expects
	 * it to be null
	 */
	public void testDeleteDiaryEntry() {
		date.getTime();
		journal.addDiaryEntry("today was nice", "yay", date);
		assertNotNull(journal.getDiaryEntry(date));
		journal.deleteDiaryEntry(date);
		assertNull(journal.getDiaryEntry(date));
	}
	
	@Test
	/**
	 * Expects an entry to be null (since none have been added), then
	 * adds an entry and expects that entry not to be null
	 */
	public void testAddMoodEntry() {
		date.getTime();
		assertNull(journal.getMoodEntry(date));
		journal.addMoodEntry(1, date);
		assertNotNull(journal.getMoodEntry(date));
		assertSame(1,journal.getMoodEntry(date).getSmiley());
	}
	
	@Test
	/**
	 * Creates and entry, deletes it and expects that entry to be null
	 */
	public void testDeleteMoodEntry() {
		date.getTime();
		assertNull(journal.getMoodEntry(date));
		journal.addMoodEntry(1, date);
		assertNotNull(journal.getMoodEntry(date));
		journal.deleteMoodEntry(date);
		assertNull(journal.getMoodEntry(date));
	}
	
	@Test
	/**
	 * Creates an entry, and expects that entry to exist
	 */
	public void testAddImageEntry() {
		Vector<String> fileList = new Vector<String>();
		date.getTime();
		assertNull(journal.getImageEntry(date));
		journal.addImageEntry(fileList, date);
		assertNotNull(journal.getImageEntry(date));
	}
	
	@Test
	/**
	 * Creates and entry, deletes it and expects it to be null
	 */
	public void testDeleteImageEntry() {
		Vector<String> fileList = new Vector<String>();
		date.getTime();
		assertNull(journal.getImageEntry(date));
		journal.addImageEntry(fileList, date);
		assertNotNull(journal.getImageEntry(date));
		journal.deleteImageEntry(date);
		assertNull(journal.getImageEntry(date));
	}
	
	@Test
	/**
	 * Creates an entry, then edits it and expects the information to be the 
	 * same as the one used for editing.
	 */
	public void testEditImageEntry() {
		Vector<String> fileList = new Vector<String>();
		Vector<String> fileList2 = new Vector<String>();
		fileList.add("image.png");
		fileList2.add("image2.png");
		date.getTime();
		journal.addImageEntry(fileList, date);
		assertEquals("image.png",journal.getImageEntry(date).getLocation().get(0));
		journal.editImageEntry(fileList2, date);
		assertEquals("image2.png",journal.getImageEntry(date).getLocation().get(0));
	}
	
	@Test
	/**
	 * Adds an entry, then expects that entry to be added
	 */
	public void testAddTopicEntry() {
		date.getTime();
		assertNull(journal.getTopicEntry(date));
		journal.addTopicEntry("Nice", date, 1);
		assertNotNull(journal.getTopicEntry(date));
		assertEquals("Nice",journal.getTopicEntry(date).getTopic());
	}
	
	@Test
	/**
	 * Adds an entry, then edits it and expects the information to be edited.
	 */
	public void testEditTopicEntry() {
		date.getTime();
		assertNull(journal.getTopicEntry(date));
		journal.addTopicEntry("Nice", date, 1);
		assertNotNull(journal.getTopicEntry(date));
		journal.editTopicEntry("Ha", date, 1);
		assertEquals("Ha", journal.getTopicEntry(date).getTopic());
	}
	
	@Test 
	/**
	 * Adds an entry, then deletes it and expects the entry to be null
	 */
	public void testDeleteTopicEntry() {
		date.getTime();
		assertNull(journal.getTopicEntry(date));
		journal.addTopicEntry("Nice", date, 1);
		assertNotNull(journal.getTopicEntry(date));
		journal.deleteTopicEntry(date);
		assertNull(journal.getTopicEntry(date));
	}
	
	@Test
	/**
	 * Expects the preferences not to be null
	 */
	public void testGetPrefs() {

		date.getTime();
		assertNotNull(journal.getPrefs());
	}
	
}

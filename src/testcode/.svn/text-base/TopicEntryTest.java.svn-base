package testcode;

import static org.junit.Assert.*;

import org.junit.*;

import java.util.Date;

import data.TopicEntry;

/**
 * This class provides test methods for the TopicEntry class
 * @author  Fernando
 */
public class TopicEntryTest {

	private TopicEntry topicEntry;
	private Date date;
	
	/**
	 * Creates a date object
	 */
	@Before
	public void setUp() {
		date = new Date();
	}

	@Test
	/**
	 * Creates a topic, and expects the topic to be equal as the one we added
	 */
	public void testGetTopic() {
		String topic = "fun";
		date.getTime();
		
		topicEntry = new TopicEntry(topic, date, 1);
		assertEquals("fun", topicEntry.getTopic());
	}
	
	@Test
	/**
	 * Creates an entry, and expects the topic count to be the same as the one added
	 */
	public void testGetCount() {
		date.getTime();
		
		topicEntry = new TopicEntry("santa xmas", date, 2);
		assertSame(2, topicEntry.getCount());
	}
	
	@Test
	/**
	 * Creates an entry, then changes the topic and expects the topic to be changed
	 */
	public void testSetTopic() {
		date.getTime();
		topicEntry = new TopicEntry("lol", date, 1);
		topicEntry.setTopic("YAY");
		assertEquals("YAY", topicEntry.getTopic());
	}
	
	@Test  
	/**
	 * Creates and entry, then the date of that entry is changed and the entry is checked to see if 
	 * the date was changed.
	 */
	public void testSetDate() {
		date.getTime();
		topicEntry = new TopicEntry("lol", date, 1);
		Date date2 = new Date();
		date2.setTime(10000);
		topicEntry.setDate(date2);
		assertEquals(date2, topicEntry.getDate());		
	}
	
	@Test
	/**
	 * Creates an entry, searches for an existing string in the entry, it expects it to be true. 
	 * It then searches for a random string, expects it to be false. 
	 */
	public void testSearch() {
		date.getTime();
		topicEntry = new TopicEntry("nice", date, 1);
		assertTrue(topicEntry.search("nice"));
		assertFalse(topicEntry.search("cool"));
	}
}

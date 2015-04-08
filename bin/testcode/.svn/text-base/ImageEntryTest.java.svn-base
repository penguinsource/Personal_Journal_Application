package testcode;

import static org.junit.Assert.*;
import data.ImageEntry;

import org.junit.*;

import java.util.Date;
import java.util.Vector;

/**
 * This class provides test methods for the ImageEntry class
 * @author  Fernando
 */
public class ImageEntryTest {

	private ImageEntry imageEntry;
	private Vector<String> imageList;
	private Date date;
	@Before
	
	/**
	 * Creates objects for the testing methods
	 */
	public void setUp() {
		date = new Date();
		date.getTime();
		imageList = new Vector<String>();	
		imageEntry = new ImageEntry(imageList, date);
	}
	@Test
	/**
	 * Adds an entry then expects the image count to be equal
	 * 1 in that entry
	 */
	public void testGetImageCount() {
		assertSame(0, imageEntry.getImageCount());
		imageList.add("someImage.png");
		imageEntry.setImageLocation(imageList);
		assertSame(1, imageEntry.getImageCount());
	}
	
	@Test
	/**
	 * Expects the date returned by imageEntry to be equal
	 * to the one originally added
	 */
	public void testGetDate() {
		assertEquals(date, imageEntry.getDate());
	}
	
	@Test
	/**
	 * Adds an entry then expects the elements in that entry
	 * to be the same as the ones just added
	 */
	public void testGetLocation() {
		imageList.add("image.png");
		imageEntry.setImageLocation(imageList);
		assertEquals("image.png", imageEntry.getLocation().elementAt(0));
	}
	
	@Test
	/**
	 * Adds an entry then changes the imageLocation and checks
	 * if it was indeed changed.
	 */
	public void testSetImageLocation() {
		imageList.add("lol.png");
		imageEntry.setImageLocation(imageList);
		assertEquals(imageList.elementAt(0), imageEntry.getLocation().elementAt(0));
	}
	
	/**
	 * Adds an image entry, then searches for the name of the string, expects it to be found, 
	 * then looks for inexistent string, expects them to return false.
	 */
	@Test
	public void testSearch() {
		imageList.add("funny.jpg");
		assertTrue(imageEntry.search("funny"));
		assertFalse(imageEntry.search("terrible"));
		assertFalse(imageEntry.search(" "));
		assertFalse(imageEntry.search("unfunny"));
	}
}

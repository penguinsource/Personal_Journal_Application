package testcode;

import static org.junit.Assert.*;

import org.junit.*;

import data.ImageEntryList;

import java.util.Date;
import java.util.Vector;

/**
 * This class provides test methods for the ImageEntryList class
 * @author  Fernando
 */
public class ImageEntryListTest {
	
	private ImageEntryList imageEntryList;
	private Vector<String> list;
	private Date date;
	
	@Before
	/**
	 * Creates an ImageEntryList object, a Vector and a date for testing the methods
	 */
	public void setUp() {
		imageEntryList = new ImageEntryList();
		list = new Vector<String>();
		date = new Date();
	}
	
	@Test
	/**
	 * This method first adds two strings to a list, and adds that list to the ImageEntryList,
	 * then the elements are checked to see if they're equal to the ones added in the original list.
	 */
	public void testAddEntry() {
		list.add("image1.png");
		list.add("image2.png");
		date.getTime();
		assertSame(0, imageEntryList.getImageEntryList().size());
		imageEntryList.addEntry(list, date);
		assertSame(1, imageEntryList.getImageEntryList().size());
		assertEquals("image1.png", imageEntryList.getImageEntry(date).
				getLocation().elementAt(0)); //tests if image was added
		assertEquals("image2.png", imageEntryList.getImageEntry(date).
				getLocation().elementAt(1)); //tests if image was added
	}
	
	@Test
	/**
	 * This method first adds an entry, then another date is created and ImageEntryList is checked
	 * to see if it returns an entry with the new date.
	 */
	public void testGetEntry() {
		list.add("image1.png");
		date.getTime();
		imageEntryList.addEntry(list, date);
		Date date2 = new Date();
		date2.setTime(100000);
		assertNull(imageEntryList.getImageEntry(date2)); //tests if image doesn't exist
		assertNotNull(imageEntryList.getImageEntry(date)); //tests if image exists
	}
	
	@Test
	/**
	 * This method first creates an entry with 2 elements, then the whole list is deleted
	 * and a new element is added. ImageEntry is edited with the new information and is
	 * checked to see if the elements were indeed edited.
	 */
	public void testEditEntry() {
		list.add("newImage.png");
		list.add("other.png");
		date.getTime();
		imageEntryList.addEntry(list, date);
		list.clear();
		list.add("edited.png");
		imageEntryList.editEntry(list, date);
		assertEquals(list.elementAt(0), imageEntryList.getImageEntry(date)
				.getLocation().elementAt(0)); //tests if image was edited
	}
	
	@Test
	/**
	 * This method first adds an entry, then the size of the list is checked to be 1, then
	 * the entry is deleted and the size of the list is checked again, this time expecting
	 * it to be 0.
	 */
	public void testDeleteEntry() {
		list.add("image.png");
		date.getTime();
		imageEntryList.addEntry(list, date);
		assertSame(1, imageEntryList.getImageEntryList().size());
		imageEntryList.deleteEntry(date);
		assertSame(0, imageEntryList.getImageEntryList().size()); //tests if image was deleted
	}
	
	@Test
	/**
	 * This method first adds an entry, looks for the right name of the image, expects it to find it,
	 * then searches for the wrong extension and expects it to not find it.
	 */
	public void testSearch() {
		list.add("image.png");
		date.getTime();
		imageEntryList.addEntry(list, date);
		assertTrue(imageEntryList.searchEntry("image.png", date));
		assertFalse(imageEntryList.searchEntry("image.jpg", date));
	}
}

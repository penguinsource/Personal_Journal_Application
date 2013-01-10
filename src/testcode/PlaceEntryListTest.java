package testcode;

import static org.junit.Assert.*;

import org.junit.*;

import java.util.Date;

import data.PlaceEntryList;

/**
 * This class provides test methods for the PlaceEntryList class
 * @author  Fernando
 */
public class PlaceEntryListTest {

	private PlaceEntryList placeEntryList;
	private Date date;
	
	@Before
	/*Sets up the objects*/
	public void setUp() {
		date = new Date();
		placeEntryList = new PlaceEntryList();
	}
	
	@Test
	/*Tests if an entry was added*/
	public void addPlaceEntry() {
		date.getTime();
		placeEntryList.addPlaceEntry(date);
		assertNotNull(placeEntryList.getPlaceEntry(date));
	}
	
	@Test
	/*tests if entries get deleted*/
	public void deletePlaceEntry() {
		date.getTime();
		placeEntryList.addPlaceEntry(date);
		assertNotNull(placeEntryList.getPlaceEntry(date));
		placeEntryList.deletePlaceEntry(date);
		assertNull(placeEntryList.getPlaceEntry(date));
	}

}

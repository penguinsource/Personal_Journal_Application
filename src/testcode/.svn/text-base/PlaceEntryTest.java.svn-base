package testcode;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import data.PlaceEntry;

/**
 * This class provides test methods for the PlaceEntry class
 * @author  Fernando
 */
public class PlaceEntryTest {

	private Date date;
	private PlaceEntry placeEntry;
	
	
	@Before
	public void setUp(){
		date = new Date();
		placeEntry = new PlaceEntry(date);
	}
	
	@Test
	/*tests if entries get added*/
	public void testAddLocation() {
		assertSame(0, placeEntry.getAllLocations().size());
		placeEntry.addLocation("mexico", 20.0, 20.5, 35.5);
		assertEquals("mexico",placeEntry.getAllLocations().get(0).getLocationName());
	}
	@Test
	/*tests if entries are recovered*/
	public void testGetLocationCount() {
		placeEntry.addLocation("mexico", 20.0, 20.5, 35.5);
		placeEntry.addLocation("canada", 40.0, 40.5, 55.5);
		assertSame(2, placeEntry.getLocationCount());
	}
	@Test
	/*tests if entries get deleted*/
	public void testDeleteLocation() {
		placeEntry.addLocation("canada", 40.0, 40.5, 55.5);
		assertSame(1, placeEntry.getAllLocations().size());
		placeEntry.deleteLocation("canada");
		assertSame(0, placeEntry.getAllLocations().size());
	}
	@Test
	/*tests if all entries get deleted*/
	public void testDeleteAllLocations(){
		placeEntry.addLocation("mexico", 20.0, 20.5, 35.5);
		placeEntry.addLocation("canada", 40.0, 40.5, 55.5);
		placeEntry.deleteAllLocations();
		assertSame(0, placeEntry.getAllLocations().size());
	}
	
	@Test
	/**
	 * Adds 2 entries, searches for them, expects them to be found, searches for a random entry, expects
	 * it to not be found.
	 */
	public void testSearch() {
		placeEntry.addLocation("mexico", 20.0, 20.5, 35.5);
		placeEntry.addLocation("canada", 40.0, 40.5, 55.5);
		assertTrue(placeEntry.search("mexico"));
		assertTrue(placeEntry.search("canada"));
		assertFalse(placeEntry.search("USA"));
	}

}

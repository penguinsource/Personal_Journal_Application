package data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import java.util.Date;
/**
 * PlaceEntryList first initiates a vector which will contains a list of place entries <br> Then, it is able to manipulate that list by being able to add, remove and get an entry based on the date.<br>
 * @author  Mihai
 */
public class PlaceEntryList extends EntryList {
	
	private static final long serialVersionUID = -1871408286411273055L;
	private Vector<PlaceEntry> placeEntryList;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM-dd-yyyy");
	
	/**
	 * Constructor; initiates the vector containing PlaceEntry objects
	 */
	public PlaceEntryList() {
		placeEntryList = new Vector <PlaceEntry>();
	}
	/**
	 * Add a place entry on Date date
	 */
	public void addPlaceEntry(Date date) {
		placeEntryList.add(new PlaceEntry(date));
	}
	/**
	 * get the place entry from Date date
	 */
	public PlaceEntry getPlaceEntry(Date date) {
		String formatted_date = dateFormat.format(date).toString();
		
		for (int i = 0; i < placeEntryList.size(); i++){
			if (dateFormat.format(placeEntryList.get(i).getDate()).toString()
					.matches(formatted_date)){
				return placeEntryList.get(i);
			}
		}
		return null;
	}
	
	
	public Vector<PlaceEntry> getAllPlaceEntries(){
		return placeEntryList;
	}
	
	/**
	 * delete the place entry on Date date
	 */
	public void deletePlaceEntry(Date date) {
		String formattedDate = dateFormat.format(date).toString();
		
		for (int i = 0; i < placeEntryList.size(); i++){
			String formatted_date_from_list = dateFormat.format(placeEntryList.get(i).getDate()).toString();
			if (formatted_date_from_list.matches(formattedDate)){
				placeEntryList.remove(i);
			}
		}
	}
	
	/**
	 * get the vector of all the place entries
	 */
	public Vector<PlaceEntry> getPlaceEntryList() {
		return placeEntryList;
	}
	public boolean searchEntry(String searchText, Date date) {
		PlaceEntry entryObject = getPlaceEntry(date);	
		
		if (entryObject == null){
			return false;
		} 
		
		return entryObject.search(searchText);
	}
	public ArrayList<Date> getEntryDates(ArrayList<Date> entryDates) {
Calendar cal = Calendar.getInstance();
		
		for (int i = 0; i < placeEntryList.size(); i++){	
			Date date = placeEntryList.get(i).getDate();
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			if (!entryDates.contains(cal.getTime())){
				entryDates.add(cal.getTime());
			}
		}
		
		return entryDates;
	}	
}

package data;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;
/**
 * PlaceEntry stores the a vector of location objects for a specific date; it also stores the number of locations that exist at a specific date.
 * @author  Mihai
 */
public class PlaceEntry implements Serializable{

	private static final long serialVersionUID = -4928459505000998247L;
	/**
	 */
	private Date date;
	private int location_count;
	/**
	 */
	private Vector<LocationEntry> LocationList;
	
	/**
	 * Constructor; 
	 */
	public PlaceEntry(Date date_arg) {
		LocationList = new Vector <LocationEntry>();
		this.date = date_arg;
		
	}
	
	/**
	 * add a location entry to the vector LocationList. A location entry contains a location name, a latitude, a
	 * longitude and an elevation
	 */
	public void addLocation(String location_name_arg, Double latitude_arg, Double longitude_arg, Double elevation_arg){
		LocationList.add(new LocationEntry(location_name_arg, latitude_arg, longitude_arg, elevation_arg));
		location_count++;
	}
	
	/**
	 * returns the number of total locations on Date date
	 */
	public int getLocationCount(){
		return location_count;
	}
	
	/**
	 * delete the location with the name loc_name_arg
	 */
	public void deleteLocation(String loc_name_arg){
		for (int i = 0; i < LocationList.size(); i++){
			if (loc_name_arg.matches(LocationList.get(i).getLocationName())){
				LocationList.remove(i);
			}
		}
	}
	
	/**
	 * delete all the locations in the vector
	 */
	public void deleteAllLocations(){
		LocationList.removeAllElements();
		location_count = 0;
	}
	
	public Date getDate() {
		return date;
	}
	
	/**
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * returns a vector of all the locations on Date date
	 */
	public Vector<LocationEntry> getAllLocations() {
		return LocationList;
	}
	
	/**
	 * get all the location names from the vector LocationList
	 */
	public String getLocationNames(){
		String names = "";
		for (int i = 0; i < LocationList.size(); i++){
			if(i == LocationList.size() - 1){
				names = names + LocationList.get(i).getLocationName() + ".";
			} else {
				names = names + LocationList.get(i).getLocationName() + ", ";
			}
		}
		return names;
	}

	public boolean search(String searchText) {
		String names = getLocationNames().toLowerCase();
		String lowSearchText = searchText.toLowerCase();
		int index = names.indexOf(lowSearchText);
		
		if (index > -1){
			return true;
		}
		
		return false;
	}
	
	public void setLocationCount(int locCount){
		this.location_count = locCount;
	}
	
	/**
	 * @param locList
	 */
	public void setLocationList(Vector<LocationEntry> locList){
		this.LocationList = locList;
	}
	
	@SuppressWarnings("unchecked")
	public Vector<LocationEntry> getLocationList(){
		return (Vector<LocationEntry>) this.LocationList.clone();
	}
		
	public PlaceEntry clone(){
		PlaceEntry entry = new PlaceEntry(this.getDate());
		entry.setLocationCount(this.location_count);
		entry.LocationList.addAll(this.LocationList);
		return entry;
	}
}

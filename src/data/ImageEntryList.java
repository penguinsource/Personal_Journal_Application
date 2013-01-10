package data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import java.util.Date;

/**
 * This class adds, deletes, edits and provides information about ImageEntries.
 * @author  Fernando
 */
public class ImageEntryList extends EntryList {

	private static final long serialVersionUID = 1265067762781219387L;
	private Vector <ImageEntry> imageEntryList;	

	public ImageEntryList() {
		imageEntryList = new Vector<ImageEntry>();
	}
	
	/**
	 * Adds an entry to the list. If there's an entry with the same date it edits that entry.
	 * 
	 * @param fileList A vector containing the filenames of the images
	 * @param date The date selected in the calendar
	 */
	public void addEntry(Vector <String> fileList, Date date) {
		if(getImageEntry(date) != null) {
			editEntry(fileList, date);
		}
		else {
			ImageEntry imageEntry = new ImageEntry(fileList, date);
			imageEntryList.add(imageEntry);
		}
	}
	
	/**
	 * Returns a list containing all of the ImageEntry objects
	 * @return  A vector containing the ImageEntry objects
	 */
	public Vector <ImageEntry> getImageEntryList() {
		return this.imageEntryList;
	}
	
	/**
	 * Returns an ImageEntry object with the respective date, if such entry doesn't exist null is returned
	 * 
	 * @param date A possible date of an ImageEntry object
	 * @return An ImageEntry if it exists, otherwise null
	 */
	public ImageEntry getImageEntry(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		String formattedDate = dateFormat.format(date).toString();		
		
		for(int i = 0; i < imageEntryList.size(); i++) {
			if(dateFormat.format(imageEntryList.elementAt(i).date).toString().
					equals(formattedDate)) {
				return imageEntryList.elementAt(i);
			}
		}
		return null;
	}
	
	/**
	 * Edits the ImageEntry object of the respective date with the new information
	 * 
	 * @param fileList A vector containing the filenames of the images
	 * @param date The date of the entry to be edited
	 */
	public void editEntry(Vector <String> fileList, Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		String formattedDate = dateFormat.format(date).toString();	
		
		for(int i = 0; i < imageEntryList.size(); i++) {
			if(formattedDate.equals
					(dateFormat.format(imageEntryList.elementAt(i).date).toString())) {
				imageEntryList.elementAt(i).imageList = fileList;
				imageEntryList.elementAt(i).date = date;
				return;
			}
		}
	}
	
	/**
	 * Deletes the entry that has the date provided.
	 * 
	 * @param date The date of the entry to be deleted.
	 */
	public void deleteEntry(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		String formattedDate = dateFormat.format(date).toString();	
		
		for(int i = 0; i < imageEntryList.size(); i++)
			if(formattedDate.equals
					(dateFormat.format(imageEntryList.elementAt(i).date).toString())) {
				imageEntryList.removeElementAt(i);	
				return;
			}
	}

	/**
	 * Searches an entry with the provided string, Returns true if the string is found, otherwise false
	 * 
	 * @param searchText The string to be searched
	 * @param date The date of the entry
	 * @return True if the string is found, otherwise false
	 */
	public boolean searchEntry(String searchText, Date date) {
		ImageEntry entryObject = getImageEntry(date);	
		
		if (entryObject == null){
			return false;
		} 
		
		return entryObject.search(searchText);
	}

	/**
	 * Returns a sorted list of all of the dates within ImageEntryList
	 * 
	 * @param entryDates An array list of the entries to be sorted
	 * @return The sorted date array list
	 */
	public ArrayList<Date> getEntryDates(ArrayList<Date> entryDates) {
		Calendar cal = Calendar.getInstance();
		
		for (int i = 0; i < imageEntryList.size(); i++){	
			Date date = imageEntryList.get(i).getDate();
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

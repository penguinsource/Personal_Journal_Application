package data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
/**
 * DiaryEntryList initiates a vector which will contains a list of diary entries <br> 
 * Then, it is able to manipulate that list by being able to add, remove and get an 
 * entry based on the date.<br>
 * @author  Mihai
 */
public class DiaryEntryList extends EntryList implements Serializable{

	private static final long serialVersionUID = 7401120573135269332L;
	private Vector <DiaryEntry> diaryEntryList;
	private DiaryEntry entryObject;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM-dd-yyyy");
	
	/**
	 * Constructor; initiates the vector containing DiaryEntry objects
	 */
	public DiaryEntryList() {
		diaryEntryList = new Vector <DiaryEntry>();
	}
	
	/**
	 * add a diary entry containing title, content on Date date
	 */
	public void addEntry(String title, String content, Date date) {
		DiaryEntry diaryEntry = new DiaryEntry(title, content, date);
		diaryEntryList.add(diaryEntry);
	}	
	
	/**
	 * edit a diary entry by replacing the title, content on Date onDate
	 */
	public void editEntry(String newTitle, String newContent, Date onDate) {
		entryObject = this.getEntry(onDate);
		entryObject.setContent(newContent);
		entryObject.setTitle(newTitle);
	}
	
	/**
	 * return the vector of all the DiaryEntry objects
	 */
	public Vector <DiaryEntry> getDiaryEntryList() {
		return this.diaryEntryList;
	}
	
	/**
	 * delete the diary entry on Date date
	 */
	public void deleteEntry(Date date) {
		String formattedDate = dateFormat.format(date).toString();
		
		for (int i = 0; i < diaryEntryList.size(); i++){
			if (dateFormat.format(diaryEntryList.get(i).getDate()).toString()
					.matches(formattedDate)){
				diaryEntryList.remove(i);
			}
		}
	}
	
	/**
	 * return the DiaryEntry object on Date date
	 */
	public DiaryEntry getEntry(Date date) {
		String formattedDate = dateFormat.format(date).toString();
		
		for (int i = 0; i < diaryEntryList.size(); i++){
			if (dateFormat.format(diaryEntryList.get(i).getDate()).toString()
					.matches(formattedDate)){
				return diaryEntryList.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Searches the entry for the text
	 * @param searchText
	 * @param date
	 * @return true if found
	 */
	public boolean searchEntry(String searchText, Date date) {
		entryObject = getEntry(date);	
		
		if (entryObject == null){
			return false;
		} 
		
		return entryObject.search(searchText);
	}

	/**
	 * Gets all dates that have diary entries on them
	 * @param entryDates arraylist to add the dates to
	 * @return the entryDates arraylist
	 */
	public ArrayList<Date> getEntryDates(ArrayList<Date> entryDates) {
		Calendar cal = Calendar.getInstance();
		
		for (int i = 0; i < diaryEntryList.size(); i++){	
			Date date = diaryEntryList.get(i).getDate();
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

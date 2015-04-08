package data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import java.util.Date;
/**
 * MoodEntryList first initiates a vector which will contains a list of mood entries <br> Then, it is able to manipulate that list by being able to add, remove and get an entry based on the date.
 * @author  Mihai
 */
public class MoodEntryList extends EntryList {

	private static final long serialVersionUID = 6701605279125817454L;
	private MoodEntry moodEntry;
	private Vector<MoodEntry> moodEntryList;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM-dd-yyyy");
	
	/**
	 * Constructor; initiates the list of MoodEntry objects
	 */
	public MoodEntryList() {
		moodEntryList = new Vector<MoodEntry>();
	}
	
	/**
	 * add a mood entry on Date date with smiley number smiley_no
	 */
	public void addEntry(int smiley_no, Date date) {
		moodEntry = new MoodEntry(smiley_no, date);
		moodEntryList.add(moodEntry);
	}
	
	// counts of each type of entry for the mood chart
	public int getExcitedCount() {
		int count = 0;
		for(int i = 0; i < moodEntryList.size(); i++) {
			if(moodEntryList.elementAt(i).getSmiley() == 6) {
				count++;
			}
		}
		return count;
	}
	
	public int getHappyCount() {
		int count = 0;
		for(int i = 0; i < moodEntryList.size(); i++) {
			if(moodEntryList.elementAt(i).getSmiley() == 5) {
				count++;
			}
		}
		return count;
	}
	
	public int getMehCount() {
		int count = 0;
		for(int i = 0; i < moodEntryList.size(); i++) {
			if(moodEntryList.elementAt(i).getSmiley() == 4) {
				count++;
			}
		}
		return count;
	}
	
	public int getSadCount() {
		int count = 0;
		for(int i = 0; i < moodEntryList.size(); i++) {
			if(moodEntryList.elementAt(i).getSmiley() == 3) {
				count++;
			}
		}
		return count;
	}
	
	public int getCryingCount() {
		int count = 0;
		for(int i = 0; i < moodEntryList.size(); i++) {
			if(moodEntryList.elementAt(i).getSmiley() == 2) {
				count++;
			}
		}
		return count;
	}
	
	public int getAngryCount() {
		int count = 0;
		for(int i = 0; i < moodEntryList.size(); i++) {
			if(moodEntryList.elementAt(i).getSmiley() == 1) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * return the MoodEntry object from Date date
	 */
	public MoodEntry getEntry(Date date) {
		String formatted_date = dateFormat.format(date).toString();

		for (int i = 0; i < moodEntryList.size(); i++){
			String formatted_date_from_list = dateFormat.format(moodEntryList.get(i).getDate()).toString();
			if (formatted_date_from_list.matches(formatted_date)){
				return moodEntryList.get(i);
			}
		}
		return null;
	}
	
	/**
	 * delete the MoodEntry object from Date date
	 */
	public void deleteEntry(Date date) {
		String formattedDate = dateFormat.format(date).toString();
		
		for (int i = 0; i < moodEntryList.size(); i++){
			String formatted_date_from_list = dateFormat.format(moodEntryList.get(i).getDate()).toString();
			if (formatted_date_from_list.matches(formattedDate)){
				moodEntryList.remove(i);
			}
		}
	}

	/**
	 * Search mood entry at specified date for the searchText
	 * @param searchText
	 * @param date
	 * @return true if searchText is found in entry
	 */
	public boolean searchEntry(String searchText, Date date) {
		moodEntry = getEntry(date);	
		
		if (moodEntry == null){
			return false;
		} 
		
		return moodEntry.search(searchText);
	}

	/**
	 * Gets all dates that have mood entries on them
	 * @param entryDates arraylist to add the dates to
	 * @return the entryDates arraylist
	 */
	public ArrayList<Date> getEntryDates(ArrayList<Date> entryDates) {
		Calendar cal = Calendar.getInstance();
		
		for (int i = 0; i < moodEntryList.size(); i++){	
			Date date = moodEntryList.get(i).getDate();
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

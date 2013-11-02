package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

/**
 * The Journal class contains all of the information of a user. It has a list of every type of entry available and it 
 * also stores the preferences made by the user.
 * 
 * @author  Everyone
 */

public class Journal implements Serializable{
	private static final long serialVersionUID = 4526069953945672722L;
	private DiaryEntryList diaryEntryList;
	private PlaceEntryList placeEntryList;
	private ImageEntryList imageEntryList;
	private TopicEntryList topicEntryList;
	private MoodEntryList moodEntryList;
	private DismissList dismissList;
	private Preference prefs;
	private TwitterAccount twitterAccount;
	
	/**
	 * Constructor for the Journal class.
	 */
	public Journal() {
		diaryEntryList = new DiaryEntryList();
		placeEntryList = new PlaceEntryList();
		imageEntryList= new ImageEntryList();
		topicEntryList = new TopicEntryList();
		moodEntryList = new MoodEntryList();
		dismissList = new DismissList();
		prefs = new Preference();
		twitterAccount = new TwitterAccount();
	}
	
	/**
	 * Adds a diary entry to the journal
	 * 
	 * @param title The title of the diary entry
	 * @param content The content of the diary entry
	 * @param date The date of the diary entry
	 */
	public void addDiaryEntry(String title, String content, Date date) {
		diaryEntryList.addEntry(title, content, date);
	}
	
	/**
	 * Edits a diary entry from the journal
	 * 
	 * @param title The new title of the entry
	 * @param content The new content of the entry
	 * @param date The date of the entry to be edited
	 */
	public void editDiaryEntry(String title, String content, Date date) {
		diaryEntryList.editEntry(title, content, date);
	}
	
	/**
	 * Deletes a diary entry from the journal
	 * 
	 * @param date The date of the entry to be deleted
	 */
	public void deleteDiaryEntry(Date date) {
		diaryEntryList.deleteEntry(date); 
	}
	
	/**
	 * Returns the diary entry corresponding to the provided date
	 * 
	 * @param date The date of the entry
	 * @return The diary entry from that date
	 */
	public DiaryEntry getDiaryEntry(Date date) {
		return diaryEntryList.getEntry(date);
	}
		
	/**
	 * Adds a place entry to the journal
	 * 
	 * @param date The date of the Place entry to be added
	 */
	public void addPlaceEntry(Date date){
		placeEntryList.addPlaceEntry(date);
	}
	
	/**
	 * Deletes a place entry from the journal
	 * 
	 * @param date The date of the place entry to be deleted
	 */
	public void deletePlaceEntry(Date date){
		placeEntryList.deletePlaceEntry(date);
	}
	
	/**
	 * Returns a place entry 
	 * @param date
	 * @return The place entry corresponding to the provided date
	 */
	public PlaceEntry getPlaceEntry(Date date){
		return placeEntryList.getPlaceEntry(date);
	}
	
	/**
	 * Returns all the place entries of the journal
	 * 
	 * @return A vector containing all of the place entries of the journal.
	 */
	public Vector<PlaceEntry> getAllPlaceEntries(){
		return placeEntryList.getAllPlaceEntries();
	}
	
	/**
	 * Adds a mood entry to the journal
	 * 
	 * @param smiley_no The type of smiley to be added
	 * @param date The date of the entry
	 */
	public void addMoodEntry(int smiley_no, Date date){
		moodEntryList.addEntry(smiley_no, date);
	}
	
	/**
	 * Returns a mood entry from the journal
	 * 
	 * @param date The date of the entry to be returned
	 * @return The mood entry corresponding to the provided date
	 */
	public MoodEntry getMoodEntry(Date date) {
		return moodEntryList.getEntry(date);
	}
	
	/**
	 * Deletes a mood entry from the journal
	 * 
	 * @param date The date of the entry to be deleted
	 */
	public void deleteMoodEntry(Date date) {
		moodEntryList.deleteEntry(date);	
	}
	
	/**
	 * Returns all of the mood entries of the journal
	 * 
	 * @return An array of doubles containing the mood entry types
	 */
	public double[] getMoodTotals() {
		double [] moods = {
				moodEntryList.getExcitedCount(),
				moodEntryList.getHappyCount(),
				moodEntryList.getMehCount(),
				moodEntryList.getSadCount(),
				moodEntryList.getCryingCount(),
				moodEntryList.getAngryCount()
		};
		return moods;
	}
	
	/**
	 *Adds an image entry to the journal
	 * 
	 * @param fileList A vector containing the filenames of the images
	 * @param date The date of the entry
	 */
	public void addImageEntry(Vector<String> fileList, Date date) {
		imageEntryList.addEntry(fileList, date);
	}
	
	/**
	 * Returns an image entry from the journal 
	 * 
	 * @param date The date of the image entry to be returned
	 * @return The image entry corresponding to the provided date
	 */
	public ImageEntry getImageEntry(Date date) {
		return imageEntryList.getImageEntry(date);
	}
	
	/**
	 * Deletes an entry from the journal.
	 * 
	 * @param date The date of the entry to be deleted.
	 */
	public void deleteImageEntry(Date date) {
		imageEntryList.deleteEntry(date);
	}
	
	/**
	 * Edits an existing image entry from the journal
	 * 
	 * @param fileList The new fileList to be added
	 * @param date The date of the entry to be edited
	 */
	public void editImageEntry(Vector<String> fileList, Date date) {
		imageEntryList.editEntry(fileList, date);
	}
	
	/**
	 * Adds a topic entry to the journal
	 * 
	 * @param topics The topics to be added
	 * @param date The date of the entry
	 * @param count The topic count of the entry
	 */
	public void addTopicEntry(String topics, Date date, int count) {
		topicEntryList.addEntry(topics, date, count);
	}
	
	/**
	 * Returns a topic entry from the journal
	 * 
	 * @param date The date of the topic to be returned
	 * @return The topic entry corresponding to the provided date
	 */
	public TopicEntry getTopicEntry(Date date){
		return topicEntryList.getEntry(date);
		
	}
	
	/**
	 * Returns all of the topics of the journal
	 * 
	 * @return The topics from the journal
	 */
	public String getAllTopics(){
		return topicEntryList.getAllTopics();
	}
	
	/**
	 * Edits an existing topic from the journal
	 * 
	 * @param topic The new topics to be added
	 * @param date The date of the entry to be edited
	 * @param count The new topic count
	 */
	public void editTopicEntry(String topic, Date date, int count) {
		topicEntryList.editEntry(topic, date, count);
	}
	
	/**
	 * Deletes a topic entry from the journal
	 * 
	 * @param date The date of the entry to be deleted
	 */
	public void deleteTopicEntry(Date date) {
		topicEntryList.deleteEntry(date);
	}
	
	/**
	 * Returns all of the Preferences from the journal
	 * 
	 * @return A Preference object containing all of the information about the user's preferences
	 */
	public Preference getPrefs(){
		return prefs;
	}
	
	/**
	 * Sets the order of the entries
	 * 
	 * @param order An array list containing the order of the entries
	 */
	public void setOrder(ArrayList<String> order){
		prefs.setOrder(order);
	}
	
	/**
	 * Sets the reminders of the entries
	 * 
	 * @param reminders An array list containing the reminders of the entries
	 */
	public void setReminders(ArrayList<String> reminders) {
		prefs.setReminders(reminders);		
	}
	
	/**
	 * Sets a new label that replaces the diary string in any label
	 * 
	 * @param diaryLabel The new label for diary entries
	 */
	public void setDiaryLabel(String diaryLabel) {
		prefs.setDiaryLabel(diaryLabel);
	}

	/**
	 * Sets a new label that replaces the topic string in any label
	 * 
	 * @param topicLabel The new label for topics
	 */
	public void setTopicLabel(String topicLabel) {
		prefs.setTopicLabel(topicLabel);
	}

	/**
	 * Sets a new label that replaces the image string in any label
	 * @param imageLabel The new image label
	 */
	public void setImageLabel(String imageLabel) {
		prefs.setImageLabel(imageLabel);
	}

	/**
	 * Sets a new label that replaces the place string in any label
	 * @param placeLabel The new place label
	 */
	public void setPlaceLabel(String placeLabel) {
		prefs.setPlaceLabel(placeLabel);
	}

	/**
	 * Sets a new label that replaces the mood string in any label
	 * @param moodLabel the new mood label
	 */
	public void setMoodLabel(String moodLabel) {
		prefs.setMoodLabel(moodLabel);
	}
	
	/**
	 * Dismisses existing reminders from the journal
	 * @param type The type of entry to be dismissed
	 * @param date The date of the entry to be dismissed
	 */
	public void dismissEntry(String type, Date date){
		dismissList.dismissEntry(type, date);
	}
	
	/**
	 * Reverts any dismiss for reminders from the journal
	 * @param type The type of entry to be reminded
	 * @param date The date of the entry
	 */
	public void undismissEntry(String type, Date date) {
		dismissList.undismissEntry(type, date);
	}

	/**
	 * Returns all of the dismissed reminders from the journal
	 * @param date The date of the dismissed reminders
	 * @return An array of booleans containing true if the types are dismissed, false if they are not dismissed
	 */
	public boolean[] getDismissed(Date date){
		return dismissList.getDismissed(date);		
	}
	
	/**
	 * Returns the User twitter account information
	 * 
	 * @return A TwitterAccount object containing the user's twitter information
	 */
	public TwitterAccount getTwitterAccount() {
		if(twitterAccount == null) {
			twitterAccount = new TwitterAccount();
			return twitterAccount;
		}
		else {
			return twitterAccount;
		}
	}
	
	/**
	 * Sets the journal's twitter account to the user's account
	 * 
	 * @param acc The user TwitterAccount object
	 */
	public void setTwitterAccount(TwitterAccount acc){
		twitterAccount = acc;
	}

	/**
	 * Searches a diary entry for the provided string, if the text is found true is returned,
	 * otherwise false.
	 * 
	 * @param searchText The text to be searched
	 * @param date The date of the entry
	 * @return True if the text is found, otherwise false.
	 */
	public boolean searchDiary(String searchText, Date date) {
		return diaryEntryList.searchEntry(searchText, date);
	}

	/**
	 * Searches a topic entry for the provided string, if the text is found true is returned,
	 * otherwise false
	 * 
	 * @param searchText The text to be searched
	 * @param date The date of the entry
	 * @return True is the text is found, otherwise false
	 */
	public boolean searchTopic(String searchText, Date date) {
		return topicEntryList.searchEntry(searchText, date);
	}

	/**
	 * Searches an image entry for the provided string, if the text is found true is returned,
	 * otherwise false
	 *  
	 * @param searchText The text to be searched
	 * @param date The date of the entry
	 * @return True if the text is found, otherwise false
	 */
	public boolean searchImage(String searchText, Date date) {
		return imageEntryList.searchEntry(searchText, date);
	}

	/**
	 * Searches a place entry for the provided string, if the text is found true is returned,
	 * otherwise false
	 * 
	 * @param searchText The text to be searched
	 * @param date The date of the entry
	 * @return True if the text is found, otherwise false
	 */
	public boolean searchPlace(String searchText, Date date) {
		return placeEntryList.searchEntry(searchText, date);
	}
	
	/**
	 * Searches a mood entry for the provided string, if the text is found true is returned,
	 * otherwise false
	 * 
	 * @param searchText The text to be searched
	 * @param date The date of the entry
	 * @return True if the text is found, otherwise false
	 */
	public boolean searchMood(String searchText, Date date) {
		return moodEntryList.searchEntry(searchText, date);
	}

	/**
	 * Deletes the current twitter account of the journal
	 */
	public void removeTwitterAccount() {
		twitterAccount = new TwitterAccount();
	}

	/**
	 * Returns a sorted list of all the dates within the journal, so that they can be searched faster.
	 * 
	 * @return A sorted array list of all of the dates of the journal
	 */
	public ArrayList<Date> getEntryDates() {
		ArrayList<Date> entryDates = new ArrayList<Date>();
		
		entryDates = diaryEntryList.getEntryDates(entryDates);
		entryDates = topicEntryList.getEntryDates(entryDates);
		entryDates = imageEntryList.getEntryDates(entryDates);
		entryDates = placeEntryList.getEntryDates(entryDates);
		entryDates = moodEntryList.getEntryDates(entryDates);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
			
		Collections.sort(entryDates);
				
		return entryDates;
	}

}

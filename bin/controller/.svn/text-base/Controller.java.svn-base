package controller;

import gov.nasa.worldwind.geom.Position;
import gui.LocationEntryPanel;
import gui.MainGUI;
import gui.MoodFrame;
import gui.PlaceFrame;
import gui.TwitterConfirmFrame;
import gui.DiaryFrame;
import gui.JournalSelectFrame;
import gui.TagCloudFrame;
import gui.TopicFrame;
import gui.ImageFrame;
import gui.MoodChartFrame;

import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JOptionPane;

import twitter4j.auth.AccessToken;

import data.DiaryEntry;
import data.ImageEntry;
import data.Journal;
import data.LocationEntry;
import data.MoodEntry;
import data.PlaceEntry;
import data.TopicEntry;
import data.TwitterAccount;

/**
 * The controller class of the entire application handles modifying the model as well as well as updating the views. Whenever any method modifies the model, the updateActiveViews() method is called which updates all open views with the most recent data.
 * @author  Mikus Lorencs, Mihai Oprescu, Fernando Lopez de la Mora, Haoran Wang
 */
public class Controller implements Serializable{

	private static final long serialVersionUID = 391766212662472403L;
	private Journal journal = new Journal();
	private MainGUI gui; 
	private DiaryFrame diaryViewGUI; 
	private TopicFrame topicView;
	private TagCloudFrame tagCloudView;
	private MoodChartFrame moodChartFrame;
	private ImageFrame imageView;
	private PlaceFrame placeView;
	private MoodFrame moodView;
	private DiaryEntry diaryEntry;
	private TopicEntry topicEntry;
	private ImageEntry imageEntry;
	private MoodEntry moodEntry;
	private PlaceEntry placeEntry;
	private TwitterCommand twitterCommand;
	private int orientation;
	
	//booleans keep track of active views
	private boolean diaryIsActive = false;
	private boolean topicIsActive = false;
	private boolean topicViewActive = false;
	private boolean imageIsActive = false;
	private boolean placeIsActive = false;
	private boolean moodIsActive = false;
	private boolean tagCloudIsActive = false;
	private boolean moodChartIsActive = false;
	private boolean mapIsActive = false;
	
	// name of the journal file to save to
	private String journalFileName;
	Date selectedDate = new Date();
	
	private Vector<EditCommand> imageUndoStack = new Vector<EditCommand>();
	private Vector<EditCommand> imageRedoStack = new Vector<EditCommand>();
	
	private Vector<MainUndoRedo> mainUndoStack = new Vector<MainUndoRedo>();
	private Vector<MainUndoRedo> mainRedoStack = new Vector<MainUndoRedo>();
	
	/**
	 * Starts the journal manager gui (before starting main app)
	 * @param file file that contains the name of all existing journals
	 */
	public void startJournalSelect(File file){
		new JournalSelectFrame(this, file, journal);
	}
	
	
	/**
	 * Takes the user to the authentication page and saves the required information 
	 * to the journal when the user enters the PIN
	 */
	public void initiateTwitter(int type) {
			gui.showTwitterFrame(type);
			twitterCommand.initiate();
	}
	
	
	public int addPIN(String pin) {
		return twitterCommand.addPIN(pin);
	}
	
	
	/**
	 * Obtains diary content and launches twitter confirm window (which later tweets the entry)
	 * @param date
	 */
	public void tweetDiaryEntry(Date date) {
		String diaryText = 	journal.getDiaryEntry(date).getContent();
		new TwitterConfirmFrame("diary", diaryText, this);
		disableMain();
	}
	
	/**
	 * Obtains diary content and launches twitter confirm window (which later tweets the entry)
	 * @param date
	 */
	public void tweetTopicEntry(Date date) {
		String topics = journal.getTopicEntry(date).getTopic();
		new TwitterConfirmFrame("topic", topics, this);
		disableMain();
	}
	
	/**
	 * Obtains topics and launches twitter confirm window (which later tweets the entry)
	 * @param date
	 */
	public void tweetImageEntry(Date date) {
		ImageEntry image = journal.getImageEntry(date);
		new TwitterConfirmFrame("image", image.getImageNames(0), this);
		disableMain();
	}
	
	/**
	 * Obtains location names and launches twitter confirm window (which later tweets the entry)
	 * @param date
	 */
	public void tweetPlaceEntry(Date date) {
		String places = journal.getPlaceEntry(date).getLocationNames();
		new TwitterConfirmFrame("place", places, this);
		disableMain();
	}
	
	
	/**
	 * Place holder. Tweeting mood entry may not be implemented.
	 */
	public void tweetMoodEntry(Date date) {
		String moodId = Integer.toString(journal.getMoodEntry(date).getSmiley());
		new TwitterConfirmFrame("mood", moodId, this);
		disableMain();
	}
	
	public void viewMoodChart() {
		if(moodChartIsActive) {
			moodChartFrame.requestFocus();
			return;
		}
		
		moodChartFrame = new MoodChartFrame("Mood Chart",
				getMoodTotals(), this);
		
		addActiveView("moodChart");
	}
	
	public double[] getMoodTotals(){
		return journal.getMoodTotals();
	}
	
	/**
	 * Tweets the given string (called after the tweet has been properly formatted)
	 * @param text string to tweet
	 * @return returns 1 if tweet is successful
	 */
	public int tweetEntry(String text){
		return twitterCommand.tweet(text);
	}
	
	/**
	 * Starts the main GUI of the application, assigns it to pointer for later manipulation
	 */
	public void startApp(){
		String journalName = journalFileName.substring(0, journalFileName.length() - 4);
		gui = new MainGUI(this, journalName);		
	}
	
	// start of methods for places *************************************************************************************************************************
	
	/**
	 * Creates place entry at specified date, dismisses any reminder for place entry at that date 
	 * @param date_arg date of entry
	 * @param listOfSavedEntries 
	 */
	public void addPlaceEntry(Date date_arg, Vector<LocationEntryPanel> listOfSavedEntries){
		journal.addPlaceEntry(date_arg);		
		this.addLocation(date_arg, listOfSavedEntries);
		
		//add to stack
		placeEntry = journal.getPlaceEntry(date_arg);
		mainUndoStack.add(new MainUndoRedo("add", "place", null, null, null, placeEntry.clone(), null));
		mainRedoStack.clear();
		
		journal.dismissEntry("place", date_arg);
		updateActiveViews(date_arg, 1);
	}
	
	/**
	 * Adds a vector of locations to a place entry at the specified date
	 * @param date_arg date of entry
	 * @param location_list vector list of location objects
	 */
	public void addLocation(Date date_arg, Vector <LocationEntryPanel> location_list){
		placeEntry = this.getPlaceEntry(date_arg);
		placeEntry.deleteAllLocations();
		
		// converting each position of each location to Double's
		Double elevation;
		Double latitude;
		Double longitude;
		
		for (int i = 0; i < location_list.size(); i++){
			Position location_position = location_list.get(i).getPosition();
			latitude = location_position.getLatitude().getDegrees();
			longitude = location_position.getLongitude().getDegrees();
			elevation = location_position.getElevation();
			placeEntry.addLocation(location_list.get(i).getLocationName(), latitude, longitude, elevation);
		}
						
		updateActiveViews(date_arg, 0);
	}
	
	public void editPlaceEntry(Date date_arg, Vector<LocationEntryPanel> location_list){
		placeEntry = this.getPlaceEntry(date_arg);
		if (placeEntry == null){
			this.addPlaceEntry(date_arg, location_list);
		}else{
			//add to stack
			mainUndoStack.add(new MainUndoRedo("edit", "place", null, null, null, placeEntry.clone(), null));
			
			placeEntry.deleteAllLocations();
			
			// converting each position of each location to Double's
			Double elevation;
			Double latitude;
			Double longitude;
			
			for (int i = 0; i < location_list.size(); i++){
				Position location_position = location_list.get(i).getPosition();
				latitude = location_position.getLatitude().getDegrees();
				longitude = location_position.getLongitude().getDegrees();
				elevation = location_position.getElevation();
				placeEntry.addLocation(location_list.get(i).getLocationName(), latitude, longitude, elevation);
			}
						
			//add to stack
			placeEntry = this.getPlaceEntry(date_arg);
			mainUndoStack.add(new MainUndoRedo("edit", "place", null, null, null, placeEntry.clone(), null));
			mainRedoStack.clear();
			
			updateActiveViews(date_arg, 1);
			
		}
		
	}
	
	/**
	 * Returns a placeEntry object at the specified date
	 * @param date
	 * @return placeEntry object
	 */
	public PlaceEntry getPlaceEntry(Date date){
		placeEntry = journal.getPlaceEntry(date);
		return placeEntry;
	}
	
	/**
	 * Deletes a placeEntry at specified date
	 * @param date
	 */
	public void deletePlaceEntry(Date date) {
		placeEntry = this.getPlaceEntry(date);
		journal.deletePlaceEntry(date);		
		
		//add to stack
		mainUndoStack.add(new MainUndoRedo("delete", "place", null, null, null, placeEntry.clone(), null));
		mainRedoStack.clear();
		
		updateActiveViews(date, 1);
	}
	
	/**
	 * Gets all locations
	 */
	public Vector<LocationEntry> getAllLocations(){
		Vector<LocationEntry> allLocations = new Vector<LocationEntry>();
		Vector<PlaceEntry> place_entry_list = journal.getAllPlaceEntries();
		for (int i = 0; i < place_entry_list.size(); i++){
			PlaceEntry placeEntryObject = place_entry_list.get(i);
			Vector<LocationEntry> loc_list = placeEntryObject.getAllLocations();
			for (int j = 0; j <loc_list.size() ; j++){
				allLocations.add(loc_list.get(j));
			}
		}
		return allLocations;		
	}
	
	public void viewPlaceEntry(Date date_arg){
		placeEntry = journal.getPlaceEntry(date_arg);
		
		if(!placeIsActive) {
			placeView = new PlaceFrame("view", date_arg, this);
			addActiveView("placeView");
			updateActiveViews(date_arg, 0);
		}
	}
	
	// end of methods for places *************************************************************************************************************************
	
	public boolean isMapActive() {
		return mapIsActive;
	}

	public boolean isMoodActive() {
		return moodIsActive;
	}


	/**
	 * Adds diary entry at the specified date (called from diaryGUI)
	 * @param title title of the diary
	 * @param content content of the diary
	 * @param date date at which to add
	 */
	public void addDiaryEntry(String title, String content, Date date){
		journal.addDiaryEntry(title, content, date);
		
		//add to stack
		diaryEntry = journal.getDiaryEntry(date);
		mainUndoStack.add(new MainUndoRedo("add", "diary", diaryEntry.clone(), null, null, null, null));
		mainRedoStack.clear();
		gui.setUndoEnabled(true);
		gui.setRedoEnabled(false);
		
		this.dismissEntry("diary", date);
	}
	
	/**
	 * Deletes diary entry at the specified date, and closes the diary view if it is open
	 * @param date
	 */
	public void deleteDiaryEntry(Date date){
		diaryEntry = journal.getDiaryEntry(date);
		
		journal.deleteDiaryEntry(date);
		
		//add to stack		
		mainUndoStack.add(new MainUndoRedo("delete", "diary", diaryEntry.clone(), null, null, null, null));
		mainRedoStack.clear();
		gui.setUndoEnabled(true);
		gui.setRedoEnabled(false);
		
		updateActiveViews(date,1);
			
		if (diaryIsActive){
			removeActiveView("diaryViewEdit");
			diaryViewGUI.disposeFrame();
		}		
	}
	
	
	/**
	 * Edits the diary entry with the specified content and title at the specified date
	 * @param title
	 * @param content
	 * @param date
	 */
	public void editDiaryEntry(String title, String content, Date date){
		//add unedited entry to stack
		diaryEntry = journal.getDiaryEntry(date);
		mainUndoStack.add(new MainUndoRedo("edit", "diary", diaryEntry.clone(), null, null, null, null));

		journal.editDiaryEntry(title, content, date);
		
		//add edited entry to stack
		diaryEntry = journal.getDiaryEntry(date);
		mainUndoStack.add(new MainUndoRedo("edit", "diary", diaryEntry.clone(), null, null, null, null));
		mainRedoStack.clear();
		gui.setUndoEnabled(true);
		gui.setRedoEnabled(false);
		
		updateActiveViews(date,1);
	}
	
	/**
	 * Returns diaryEntry object at specified date
	 * @param date
	 */
	public DiaryEntry getDiaryEntry(Date date){
		return journal.getDiaryEntry(date);
	}
		
	/**
	 * Opens a view of an existing diary at a specified date
	 * @param date
	 */
	public void viewDiaryEntry(Date date){
		diaryEntry = getDiaryEntry(date);
				
		if (diaryEntry == null) {
		}
		else if (!diaryIsActive) { 
			diaryViewGUI = new DiaryFrame("View Diary Entry", date, this);
			addActiveView("diaryViewEdit");
			updateActiveViews(date,0);
		}
	}
	
	/**
	 * Opens a view of an existing image entry at a specified date
	 * @param date
	 */
	public void viewImageEntry(Date date) {
		imageEntry = journal.getImageEntry(date);
		
		if(!imageIsActive) {
			imageView = new ImageFrame("View Images", date, this);
			addActiveView("image");
			updateActiveViews(date,0);
		}
	}
	
	/**
	 * Creates an image entry at the specified date
	 * @param fileList list of image filepaths to add 
	 * @param date date of the entry
	 */
	public void addImageEntry(Vector<String> fileList, Date date) {
		journal.addImageEntry(fileList, date);
		
		//add to stack
		imageEntry = journal.getImageEntry(date);
		mainUndoStack.add(new MainUndoRedo("add", "image", null, null, imageEntry.clone(), null, null));	
		mainRedoStack.clear();
		gui.setUndoEnabled(true);
		gui.setRedoEnabled(false);
		
		dismissEntry("image", date);
	}
	
	public boolean imageEntryExists(Date date) {
		 if(journal.getImageEntry(date) != null)
			return true;
		else 
			return false;
	}
	
	/**
	 * Deletes image entry at specified date
	 * @param date
	 */
	public void deleteImageEntry(Date date){
		imageEntry = journal.getImageEntry(date);
		
		journal.deleteImageEntry(date);
		
		//add to stack
		mainUndoStack.add(new MainUndoRedo("delete", "image", null, null, imageEntry.clone(), null, null));	
		mainRedoStack.clear();
		gui.setUndoEnabled(true);
		gui.setRedoEnabled(false);
		
		updateActiveViews(date,1);
	}

	/**
	 * Edits image entry at specified date
	 * @param fileList list of image filepaths to replace the old list with
	 * @param date
	 */
	public void editImageEntry(Vector<String> fileList, Date date){
		//add unedited entry to stack
		imageEntry = journal.getImageEntry(date);
		mainUndoStack.add(new MainUndoRedo("edit", "image", null, null, imageEntry.clone(), null, null));
		
		journal.editImageEntry(fileList, date);
		
		//add edited entry to stack
		imageEntry = journal.getImageEntry(date);
		mainUndoStack.add(new MainUndoRedo("edit", "image", null, null, imageEntry.clone(), null, null));
		mainRedoStack.clear();
		gui.setUndoEnabled(true);
		gui.setRedoEnabled(false);
		
		updateActiveViews(date,1);
	}


	/**
	 * Creates mood entry at specified date 
	 * @param smiley_no integer representing the smiley selected by the user
	 * @param date date of the entry
	 */
	public void addMoodEntry(int smiley_no, Date date){
		journal.addMoodEntry(smiley_no, date);
		
		//add to stack
		moodEntry = journal.getMoodEntry(date);
		mainUndoStack.add(new MainUndoRedo("add", "mood", null, null, null, null, moodEntry.clone()));		
		mainRedoStack.clear();
		gui.setUndoEnabled(true);
		gui.setRedoEnabled(false);
		
		dismissEntry("mood", date);
	}

	/**
	 * Edits mood entry at the specified date 
	 * @param iconID integer representing the smiley selected by the user
	 * @param date date of the entry
	 */
	public void editMoodEntry(int iconID, Date date){
		//add unedited entry to stack
		moodEntry = journal.getMoodEntry(date);
		mainUndoStack.add(new MainUndoRedo("edit", "mood", null, null, null, null, moodEntry.clone()));
		
		moodEntry.setSmiley(iconID);
		
		//add edited entry to stack
		moodEntry = journal.getMoodEntry(date);
		mainUndoStack.add(new MainUndoRedo("edit", "mood", null, null, null, null, moodEntry.clone()));
		mainRedoStack.clear();
		gui.setUndoEnabled(true);
		gui.setRedoEnabled(false);
		
		updateActiveViews(date,1);
	}
	
	public void viewMoodEntry() {
		if(!moodIsActive) {
			moodView = new MoodFrame(selectedDate, this, 1);	
			addActiveView("mood");
		}		
	}


	/**
	 * Deletes mood entry at specified date
	 * @param date
	 */
	public void deleteMoodEntry(Date date){
		moodEntry = journal.getMoodEntry(date);
		journal.deleteMoodEntry(date);
		
		//add to stack
		mainUndoStack.add(new MainUndoRedo("delete", "mood", null, null, null, null, moodEntry.clone()));		
		mainRedoStack.clear();
		gui.setUndoEnabled(true);
		gui.setRedoEnabled(false);
		
		//dipose mood view if it is active
		if (moodIsActive){
			removeActiveView("mood");
			moodView.disposeFrame();			
		}
		
		updateActiveViews(date,1);
	}

	/**
	 * Creates topic entry at specified date
	 * @param topic list of topics (string with topics separated by spaces)
	 * @param date date of entry
	 * @param count count of topics in the entry
	 */
	public void addTopicsEntry(String topic, Date date, int count){
		journal.addTopicEntry(topic, date, count);
		
		//add to stack
		topicEntry = journal.getTopicEntry(date);
		mainUndoStack.add(new MainUndoRedo("add", "topic", null, topicEntry.clone(), null, null, null));
		mainRedoStack.clear();
		gui.setUndoEnabled(true);
		gui.setRedoEnabled(false);
		
		dismissEntry("topic", date);
	}
	
	/**
	 * Deletes topic entry at the specified date
	 * @param date
	 */
	public void deleteTopicEntry(Date date){
		topicEntry = journal.getTopicEntry(date);
		journal.deleteTopicEntry(date);
		
		//add to stack
		mainUndoStack.add(new MainUndoRedo("delete", "topic", null, topicEntry.clone(), null, null, null));
		mainRedoStack.clear();
		gui.setUndoEnabled(true);
		gui.setRedoEnabled(false);
		
		updateActiveViews(date,1);
	}

	/**
	 * Edits topic entry at the specified date
	 * @param topic list of topics
	 * @param date date of entry
	 * @param count count of topics in entry
	 */
	public void editTopicsEntry(String topic, Date date, int count){
		//add unedited entry to stack
		topicEntry = journal.getTopicEntry(date);
		mainUndoStack.add(new MainUndoRedo("edit", "topic", null, topicEntry.clone(), null, null, null));

		journal.editTopicEntry(topic, date, count);
		
		//add edited entry to stack
		topicEntry = journal.getTopicEntry(date);
		mainUndoStack.add(new MainUndoRedo("edit", "topic", null, topicEntry.clone(), null, null, null));
		mainRedoStack.clear();

		
		updateActiveViews(date,1);
	}
	
	/**
	 * Returns a string of all topics in the journal (for use with tag cloud)
	 * @return string of topics
	 */
	public String getAllTopics(){
		return journal.getAllTopics();
	}
	
	public TopicEntry getTopicEntry(Date date){
		return journal.getTopicEntry(date);
	}
	
	/**
	 * Creates a view of an existing topic entry at specified date
	 * @param date
	 */
	public void viewTopicEntry(Date date){
		topicEntry = journal.getTopicEntry(date);
				
		if (topicEntry == null) {
		}
		else if (!topicIsActive) { 
			topicView = new TopicFrame("view", date, this);
			addActiveView("topic");
			updateActiveViews(date,0);
		}
	}
	
	public boolean topicViewActive(){
		return topicViewActive;
	}

	public void setTopicView(boolean f){
		topicViewActive = f;
	}
	/**
	 * Creates a view of the tag cloud
	 */
	public void viewTagCloud(Date current_date){
		if(!tagCloudIsActive){
			tagCloudView = new TagCloudFrame(this, current_date);
			addActiveView("tagcloud");
		
		}
	}
	// addActiveView is called when a new gui frame is created
	// create all pointer references to that frame in this method
	// and make the activeViews of the frame here

	/**
	 * Adds an active view of the specified entry. Used when updating active views.
	 * @param name name of the type of entry 
	 */
	public void addActiveView(String name){
		
		if (name.matches("diaryViewEdit")){
			diaryIsActive = true;
		}
		if (name.matches("topic")){
			topicIsActive = true;
		}
		if (name.matches("image")){
			imageIsActive = true;
		}				
		if(name.matches("tagcloud")){
			tagCloudIsActive = true;
		}
		if(name.matches("moodChart")){
			moodChartIsActive = true;
		}
		if(name.matches("placeView")){
			placeIsActive = true;
		}
		if(name.matches("map")){
			mapIsActive = true;
		}
		if(name.matches("mood")){
			moodIsActive = true;
		}
	}

	/**
	 * Returns true of a topic view is active
	 * @param name
	 * @return true or false
	 */
	public boolean IsTopicActive(String name){
		return topicIsActive;
	}
	
	public boolean IsTopicExist(Date date){
		topicEntry = journal.getTopicEntry(date);
		if (topicEntry == null){
			return false;
		} else{
			return true;
		}
	}
	/**
	 * Removes an active view. This view will not be updated with updateActiveViews()
	 * @param name name of the type of entry view
	 */
	public void removeActiveView(String name){
		if (name.matches("diaryViewEdit")){
			diaryIsActive = false;
		}
		if (name.matches("topic")){
			topicIsActive = false;
		}
		if (name.matches("image")){
			imageIsActive = false;
		}
		if(name.matches("tagcloud")){
			tagCloudIsActive = false;
		}
		if(name.matches("moodChart")){
			moodChartIsActive = false;
		}
		if(name.matches("placeView")){
			placeIsActive = false;
		}		
		if(name.matches("map")){
			mapIsActive = false;
		}
		if(name.matches("mood")){
			moodIsActive = false;
		}
		
		if (getActiveViewCount() == 0){
			gui.enablePrefs();
		}
	}
	
	/**
	 * Invoked by mainGUI when the selected date is changed
	 * @param newDate selected date
	 */
	public void dayChanged(Date newDate){
		selectedDate = newDate;
		updateActiveViews(newDate,1);	
	}
	
	public Date getCurrentDate(){		
		return selectedDate;
	}
	
	/**
	 * Updates all the active views and the mainGUI window with the most recent data
	 * @param date date at which to fetch the data
	 */
	public void updateActiveViews(Date date, int type){
		// update the maingui view
		if (type == 1){
		updateGUI(date);
		}
		
		////////// update diary view		
		// get the reference(diary_entry) to the DiaryEntry that has the date selected in the calendar
		if (diaryIsActive) {
			diaryEntry = this.getDiaryEntry(date);
			
			if (diaryEntry == null){
				diaryViewGUI.updateView("Title", "There is no diary entry for this date.", date);
				diaryViewGUI.disableEditButton();
			}
			else {
				// update view with title, content and date
				diaryViewGUI.updateView(diaryEntry.getTitle(), diaryEntry.getContent(), date);
				diaryViewGUI.enableEditButton();
			}
		}
				
		// update the places view
		if (placeIsActive) {
			placeEntry = this.getPlaceEntry(date);
			
			if (placeEntry == null){
				placeView.updateView(placeEntry, date);
			}
			else {
				// update view with locations
				placeView.updateView(placeEntry, date);
			}
		}
		
		// update topic view
		if(topicIsActive) {
			topicEntry = journal.getTopicEntry(date);
			
			if (topicEntry == null) {
				topicView.updateView("");
				
			}
			else  {
				//update topicView with the topic from the selected date
				topicView.updateView(topicEntry.getTopic());

			}
		}
		
		//update image view
		if(imageIsActive) {
			imageEntry = journal.getImageEntry(date);
			
			if(imageEntry != null) {
				imageView.updateView(imageEntry.getLocation(), date);
			}
			else {
				Vector<String> list = new Vector<String>();
				list.add("images/noImage.png");
				imageView.updateView(list, date);
			}
		}
		
		//update tag cloud
		if(tagCloudIsActive){
			tagCloudView.updateView();
		}
		
		//update mood chart
		if(moodChartIsActive){
			moodChartFrame.updateView(getMoodTotals());
		}
		
		//close mood view if it is open (it has no info to display from day to day
		//so the user doesn't need it open when they change days)
		if(moodIsActive){
			moodView.disposeFrame();
			removeActiveView("mood");
		}
	}
	
	/**
	 * Fetches and sends the required data from the model to the day view in mainGUI to update it
	 * @param date date at which to fetch data
	 */
	private void updateGUI(Date date) {
		String diaryTitle = null;
		int topicCount = 0;
		int imageCount = 0;
		int placeCount = 0;
		int moodSmiley = 0;
		diaryEntry = this.getDiaryEntry(date);
		topicEntry = journal.getTopicEntry(date);
		moodEntry = journal.getMoodEntry(date);
		imageEntry = journal.getImageEntry(date);
		placeEntry = journal.getPlaceEntry(date);
		//array to keep track of which entries have been dismissed
		boolean[] dismissed = journal.getDismissed(date);
		//booleans to disable or enable undo and redo button in main gui
		boolean enableUndo = true;
		boolean enableRedo = true;

		if (mainUndoStack.size() < 1)
			enableUndo = false;
		if (mainRedoStack.size() < 1)
			enableRedo = false;
		
		if (diaryEntry != null)
			diaryTitle = diaryEntry.getTitle();			
		
		if(topicEntry != null)
			topicCount = topicEntry.getCount();		
		
		if(imageEntry != null) 
			imageCount = imageEntry.getImageCount();		
		
		if(placeEntry != null) 
			placeCount = placeEntry.getLocationCount();		
		
		if(moodEntry != null)
			moodSmiley = moodEntry.getSmiley();		
		
		ArrayList<String> remDays = getRemDays(date);
		
		int activeViewCount = getActiveViewCount();
		
		gui.updateView(date, diaryTitle, topicCount, imageCount, placeCount, moodSmiley, 
				getOrder(), getReminders(), dismissed, remDays, enableUndo, enableRedo, activeViewCount);
	}

	
	/**
	 * Returns the count of active views
	 * @return
	 */
	private int getActiveViewCount() {
		int count = 0;
		
		if (diaryIsActive == true)
			count++;
		if (topicIsActive == true)
			count++;
		if (topicViewActive == true)
			count++;
		if (imageIsActive == true)
			count++;
		if (placeIsActive == true)
			count++;
		
		return count;
	}


	/**
	 * Returns an arraylist of strings (formatted dates) which have one or more reminders
	 * @param date date that is being updated (only the month of this date is needed)
	 * @return ArrayList of strings 
	 */
	public ArrayList<String> getRemDays(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM-dd-yyyy");
		ArrayList<String> remDays = new ArrayList<String>();
		GregorianCalendar calendar = new GregorianCalendar();
		GregorianCalendar todayCal = new GregorianCalendar();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.DATE, 1);
		
		while (calendar.get(Calendar.MONTH) == month && calendar.before(todayCal)){
			Date time = calendar.getTime();
			
			//if any entry type does not exist, has a reminder, and it hasn't been dismissed
			//then add that date to the list of reminder dates and continue to the next iteration
			if (journal.getDiaryEntry(time) == null && hasReminder("diary") && !journal.getDismissed(time)[0]){
				remDays.add(dateFormat.format(time).toString());
				calendar.add(Calendar.DATE, 1);
				continue;
			} else if (journal.getTopicEntry(time) == null && hasReminder("topic") && !journal.getDismissed(time)[1]){
				remDays.add(dateFormat.format(time).toString());
				calendar.add(Calendar.DATE, 1);
				continue;
			} else if (journal.getImageEntry(time) == null && hasReminder("image") && !journal.getDismissed(time)[2]){
				remDays.add(dateFormat.format(time).toString());
				calendar.add(Calendar.DATE, 1);
				continue;
			} else if (journal.getPlaceEntry(time) == null && hasReminder("place") && !journal.getDismissed(time)[3]){
				remDays.add(dateFormat.format(time).toString());
				calendar.add(Calendar.DATE, 1);
				continue;
			} else if (journal.getMoodEntry(time) == null && hasReminder("mood") && !journal.getDismissed(time)[4]){
				remDays.add(dateFormat.format(time).toString());
				calendar.add(Calendar.DATE, 1);
				continue;
			}
			calendar.add(Calendar.DATE, 1);
		}
		
		return remDays;
	}
	

	/**
	 * Returns the entry order preference
	 * @return array list of strings representing the order in which entries should be displayed
	 */
	public ArrayList<String> getOrder(){
		return journal.getPrefs().getOrder();
	}
	
	/**
	 * Returns the entry reminders preference
	 * @return array list of strings representing the entries which should trigger reminders
	 */
	public ArrayList<String> getReminders(){
		return journal.getPrefs().getReminders();
	}
		
	public boolean hasReminder(String type){		
		boolean ret = false;
		for(int i = 0; i < getReminders().size(); i++){
			if (getReminders().get(i).equals(type)){
				ret = true;
			}
		}
		return ret;		
	}	
	
	/**
	 * Disabled the mainGUI frame (so that day may not be changed when creating new entries etc)
	 */
	public void disableMain(){
		gui.disableMainGUI();
	}
	
	/**
	 * Enables the mainGUI
	 */
	public void enableMain(){
		gui.enableMainGUI();
	}
	
	
	/**
	 * Loads the journal object from the specified file into the model. Also instantiates
	 * the twitter command object (done here because it requires the model to be initiated 
	 * in order to have the correct information about the twitter account)
	 * @param journalFile
	 * @return 1 if load is successfull, 0 otherwise
	 */
	public int initModel(File journalFile) {
		this.journalFileName = journalFile.getName();
		try {
			FileInputStream fis = new FileInputStream("userfiles/journals/" + journalFileName);
			ObjectInputStream in = new ObjectInputStream(fis);
			journal = (Journal) in.readObject();
			twitterCommand = new TwitterCommand(journal.getTwitterAccount(), this);
			in.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,"An error has occured. This is most " +
					"likely due to missing journal files.\nTry deleting the journal that " +
					"caused this error. If the error persists, \nplease delete the \"userfiles\" " +
					"directory and restart the application.", null, JOptionPane.ERROR_MESSAGE);
			return 0;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"An error has occured. This is most " +
					"likely due to missing journal files.\nTry deleting the journal that " +
					"caused this error. If the error persists, \nplease delete the \"userfiles\" " +
					"directory and restart the application.", null, JOptionPane.ERROR_MESSAGE);
			return 0;
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null,"An error has occured. This is most " +
					"likely due to missing journal files.\nTry deleting the journal that " +
					"caused this error. If the error persists, \nplease delete the \"userfiles\" " +
					"directory and restart the application.", null, JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		return 1;
	}
	/**
	 * Returns true if twitter is setup and ready to tweet
	 */
	public boolean isTwitterReady() {
		return twitterCommand.isTwitterReady();
	}

	/**
	 * Dismisses a reminder for an entry (even if a reminder isn't present for it, it still registers a dismissal for the entry)
	 * @param type type of entry to dismiss
	 * @param date date at which to dismiss the reminder
	 */
	public void dismissEntry(String type, Date date) {
		journal.dismissEntry(type, date);
		updateActiveViews(date,1);
	}
	
	public void undismissEntry(String type, Date date) {
		journal.undismissEntry(type, date);
		updateActiveViews(date,1);
	}
	
	/**
	 * Updates the preference object in journal with the specified array lists for order and reminders
	 * @param moodLabel 
	 * @param placeLabel 
	 * @param imageLabel 
	 * @param topicLabel 
	 * @param diaryLabel 
	 */
	public void updatePrefs(ArrayList<String> order, ArrayList<String> reminders, String diaryLabel, 
			String topicLabel, String imageLabel, String placeLabel, String moodLabel){
		journal.setOrder(order);
		journal.setReminders(reminders);
		
		journal.setDiaryLabel(diaryLabel);
		journal.setTopicLabel(topicLabel);
		journal.setImageLabel(imageLabel);
		journal.setPlaceLabel(placeLabel);
		journal.setMoodLabel(moodLabel);
		
		if (gui.dayVisible()){
			updateActiveViews(gui.getDate(),1);
		} else {
			gui.setCalendarReminders(getRemDays(selectedDate));
		}
	}

	/**
	 * Saves the journal object to a file (for which the filename is saved to the global variable journalFileName)
	 */
	public void saveToFile() {
		try {
			FileOutputStream fos = new FileOutputStream("userfiles/journals/" + journalFileName);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(journal);
			out.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
		
	/**
	 * Returns the account name of the currently authenticated twitter account
	 * @return account name if the account exists, "no account setup" if an account has not been authenticated
	 */
	public String getAccountName(){
		AccessToken token = journal.getTwitterAccount().accessToken;
		if (token == null){
			return "no account setup";
		}
		return journal.getTwitterAccount().accessToken.getScreenName();
	}
	
	/**
	 * Sets the twitter account in the journal to the specified account
	 * @param acc twitter account object to set
	 */
	public void setTwitterAccount(TwitterAccount acc){
		journal.setTwitterAccount(acc);
	}
	/**
	 * Sets the journal pointer in controller to the specified journal (called by the prefSelectDialog
	 * @param  jrnl
	 */
	public void setJournal(Journal jrnl){
		this.journal = jrnl;
	}

	/**
	 * When a user goes to re-authenticate an account, the twitter account in journal needs to be 
	 * re-instantiated to a new twitterAccount object. This method resets the twitter account to 
	 * the previous one in case the user presses cancel on the re-authentication window.
	 */
	public void revertTwitterAccount() {
		twitterCommand.resetAccount();		
	}


	/**
	 * Searches all entry in all days of the current month for the specified string
	 * @param searchText string to search in entries
	 * @return array list of dates (in string format) in the current month that contain one or more entries with that search string
	 */
	public ArrayList<String> search(String searchText) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM-dd-yyyy");
		ArrayList<String> highDays = new ArrayList<String>();
		ArrayList<Date> highDates = new ArrayList<Date>();
		
		ArrayList<Date> entryDates = journal.getEntryDates();
		
		//return if there are no entries
		if (entryDates.size() == 0){
			return highDays;
		}
		
		for (int i = 0; i < entryDates.size(); i++){			
			//search each type of entry one by one for the string, and if any contains that string,
			//add the date of the entry to the list, and continue to next iteration of while loop
			if (journal.searchDiary(searchText, entryDates.get(i))){
				highDates.add(entryDates.get(i));
				continue;
			} else if (journal.searchTopic(searchText, entryDates.get(i))){
				highDates.add(entryDates.get(i));
				continue;
			} else if (journal.searchImage(searchText, entryDates.get(i))){
				highDates.add(entryDates.get(i));
				continue;
			} else if (journal.searchPlace(searchText, entryDates.get(i))){
				highDates.add(entryDates.get(i));
				continue;
			} else if (journal.searchMood(searchText, entryDates.get(i))){
				highDates.add(entryDates.get(i));
				continue;
			}
		}
		
		//return if no results were found
		if (highDates.size() == 0){
			return highDays;
		}
		
		Date selectedDate = gui.getDate();
		gui.setNearestDay(getNearestDay(highDates, selectedDate));
		
		for (int i = 0; i < highDates.size(); i++){
			highDays.add(dateFormat.format(highDates.get(i)).toString());
		}
		
		return highDays;
	}

	private Date getNearestDay(ArrayList<Date> highDates, Date selectedDate) {
		Date nearestDate = highDates.get(0);
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(selectedDate);
		cal2.setTime(nearestDate);
		long minDiff = Math.abs(cal1.getTimeInMillis() - cal2.getTimeInMillis());

		for (int i = 1; i < highDates.size(); i++){
			cal2.setTime(highDates.get(i));
			if (minDiff > Math.abs(cal1.getTimeInMillis() - cal2.getTimeInMillis())){
				minDiff =  Math.abs(cal1.getTimeInMillis() - cal2.getTimeInMillis());
				nearestDate = highDates.get(i);
			}
		}
		
		return nearestDate;
	}


	public void removeTwitterAccount() {
		journal.removeTwitterAccount();
		twitterCommand.backupAccount();
		twitterCommand.setTwitterReady(false);
	}


	public void enablePreferenceFrame() {
		gui.enablePreferenceFrame();
	}
	
	public void clearUndoImageStack() {
		imageUndoStack.clear();
		imageRedoStack.clear();
	}
	
	public void addEdit(String image, Date date,String action, int index) {
		EditCommand edit = new EditCommand(image, date, action, index);
		imageUndoStack.add(edit);
	}
	
	public EditCommand getUndoAction(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		EditCommand edit = null;
		int index = 0;
		
		for(int i = 0; i < imageUndoStack.size(); i++) {
			if(dateFormat.format(date).
					equals(dateFormat.format(imageUndoStack.elementAt(i).getDate()))) {
				edit = imageUndoStack.elementAt(i);
				index = i;
			}
		}
		
		if(edit != null) {
			imageRedoStack.add(imageUndoStack.elementAt(index));
			imageUndoStack.removeElementAt(index);
			for(int i = 0; i < imageUndoStack.size(); i++) {
			}
			for(int i = 0; i < imageRedoStack.size(); i++) {
			}
		}
		
		return edit;
	}
	
	public boolean isImageUndoStackEmpty() {
		return imageUndoStack.isEmpty();
	}
	
	public boolean isImageRedoStackEmpty() {
		return imageRedoStack.isEmpty();
	}
	public EditCommand getRedoAction(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		EditCommand edit = null;
		int index = 0;
		for(int i = 0; i < imageRedoStack.size(); i++) {
			if(dateFormat.format(date).
					equals(dateFormat.format(imageRedoStack.elementAt(i).getDate()))) {
				edit = imageRedoStack.elementAt(i);
				index = i;
			}
		}
		
		if(edit != null) {
			imageUndoStack.add(imageRedoStack.elementAt(index));
			imageRedoStack.removeElementAt(index);
		}
		
		return edit;
	}

	public void mainUndo() {
		int size = mainUndoStack.size();
		if (size > 0){
			MainUndoRedo object = mainUndoStack.remove(size-1);
			//undo adding
			if (object.getAction().equals("add")){
				if (object.getType().equals("diary")){
					journal.deleteDiaryEntry(object.getDiary().getDate());
					undismissEntry("diary", object.getDiary().getDate());
					mainRedoStack.add(object);
					gui.setCalendarDate(object.getDiary().getDate());
				} else if (object.getType().equals("topic")){
					journal.deleteTopicEntry(object.getTopic().getDate());
					undismissEntry("topic", object.getTopic().getDate());
					mainRedoStack.add(object);
					gui.setCalendarDate(object.getTopic().getDate());
				} else if (object.getType().equals("image")){
					journal.deleteImageEntry(object.getImage().getDate());
					undismissEntry("image", object.getImage().getDate());
					mainRedoStack.add(object);
					gui.setCalendarDate(object.getImage().getDate());
				} else if (object.getType().equals("place")){
					journal.deletePlaceEntry(object.getPlace().getDate());
					undismissEntry("place", object.getPlace().getDate());
					mainRedoStack.add(object);
					gui.setCalendarDate(object.getPlace().getDate());
				} else if (object.getType().equals("mood")){
					journal.deleteMoodEntry(object.getMood().getDate());
					undismissEntry("mood", object.getMood().getDate());
					mainRedoStack.add(object);
					gui.setCalendarDate(object.getMood().getDate());
				}
				
			//undo deleting
			} else if (object.getAction().equals("delete")){
				if (object.getType().equals("diary")){
					DiaryEntry diary = object.getDiary();
					journal.addDiaryEntry(diary.getTitle(), diary.getContent(), diary.getDate());
					dismissEntry("diary", diary.getDate());
					mainRedoStack.add(object);
					gui.setCalendarDate(diary.getDate());
				} else if (object.getType().equals("topic")){
					TopicEntry topic = object.getTopic();
					journal.addTopicEntry(topic.getTopic(), topic.getDate(), topic.getCount());
					dismissEntry("topic", topic.getDate());
					mainRedoStack.add(object);
					gui.setCalendarDate(topic.getDate());
				} else if (object.getType().equals("image")){
					ImageEntry image = object.getImage();
					journal.addImageEntry(image.getLocation(), image.getDate());
					dismissEntry("image", image.getDate());
					mainRedoStack.add(object);
					gui.setCalendarDate(image.getDate());
				} else if (object.getType().equals("place")){
					PlaceEntry place = object.getPlace();
					journal.addPlaceEntry(place.getDate());
					placeEntry = journal.getPlaceEntry(place.getDate());
					placeEntry.setLocationCount(place.getLocationCount());
					placeEntry.setLocationList(place.getLocationList());
					dismissEntry("place", placeEntry.getDate());
					mainRedoStack.add(object);
					gui.setCalendarDate(placeEntry.getDate());
				} else if (object.getType().equals("mood")){
					MoodEntry mood = object.getMood();
					journal.addMoodEntry(mood.getSmiley(), mood.getDate());
					dismissEntry("mood", mood.getDate());
					mainRedoStack.add(object);
					gui.setCalendarDate(mood.getDate());
				}
				
			//undo editing
			} else if (object.getAction().equals("edit")){
				if (object.getType().equals("diary")){
					journal.deleteDiaryEntry(object.getDiary().getDate());
					mainRedoStack.add(object);
					size = mainUndoStack.size();
					object = mainUndoStack.remove(size-1);
					DiaryEntry diary = object.getDiary();
					journal.addDiaryEntry(diary.getTitle(), diary.getContent(), diary.getDate());
					mainRedoStack.add(object);
					gui.setCalendarDate(diary.getDate());
				} else if (object.getType().equals("topic")){
					journal.deleteTopicEntry(object.getTopic().getDate());
					mainRedoStack.add(object);
					size = mainUndoStack.size();
					object = mainUndoStack.remove(size-1);
					TopicEntry topic = object.getTopic();
					journal.addTopicEntry(topic.getTopic(), topic.getDate(), topic.getCount());
					mainRedoStack.add(object);
					gui.setCalendarDate(object.getTopic().getDate());
				} else if (object.getType().equals("image")){
					journal.deleteImageEntry(object.getImage().getDate());
					mainRedoStack.add(object);
					size = mainUndoStack.size();
					object = mainUndoStack.remove(size-1);
					ImageEntry image = object.getImage();
					journal.addImageEntry(image.getLocation(), image.getDate());
					mainRedoStack.add(object);
					gui.setCalendarDate(object.getImage().getDate());
				} else if (object.getType().equals("place")){
					journal.deletePlaceEntry(object.getPlace().getDate());
					mainRedoStack.add(object);
					size = mainUndoStack.size();
					object = mainUndoStack.remove(size-1);
					PlaceEntry place = object.getPlace();
					journal.addPlaceEntry(place.getDate());
					placeEntry = journal.getPlaceEntry(place.getDate());
					placeEntry.setLocationCount(place.getLocationCount());
					placeEntry.setLocationList(place.getLocationList());
					mainRedoStack.add(object);
					gui.setCalendarDate(object.getPlace().getDate());
				} else if (object.getType().equals("mood")){
					journal.deleteMoodEntry(object.getMood().getDate());
					mainRedoStack.add(object);
					size = mainUndoStack.size();
					object = mainUndoStack.remove(size-1);
					MoodEntry mood = object.getMood();
					journal.addMoodEntry(mood.getSmiley(), mood.getDate());
					mainRedoStack.add(object);
					gui.setCalendarDate(object.getMood().getDate());
				}
			}
			
		}
	}


	public void mainRedo() {
		int size = mainRedoStack.size();
		if (size > 0){
			MainUndoRedo object = mainRedoStack.remove(size-1);
			//redo adding
			if (object.getAction().equals("add")){
				if (object.getType().equals("diary")){
					DiaryEntry diary = object.getDiary();
					journal.addDiaryEntry(diary.getTitle(), diary.getContent(), diary.getDate());
					dismissEntry("diary", diary.getDate());
					mainUndoStack.add(object);
					gui.setCalendarDate(diary.getDate());
				} else if (object.getType().equals("topic")){
					TopicEntry topic = object.getTopic();
					journal.addTopicEntry(topic.getTopic(), topic.getDate(), topic.getCount());
					dismissEntry("topic", topic.getDate());
					mainUndoStack.add(object);
					gui.setCalendarDate(topic.getDate());
				} else if (object.getType().equals("image")){
					ImageEntry image = object.getImage();
					journal.addImageEntry(image.getLocation(), image.getDate());
					dismissEntry("image", image.getDate());
					mainUndoStack.add(object);
					gui.setCalendarDate(image.getDate());
				} else if (object.getType().equals("place")){
					PlaceEntry place = object.getPlace();
					journal.addPlaceEntry(place.getDate());
					placeEntry = journal.getPlaceEntry(place.getDate());
					placeEntry.setLocationCount(place.getLocationCount());
					placeEntry.setLocationList(place.getLocationList());
					dismissEntry("place", placeEntry.getDate());
					mainUndoStack.add(object);
					gui.setCalendarDate(placeEntry.getDate());
				} else if (object.getType().equals("mood")){
					MoodEntry mood = object.getMood();
					journal.addMoodEntry(mood.getSmiley(), mood.getDate());
					dismissEntry("mood", mood.getDate());
					mainUndoStack.add(object);
					gui.setCalendarDate(mood.getDate());
				}
				
			// redo adding
			} else if (object.getAction().equals("delete")){
				if (object.getType().equals("diary")){
					journal.deleteDiaryEntry(object.getDiary().getDate());
					undismissEntry("diary", object.getDiary().getDate());
					mainUndoStack.add(object);
					gui.setCalendarDate(object.getDiary().getDate());
				} else if (object.getType().equals("topic")){
					journal.deleteTopicEntry(object.getTopic().getDate());
					undismissEntry("topic", object.getTopic().getDate());
					mainUndoStack.add(object);
					gui.setCalendarDate(object.getTopic().getDate());
				} else if (object.getType().equals("image")){
					journal.deleteImageEntry(object.getImage().getDate());
					undismissEntry("image", object.getImage().getDate());
					mainUndoStack.add(object);
					gui.setCalendarDate(object.getImage().getDate());
				} else if (object.getType().equals("place")){
					journal.deletePlaceEntry(object.getPlace().getDate());
					undismissEntry("place", object.getPlace().getDate());
					mainUndoStack.add(object);
					gui.setCalendarDate(object.getPlace().getDate());
				} else if (object.getType().equals("mood")){
					journal.deleteMoodEntry(object.getMood().getDate());
					undismissEntry("mood", object.getMood().getDate());
					mainUndoStack.add(object);
					gui.setCalendarDate(object.getMood().getDate());
				}
			//redo editing
			} else if (object.getAction().equals("edit")){
				if (object.getType().equals("diary")){
					journal.deleteDiaryEntry(object.getDiary().getDate());
					mainUndoStack.add(object);
					size = mainRedoStack.size();
					object = mainRedoStack.remove(size-1);
					DiaryEntry diary = object.getDiary();
					journal.addDiaryEntry(diary.getTitle(), diary.getContent(), diary.getDate());
					mainUndoStack.add(object);
					gui.setCalendarDate(diary.getDate());
				} else if (object.getType().equals("topic")){
					journal.deleteTopicEntry(object.getTopic().getDate());
					mainUndoStack.add(object);
					size = mainRedoStack.size();
					object = mainRedoStack.remove(size-1);
					TopicEntry topic = object.getTopic();
					journal.addTopicEntry(topic.getTopic(), topic.getDate(), topic.getCount());
					mainUndoStack.add(object);
					gui.setCalendarDate(object.getTopic().getDate());
				} else if (object.getType().equals("image")){
					journal.deleteImageEntry(object.getImage().getDate());
					mainUndoStack.add(object);
					size = mainRedoStack.size();
					object = mainRedoStack.remove(size-1);
					ImageEntry image = object.getImage();
					journal.addImageEntry(image.getLocation(), image.getDate());
					mainUndoStack.add(object);
					gui.setCalendarDate(object.getImage().getDate());
				} else if (object.getType().equals("place")){
					journal.deletePlaceEntry(object.getPlace().getDate());
					mainUndoStack.add(object);
					size = mainRedoStack.size();
					object = mainRedoStack.remove(size-1);
					PlaceEntry place = object.getPlace();
					journal.addPlaceEntry(place.getDate());
					placeEntry = journal.getPlaceEntry(place.getDate());
					placeEntry.setLocationCount(place.getLocationCount());
					placeEntry.setLocationList(place.getLocationList());
					mainUndoStack.add(object);
					gui.setCalendarDate(object.getPlace().getDate());
				} else if (object.getType().equals("mood")){
					journal.deleteMoodEntry(object.getMood().getDate());
					mainUndoStack.add(object);
					size = mainRedoStack.size();
					object = mainRedoStack.remove(size-1);
					MoodEntry mood = object.getMood();
					journal.addMoodEntry(mood.getSmiley(), mood.getDate());
					mainUndoStack.add(object);
					gui.setCalendarDate(object.getMood().getDate());
				}
			}
		}
	}
	
	public String getDiaryLabel(){
		return journal.getPrefs().getDiaryLabel();
	}
	
	public String getTopicLabel(){
		return journal.getPrefs().getTopicLabel();
	}
	
	public String getImageLabel(){
		return journal.getPrefs().getImageLabel();
	}
	
	public String getPlaceLabel(){
		return journal.getPrefs().getPlaceLabel();
	}
	
	public String getMoodLabel(){
		return journal.getPrefs().getMoodLabel();
	}

	public void goToJournalManager() {
		Controller controller = new Controller();
		disposeAllViews();				
		new JournalSelectFrame(controller, new File("userfiles/config/journal.config"), controller.journal);
	}


	private void disposeAllViews() {
		Frame[] frames = Frame.getFrames();
		for (int i = 0; i < frames.length; i++){
			frames[i].dispose();
		}
	}


	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}


	public int getOrientation() {
		return orientation;
	}
}
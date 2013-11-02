package controller;

import data.DiaryEntry;
import data.ImageEntry;
import data.MoodEntry;
import data.PlaceEntry;
import data.TopicEntry;

/**
 * Object that contains the information for each action performed on the mainGUI frame. Whenever a user
 * creates, edits, or deletes an entry, this object is pushed onto the "undo" stack so that when a user
 * later goes to "undo", this state of the entry can be recovered.
 * @author  Mikus Lorencs
 */
public class MainUndoRedo {
	private String action;
	private String type;
	private DiaryEntry diary;
	private TopicEntry topic;
	private ImageEntry image;
	private PlaceEntry place;
	private MoodEntry mood;
	
	/**
	 * Constructs the object. It takes all 5 entry types as parameters because it is unknown what time of 
	 * entry it will be storing. If it is a "diary" entry, all the other parameters are simply null.
	 * @param actionArg type of action (add, delete, or edit)
	 * @param typeArg type of entry (diary, topic, image, place, or mood)
	 * @param diaryArg DiaryEntry object
	 * @param topicArg TopicEntry object
	 * @param imageArg ImageEntry object
	 * @param placeArg PlacEntry object
	 * @param moodArg MoodEntry object
	 */
	public MainUndoRedo(String actionArg, String typeArg, DiaryEntry diaryArg, TopicEntry 
			topicArg, ImageEntry imageArg, PlaceEntry placeArg, MoodEntry moodArg){
		this.action = actionArg;
		this.type = typeArg;
		this.diary = diaryArg;
		this.topic = topicArg;
		this.image = imageArg;
		this.place = placeArg;
		this.mood = moodArg;
	}
	
	public String getAction(){
		return action;
	}
	
	public String getType(){
		return type;
	}
	
	public void setAction(String actionArg){
		this.action = actionArg;
	}
	
	public void setType(String typeArg){
		this.type = typeArg;
	}
	
	public DiaryEntry getDiary(){
		return diary;
	}
	
	public TopicEntry getTopic(){
		return topic;
	}
	
	public ImageEntry getImage(){
		return image;
	}
	
	public PlaceEntry getPlace(){
		return place;
	}
	
	public MoodEntry getMood(){
		return mood;
	}
}

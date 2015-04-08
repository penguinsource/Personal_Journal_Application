package data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Preference object which contains two array lists. One for the Order preference and  one for Reminders preference. Only contains getters and setters
 * @author Mikus Lorencs
 */
public class Preference implements Serializable{

	private static final long serialVersionUID = -1384631995569729344L;
	private ArrayList<String> order;
	private ArrayList<String> reminders;
	private String diaryLabel;
	private String topicLabel;
	private String imageLabel;
	private String placeLabel;
	private String moodLabel;	
	
	/**
	 * @param order
	 */
	public void setOrder (ArrayList<String> order){
		this.order = order;
	}
	
	/**
	 * @param reminders
	 */
	public void setReminders (ArrayList<String> reminders){
		this.reminders = reminders;
	}
	
	public ArrayList<String> getOrder (){
		return this.order;
	}
	
	public ArrayList<String> getReminders (){
		return this.reminders;
	}

	//getters and setters for the custom entry labels 
	public String getDiaryLabel() {
		return diaryLabel;
	}

	public void setDiaryLabel(String diaryLabel) {
		this.diaryLabel = diaryLabel;
	}

	public String getTopicLabel() {
		return topicLabel;
	}

	public void setTopicLabel(String topicLabel) {
		this.topicLabel = topicLabel;
	}

	public String getImageLabel() {
		return imageLabel;
	}

	public void setImageLabel(String imageLabel) {
		this.imageLabel = imageLabel;
	}

	public String getPlaceLabel() {
		return placeLabel;
	}

	public void setPlaceLabel(String placeLabel) {
		this.placeLabel = placeLabel;
	}

	public String getMoodLabel() {
		return moodLabel;
	}

	public void setMoodLabel(String moodLabel) {
		this.moodLabel = moodLabel;
	}
}

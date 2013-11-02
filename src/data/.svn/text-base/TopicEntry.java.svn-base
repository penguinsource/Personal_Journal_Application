package data;

import java.io.Serializable;
import java.util.Date;

/**
 * TopicEntry stores the topic string and topic(s) count of a specific date.
 * @author  Haoran
 */
public class TopicEntry implements Serializable{
	
	private static final long serialVersionUID = 9018855679040977173L;
	private String topic;
	private Date date;
	int count;	

	/**
	 * A topic entry object has three values which are topic, date, and count.
	 * @param topic
	 * @param date
	 * @param count
	 */
	public TopicEntry(String topic, Date date, int count) {
		this.topic = topic;
		this.date = date;
		this.count = count;
	}
	
	/**
	 * Getting the topic.
	 */
	public String getTopic() {
		return topic;
	}
	
	/**
	 * Getting the date.
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Setting a topic into the topic textfield.
	 * @param  topic
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	/**
	 * Setting a date.
	 * @param  date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * Getting the count.
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * @param count_arg
	 */
	public void setCount(int count_arg) {
		this.count = count_arg;
	}
	
	/**
	 * Searched the entry for the searchText
	 * @param searchText
	 * @return true if searchText found in entry
	 */
	public boolean search(String searchText) {
		String lowTopic = getTopic().toLowerCase();
		String lowSearchText = searchText.toLowerCase();
		int index = lowTopic.indexOf(lowSearchText);
		
		if (index > -1){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Creates a copy of this object and returns it
	 * 
	 * @return A copy of this object
	 */
	public TopicEntry clone(){
		return new TopicEntry(this.getTopic(), this.getDate(), this.getCount());
	}
}

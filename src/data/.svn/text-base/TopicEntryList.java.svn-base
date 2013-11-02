package data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import java.util.Date;

/**
 * TopicEntryList stores a list of topics entrys.
 * @author Haoran
 *
 */
public class TopicEntryList extends EntryList {

	private static final long serialVersionUID = -2122875715465687154L;
	private Vector<TopicEntry> topicEntryList;
	
	public TopicEntryList() {
		topicEntryList = new Vector<TopicEntry>();
	}
	/**
	 * Adding a new topic.
	 * @param topic
	 * @param date
	 * @param count
	 */
	public void addEntry(String topic, Date date, int count) {
		topicEntryList.add(new TopicEntry(topic, date, count));
	}
	
	/**
	 * Edit the specific topic entry by date.
	 * @param newTopic
	 * @param newDate
	 * @param count
	 */
	public void editEntry(String newTopic, Date newDate, int count) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM-d-yyyy");
		String formattedDate = dateFormat.format(newDate).toString();
		boolean flag = false;
		for (int i = 0; i < topicEntryList.size(); i++){
			if (dateFormat.format(topicEntryList.get(i).getDate()).toString()
					.matches(formattedDate)){
				flag = true;
				topicEntryList.elementAt(i).setTopic(newTopic);
				topicEntryList.elementAt(i).setDate(newDate);
				topicEntryList.elementAt(i).setCount(count);
			}
		}
		
		if(!flag){
		}
		
	}
	
	/** 
	 * Delete the specific topic entry by date.
	 * @param date
	 */
	public void deleteEntry(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM-d-yyyy");
		String formattedDate = dateFormat.format(date).toString();
		boolean flag = false;
		for (int i = 0; i < topicEntryList.size(); i++){
			if (dateFormat.format(topicEntryList.get(i).getDate()).toString()
					.matches(formattedDate)) {
				flag = true;
				topicEntryList.removeElementAt(i);
			}
		}
		if(!flag){
		}
		
	}
	/**
	 * Get the specific topic entry by date.
	 * @param date
	 */
	public TopicEntry getEntry(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM-d-yyyy");
		String formatted_date = dateFormat.format(date).toString();
		
		for (int i = 0; i < topicEntryList.size(); i++){
			if (dateFormat.format(topicEntryList.get(i).getDate()).toString()
					.matches(formatted_date)){
				return topicEntryList.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Return all topics which a user has entered.
	 */
	public String getAllTopics(){
		String topics = "";
		
		for(int i=0;i<topicEntryList.size();i++){
			topics = topics.concat(topicEntryList.get(i).getTopic());
		}
		return topics;
	}
	public boolean searchEntry(String searchText, Date date) {
		TopicEntry entryObject = getEntry(date);	
		
		if (entryObject == null){
			return false;
		} 
		
		return entryObject.search(searchText);
	}
	public ArrayList<Date> getEntryDates(ArrayList<Date> entryDates) {
		Calendar cal = Calendar.getInstance();
		
		for (int i = 0; i < topicEntryList.size(); i++){	
			Date date = topicEntryList.get(i).getDate();
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

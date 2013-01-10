package data;

import java.io.Serializable;
import java.util.Date;
/**
 * MoodEntry stores the data of a diary entry such as the smiley and date of the entry.
 * @author  Mihai
 */
public class MoodEntry implements Serializable{

	private static final long serialVersionUID = 1661947220456960117L;
	int smiley; // 1 to 6
	String smileyText; //textual description of smiley for searching
	Date date;
	
	public MoodEntry(int smiley_arg, Date date) {
		this.smiley = smiley_arg;
		this.date = date;
		//set text corresponding to each smiley so that the mood entry can be searchable
		switch (smiley) {
	        case 1:  smileyText = "angry";  	break;
	        case 2:  smileyText = "crying"; 	break;
	        case 3:  smileyText = "sad";    	break;
	        case 4:  smileyText = "meh";    	break;
	        case 5:  smileyText = "happy";  	break;
	        case 6:  smileyText = "excited";	break;
	        default: smileyText = ""; 			break;
		}
	}
	
	/**
	 * returns the smiley number of a MoodEntry object
	 */
	public int getSmiley() {
		return smiley;
	}
		
	/**
	 * sets the smiley number of a MoodEntry object
	 */
	public void setSmiley(int smiley_arg) {
		this.smiley = smiley_arg;
		switch (smiley) {
	        case 1:  smileyText = "angry";  	break;
	        case 2:  smileyText = "crying"; 	break;
	        case 3:  smileyText = "sad";    	break;
	        case 4:  smileyText = "meh";    	break;
	        case 5:  smileyText = "happy";  	break;
	        case 6:  smileyText = "excited";	break;
	        default: smileyText = ""; 			break;
		}
	}
	
	/**
	 * returns the date of a MoodEntry object
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * set the date of a MoodEntry object
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	
	/**
	 * Searches the entry for the string
	 * @param searchText
	 * @return true if string is found
	 */
	public boolean search(String searchText) {
		String text = smileyText.toLowerCase();

		String lowSearchText = searchText.toLowerCase();
		int index = text.indexOf(lowSearchText);
		
		if (index > -1){
			return true;
		}
		
		return false;
	}
	
	public MoodEntry clone(){
		return new MoodEntry(this.getSmiley(), this.getDate());
	}
}

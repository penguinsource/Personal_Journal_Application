package data;

import java.io.Serializable;
import java.util.Date;

/**
 * A dismiss object specifies which entries for that day have had their reminders dismissed.
 * @author  Lorencs
 */
public class Dismiss implements Serializable{
	private static final long serialVersionUID = -5115841542338592859L;
	private boolean[] entryList = {false, false, false, false, false};
	private Date date;
	
	/**
	 * @param date specifies date to create the Dismiss object for
	 */
	public Dismiss(Date date){
		this.date = date;
	}
	
	/**
	 * Sets the specified entry to dismissed
	 * @param type type of entry to set to dismissed
	 */
	public void setDismissed(String type){
		if (type.equals("diary")){
			entryList[0] = true;		
		} else if (type.equals("topic")){
			entryList[1] = true;
		} else if (type.equals("image")){
			entryList[2] = true;
		} else if (type.equals("place")){
			entryList[3] = true;
		} else if (type.equals("mood")){
			entryList[4] = true;
		}
	}
	
	public boolean[] getDismissed(){
		return entryList;		
	}
	
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the specified entry to undismissed
	 * @param type type of entry to set undismissed
	 */
	public void setUndismissed(String type) {
		if (type.equals("diary")){
			entryList[0] = false;	
		} else if (type.equals("topic")){
			entryList[1] = false;
		} else if (type.equals("image")){
			entryList[2] = false;
		} else if (type.equals("place")){
			entryList[3] = false;
		} else if (type.equals("mood")){
			entryList[4] = false;
		}
	}
}

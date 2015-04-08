package data;

import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Aggregation of all Dismiss objects with methods to retrieve and manage them.
 * @author Lorencs
 *
 */
public class DismissList implements Serializable{

	private static final long serialVersionUID = 1567864685943794779L;
	ArrayList<Dismiss> dismissList;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM-dd-yyyy");
	
	/**
	 * Constructor instantiates a new array list of Dismiss objects
	 */
	public DismissList() {
		dismissList = new ArrayList<Dismiss>();
	}
	
	/**
	 * Dismisses the specified entry type at a specified date. Creates a new Dismiss
	 * objects if it doesn't exist (if not entries have yet been dismissed on that day)
	 */
	public void dismissEntry(String type, Date date){
		Dismiss object = getDismiss(date);
		//if dismiss object doesn't exist for that date, create one
		if (object == null){
			object = new Dismiss(date);
			dismissList.add(object);
		}
		
		object.setDismissed(type);
	}

	
	/**
	 * Gets a Dismiss object at the specified date. Called only from within this class
	 * @return Dismiss object, null if the object doesn't exist at the specified date
	 */
	private Dismiss getDismiss(Date date) {
		String formatted_date = dateFormat.format(date).toString();
		for (int i = 0; i < dismissList.size(); i++){
			String formatted_date_from_list = dateFormat.format(dismissList.get(i).getDate()).toString();
			if (formatted_date_from_list.matches(formatted_date)){
				return dismissList.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Returns an array of booleans which represent which entries have been dismissed.
	 * If a Dismiss object doens't exist at the date, creates an array of all false and
	 * returns that.
	 * @param date
	 * @return array of booleans, true for dismissed, false for not dismissed, in the order (diary, topic, image, place, mood)
	 */
	public boolean[] getDismissed(Date date){
		Dismiss object = getDismiss(date);
		if (object == null){
			boolean[] array = {false, false, false, false, false};
			return array;
		} else {
			return object.getDismissed();			
		}
	}

	public void undismissEntry(String type, Date date) {
		Dismiss object = getDismiss(date);
		//if dismiss object doesn't exist for that date, create one
		if (object == null){
			object = new Dismiss(date);
			dismissList.add(object);
		}
		
		object.setUndismissed(type);
	}
}

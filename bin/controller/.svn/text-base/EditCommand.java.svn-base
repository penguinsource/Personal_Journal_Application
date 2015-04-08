package controller;

import java.util.Date;

/**
 * This class holds the information of the actions made by the user in ImageFrame. Such information is used for undo/redo.
 * @author  Fernando
 */
public class EditCommand {

	private String element;
	private Date date;
	private String action;
	private int index;
	
	/**
	 * Constructor for EditCommand
	 * 
	 * @param element
	 * @param date
	 * @param action
	 * @param index
	 */
	public EditCommand(String element, Date date, String action, int index) {
		this.element = element;
		this.date = date;
		this.action = action;
		this.index = index;
	}
	
	public EditCommand() {
	}
	
	/**
	 * Returns the element that was added/deleted
	 * 
	 * @return The element that was added/deleted.
	 */
	public String getElement() {
		return element;
	}
	
	/**
	 * Returns the action made by the user
	 * 
	 * @return A string containing the action made by the user (delete or insert)
	 */
	public String getAction() {
		return action;
	}	
	
	/**
	 * Returns the index of the element that was inserted/deleted
	 * 
	 * @return The index of the element
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * Returns the date of the edit
	 * 
	 * @return The date of the edit
	 */
	public Date getDate() {
		return date;
	}
}

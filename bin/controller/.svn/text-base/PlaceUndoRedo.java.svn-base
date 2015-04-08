package controller;

import gui.LocationEntryPanel;

/**
 * Object that contains the information for each action performed on the PlaceEntry frame. Whenever a user
 * creates, edits, or deletes an entry, this object is pushed onto the "undo" stack so that when a user
 * later goes to "undo", this state of the entry can be recovered. 
 * (similar function to the MainUndoRedo class).
 * 
 * @author  Mihai Oprescu
 */

public class PlaceUndoRedo {
	/**
	 */
	private String action;
	private LocationEntryPanel panel;

	
	public PlaceUndoRedo(String action_arg, LocationEntryPanel panel_arg){
		this.action = action_arg;
		this.panel = panel_arg;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}

	public LocationEntryPanel getPanel() {
		return panel;
	}
	
	public void setPanel(LocationEntryPanel panel) {
		this.panel = panel;
	}
}

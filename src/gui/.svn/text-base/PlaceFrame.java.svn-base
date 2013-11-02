package gui;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

import controller.Controller;
import controller.PlaceUndoRedo;
import data.LocationEntry;
import data.PlaceEntry;

/**
 * This is the GUI class for entering places where you have been. If you do not currently have any place entries entered, then you can add a place entry. If you do have a places entry for a specific date, then you can only view them. In the PlaceGUI, you can add locations (by pressing the add button) or just view the locations that are saved into the panel by clicking the "View Location on map" button. When completing a location entry: Map button gets the coordinates of your location; Save button saves the location entry to the panel; Delete button removes the location entry from the panel; Edit button edits the location entry. When you complete adding locations, press the 'Ok' button; this will save the locations that are current saved to the panel to the model.
 * @author  Mihai !
 * 
 */
public class PlaceFrame implements ActionListener, Serializable {

	private static final long serialVersionUID = -4117652074437733909L;
	private JFrame frame;
    private Date date_selected;
    private JLabel date_label;
    private Controller control;
    private LocationEntryPanel locationEntryPanelObject;
    
    private PlaceEntry place_entry;
    
    private JPanel rightPanel;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel locationEntriesPanel;
    private JPanel bottomButtonPanel;
    
    private JLabel separator;
    private JLabel separator2;
    private JLabel separator3;
    private boolean first;
    private boolean editMode;
    private JButton undoButton;
    private JButton redoButton;
    private JButton viewLocationsButton;
    private JButton addLocationButton;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField LocationNameField;
    private JTextField CoordLabel;
    private SimpleDateFormat date_format = new SimpleDateFormat("MMMMMMMMM d, yyyy");
    
    private int initial_height;
    private int firstWidth;
    private int secondWidth;
    private int firstHeight;
    
    private String buttonGapLeft;
    private String bottomPanelGap;
    private String type;
    Vector <LocationEntryPanel> listOfSavedEntries;
    Vector <PlaceUndoRedo> undoList;
    Vector <PlaceUndoRedo> redoList;
    private PlacesEntryMapFrame map;
    
    private MessageFrame messFrame;
    
    public PlaceFrame(String type_arg, Date selected_date, Controller control_arg){
    	
    	// boolean editMode is set to true when a location is being edited
    	editMode = false;
    	date_selected = selected_date;
    	control = control_arg;
    	type = type_arg;   	
    	
    	first = true;        
        
    	messFrame = new MessageFrame("");
    	
    	undoList = new Vector<PlaceUndoRedo>();
    	redoList = new Vector<PlaceUndoRedo>();
    	
        mainPanel = new JPanel(new MigLayout("", "0 [] 0", "0 [] 0"));
      	rightPanel = new JPanel(new MigLayout("", "0 [] 0", "0 [] 0"));
      	leftPanel = new JPanel(new MigLayout("", "[][]", "[] 0 []"));
      	locationEntriesPanel = new JPanel(new MigLayout("", "[][]"));
      	bottomButtonPanel = new JPanel(new MigLayout());
      	
		// build the frame
		frame = new JFrame("Place Entry");
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// add components onto the frame
		frame.add(mainPanel);
		
		// linux frame size
		firstWidth = 407;
		secondWidth = 407;
		firstHeight = 118;
		initial_height = 146;
		
		frame.setSize(firstWidth, firstHeight);
		
		// window listener, enabled main GUI on X
        frame.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
				control.enableMain();
				control.removeActiveView("placeView");				
		      }
		});
		
		mainPanel.add(leftPanel, "dock west");
		mainPanel.add(rightPanel, "dock east");
		
		date_label = new JLabel();
        date_label.setText(date_format.format(selected_date).toString());
        date_label.setForeground(new Color(170, 170, 170));
        
        LocationNameField = new JTextField("Title");
        LocationNameField.setMinimumSize(new Dimension(109, 25));
        
        CoordLabel = new JTextField("0.00, 0.00");
        CoordLabel.setMinimumSize(new Dimension(77, 10));
        
		separator = new JLabel();
		separator.setIcon(new ImageIcon("images/separator3.PNG"));
		
		separator2 = new JLabel();
		separator2.setIcon(new ImageIcon("images/separator3.PNG"));
		
		separator3 = new JLabel();
		separator3.setIcon(new ImageIcon("images/separatorStart.PNG"));
				
		addLocationButton = new JButton("Add Location");
		addLocationButton.addActionListener(this);
		addLocationButton.setActionCommand("addLocationButton");
		addLocationButton.setIcon(new ImageIcon("images/bullet_add.png"));
		
		
		viewLocationsButton = new JButton("View Locations on Map");
		viewLocationsButton.addActionListener(this);
		viewLocationsButton.setActionCommand("viewLocationsButton");
		viewLocationsButton.setIcon(new ImageIcon("images/bullet-go.png"));
		
		JButton addCoordsButton = new JButton("");
		addCoordsButton.addActionListener(this);
		addCoordsButton.setActionCommand("ExtendAndGetCoords");
		addCoordsButton.setIcon(new ImageIcon("images/bullet-go.png"));

		okButton = new JButton("Ok");
		okButton.addActionListener(this);
		okButton.setActionCommand("okButton");
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		cancelButton.setActionCommand("cancelButton");
		
		undoButton = new JButton();
		undoButton.setActionCommand("undo");
		undoButton.addActionListener(this);
		undoButton.setIcon(new ImageIcon("images/Undo-icon.png"));
		ImageIcon icon = new ImageIcon("images/Undo-icon.png");
		Image img =  icon.getImage(); 
		Image newimg = img.getScaledInstance(12, 12,  java.awt.Image.SCALE_SMOOTH);  
		undoButton.setIcon(new ImageIcon(newimg));
		
		redoButton = new JButton();
		redoButton.setActionCommand("redo");
		redoButton.addActionListener(this);		
		ImageIcon icon2 = new ImageIcon("images/Redo-icon.png");
		Image img2 =  icon2.getImage(); 
		Image newimg2 = img2.getScaledInstance(12, 12,  java.awt.Image.SCALE_SMOOTH);  
		redoButton.setIcon(new ImageIcon(newimg2));
		
		this.setUndoRedo(false, false);
		
        JButton helpBtn = new JButton();
        helpBtn.setToolTipText("Help");
        helpBtn.setMaximumSize(new Dimension(30,30));
        helpBtn.setIcon(new ImageIcon("images/help4.png"));
        helpBtn.setActionCommand("help");
        helpBtn.addActionListener(this);
		
		bottomPanelGap = "gapbottom 5";
		leftPanel.add(addLocationButton, "gapleft 1, gaptop 10");
		leftPanel.add(viewLocationsButton, "gaptop 10, " + bottomPanelGap);
		leftPanel.add(undoButton, "gapleft 5, gaptop 5");
		leftPanel.add(redoButton, "gaptop 5, wrap");
		leftPanel.add(separator2, "span, wrap");
		leftPanel.add(bottomButtonPanel, "span");
		
		buttonGapLeft = "gapleft 243";
		bottomButtonPanel.add(helpBtn);
		bottomButtonPanel.add(okButton, buttonGapLeft);
		bottomButtonPanel.add(cancelButton);
		
    	// if type is "new", then no current entries exist for the selected_date
    	// that means that you can't view any locations on the map
    	if (type.matches("new")){
    		control.disableMain();
    	}else if (type.matches("view")){
    		control.updateActiveViews(selected_date,1);
    	}
    	frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
    
    public void updateView(PlaceEntry placeEntry_arg, Date new_date){
    	date_selected = new_date;
    	// should delete whatever is here, and set frame size to initial w and h ?
    	loadEntries(placeEntry_arg);
    	this.setUndoRedo(false, false);
    	addLocationButton.setEnabled(true);
    	this.enableAllEdits();
    	this.enableAllDeletes();
    }
    
    public void resetPanel(){
    	
    	locationEntriesPanel.removeAll();
    	first = true;
    	
    	Component[] list = locationEntriesPanel.getComponents();
    	
    	if (list.length == 0){
    		resizeFrame(3);
    	}
    }
    
    public void loadEntries(PlaceEntry placeEntry_arg){
    	
    	this.resetPanel();
    	
    	if (placeEntry_arg == null){
    		frame.setSize(new Dimension(firstWidth, firstHeight));
    		frame.validate();
    	}else{
    		place_entry = placeEntry_arg;
    		Vector<LocationEntry> entryList = place_entry.getAllLocations();
    	
    		for (int i = 0; i < entryList.size(); i++){
    		
    			initial_height += 30;
    		
			if (first){
				resizeFrame(1);
				first = false;
			}else{
				frame.setSize(new Dimension(secondWidth, initial_height));		
			}
			
			String coords = String.format("%.1f", entryList.get(i).getLatitude()) + ", " + String.format("%.1f", entryList.get(i).getLongitude());
			String loc_name = entryList.get(i).getLocationName();
			Angle latitude =  Angle.fromDegrees(entryList.get(i).getLatitude());
			Angle longitude =  Angle.fromDegrees(entryList.get(i).getLongitude());
			Double elevation = entryList.get(i).getElevation();
			
			Position position = new Position(latitude, longitude, elevation);
			locationEntryPanelObject = new LocationEntryPanel(coords, position, loc_name, "complete", addLocationButton, this, control);
			locationEntriesPanel.add(locationEntryPanelObject, "wrap");
			frame.validate();		
    		}
    		initial_height = 146;
    	}
    	    	
    	frame.validate();
    	
    }
    
    public void removeFromPanel(String name_arg){
    	locationEntryPanelObject = this.getLocationEntryPanelRef(name_arg);
    	locationEntriesPanel.remove(locationEntryPanelObject);
    	locationEntriesPanel.revalidate();
    	
    	Component[] list = locationEntriesPanel.getComponents();

    	if (list.length == 0){
    		resizeFrame(3);
    		first = true;
    		
    	}else{
    		resizeFrame(4);
			
			// fix size
			while ((frame.getHeight() - leftPanel.getHeight()) > - 1){
				frame.setSize(new Dimension(secondWidth, frame.getHeight() - 1));
			}
		}
    	
    }
    
    public LocationEntryPanel getLocationEntryPanelRef(String name){
    	Component[] list = locationEntriesPanel.getComponents();
    	for (int i = 0; i < list.length; i++){
    		LocationEntryPanel location = (LocationEntryPanel) list[i];
    		if (name.matches(location.getLocationName())){
    			return location;
    		}
    	}
    	return null;
    }
    
    public void disableButtons(String type){
    	Vector <LocationEntryPanel> List = this.getPanelList();
    	for (int i = 0; i < List.size(); i++){
    		List.get(i).disableButtons("delete");
    		List.get(i).disableButtons("edit");
    	}
    }
    
    public int getnoPanels(Vector<LocationEntryPanel> list){
    	int count = 0;
    	if (list == null){
    		return 0;
    	}
    	if (list.isEmpty()){
    		return 0;
    	}
    	for (int i = 0; i < list.size(); i++){
    		count++;
    	}
    	return count;
    }
    
    public Component[] getListLocationPanels(){
    	Component[] list = locationEntriesPanel.getComponents();
    	return list;
    }
    
    public Vector<LocationEntryPanel> getPanelList(){
    	Component[] list = locationEntriesPanel.getComponents();
    	Vector <LocationEntryPanel> panelList = new Vector<LocationEntryPanel>();
    	for (int i = 0; i < list.length; i++){
    		LocationEntryPanel location = (LocationEntryPanel) list[i];
    		panelList.add(location);
    	}
    	return panelList;
    }
    
    public Vector<LocationEntryPanel> getListLocationSavedPanels(){
    	Component[] list = locationEntriesPanel.getComponents();
    	Vector <LocationEntryPanel> SavedLocationEntries = new Vector <LocationEntryPanel>();
    	for (int i = 0; i < list.length; i++){
    		LocationEntryPanel panel = (LocationEntryPanel) list[i];
    		if (panel.isitSaved()){
    			SavedLocationEntries.add(panel);
    		}
    	}
    	
    	return SavedLocationEntries;
    }
    
    public void printAllTitles(){
    	Component[] hey = locationEntriesPanel.getComponents();
    	for (int i = 0; i < hey.length; i++){
    	}
    }
    
    // checks if the location name of the text field is unique and if it is empty
    public boolean checkLocationName(String location_name_arg){
    	// if location name is empty
    	if (location_name_arg.matches("")) return false;
    	Vector <LocationEntryPanel> SavedLocationEntries = this.getListLocationSavedPanels();
    	for (int i = 0; i < SavedLocationEntries.size(); i++){;
    		if (SavedLocationEntries.get(i).getLocationName().matches(location_name_arg)){
    			return false;
    		}
    	}
    	
    	return true;
    }
    
    public void disablePlacesGUI(){
    	frame.setEnabled(false);
    }
    
    public void enablePlacesGUI(){
    	frame.setEnabled(true);
    	map = null;
    }
    
    public void enableEditMode(){
    	editMode = true;
    }
    
    public void disableEditMode(){
    	editMode = false;
    }
    
    public void enableAllEdits(){
    	Vector<LocationEntryPanel> list = this.getPanelList();
    	for (int i = 0; i < list.size(); i++){
    		list.get(i).enableEditButton();
    	}
    }
    
    public void enableAllDeletes(){
    	Vector<LocationEntryPanel> list = this.getPanelList();
    	for (int i = 0; i < list.size(); i++){
    		list.get(i).enableDleteButton();
    	}
    }
    
    public void saveToUndo(String action_arg, LocationEntryPanel panel_arg){
    	undoList.add(new PlaceUndoRedo(action_arg, panel_arg));
    	redoList.clear();
    }
    
	public void actionPerformed(ActionEvent e) {
		if ("addLocationButton".matches(e.getActionCommand())){
			if (first){
				resizeFrame(1);
				first = false;
				
			}else{
				resizeFrame(2);
				// fix size
				while ((frame.getHeight() - leftPanel.getHeight()) < 53){
					frame.setSize(new Dimension(secondWidth, frame.getHeight() + 3));
				}
			}
			
			locationEntryPanelObject = new LocationEntryPanel(null, null, null, "new", addLocationButton, this, control);
			locationEntriesPanel.add(locationEntryPanelObject, "wrap");
			frame.validate();
			
			this.setUndoRedo(false, false);
			
		}else if ("viewLocationsButton".matches(e.getActionCommand())){    		
			listOfSavedEntries = getListLocationSavedPanels();
			if (map == null){
				map = new PlacesEntryMapFrame("view", LocationNameField.getText(), this, null, listOfSavedEntries, control);
			}

		}else if ("okButton".matches(e.getActionCommand())){
			
			if (!editMode){
			
			listOfSavedEntries = getListLocationSavedPanels();
			
			int noLoc = 1;
    		for (int i = 0; i < listOfSavedEntries.size(); i++){
    			if (listOfSavedEntries.get(i).getPosition() == null){
    				//no location chosen
    				noLoc = 0;
    			}
    		}
    		
    		if (listOfSavedEntries.isEmpty()){
				JOptionPane.showMessageDialog(frame, "There are no entries saved to the panel !\n " +
						"If you are editing an entry, please save it to the panel before submitting.");
				return;
    		}
    		if (noLoc == 0){
				JOptionPane.showMessageDialog(frame, "There are missing coordinates for one or more location entries");
				return;
    		}else {
    			if (type.matches("new")){
					for (int i = 0; i < listOfSavedEntries.size(); i++){
					}
	    			control.addPlaceEntry(date_selected, listOfSavedEntries);
	    		}else if (type.matches("view")){
					
					// you are in view/edit mode. if you add locations and press ok, it will go to control.editPlaceEntry
	    			control.editPlaceEntry(date_selected, listOfSavedEntries);
	    			
	    		
	    		}
	    		control.removeActiveView("placeView");
				control.enableMain();
				frame.dispose();
			
    		}
    		
			}else {
				JOptionPane.showMessageDialog(frame, "You are modifying a location entry, please save it or delete it before submitting !");
				return;
			}
		}else if ("cancelButton".matches(e.getActionCommand())){
			control.removeActiveView("placeView");
			control.enableMain();
			frame.dispose();			
		}else if ("undo".matches(e.getActionCommand())){
			PlaceUndoRedo obj = undoList.elementAt(undoList.size() - 1);
			if (obj.getAction().matches("add")){
				//removeFromPanel
				LocationEntryPanel panel = obj.getPanel();
				removeFromPanel(panel.getLocationName());
				redoList.add(obj);
				undoList.remove(undoList.size() - 1);

			}else if (obj.getAction().matches("delete")){
				LocationEntryPanel panel = obj.getPanel();
				
				if (first){
					resizeFrame(1);
					first = false;
					
				}else{
					resizeFrame(2);
					// fix size
					while ((frame.getHeight() - leftPanel.getHeight()) < 53){
						frame.setSize(new Dimension(secondWidth, frame.getHeight() + 3));
					}
				}
				locationEntriesPanel.add(new LocationEntryPanel(panel.getCoordsLabel(), panel.getPosition(),
						panel.getLocationName(), "complete", panel.getButtonRef(), panel.getPlacesGUIref(), panel.getControlRef()), "wrap");
				redoList.add(obj);
				undoList.remove(undoList.size() - 1);
				
			}else if (obj.getAction().matches("edit")){
				
				LocationEntryPanel new_panel = obj.getPanel();
				PlaceUndoRedo obj2 = undoList.elementAt(undoList.size() - 2);
				LocationEntryPanel old_panel = obj2.getPanel();
				
				locationEntriesPanel.add(new LocationEntryPanel(old_panel.getCoordsLabel(), old_panel.getPosition(),
						old_panel.getLocationName(), "complete", old_panel.getButtonRef(), old_panel.getPlacesGUIref(), old_panel.getControlRef()), "wrap");
				removeFromPanel(new_panel.getLocationName());
				
				// obj => new panel
				// obj2 => old panel
				redoList.add(obj);
				undoList.remove(undoList.size() - 1);
				redoList.add(obj2);
				undoList.remove(undoList.size() - 1);		
				
				resizeFrame(2);
				
				while ((frame.getHeight() - leftPanel.getHeight()) < 20){
					frame.setSize(new Dimension(secondWidth, frame.getHeight() + 1));
				}
			}
			
			frame.validate();
			leftPanel.revalidate();
			locationEntriesPanel.revalidate();
			
			if (redoList.isEmpty()){
				redoButton.setEnabled(false);
			}else{
				redoButton.setEnabled(true);
			}
			if (undoList.isEmpty()){
				undoButton.setEnabled(false);
			}else{
				undoButton.setEnabled(true);
			}
			addLocationButton.setEnabled(true);
			
		}else if ("redo".matches(e.getActionCommand())){
			PlaceUndoRedo obj = redoList.elementAt(redoList.size() - 1);
			if (obj.getAction().matches("add")){
				
				if (first){
					resizeFrame(1);
					first = false;
				}else{
					resizeFrame(2);
					// fix size
					while ((frame.getHeight() - leftPanel.getHeight()) < 53){
						frame.setSize(new Dimension(secondWidth, frame.getHeight() + 3));
					}
				}
				LocationEntryPanel panel = obj.getPanel();
				
				locationEntriesPanel.add(new LocationEntryPanel(panel.getCoordsLabel(), panel.getPosition(),
						panel.getLocationName(), "complete", panel.getButtonRef(), panel.getPlacesGUIref(), panel.getControlRef()), "wrap");
				undoList.add(obj);
				redoList.remove(redoList.size() - 1);
			}else if (obj.getAction().matches("delete")){
				
				if (this.getnoPanels(listOfSavedEntries) == 1){
					resizeFrame(3);
					first = true;
					
				}else{
					resizeFrame(2);
					// fix size
					while ((frame.getHeight() - leftPanel.getHeight()) < 53){
						frame.setSize(new Dimension(secondWidth, frame.getHeight() + 1));
					}
				}
				LocationEntryPanel panel = obj.getPanel();
				removeFromPanel(panel.getLocationName());
				undoList.add(obj);
				redoList.remove(redoList.size() - 1);
				

			}else if (obj.getAction().matches("edit")){
				LocationEntryPanel old_panel = obj.getPanel();
				PlaceUndoRedo obj2 = redoList.elementAt(redoList.size() - 2);
				LocationEntryPanel new_panel = obj2.getPanel();
				
				LocationEntryPanel next = new LocationEntryPanel(new_panel.getCoordsLabel(), new_panel.getPosition(),
						new_panel.getLocationName(), "complete", new_panel.getButtonRef(), new_panel.getPlacesGUIref(), new_panel.getControlRef());
				locationEntriesPanel.add(next, "wrap");
				removeFromPanel(old_panel.getLocationName());
				
				// WARNING: method remove from Panel Also RESIZES !
				
				// obj => old panel
				// obj2 => new panel
				undoList.add(obj);
				redoList.remove(redoList.size() - 1);
				undoList.add(obj2);
				redoList.remove(redoList.size() - 1);
				
				resizeFrame(2);
				
				while ((frame.getHeight() - leftPanel.getHeight()) < 20){
					frame.setSize(new Dimension(secondWidth, frame.getHeight() + 1));
				}
			}
			if (redoList.isEmpty()){
				redoButton.setEnabled(false);
			}else{
				redoButton.setEnabled(true);
			}
			if (undoList.isEmpty()){
				undoButton.setEnabled(false);
			}else{
				undoButton.setEnabled(true);
			}
			addLocationButton.setEnabled(true);
		}else if ("help".matches(e.getActionCommand())){
	    	if (!messFrame.isVisible()){
	    		messFrame.dispose();
	    		messFrame = new MessageFrame("<html>" + 
						"<p>This frame allows you to enter the locations <br>" +
						" that you have visited each day.<br><br>" +
						"You can add a location by pressing the <br>" +
						"<b>Add Location</b> button. Then, you are<br>" +
						" required to choose a unique name <br>" +
						" for the location and a set of coordinates<br>" +
						"that can be chosen by pressing the map button<br>" +
						"(the first button from the left)<br>" +
						" This will pop up a frame with a map where <br>" +
						"you can pick a coordinate for your<br>" +
						"location by simply clicking the mouse onto any <br>" +
						"spots until you are satisfied with your pick.<br><br>" +
						" You can save each location to the panel <br>" +
						"by pressing the <b>checkmark symbol</b><br>" +
						"You will not be able to submit a location<br>" +
						"with no coordinates or an empty/non unique <br>" +
						"location name.<br><br>" +
						" You can delete a location by pressing<br>" +
						"the <b>red x</b> button.<br><br>" +
						" You can edit an entry by pressing the <b> crayon button</b><br>" +
						"(the first button from the right).<br><br>" +
						" You can cancel the unsubmitted locations<br>" +
						"by press the <b>Cancel</b> button.<br><br>" +
						" Note that the locations are not saved into <br>" +
						"the data model if they are 'checkmarked'. <br>" +
						" You needs to press the <b> Ok</b> button<br>" +
						"in order to save the data entered in the panel.<br><br>" +
						" You can see the mapped locations of all the entries<br>" +
						"that are saved into the panel by pressing the<br>" +
						" <b>View Locations on Map</b> button.<br><br>" +
						"</p></html>");
			} else {
				messFrame.requestFocus();
				messFrame.toFront();
			}
	    }
	}
	
	public void setUndoRedo(Boolean undo_arg, Boolean redo_arg){
		undoButton.setEnabled(undo_arg);
		redoButton.setEnabled(redo_arg);
	}
	
	public void updateUndoRedo(){
		if (undoList.isEmpty()){
			undoButton.setEnabled(false);
		}else 
			undoButton.setEnabled(true);
		
		if (redoList.isEmpty()){
			redoButton.setEnabled(false);
		}else 
			redoButton.setEnabled(true);
	}
	
	public void resizeFrame(int state){
		// state 0 is fix frame
		// state 1 is adding first location
		// state 2 is adding a location (1 or more locs already exist)
		// state 3 is removing the only location that is there
		// state 4 is removing a location (2 or more locs already exist)
		switch (state) {
		case 0: 
			break;
		case 1: 
			
			// resize frame
			int i = firstWidth;
			
			while (i < secondWidth){			
				frame.setSize(new Dimension(i, 90));
				i = i + 3;
			}
			i = 90;
			while (i < 175){
				frame.setSize(new Dimension(secondWidth, i));
				i = i + 3;
			}
			
			leftPanel.remove(separator2);
			
			bottomButtonPanel.remove(okButton);
			bottomButtonPanel.remove(cancelButton);
			leftPanel.remove(bottomButtonPanel);
			
			leftPanel.add(separator, "span, wrap");
			leftPanel.add(locationEntriesPanel, "gaptop 6, span");
			leftPanel.add(separator2, "span, wrap");
			
			buttonGapLeft = "gapleft 243";
			bottomButtonPanel.add(okButton, buttonGapLeft);
			bottomButtonPanel.add(cancelButton);
			leftPanel.add(bottomButtonPanel, "span");
			
			break;
		case 2: 
			
			int j = frame.getHeight() + 30;
			i = frame.getHeight();
			while (i < j){
	
				frame.setSize(new Dimension(secondWidth, i));
				i = i + 3;
			}			
			break;
		case 3: 
    		i = frame.getHeight();
    		j = frame.getWidth();
    		int last_height = 0;

    		while (i > firstHeight){
    			frame.setSize(new Dimension(secondWidth, i));
    			i = i - 1;
    			last_height = i;
    		}
    		while (j > firstWidth){
    			frame.setSize(new Dimension(j, last_height));
    			j = j - 3;
    		}
			bottomButtonPanel.remove(okButton);
			bottomButtonPanel.remove(cancelButton);
			leftPanel.remove(bottomButtonPanel);
			
			leftPanel.remove(separator);
			leftPanel.remove(locationEntriesPanel);
			leftPanel.remove(separator2);
			
			leftPanel.add(separator2, "span");
			
			buttonGapLeft = "gapleft 243";
			bottomButtonPanel.add(okButton, buttonGapLeft);
			bottomButtonPanel.add(cancelButton);
			leftPanel.add(bottomButtonPanel, "span");
			
			break;
		case 4: 
			i = frame.getHeight();
			frame.setSize(new Dimension(secondWidth, i - 30));
			break;
		}
	}
	
}

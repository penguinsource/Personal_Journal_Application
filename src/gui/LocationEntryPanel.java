
package gui;

import gov.nasa.worldwind.geom.Position;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Controller;
import net.miginfocom.swing.MigLayout;

/**
 * This is the GUI class that creates a panel with 2 text labels (for location name and coordinates) and 4 buttons (map, save, delete, edit). Every time the user wants to enter a new location, an object of this type of class is instantiated. The user can get the coordinates (map) of the place chosen, save the current coordinates (save), delete the current instance of the object (delete) and edit the current instance of the object (edit)
 * @author  Mihai !
 */
public class LocationEntryPanel extends JPanel implements ActionListener, MouseListener, Cloneable{

	private static final long serialVersionUID = 1L;
	private Controller control;
	private JTextField LocationNameField;
	private JTextField CoordsField;
	private JButton getMapButton;
	private JButton saveButton;
	private JButton deleteButton;
	private JButton PGUIaddLocationButton;
	private JButton editButton;
	
	private LocationEntryPanel old_panel_clone;
	private PlaceFrame placesGUIref;
	private boolean isSaved;
	private boolean editing;
	private boolean editing_special;
	private Position position;
	
	private String typeEntry;
	private JButton addButtonRef;
	private PlacesEntryMapFrame map;
		
	public LocationEntryPanel(String coords_arg, Position pos_arg, String loc_name_arg,
			String type, JButton addLocationButton, PlaceFrame ref, Controller control_arg){
		

		addButtonRef = addLocationButton;
		typeEntry = type;
		control = control_arg;
		editing = false;
		isSaved = false;
		if (type.matches("new")){
			editing_special = true;
		}else 
			editing_special = false;
		addButtonRef.setEnabled(false);
		// 1 is 1 space. 0 is no space, push pushes buttons
		this.setLayout(new MigLayout("","", "0 [] 0"));
		this.setBackground(new Color(240,240,240));
		
		PGUIaddLocationButton = addLocationButton;
		placesGUIref = ref;
		
		// LocationNameField is enabled (as it is just init)
		LocationNameField = new JTextField("Location Name");
		LocationNameField.setMinimumSize(new Dimension(135, 25));
		LocationNameField.setMaximumSize(new Dimension(135, 25));
		LocationNameField.addMouseListener(this);
		
		CoordsField = new JTextField("no set location");
		CoordsField.setMinimumSize(new Dimension(90, 25));
		CoordsField.setMaximumSize(new Dimension(90, 25));
		CoordsField.setEnabled(false);
		
		
		// getMapButton is enabled (as it is just init)
		getMapButton = new JButton();
		getMapButton.setIcon(new ImageIcon(getResourceURL("/images/mapimages/mapicon2.png"),""));
		getMapButton.addActionListener(this);
		getMapButton.setActionCommand("getLocationMap");
		getMapButton.setMaximumSize(new Dimension(28, 26));
	
		saveButton = new JButton();
		saveButton.setIcon(new ImageIcon(getResourceURL("/images/mapimages/tick.png"),""));
		saveButton.addActionListener(this);
		saveButton.setActionCommand("saveButton");
		saveButton.setEnabled(true);
		saveButton.setMaximumSize(new Dimension(28, 26));

		
		deleteButton = new JButton();
		deleteButton.setIcon(new ImageIcon(getResourceURL("/images/mapimages/deleteicon2.png"),""));
		deleteButton.addActionListener(this);
		deleteButton.setActionCommand("deleteButton");
		deleteButton.setMaximumSize(new Dimension(28, 26));
		
		editButton = new JButton();
		editButton.setEnabled(false);
		editButton.setIcon(new ImageIcon(getResourceURL("/images/mapimages/Pencil_14.png"),""));
		editButton.addActionListener(this);
		editButton.setActionCommand("editButton");
		editButton.setMaximumSize(new Dimension(28, 26));
		
		
		LocationNameField.setForeground(new Color(181, 181, 181));
		this.add(LocationNameField);
		this.add(CoordsField);
		this.add(getMapButton);
		this.add(saveButton);
		this.add(deleteButton);
		this.add(editButton);
		
		if (type.matches("complete")){
			getMapButton.setEnabled(false);
			saveButton.setEnabled(false);
			LocationNameField.setText(loc_name_arg);
			LocationNameField.setEnabled(false);
			CoordsField.setText(coords_arg);
			editButton.setEnabled(true);
			setLocationName(loc_name_arg);
			setPosition(pos_arg);
			
			isSaved = true;
		}else{
			placesGUIref.disableButtons("delete");
			placesGUIref.disableButtons("edit");
			deleteButton.setEnabled(true);
			
		}
		
	}
	
    public URL getResourceURL(String path){
    	return this.getClass().getResource(path);
    }
	
	public String getLocationName(){
		if (LocationNameField != null){
			return LocationNameField.getText();
		}
		return "";
	}
	
	public void setLocationName(String loc_name_arg){
			LocationNameField.setText(loc_name_arg);
	}
	
	public Position getPosition(){
		if (position != null){
			return position;
		}else return null;
		
	}
	
	/**
	 * @param p
	 */
	public void setPosition(Position p){
		position = p;
	}
	
	public String getType(){
		return typeEntry;
	}
	
	public JButton getButtonRef(){
		return addButtonRef;
	}
	
	public PlaceFrame getPlacesGUIref(){
		return placesGUIref;
	}
	
	public Controller getControlRef(){
		return control;
	}
	
	public String getCoordsLabel(){
		Position pos = this.getPosition();
		String coords = String.format("%.1f", pos.getLatitude().getDegrees()) + ", " + String.format("%.1f", pos.getLongitude().getDegrees());
		return coords;
	}
	
    public void setCoords(String latitude, String longitude){
    	CoordsField.setText(latitude + ", " + longitude);
    }
    
    public boolean isitSaved(){
    	return isSaved;
    }
    
    public void disableButtons(String type){
    	if (type.matches("delete")){
    		deleteButton.setEnabled(false);
    	}else if (type.matches("edit")){
    		editButton.setEnabled(false);
    	}
    }
    
    public void enableEditButton(){
    	editButton.setEnabled(true);
    }
    
    public void enableDleteButton(){
    	deleteButton.setEnabled(true);
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("getLocationMap".matches(e.getActionCommand())){
			if (map == null){
				map = new PlacesEntryMapFrame("new", LocationNameField.getText(), placesGUIref, this, null, control);
			} else if (!map.getFrame().isVisible()){
				map = new PlacesEntryMapFrame("new", LocationNameField.getText(), placesGUIref, this, null, control);
			}
		}else if ("saveButton".matches(e.getActionCommand())){
			if (CoordsField.getText().matches("no set location")){
				JOptionPane.showMessageDialog(this, "Please select the coordinates of your location");
				return;	
			}
			if (placesGUIref.checkLocationName(LocationNameField.getText())){
				PGUIaddLocationButton.setEnabled(true);
				deleteButton.setEnabled(true);
				editButton.setEnabled(true);
				saveButton.setEnabled(false);
				LocationNameField.setEnabled(false);
				getMapButton.setEnabled(false);
				placesGUIref.disableEditMode();
				isSaved = true;
				if (editing){
					LocationEntryPanel new_panel_clone = (LocationEntryPanel) this.clone();
					placesGUIref.saveToUndo("edit", old_panel_clone);
					placesGUIref.saveToUndo("edit", new_panel_clone);
				}else{
					LocationEntryPanel panel_clone = this.cloneMe();
					placesGUIref.saveToUndo("add", panel_clone);
				}
				editing = false;
				editing_special = false;
			}else{
				JOptionPane.showMessageDialog(this, "The location name already exists or is empty.");
				return;	
			}
			placesGUIref.updateUndoRedo();
			addButtonRef.setEnabled(true);
			placesGUIref.enableAllEdits();
			placesGUIref.enableAllDeletes();
			
		}else if ("deleteButton".matches(e.getActionCommand())){
			placesGUIref.removeFromPanel(LocationNameField.getText());
			if (editing_special){
				addButtonRef.setEnabled(true);
			}else{
				
				LocationEntryPanel panel_clone = (LocationEntryPanel) this.clone();
				placesGUIref.saveToUndo("delete", panel_clone);
			
				placesGUIref.updateUndoRedo();
			}
		}else if ("editButton".matches(e.getActionCommand())){
			placesGUIref.setUndoRedo(false, false);
			isSaved = false;
			saveButton.setEnabled(true);
			LocationNameField.setEnabled(true);
			getMapButton.setEnabled(true);
			editButton.setEnabled(false);
			placesGUIref.enableEditMode();
			LocationNameField.setForeground(new Color(0, 0, 0));
			
			old_panel_clone = this.cloneMe();
			
			placesGUIref.disableButtons("delete");
			placesGUIref.disableButtons("edit");
			// enable buttons for this class !
			deleteButton.setEnabled(true);
			editing = true;
			editing_special = true;
			addButtonRef.setEnabled(false);
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource().equals(LocationNameField)){
			if (LocationNameField.getText().matches("Location Name") && LocationNameField.isEnabled()){
				LocationNameField.setText("");
				LocationNameField.setForeground(new Color(0, 0, 0));}
			else{}
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	public Object clone(){
		try{
			return (LocationEntryPanel) super.clone();
		}catch(CloneNotSupportedException e){
			return null;
		}
	}
		
	public LocationEntryPanel cloneMe(){
				
		return new LocationEntryPanel(this.getCoordsLabel(), this.getPosition(), LocationNameField.getText(),
				"complete", this.getButtonRef(), placesGUIref, control );
		
	}
	
}

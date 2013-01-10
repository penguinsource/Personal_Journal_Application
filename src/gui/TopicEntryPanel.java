
package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import controller.Controller;

import net.miginfocom.swing.MigLayout;

/**
 * This is the GUI class that creates a panel with 2 text labels (for location name and coordinates) and 4 buttons (map, save, delete, edit). Every time the user wants to enter a new location, an object of this type of class is instantiated. The user can get the coordinates (map) of the place chosen, save the current coordinates (save), delete the current instance of the object (delete) and edit the current instance of the object (edit)
 * @author  Mihai !
 */
public class TopicEntryPanel extends JPanel implements ActionListener, MouseListener{

	private static final long serialVersionUID = 1L;

	private JTextField topicField;
	private JButton deleteButton;

	

	private TopicFrame topicFrameRef;
	private boolean isSaved;

	
	
	public static UndoManager manager = new UndoManager();
    
	//private JPanel leftPanel; //for the textfield of location and 
	//private JPanel rightPanel;
	public TopicEntryPanel(String topic_content, String type_arg, Controller control_arg, TopicFrame topicFrameRef_arg){
		
		topicFrameRef =  topicFrameRef_arg;
		// 1 is 1 space. 0 is no space, push pushes buttons
		this.setLayout(new MigLayout("","", "0 [] 0"));
		this.setBackground(new Color(240,240,240));
		
		topicField = new JTextField();
		topicField.setMinimumSize(new Dimension(280, 25));
		topicField.setMaximumSize(new Dimension(280, 25));
		topicField.addMouseListener(this);
		topicField.setEnabled(true);
		
		//add an undoable edit listener
		topicField.getDocument().addUndoableEditListener(manager);

		
		deleteButton = new JButton();
		deleteButton.setEnabled(false);
		//deleteFromPanelButton.setIcon(new ImageIcon("images/bullet-delete.png",""));
		deleteButton.setIcon(new ImageIcon("images/mapimages/deleteicon2.png",""));
		deleteButton.addActionListener(this);
		deleteButton.setActionCommand("deleteButton");
		deleteButton.setMaximumSize(new Dimension(28, 26));
		deleteButton.setEnabled(true);
		//deleteFromPanelButton.setContentAreaFilled(false);
		topicField.setText(topic_content);
		//topicField.setForeground(new Color(181, 181, 181));
		this.add(topicField);
		this.add(deleteButton);
		
		
	}
	public void newManager(){
		manager = new UndoManager();
		
	}
	
	public void disableDelete(){
		deleteButton.setEnabled(false);
	}
	
	public void enableDelete(){
		deleteButton.setEnabled(true);
	}
	
	public void disableTextFields(){
		topicField.setEnabled(false);
	}
	
	public void enableTextFields(){
		topicField.setEnabled(true);
	}
	
	
	public String getTopicContent(){
		if (topicField != null){
			return topicField.getText();
		}
		return "";
	}
	
	public void setTopicContent(String topic_arg){
		topicField.setText(topic_arg);
	}
    
    public boolean isitSaved(){
    	return isSaved;
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("deleteButton".matches(e.getActionCommand())){
			topicFrameRef.removeFromPanel(this);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
	}
	
	public static UndoManager getManager(){
		return manager;
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
}


//Undo redo manager
class UndoManagerHelper {

	  public static Action getUndoAction(UndoManager manager, String label) {
	    return new UndoAction(manager, label);
	  }

	  public static Action getUndoAction(UndoManager manager) {
	    return new UndoAction(manager, "Undo");
	  }

	  public static Action getRedoAction(UndoManager manager, String label) {
	    return new RedoAction(manager, label);
	  }

	  public static Action getRedoAction(UndoManager manager) {
	    return new RedoAction(manager, "Redo");
	  }

	  public abstract static class UndoRedoAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		UndoManager undoManager = new UndoManager();

	    String errorMessage = "Cannot undo";

	    String errorTitle = "Undo Problem";

	    protected UndoRedoAction(UndoManager manager, String name) {
	      super(name);
	      undoManager = manager;
	    }

	    public void setErrorMessage(String newValue) {
	      errorMessage = newValue;
	    }

	    public void setErrorTitle(String newValue) {
	      errorTitle = newValue;
	    }

	    protected void showMessage(Object source) {
	      if (source instanceof Component) {
	        JOptionPane.showMessageDialog((Component) source, errorMessage,
	            errorTitle, JOptionPane.WARNING_MESSAGE);
	      } else {
	        System.err.println(errorMessage);
	      }
	    }
	  }

	  public static class UndoAction extends UndoRedoAction {
		private static final long serialVersionUID = 1L;

		public UndoAction(UndoManager manager, String name) {
	      super(manager, name);
	      setErrorMessage("Cannot undo");
	      setErrorTitle("Undo Problem");
	    }

	    public void actionPerformed(ActionEvent actionEvent) {
	      try {
	        undoManager.undo();
	      } catch (CannotUndoException cannotUndoException) {
	        showMessage(actionEvent.getSource());
	      }
	    }
	  }

	  public static class RedoAction extends UndoRedoAction {
		private static final long serialVersionUID = 1L;

		String errorMessage = "Cannot redo";

	    String errorTitle = "Redo Problem";

	    public RedoAction(UndoManager manager, String name) {
	      super(manager, name);
	      setErrorMessage("Cannot redo");
	      setErrorTitle("Redo Problem");
	    }

	    public void actionPerformed(ActionEvent actionEvent) {
	      try {
	        undoManager.redo();
	      } catch (CannotRedoException cannotRedoException) {
	        showMessage(actionEvent.getSource());
	      }
	    }
	  }

	}
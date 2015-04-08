package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import controller.Controller;
import net.miginfocom.swing.MigLayout;

/**
 * This is the GUI class for creating a new diary entry and viewing/editing other diary entries. 
 * When an user submits an entry, the title and content are checked and then added to the model
 * (unless the format is illegal). When an user views an entry, the data (title and content) for
 * that date is updated from the model.  The user can edit an entry if there is an existing entry 
 * in the model for that date. 
 * @author  Mihai !
 */
public class DiaryFrame implements ActionListener{

    private JFrame frame;
    private JTextArea bodyContent = new JTextArea(1,1);
    JTextField TitleTextField = new JTextField("Title");
    private Controller controller;
    private Date date_selected;
    private JLabel date_label;
	private SimpleDateFormat date_format = new SimpleDateFormat("MMMMMMMMM d, yyyy");
    
	private String SubmitButtonLabel = "Submit";
	private JButton SubmitButton;
	private String SubmitButtonAC = new String("SubmitButton");
	private JButton ClearButton;
	private String CancelButtonLabel = "Cancel";
	private JButton CancelButton;
	private String CancelButtonAC = new String("CancelButton");
	
	private Boolean undoTrue;
	
    private UndoManager undoManager;
    private UndoManager undoManagerTitle;
    
    private Action redoAction;
    private Action undoAction;
    private String frameName;
    
    private String type;
    
    private MessageFrame messFrame;
    
    public DiaryFrame(final String frameNameArg, Date selected_date, Controller controller_arg){
    	// set the local var date_selected to the selected_date of the calendar
    	date_selected = selected_date;
    	controller = controller_arg;
    	// Need to initiate clear button here as it will be enabled/disabled
    	// based on what view is active 
    	undoTrue = false;
    	type = frameNameArg;
    	frameName = frameNameArg;
    	messFrame = new MessageFrame("");
    	
    	if (frameName.matches("New Diary Entry")){ // NEW MODE
    		ClearButton = new JButton("Clear"); 
    		ClearButton.setActionCommand("ClearButton");
    		ClearButton.setToolTipText("Clear the contents of the body");
    		controller.disableMain();
    		undoTrue = true;
    	}
    	else if (frameName.matches("Edit Diary Entry")){ // EDIT MODE	
    	}
    	else if (frameName.matches("View Diary Entry")){ // VIEW MODE
    		bodyContent.setEditable(false);
    		TitleTextField.setEditable(false);
    		SubmitButtonLabel = "Edit Entry";
    		CancelButtonLabel = "Close View";
    		CancelButtonAC = "CancelEdit";
    		SubmitButtonAC = "GotoEditMode";
    		ClearButton = new JButton("Save"); 
    		ClearButton.setActionCommand("SaveButton");
    		ClearButton.setToolTipText("Save the changes to the entry");
    		ClearButton.setEnabled(false);
    	}
    	
        int minwidth = 305;
        int minheight = 460;
        
        // frame and main panel
        frame = new JFrame();
        frame.setSize(new Dimension(minwidth, minheight));
        frame.setMinimumSize(new Dimension(minwidth,minheight));
        frame.setResizable(false);
        JPanel mainPanel = new JPanel(new MigLayout("insets 5 5 0 0"));
        // when window is closed (with 'x'), re-enable mainGUI
        frame.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		    	  if (frameName.equals("View Diary Entry")){
		    		  controller.removeActiveView("diaryViewEdit");
		    	  }
		    	  controller.enableMain();
		      }
		      @SuppressWarnings("unused")
			public void windowOpening(WindowEvent e){
		    	  frame.requestFocus();
		    	  TitleTextField.requestFocus();
		    	  TitleTextField.setEnabled(true);
		      }
		});       
        
        //topPanel JPanel
        Font font1 = new Font("Tahoma", Font.PLAIN, 18);
        JPanel topPanel = new JPanel(new MigLayout("", "5[]push[]0"));
        topPanel.setMinimumSize(new Dimension(minwidth-15,0));
        String[] array = frameName.split(" ");
        array[1] = controller.getDiaryLabel();
        String name = array[0] + " " + array[1] + " " + array[2];
               
        JLabel title = new JLabel();
        
        FontMetrics fontMetrics = topPanel.getFontMetrics(font1);
        int width = fontMetrics.stringWidth(name);
        
        boolean tooLong = false;
        int maxWidth = 220;
		while (width > maxWidth){
			tooLong = true;
			name = name.substring(0,name.length() - 1);
			width = fontMetrics.stringWidth(name);
		}
		
		if (tooLong)
			name = name.substring(0,name.length() - 1) + "..";
        
		title.setText(name);
		
        title.setForeground(new Color(204, 204, 204));
        title.setFont(font1);
        topPanel.add(title);

        // add the logo label
        ImageIcon icon = new ImageIcon(getResourceURL("/images/logo.png"),"");
        
        
        Image img =  icon.getImage(); 
		Image newimg = img.getScaledInstance(40,40,  java.awt.Image.SCALE_SMOOTH);  
		icon = new ImageIcon(newimg);
        JLabel logo = new JLabel();
        logo.setIcon(icon);
        topPanel.add(logo, "gapleft 20");
        
        //bodyPanel JPanel
        JPanel bodyPanel = new JPanel (new MigLayout("", "0 [] 0", "0 [] 0"));
        TitleTextField.setMinimumSize(new Dimension(165, 20));
        TitleTextField.setEnabled(true);
        bodyPanel.add(TitleTextField);
        
        	// add the date label
		SimpleDateFormat date_format = new SimpleDateFormat("MMMMMMMMM d, yyyy");
        date_label = new JLabel();
        date_label.setText(date_format.format(selected_date).toString());
        date_label.setForeground(new Color(170, 170, 170));
        bodyPanel.add(date_label, "gapleft 5, span");
        
        JPanel bottomPanel = new JPanel(new MigLayout("", "0 [] 0 [] push [] 0 [] 0", "0 [] 0"));
        bottomPanel.setPreferredSize(new Dimension(285,32));
        // content area is in a JTextArea, which is enclosed 
        // into a scrollpanel
        bodyContent.setLineWrap(true);
        bodyContent.setWrapStyleWord(true);
        JScrollPane scrollBodyContent = new JScrollPane(bodyContent);
        scrollBodyContent.setMinimumSize(new Dimension(290, 300));
        bottomPanel.add(scrollBodyContent, "span");
                
        //bottom part of bodyPanel (separator and buttons)
        ImageIcon sepIcon = new ImageIcon(getResourceURL("/images/separator.PNG"));
        JLabel separator = new JLabel("");
        separator.setIcon(sepIcon);
        bottomPanel.add(separator, "span, wrap");
        
        SubmitButton = new JButton(SubmitButtonLabel);
        SubmitButton.setIcon(new ImageIcon(getResourceURL("/images/bullet_add.png")));
        SubmitButton.addActionListener(this);
        SubmitButton.setActionCommand(SubmitButtonAC);
        bottomPanel.add(SubmitButton);
        
        CancelButton = new JButton(CancelButtonLabel);
        CancelButton.setIcon(new ImageIcon(getResourceURL("/images/bullet-delete.png")));
        CancelButton.addActionListener(this);
        CancelButton.setActionCommand(CancelButtonAC);
        bottomPanel.add(CancelButton);
        
        JButton helpBtn = new JButton();
        helpBtn.setToolTipText("Help");
        helpBtn.setMaximumSize(new Dimension(30,30));
        helpBtn.setIcon(new ImageIcon(getResourceURL("/images/help4.png")));
        helpBtn.setActionCommand("help");
        helpBtn.addActionListener(this);
        bottomPanel.add(helpBtn);
        
        // all other declarations for the ClearButton have been made
        // earlier in this file
        ClearButton.addActionListener(this);
        ClearButton.setIcon(new ImageIcon(getResourceURL("/images/clear.png")));
        
        undoManager = new UndoManager();
         
        bodyContent.getDocument().addUndoableEditListener(
                new UndoableEditListener() {
                  public void undoableEditHappened(UndoableEditEvent e) {
                    undoManager.addEdit(e.getEdit());
                  }
        });
        
        undoManagerTitle = new UndoManager();
        TitleTextField.getDocument().addUndoableEditListener(
                new UndoableEditListener() {
                  public void undoableEditHappened(UndoableEditEvent e) {
                	  undoManagerTitle.addEdit(e.getEdit());
                  }
        });
        
        // set up the undo and redo actions (and keyboard strokes)
        this.setupUndoRedo();
        
        bottomPanel.add(ClearButton);
        //bodyPanel.add(new JButton("button 1"), "w 100%, span 3 1");
        
        // frame and main panel
        mainPanel.add(topPanel, "wrap");
        mainPanel.add(bodyPanel, "wrap");
        mainPanel.add(bottomPanel);
        
        frame.getContentPane().add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }     
    
    public URL getResourceURL(String path){
    	return this.getClass().getResource(path);
    }
    
    public void viewEmptyEntry(){
    	bodyContent.setBackground(new Color(245,245,245));
    	TitleTextField.setVisible(false);
    }
        
    public void updateView(String entry_title, String entry_content, Date current_date){
    	//update the date selected on the calendar
    	date_selected = current_date;
    	
    	//update the title, content and date
    	TitleTextField.setText(entry_title);
    	bodyContent.setText(entry_content);
    	date_label.setText(date_format.format(date_selected).toString());
    }
    
    public void disposeFrame(){
    	frame.dispose();
    }
    
    public void enableEditButton(){
    	SubmitButton.setEnabled(true);
    }
    
    public void disableEditButton(){
    	SubmitButton.setEnabled(false);
    }
    

    private void setupUndoRedo() {
    	    		
        undoAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
            	if (type.matches("New Diary Entry") || undoTrue){
                try {
                    undoManager.undo();
                  } catch (CannotRedoException cre) {
                  } catch (CannotUndoException cue){
                  	
                  }
            }
       		}
        };
        redoAction = new AbstractAction() {
  			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
            	if (type.matches("New Diary Entry") || undoTrue){
                try {
                    undoManager.redo();
                  } catch (CannotRedoException cre) {
                  }
            }
            }
        };
        
        Action undoAction2 = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
            	if (type.matches("New Diary Entry") || undoTrue){
                try {
                    undoManagerTitle.undo();
                  } catch (CannotRedoException cre) {
                  } catch (CannotUndoException cue){
                  	
                  }
            }
       		}
        };
        Action redoAction2 = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
            if (type.matches("New Diary Entry") || undoTrue){	
                try {
                    undoManagerTitle.redo();
                  } catch (CannotRedoException cre) {
                  }
            }
            }
        };
        
        bodyContent.getActionMap().put("undo", undoAction);
        bodyContent.getActionMap().put("redo", redoAction);
        
        TitleTextField.getActionMap().put("undo", undoAction2);
        TitleTextField.getActionMap().put("redo", redoAction2);
        
        InputMap[] inputMaps = new InputMap[] {
            bodyContent.getInputMap(JComponent.WHEN_FOCUSED),
            bodyContent.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT),
            bodyContent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW),
            
            TitleTextField.getInputMap(JComponent.WHEN_FOCUSED),
            TitleTextField.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT),
            TitleTextField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW),
        };
        
        for(InputMap i : inputMaps) {
            i.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "undo");
            i.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "redo");
        }
        
    	
    }
	
	@Override
    public void actionPerformed(ActionEvent e) {
   
    if ("ClearButton".matches(e.getActionCommand())){
    	bodyContent.setText("");
    }else if ("CancelButton".matches(e.getActionCommand())){
    	frame.dispose();
    	controller.enableMain();
    }else if ("SubmitButton".matches(e.getActionCommand())){
    	String title = TitleTextField.getText();
    	String content = bodyContent.getText();
    	
    	if (title.matches("")){
    		JOptionPane.showMessageDialog(frame, "There is no title entered !");
    		return;
    	}
    	if (content.matches("")){
    		JOptionPane.showMessageDialog(frame, "There is no content entered !");
    		return;
    	}

    	controller.addDiaryEntry(title, content, date_selected);
    	frame.dispose();
    	controller.enableMain();
    }else if ("GotoEditMode".matches(e.getActionCommand())){
    	ClearButton.setEnabled(true);
    	SubmitButton.setEnabled(false);
    	bodyContent.setEditable(true);
    	TitleTextField.setEditable(true);
    	undoManager.discardAllEdits();
    	undoManagerTitle.discardAllEdits();
    	undoTrue = true;
    	
    }else if ("SaveButton".matches(e.getActionCommand())){
    	if (!(controller.getDiaryEntry(date_selected) == null)){
    		controller.editDiaryEntry(TitleTextField.getText(), bodyContent.getText(), date_selected);
    	}
    	else{
    		controller.addDiaryEntry("Title", "There is no diary entry for this date.", date_selected);
    	}

    	ClearButton.setEnabled(false);
    	SubmitButton.setEnabled(true);
    	bodyContent.setEditable(false);
    	TitleTextField.setEditable(false);
    	undoTrue = false;
    }else if ("CancelEdit".matches(e.getActionCommand())){
      	frame.dispose();
    	controller.removeActiveView("diaryViewEdit");
    }else if ("help".matches(e.getActionCommand())){
    	if (!messFrame.isVisible()){
    		messFrame.dispose();
    		messFrame = new MessageFrame("<html>" + 
    							"<p>This frame allows the user to enter a diary <br>" +
    							" entry in their journal.<br><br>" +
    							"The user can simply add a title and some <br>" +
    							"content in the text areas and then click <b> Submit </b><br>" +
    							" in order to complete a diary entry. <br><br>" +
    							"Pressing the <b> Clear </b> button will automatically <br> " +
    							"clear all the text in the content area.<br><br>" +
    							"The <b> Cancel </b> button will cancel the current <br>" +
    							"unsubmitted changes of a " +
    							"diary entry.</p></html>");
		} else {
			messFrame.requestFocus();
			messFrame.toFront();
		}
    }
    
    }
	
}
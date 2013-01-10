package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Vector;


import javax.swing.*;
import javax.swing.undo.UndoManager;


import net.miginfocom.swing.MigLayout;

import controller.Controller;
import data.TopicEntry;

/**
 * This is the GUI class for entering,viewing and editing the topics entered by a user on a date. The user can enter any number of topics that they want to save for a specific day. bugs / others : - updating days in the calendar; - not being able to edit if the current day doesn't have an existing topics entry !  reasons: I wrote the code last night, but it didn't update due to some conflicting. I think this is ok right now. bugs -> when viewing a topic entry, then switching to a different day, then editing and submitting changes, it makes a new entry for the original day that you viewed so something needs to be changed in the update methods Bug found: I think I passed a wrong date in the situation you described. I fixed it. I hope it would be ok.
 * @author  Mihai !
 */
public class TopicFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JButton submit;
	private JButton cancel;
	private JButton edit;
	private JButton addButton;
	
	private JButton help;
	
	private JTextField field;
	
	private JPanel topPanel;
	private JPanel mainPanel;
	private JPanel topicEntriesPanel;
	private JPanel bottomPanel;
	private int previous;
	
    private int firstWidth;
    private int firstHeight;
    
    private JButton undo;
    private JButton redo;
    
    private TopicEntry topic_entry;
	
	Vector <LocationEntryPanel> listTopicEntries;
	
	private TopicEntryPanel topic_panel;
	
	private boolean first;
	private Controller control;
	private MessageFrame messageFrame;
	private UndoManager manager;
	
	private String mode = "";
	public TopicFrame(String type_arg, Date selected_date, Controller control_arg){
		//get the manager from TopicEntryPanel.java
	    manager = TopicEntryPanel.getManager();
	    manager.discardAllEdits();
		control = control_arg;
		
		mode = type_arg;
		
		first = true;
		// panels
		topPanel = new JPanel(new MigLayout("", "5 [] push [] 0", "0[]0"));
		mainPanel = new JPanel(new MigLayout("insets 0 0 0 0"));
		topicEntriesPanel = new JPanel(new MigLayout("insets 0 0 0 0","0 [][] 0"));
		bottomPanel = new JPanel(new MigLayout("insets 0 0 0 0",""));
		topPanel.setPreferredSize(new Dimension(300,5));
		
		messageFrame = new MessageFrame("");
	    
		//this = new JFrame("Topics");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.add(mainPanel);
		
		
		firstWidth = 335;
		firstHeight = 225;
		previous = firstHeight;
		this.setSize(new Dimension(firstWidth, firstHeight));
		this.setPreferredSize(new Dimension(firstWidth, firstHeight));
		
		field = new JTextField();
		field.setEnabled(true);
		//field.setPreferredSize(new Dimension(300,80));
		submit = new JButton("Submit");
		submit.setIcon(new ImageIcon("images/bullet_add.png"));
		submit.addActionListener(this);
		submit.setActionCommand("submit");
		
		cancel = new JButton("Cancel");
		cancel.setIcon(new ImageIcon("images/bullet-delete.png"));
		cancel.addActionListener(this);
		cancel.setActionCommand("cancel");
		
		edit = new JButton("Edit");
		edit.setEnabled(false);
		edit.addActionListener(this);
		edit.setActionCommand("edit");
		
		addButton = new JButton("");
		addButton.setIcon(new ImageIcon("images/bullet_add.png"));
		addButton.addActionListener(this);
		addButton.setActionCommand("addTopic");
		
		
		help = new JButton("");
		help.setIcon(new ImageIcon("images/help4.png"));
		help.addActionListener(this);
		help.setActionCommand("help");
		help.setToolTipText("Help");
		
		JLabel separator = new JLabel();
		separator.setIcon(new ImageIcon("images/separator3.PNG"));
		separator.setMaximumSize(new Dimension(315, 10));
		
		JLabel separator2 = new JLabel();
		separator2.setIcon(new ImageIcon("images/separator3.PNG"));
		separator2.setMaximumSize(new Dimension(315, 10));
		String kind = "";
		if (type_arg.matches("new")){
			kind = "Add new " + control.getTopicLabel();
		}else if (type_arg.matches("view")){
			kind = "View " + control.getTopicLabel() + "s";
		}
		
		JLabel title = new JLabel(kind);
		title.setForeground(new Color(181, 181, 181));
		
		topPanel.add(title);
		topPanel.add(help,"wrap");
		topPanel.add(separator2, "gapbottom 0, span, wrap");
		
		
		bottomPanel.add(addButton, "gapleft 7, wrap");
		bottomPanel.add(separator, "gapbottom 0, gapleft 6, gapright 6, wrap");
		bottomPanel.add(submit, "gapleft 10, gaptop 0, split 3");
		bottomPanel.add(cancel);
		
		//It does not add edit button.
		bottomPanel.add(edit,"gapleft 100");
		
		mainPanel.add(topPanel, "gaptop 10, wrap");
		mainPanel.add(topicEntriesPanel, "wrap");
		mainPanel.add(bottomPanel);

		
		
		
        this.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {

		    	    control.enableMain();
		    	    if(mode.matches("view"))
		    	    	control.removeActiveView("topic");
		      }
		}); 
        
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JToolBar toolbar = new JToolBar();
					undo = new JButton(UndoManagerHelper.getUndoAction(manager));
			undo.setIcon(new ImageIcon("images/undo2png"));
			toolbar.add(undo);
					redo = new JButton(UndoManagerHelper.getRedoAction(manager));
			redo.setIcon(new ImageIcon("images/redo2.png"));
			toolbar.add(redo);
			
		    
		    toolbar.setEnabled(false);
		    topicEntriesPanel.add(toolbar, "dock north");
		
		if (first && type_arg.matches("new")){
			topic_panel = new TopicEntryPanel(null, "new", control, this);
			
			topicEntriesPanel.add(topic_panel, "wrap");
			first = false;
		}else{

			undo.setEnabled(false);
			redo.setEnabled(false);
		    
		}
		

	    

		

    	if (type_arg.matches("new")){
    		control.disableMain();
    	}else if (type_arg.matches("view")){
    		//control.disableMain();
    		addButton.setEnabled(false);
    		
    		loadEntries();
    		disableDeleteButtons();
    		disableTextFields();
    	}
		
    	// check if there's only one entry panel. if there is, there 
    	Vector <TopicEntryPanel> oneEntry = getListTopicPanels();
    	if (oneEntry.size() == 1){
    		this.disableDeleteButtons();
    	}
    	
	}

   public void loadEntries(){
	    topic_entry = control.getTopicEntry(control.getCurrentDate());
	    
	    if(topic_entry != null){
	    	String topics = topic_entry.getTopic();
		    String w[] = topics.split("\\n");
	    	
	    	for (int i = 0; i < w.length; i++){
	    		if(i == w.length-1){
	    			increase(w[i],"view","last");
	    		}else{
	    			increase(w[i],"view","continue");
	    		}
	    	}
	    	previous = firstHeight;
	    }else{
	    	increase("","view","last");
	    }
    	this.validate();

    	
    }
	   
	public void increase(String s, String type, String state){
		int currentWidth = this.getWidth();
		
		if(state.matches("continue")){
			previous += 30;
		}

		if(state.matches("addtopic")){
			this.setSize(new Dimension(currentWidth, this.getHeight()+30));
		}else{
			this.setSize(new Dimension(currentWidth, previous));
		}
		
		this.enableDeleteButtons();

    	
		
		topic_panel = new TopicEntryPanel(s, type, control, this);
		topicEntriesPanel.add(topic_panel, "wrap");
		topicEntriesPanel.revalidate();
	}
	
    public void disposeFrame(){
    	this.dispose();
    }
    
    public void removeFromPanel(TopicEntryPanel topicpanel){
    	topicEntriesPanel.remove(topicpanel);
    	topicEntriesPanel.revalidate();
    	
    	Vector <TopicEntryPanel> oneEntry = getListTopicPanels();
    	if (oneEntry.size() == 1){
    		this.disableDeleteButtons();
    	}
    	
		int currentWidth = this.getWidth();
		int currentHeight = this.getHeight();
		
		while(this.getHeight() > (currentHeight - 30) ){
			this.setSize(new Dimension(currentWidth, this.getHeight() - 1));
		}
    }
    
    public void disableDeleteButtons(){
    	Vector<TopicEntryPanel> v = getListTopicPanels();

    	
    	for(int i=0;i<v.size();i++){

    		v.get(i).disableDelete();
    	}
    }
    
    public void enableDeleteButtons(){
    	Vector<TopicEntryPanel> v = getListTopicPanels();

    	
    	for(int i=0;i<v.size();i++){

    		v.get(i).enableDelete();
    	}
    }
    
    public void disableTextFields(){
    	Vector<TopicEntryPanel> v = getListTopicPanels();

    	
    	for(int i=0;i<v.size();i++){
    		
    		v.get(i).disableTextFields();
    	}
    }
    
    public void enableTextFields(){
    	Vector<TopicEntryPanel> v = getListTopicPanels();

    	
    	for(int i=0;i<v.size();i++){

    		v.get(i).enableTextFields();
    	}
    }
    
    public void resetPanel(){
    	Vector<TopicEntryPanel> v = getListTopicPanels();
    	
    	for(int i=0;i<v.size();i++){
    		removeFromPanel(v.get(i));
    	}
    	loadEntries();
    }
    
    public Vector<TopicEntryPanel> getListTopicPanels(){
    	Component[] list = topicEntriesPanel.getComponents();
    	Vector <TopicEntryPanel> TopicEntryPanelList = new Vector <TopicEntryPanel>();
    	for (int i = 1; i < list.length; i++){
    		TopicEntryPanel panel = (TopicEntryPanel) list[i];
    		TopicEntryPanelList.add(panel);
    	}
    	
    	return TopicEntryPanelList;
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		if("submit".matches(e.getActionCommand())) {
			
			Vector<TopicEntryPanel> tpanel = getListTopicPanels();
			String topics = "";
			
			for (int i = 0; i < tpanel.size(); i++){
				if(tpanel.get(i).getTopicContent().equals("")){
					JOptionPane.showMessageDialog(this, "Please enter one or more topics");
					return;	
				}
				

				topics = topics.concat(tpanel.get(i).getTopicContent()+"\n");
			}

			if(mode.matches("new")){
				control.addTopicsEntry(topics, control.getCurrentDate(), topics.split("\n").length);
			}else if(mode.matches("view")){
				control.editTopicsEntry(topics, control.getCurrentDate(), topics.split("\n").length);
				control.removeActiveView("topic");
			}
			
			disposeFrame();
			control.enableMain();
			
			
		}
		else if("cancel".matches(e.getActionCommand())){
			disposeFrame();
			control.enableMain();
			if(mode.matches("view"))
				control.removeActiveView("topic");

			
		}else if ("edit".matches(e.getActionCommand())){
			submit.setEnabled(true);
			addButton.setEnabled(true);
			edit.setEnabled(false);
			enableTextFields();
			enableDeleteButtons();
			undo.setEnabled(true);
			redo.setEnabled(true);
			manager.discardAllEdits();
		}else if ("addTopic".matches(e.getActionCommand())){
			increase("","new","addtopic");
		}else if ("help".matches(e.getActionCommand())){
			if (messageFrame.isVisible()){
				messageFrame.requestFocus();
				messageFrame.toFront();
			} else {
				messageFrame.updateLabel("<html>" +
					"Here you can add topics to your journal.<br><br>" +
					"If you want to add more than one topic, click on<br>" +
					"<b>Add</b> button below the topic entry field.<br><br>" +
					"<p>When you finish entering topics, you can click<br>" +
					"on <b>Submit</b> button to submit your topics.<br>" +
					"All the topics you submitted will be automatically put <br>"+
					"into the Tag Cloud.<br><br>" +
					"You can delete topics using the <b>Delete</b> button on<br>" +
					"the right of each topic entry field.<br><br>" +
					"To save the topics you entered to the journal,<br>"+
					"click on <b>Submit</b> button. You cannot submit empty topics.<br><br>" +
					"To cancel adding or viewing topics, just click on the <br> " +
					"<b>Cancel</b> button. All changes made will be lost.<br><br>" +
					"If you want to undo any changes made, click on the<br>"+
					"<b>Undo</b> button, the last change made will be reverted.<br><br>" +
					"In case you want to revert any change made by the<br>Undo"+
					"button. Click on <b>Redo</b>.</p></html>");
				messageFrame.setVisible(true);
			}
		}
	}
	public void checkdo(){
		if(manager.canRedo()){
			redo.setEnabled(true);
		}else{
			redo.setEnabled(false);
		}
		
		if(manager.canUndo()){
			undo.setEnabled(true);
			
		}else{
			undo.setEnabled(false);
		}
	}

	
	public void updateView(String topic1) {
		
		if(topic1 != ""){
			resetPanel();
			
		
			disableDeleteButtons();
			disableTextFields();
			submit.setEnabled(false);
			edit.setEnabled(true);
			



		}else {
			resetPanel();
			disableDeleteButtons();
			disableTextFields();
			submit.setEnabled(false);
			edit.setEnabled(false);

		}
		addButton.setEnabled(false);
		undo.setEnabled(false);
		redo.setEnabled(false);
	}
	
}



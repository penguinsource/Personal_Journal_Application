package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JOptionPane;


import net.miginfocom.swing.MigLayout;
import com.toedter.calendar.JCalendar;

import controller.Controller;

/**
 * <p>This is the main frame of the application. 
 * It initially loads the month view. When the user clicks on a day or the 
 * "today" button, a day view slides out. The orientation of this view can be changed.
 * From this view, the user can  create new entries as well as edit or delete existing entries. 
 * An updateView() method exists which update the day view with the data passed to it by the  
 * controller class.<br><br> 
 * @author  Mikus Lorencs
 */
public class MainGUI{
	private static JPanel dayPanel;
	private static JPanel bottomPanel;
	private static JFrame frame;
	private static JButton prefsBtn;
	private static JButton journalBtn;
	private static JButton moodChartBtn;
	private static JButton tagCloudBtn;
	private static JButton locationsBtn;
	private static JButton slideBtn;
	private static JButton undoBtn;
	private static JButton redoBtn;
	private static JButton helpBtn;
	private static boolean dayVisible = false;
	private static JLabel dayLabel;
	private static TopicFrame topicGuiInst;
	private static ImageFrame imageFrameInst;
	private static PlaceFrame placeGuiInst;
	private static PreferenceFrame prefFrame;
	private static Date selectedDate;
	private static Date nearestDay = null;
	private static JCalendar cal;
	private static Controller control;
	private static EntryPanel diaryEntryPanel;
	private static EntryPanel topicEntryPanel;
	private static EntryPanel imageEntryPanel;
	private static EntryPanel placeEntryPanel;
	private static EntryPanel moodEntryPanel;
	private static JPopupMenu entryPopup;	
	private static JMenuItem diaryItem;
	private static JMenuItem topicItem;
	private static JMenuItem imageItem;
	private static JMenuItem placeItem;
	private static JMenuItem moodItem;	
	private static boolean isHorizontal = true;
	private static JTextField searchField;
	//panel that holds the day view
	private static JPanel mainPanel;
	private static int type;
	private static String searchText = "";
	private static MessageFrame helpFrame;
	
	//loads emoticons which are later applied to the mood entry button
	ImageIcon mood_one = new ImageIcon("images/emoticons/emote0.png","Angry");
	ImageIcon mood_two = new ImageIcon("images/emoticons/emote1.png","Crying");
	ImageIcon mood_three = new ImageIcon("images/emoticons/emote2.png","Sad");
	ImageIcon mood_four = new ImageIcon("images/emoticons/emote3.png","Emotionless");
	ImageIcon mood_five = new ImageIcon("images/emoticons/emote4.png","Happy");
	ImageIcon mood_six = new ImageIcon("images/emoticons/emote5.png","Super Happy");	
	
	/**
	 * Initially only loads the month view.
	 * @param controller Passed to constructor so that it may be used to call functions in controller
	 * @param journalName name of the journal to display in the taskbar
	 */
	public MainGUI(Controller controller, String journalName){
		setLookAndFeel();
		control = controller;
		frame = new JFrame(journalName + " - Journal Application");
		frame.setPreferredSize(new Dimension(279,284));
		frame.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));		
		   
		addMonthView();
		dayVisible = false;
		
		helpFrame = new MessageFrame("");
		helpFrame.setVisible(false);
		
		//save to the journal file upon closing of the application
		frame.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		    	  control.saveToFile();
		      }
		});

		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	/**
	 * Instantiates a frame that prompts the user to enter the PIN provided from the authentication page
	 * @param type 
	 */
	public void showTwitterFrame(int type) {
		new TwitterFrame(control, type);
	}
	
	/**
	 * Loads the months view
	 */
	public static void addMonthView(){
		JPanel panel = new JPanel(new MigLayout("","5[]3[]push[]3[]3[]5","6 [] 6 [] 10 [] -8"));
		
		prefsBtn = new JButton();
		prefsBtn.setIcon(new ImageIcon("images/Settings-32.png")); 
		prefsBtn.setToolTipText("Preferences");
		prefsBtn.setMaximumSize(new Dimension(40,40));
		prefsBtn.setSelected(false);
		//loads the preference selection screen (with argument 1, specifying it's being called after journal creation)
		prefsBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				prefFrame = new PreferenceFrame("", 1, control, control.getOrder(), control.getReminders());
				frame.setEnabled(false);
				frame.setFocusable(false);
			}
		});
		
		journalBtn = new JButton(); 
		journalBtn.setIcon(new ImageIcon("images/manager2.png")); 
		journalBtn.setToolTipText("<html>Journal Manager<br>Click to close all open windows and<br>go to the Journal selection screen</html>");
		journalBtn.setMaximumSize(new Dimension(40,40));
		journalBtn.setSelected(false);
		journalBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				int result = JOptionPane.showConfirmDialog(frame, "Are you sure you wish to go back to Journal Manager?" +
						"\nYour changes to the journal will be saved, and all open windows will be closed.", null, JOptionPane.YES_NO_OPTION);
				if (result == 0){
					control.saveToFile();
					control.goToJournalManager();
				}
			}
		});
		
		moodChartBtn = new JButton();
		moodChartBtn.setIcon(new ImageIcon("images/AwesomeFace_icon.png")); 
		moodChartBtn.setToolTipText("<html>" + control.getMoodLabel() + " Chart<br>Chart that plots the " + control.getMoodLabel() + " entries</html>");
		moodChartBtn.setMaximumSize(new Dimension(40,40));
		moodChartBtn.setSelected(false);
		moodChartBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				control.viewMoodChart();
			}
		});
		
		tagCloudBtn = new JButton();
		tagCloudBtn.setIcon(new ImageIcon("images/mobile_me.png")); 
		tagCloudBtn.setMaximumSize(new Dimension(40,40));
		tagCloudBtn.setToolTipText("<html>Tag Cloud<br>Visual representation of the words in the " + control.getTopicLabel() + " entries</html>");
		tagCloudBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				control.viewTagCloud(cal.getDate());
			}
		});
		
		locationsBtn = new JButton();
		locationsBtn.setIcon(new ImageIcon("images/Google-Map-32.png")); 
		locationsBtn.setToolTipText("<html>" + control.getPlaceLabel() + " Map<br>Map of all the locations in the " + control.getPlaceLabel() + " entires </html>");
		locationsBtn.setMaximumSize(new Dimension(40,40));
		locationsBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (!control.isMapActive()){
					control.addActiveView("map");					
					new PlacesEntryMapFrame("viewAll", null, null, null, null, control);
				}
			}
		});
		
		//Calendar
		cal = new JCalendar();
		cal.setFont(new java.awt.Font("Dialog", 0, 10)); 
		cal.setMaximumSize(new Dimension(270, 190));
        cal.setWeekOfYearVisible(false); 
        selectedDate = cal.getDate();
        cal.getDayChooser().setRemDays(control.getRemDays(selectedDate));
        
                
        //adds a mouse adapter to every day button in the JCalendar so that the day
        //view slides out when the user presses any day (propertyChange didn't work because 
        //JCalendar didn't register a user clicking on an already selected day)
        Component[] compArray = cal.getDayChooser().getDayPanel().getComponents();
        for (int i = 0; i < compArray.length; i++){
        	compArray[i].addMouseListener(new MouseAdapter(){
    			public void mouseClicked(MouseEvent e) {
    				selectedDate = cal.getDate();
    				if (!dayVisible){	        	            	
    	            	dayVisible = true;
    	            	slideDay();	   
	            	}	        	                
	                control.dayChanged(selectedDate);
    			}
    		});
        }
        
        JPanel calBottomPanel = new JPanel(new MigLayout("", "0 [] push [] 0 [] 0", "0 [] 0"));
        calBottomPanel.setMinimumSize(new Dimension(265, 0));
        JButton todayBtn = new JButton("Today");
        todayBtn.setToolTipText("Click to go today's date");
        todayBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				selectedDate = new Date();
				cal.setDate(selectedDate);
				if (!dayVisible){	        	            	
	            	dayVisible = true;
	                slideDay();
	            }	        	                
	            control.dayChanged(selectedDate);
			}
		});
        
        helpBtn = new JButton();
        helpBtn.setToolTipText("Help");
        helpBtn.setMaximumSize(new Dimension(27,25));
        helpBtn.setIcon(new ImageIcon("images/help4.png"));
        helpBtn.setActionCommand("month");
        helpBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (e.getActionCommand().equals("month")){
					String helpText = "<html>" +
					 "<p>This is the month view of the application.<br>" +
					 "From here you can change the preferences,<br>" +
					 "go back to the Journal Manager, or open <br>" +
					 "the Mood Chart, Tag Cloud, and Locations <br>" +
					 "Map. </p><br><p>If you select a date on the calendar <br>" +
					 "or press the \"Today\" button, a day view <br>" +
					 "of the selected day will be displayed. Days <br>" +
					 "that are colored <b>blue</b> are days which have<br>" +
					 "incomplete entries as specified in the <br> " +
					 "\"reminders\" preference. Days that contain <br>" +
					 "search results will be colored <b>yellow</b>.</p>" +
					 "</html>";
					
					if (!helpFrame.isVisible()){
						helpFrame.dispose();
						helpFrame = new MessageFrame(helpText);
					} else {
						helpFrame.updateLabel(helpText);
						helpFrame.requestFocus();
						helpFrame.toFront();
					}
				} else if (e.getActionCommand().equals("day")){
					String helpText = "<html>" +
					 "<p><b>Month View</b><br>" +
					 "From here you can change the preferences, go back to the Journal<br>" +
					 "Manager, or open the Mood Chart, Tag Cloud, and Locations Map. </p><br>" +
					 "<p>If you select a date on the calendar or press the \"Today\" button,<br>" +
					 "a day view of the selected day will be displayed. Days that are <br>" +
					 "colored blue are days which have incomplete entries as specified<br>" +
					 "in the \"reminders\" preference. Days that contain search results<br>" +
					 "will be colored yellow.</p><br>" +
					 "<p>" +
					 "<b>Day View</b><br>" +
					 "The day view presents all the entries that exist on the selected<br>" +
					 "day, as well as reminders for incomplete entries. The reminders <br>" +
					 "are distinguished with a grey color. <br><br>" +
					 "The \"New Entry\" button allows you to create a new entry of any <br>" +
					 "type. Entries may also be created straight from reminders. <br>" +
					 "The \"undo\" and \"redo\" buttons will undo or redo any creation, <br>" +
					 "deletion, or edit of an entry.<br><br>" +
					 "The searchbar allows you to search for a non-empty string across <br>" +
					 "all entries, and you are taken to the nearest day with search <br>" +
					 "results. The \"orientation\" button in the bottom right changes the <br>" +
					 "position of the day view between horizontal and vertical." +
					 "</p>" +
					 "</html>";
					
					if (!helpFrame.isVisible()){
						helpFrame.dispose();
						helpFrame = new MessageFrame(helpText);
					} else {
						helpFrame.requestFocus();
						helpFrame.toFront();
						helpFrame.updateLabel(helpText);
					}
				}
			}
		});
        
        slideBtn = new JButton();
        slideBtn.setIcon(new ImageIcon("images/right_arrow.png")); 
        slideBtn.setActionCommand("slide right");
        slideBtn.setToolTipText("Expand the day view to the right");
        slideBtn.setMaximumSize(new Dimension(27,25));
        slideBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (e.getActionCommand().equals("slide right")){
					selectedDate = cal.getDate();	        	            	
		            dayVisible = true;
		            slideDay();		            	        	                
		            control.dayChanged(selectedDate);
				} else if (e.getActionCommand().equals("slide left")){
					selectedDate = cal.getDate();	        	            	
		            dayVisible = false;
		            slideIn();		            	        	                
				} else if (e.getActionCommand().equals("slide up")){
					selectedDate = cal.getDate();	        	            	
		            dayVisible = false;
		            slideUp();		            	        	                
				} else if (e.getActionCommand().equals("slide down")){
					selectedDate = cal.getDate();	        	            	
		            dayVisible = true;
		            slideDown();	
		            control.dayChanged(selectedDate);
				}
			}
		});
        
        calBottomPanel.add(todayBtn);        
        calBottomPanel.add(helpBtn);
        calBottomPanel.add(slideBtn);
        
        JSeparator horizSeparator = new JSeparator(JSeparator.VERTICAL);
        horizSeparator.setMinimumSize(new Dimension(0,263));  
				
        panel.add(prefsBtn);
        panel.add(journalBtn);
        panel.add(moodChartBtn);
        panel.add(tagCloudBtn);
        panel.add(locationsBtn, "wrap");
		panel.add(cal, "span, wrap");	
		panel.add(calBottomPanel, "span, wrap");

		frame.add(panel);
	}
	
	/**
	 * Adds a (horizontal or vertical) day view (after the frame has been resized)
	 * @param type Specifies horizontal or vertical positioning. 0 for horizontal, 1 for vertical.
	 */
	private static void addDayView(int typeArg) {
		type = typeArg;
		control.setOrientation(type);
		helpBtn.setActionCommand("day");
		
		if (type == 0){
			mainPanel = new JPanel(new MigLayout("", "0 [] 0 [] 0 [] 0", "5 [] 5 [] 5 [] 5 [] 0"));
			slideBtn.setActionCommand("slide left");
	        slideBtn.setToolTipText("Slide the day view in to the left");
			slideBtn.setIcon(new ImageIcon("images/left_arrow.png"));
		} else if (type == 1){
			mainPanel = new JPanel(new MigLayout("", "6 [] 0 [] 0 [] 0", "2 [] 5 [] 5 [] 5 [] 0"));
			slideBtn.setActionCommand("slide up");
	        slideBtn.setToolTipText("Slide the day view up");
			slideBtn.setIcon(new ImageIcon("images/up_arrow.png"));
		}
		
		dayPanel = new JPanel(new MigLayout("", "5 [] 5", "5 [] 5 [] 5 [] 5 [] 5 [] 0"));
		dayPanel.setBorder(new LineBorder(new Color(167, 172, 180), 1, true));
		dayPanel.setBackground(new Color(250,250,250));
		if (type == 0){
			dayPanel.setPreferredSize(new Dimension(277,168));
		} else if (type == 1){
			dayPanel.setPreferredSize(new Dimension(265,168));
		}
		
		final JButton newEntryBtn = new JButton("New Entry");
		newEntryBtn.setIcon(new ImageIcon("images/bullet_add.png"));
		newEntryBtn.setText("New Entry");
        newEntryBtn.setToolTipText("Click to select a type of entry to create");
		newEntryBtn.setHorizontalAlignment(SwingConstants.LEADING);
		newEntryBtn.setIconTextGap(4);
		newEntryBtn.setMaximumSize(new Dimension (118,28));
		newEntryBtn.setMargin(new Insets(0,0,0,0));
		
        //Create the popup menu for the new Entry button
		entryPopup = new JPopupMenu();
        
		//menu items in the popup menu that create entries of the respective entry types 
        diaryItem = new JMenuItem(new AbstractAction(control.getDiaryLabel() + " Entry") {
        	private static final long serialVersionUID = 1L;
            public void actionPerformed(ActionEvent e) {
            	new DiaryFrame("New Diary Entry", selectedDate, control);
            }
        });
        
        topicItem = new JMenuItem(new AbstractAction(control.getTopicLabel() + " Entry") {
        	private static final long serialVersionUID = 1L;
            public void actionPerformed(ActionEvent e) {
                topicGuiInst = new TopicFrame("new", selectedDate, control);
                frame.setEnabled(false);
        		frame.setFocusable(false);
            }
        });
        imageItem = new JMenuItem(new AbstractAction(control.getImageLabel() + " Entry") {
        	private static final long serialVersionUID = 1L;
            public void actionPerformed(ActionEvent e) {
            	setImageFrameInst(new ImageFrame("New Images Entry", selectedDate, control));
            }
        });
        placeItem = new JMenuItem(new AbstractAction(control.getPlaceLabel() + " Entry") {
        	private static final long serialVersionUID = 1L;
            public void actionPerformed(ActionEvent e) {
            	setPlaceGuiInst(new PlaceFrame("new", selectedDate, control));
            }
        });
        moodItem = new JMenuItem(new AbstractAction(control.getMoodLabel() + " Entry") {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
            	new MoodFrame(selectedDate, control, 0);
            	frame.setEnabled(false);
        		frame.setFocusable(false);
            }
        });
        entryPopup.add(diaryItem);
        entryPopup.add(topicItem);
        entryPopup.add(imageItem);
        entryPopup.add(placeItem);
        entryPopup.add(moodItem);
                       
        //shows the popup menu when the newEntry button is pressed
        newEntryBtn.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            	// reset popup menu, otherwise it gets stuck (doesn't hide on re-focus of mainGUI frame)
            	resetPopup();            		
            	entryPopup.show(e.getComponent(), newEntryBtn.getX() + 2, newEntryBtn.getY() + 27);

            }
        });
        
        //undo button
        undoBtn = new JButton();
        undoBtn.setIcon(new ImageIcon("images/undo2png"));
        undoBtn.setToolTipText("Undo");
        undoBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
            	control.mainUndo();
            }
        });
        
        //redo button
        redoBtn = new JButton();
        redoBtn.setIcon(new ImageIcon("images/redo2.png"));
        redoBtn.setToolTipText("Redo");
        redoBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
            	control.mainRedo();
            }
        });
        
        //panel to hold the top buttons in day view
        JPanel topPanel = new JPanel(new MigLayout("", "0 [] push [] 0 [] 0", "0 [] 0"));
        if (type == 0){
        	topPanel.setPreferredSize(new Dimension(277,0));
        } else {
        	topPanel.setPreferredSize(new Dimension(265,0));
        }
        
        topPanel.add(newEntryBtn);
        topPanel.add(undoBtn);
        topPanel.add(redoBtn);
        
		dayLabel = new JLabel();
		SimpleDateFormat sdf = new SimpleDateFormat("MMMMMMMMM d, yyyy");
		dayLabel.setText(sdf.format(selectedDate));	
		
		//previous day button changes the calendar to the previous day
		JButton prevDay = new JButton("<<");
		prevDay.setFont(new Font("Tahoma", 0, 8));
		prevDay.setMargin(new Insets(0,0,0,0));
		prevDay.setMaximumSize(new Dimension(30,30));
		prevDay.setMinimumSize(new Dimension(30,30));
        prevDay.setToolTipText("Go to the previous day");
		prevDay.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Date date = cal.getDate();
				date.setTime(date.getTime() - 1 * 24 * 60 * 60 * 1000);
				selectedDate = date;
				cal.setDate(selectedDate);
				control.dayChanged(selectedDate);
			}
		});
		
		//next day button changes the calendar to the next day
		JButton nextDay = new JButton(">>");
		nextDay.setFont(new Font("Tahoma", 0, 8));
		nextDay.setMargin(new Insets(0,0,0,0));
		nextDay.setMaximumSize(new Dimension(30,30));
		nextDay.setMinimumSize(new Dimension(30,30));
		nextDay.setToolTipText("Go to the next day");
		nextDay.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Date date = cal.getDate();
				date.setTime(date.getTime() + 1 * 24 * 60 * 60 * 1000);
				selectedDate = date;
				cal.setDate(selectedDate);
				control.dayChanged(selectedDate);
			}
		});
						
		//Panel to hold search bar and orientation button that changes orientation of the dayView
		bottomPanel = new JPanel(new MigLayout("", "0 [] 2 [] push []0", "0[]0"));
		if (type == 0){
			bottomPanel.setMinimumSize(new Dimension(277,20));		
			bottomPanel.setMinimumSize(new Dimension(277,20));
		} else {
			bottomPanel.setMinimumSize(new Dimension(267,20));		
			bottomPanel.setMinimumSize(new Dimension(267,20));
		}
		
		//bottomPanel.setBorder(new LineBorder(new Color(200,200,200), 1, true));
		searchField = new JTextField();
		if (type == 0){
			searchField.setMaximumSize(new Dimension(220,21));
		} else {
			searchField.setMaximumSize(new Dimension(210,21));
		}
		searchField.setMinimumSize(new Dimension(100,21));
		searchField.setBorder(new LineBorder(new Color(167, 172, 180), 1, true));
		searchField.getDocument().addDocumentListener(new SearchFieldListener());
		searchField.setText(searchText);
		
		//search button
		JButton searchButton = new JButton();
		searchButton.setMaximumSize(new Dimension(27,25));
		searchButton.setIcon(new ImageIcon("images/search3.png"));
		searchButton.setToolTipText("<html>Search all entries for the entered text<br>" +
				"If no results are found in the current month,<br>calendar will change " +
				"to a day with results<br>Clear the field to un-highlight the results on the calendar</html>");
		searchButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String searchText = searchField.getText();
				if (!searchText.equals("") || !searchText.equals(" ") || !searchText.equals("  ")){
					ArrayList<String> highDays = control.search(searchText);
					cal.getDayChooser().setHighDays(highDays);
					
					//if the currently selected month doesn't have any results
					//switch to the nearest month with results
					
					if(nearestDay != null){
						cal.setDate(nearestDay);
						control.dayChanged(cal.getDate());
						nearestDay = null;
					}
				}
				
			}
		});
		        
		//orientation button
		JButton orientBtn = new JButton();
		orientBtn.setIcon(new ImageIcon("images/1319343746_stock_rotate.png")); 
		orientBtn.setToolTipText("Switch the position of the day view (horizontal/vertical)");
		orientBtn.setMaximumSize(new Dimension(27,25));
		orientBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				searchText = searchField.getText();
				if (isHorizontal){
					frame.remove(mainPanel);
					horizToVert();
					
				} else {
					frame.remove(mainPanel);
					vertToHoriz();
				}
			}
		});
		
		bottomPanel.add(searchField);
		bottomPanel.add(searchButton);
		bottomPanel.add(orientBtn);
		
		//panel that contains the prev day button, next day button, and the date label
		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 0));
		centerPanel.add(prevDay);
		centerPanel.add(dayLabel);
		centerPanel.add(nextDay);
		
		mainPanel.add(topPanel, "wrap");
		mainPanel.add(centerPanel, "gaptop 5, span, center, wrap");
		mainPanel.add(dayPanel, "span, wrap");
		mainPanel.add(bottomPanel);
		
		frame.add(mainPanel);
	}
	
	/**
	 * Re-instantiates the popup menu and re-adds the menu items. This takes care of 
	 * a bug where if the popup menu loses focus (such as when another frame is loaded),
	 * when it is shown again, it doesn't hide when the user clicks away
	 */
	protected static void resetPopup() {
		entryPopup = new JPopupMenu();	
		entryPopup.add(diaryItem);
		entryPopup.add(topicItem);
		entryPopup.add(imageItem);
		entryPopup.add(placeItem);
		entryPopup.add(moodItem);
	}
	
	/**
	 * Resizes the frame to fit the horizontal day view, and adds the day view panel
	 */
	private static void slideDay() {
		int i = 279;
	    
	    while(i < 572){
	        i +=3;
	        frame.setSize(new Dimension(i,310));
	    }
	    isHorizontal = true;
	    addDayView(0);
	    frame.repaint();
	}

	/**
	 * Resizes the frame from horizontal view to only fit the month view and 
	 * removes the horizontal day panel
	 * @param panel the panel to remove from the frame
	 */
	private static void slideIn() {		
		int i = 572;
        
        while(i > 286){
            i -=3;
            frame.setSize(new Dimension(i,310));
        }
        
        frame.remove(mainPanel);
        slideBtn.setIcon(new ImageIcon("images/right_arrow.png"));
        slideBtn.setActionCommand("slide right");
        slideBtn.setToolTipText("Expand the day view to the right");
        helpBtn.setActionCommand("month");
        frame.repaint();
	}

	/**
	 * Resizes the frame from vertical view to only fit month view and removes
	 * the vertical day panel
	 * @param panel panel to remove from the frame
	 */
	protected static void slideUp() {		
		int i = 560;
        
        while (i > 310){
            i -=3;
            frame.setSize(new Dimension(284,i));
        }    
        frame.setSize(new Dimension(284,310));
        frame.remove(mainPanel);    
        slideBtn.setActionCommand("slide down");
        slideBtn.setIcon(new ImageIcon("images/down_arrow.png"));
        slideBtn.setToolTipText("Expand the day view below the calendar");
        helpBtn.setActionCommand("month");
        frame.repaint();
	}
	
	/**
	 * Resizes the main frame downwards to fit the day view
	 */
	protected static void slideDown() {		
		int i = 310;
        
        while (i < 583){
            i +=3;
            frame.setSize(new Dimension(284,i));
        }    
        frame.setSize(new Dimension(284,583));
        addDayView(1);
	    isHorizontal = false;
	    control.updateActiveViews(selectedDate,1); 
	    frame.repaint();
	}


	/**
	 * Resizes the frame from horizontal layout to vertical, and reloads the vertical panel
	 */
	private static void horizToVert() {	
		int x = 572;
		int y = 310;
	    
	    while(x > 286){
	        x -=3;
	        if (y < 583){
	        	y +=3;
	        }
	        frame.setSize(new Dimension(x,y));
	    }
	    frame.setSize(new Dimension(286,585));
	    addDayView(1);
	    isHorizontal = false;
	    control.updateActiveViews(selectedDate,1);
	    frame.repaint();
	}

	/**
	 * Resizes the frame from vertical layout to horizontal, and reloads the horizontal panel
	 */
	protected static void vertToHoriz() {
		int x = 286;
		int y = 583;
        
        while(x < 572){
            x +=3;
            if (y > 310){
            	y -=3;
            }
            frame.setSize(new Dimension(x,y));
        }
        addDayView(0);
        isHorizontal = true;
        control.updateActiveViews(selectedDate,1);
        frame.repaint();
	}

	/**
	 * Updates the day view with the data passed by controller
	 * @param date date to display on the label
	 * @param diaryTitle title of the diary (if "", the entry doesn't exist)
	 * @param topicCount count of topics in the entry (if 0, the entry doesn't exist)
	 * @param imageCount count of images in the entry (if 0, the entry doesn't exist)
	 * @param placeCount count of places in the entry (if 0, the entry doesn't exist)
	 * @param moodSmiley index of the emoticon in the mood entry (if 0, the entry doesn't exist)
	 * @param order arraylist of the order preferences
	 * @param reminders arraylist of the reminders preferences
	 * @param dismissed array of booleans representing which entries have been dismissed (false for not dismissed, in the order [diary, topic, image, place, mood])
	 * @param remDays list of strings (of dates) that have un-dismissed reminders
	 * @param enableRedo boolean to enable or disable redo button
	 * @param enableUndo boolean to enable or disable undo button
	 */
	public void updateView(final Date date, String diaryTitle, final int topicCount, 
			final int imageCount, final int placeCount, int moodSmiley, ArrayList<String> 
			order, ArrayList<String> reminders, boolean[] dismissed, ArrayList<String> 
			remDays, boolean enableUndo, boolean enableRedo, int activeViews) {

		//set date label
		if (date != null){
			SimpleDateFormat sdf = new SimpleDateFormat("MMMMMMMMM d, yyyy");
			dayLabel.setText(sdf.format(date));		
		}	
						
		//disable preferences if there are active views open
		if (activeViews > 0){
			prefsBtn.setEnabled(false);
			prefsBtn.setToolTipText("Preferences (disabled if other windows are open)");
		} else {
			prefsBtn.setEnabled(true);
			prefsBtn.setToolTipText("Preferences");
		}
		
		//color the days on calendar that have reminders
		setCalendarReminders(remDays);
		
		String moodId;
		if (moodSmiley == 0){
			moodId = "";
		} else {
			moodId = Integer.toString(moodSmiley);
		}
		
		//enable or disable undo/redo buttons
		setUndoEnabled(enableUndo);
		setRedoEnabled(enableRedo);
		
		//update "new entry" button names
		diaryItem.setText(control.getDiaryLabel() + " Entry");
		topicItem.setText(control.getTopicLabel() + " Entry");
		imageItem.setText(control.getImageLabel() + " Entry");
		placeItem.setText(control.getPlaceLabel() + " Entry");
		moodItem.setText(control.getMoodLabel() + " Entry");
		
		//update tooltips
		moodChartBtn.setToolTipText(control.getMoodLabel() + " Chart");
		locationsBtn.setToolTipText(control.getPlaceLabel() + " Map");
		
		
		//////////////////////////////////////////
		//update entry panels
		dayPanel.removeAll();
		dayPanel.revalidate();	
		dayPanel.repaint();
		
		// add entryPanels for existing entries for the day according to the order
		// specified in the "order" ArrayList (or reminder panels for non-existing entries
		// if they have reminders set for them)
		for(int i = 0; i < order.size(); i++){
			if (order.get(i).equals("diary")){
				//if a diary entry exists, display it as an entryPanel
				if (diaryTitle != null){
					diaryItem.setEnabled(false);
					diaryEntryPanel = new EntryPanel("diary", diaryTitle, false, control);					
					diaryEntryPanel.addListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							if (e.getActionCommand().equals("viewDiary")) {
			               		control.viewDiaryEntry(cal.getDate());
			               	}
							//if twitter isn't initiated, asks t he user if they would like to be taken
							//to the authentication website to enter in the PIN 
							else if(e.getActionCommand().equals("tweetDiary")) {
								if(!control.isTwitterReady()) {
									int input = JOptionPane.showConfirmDialog(frame,"You must first authorize this application to use \n" +
											"your Twitter account. Would you like to do so?", 
											"Authorize Twitter", JOptionPane.YES_NO_OPTION);
									if (input == 0)
										control.initiateTwitter(0);
								}
								else {
									//tweets the diary entry if twitter is initiated
									control.tweetDiaryEntry(selectedDate);
								}
			               	} 
							//confirms if the user wants to delete the entry, proceeds if user answers ys
							else if (e.getActionCommand().equals("deleteDiary")){
			               		int input = JOptionPane.showConfirmDialog(null,"Are you sure you wish" +
			            				" to delete diary entry '" + diaryEntryPanel.getTitle() + "' ?" , "Delete Diary Entry",
			            				JOptionPane.YES_NO_OPTION);
			               		if (input == 0)
			               			control.deleteDiaryEntry(cal.getDate());			               		
			               	}
						}
					});
					
					dayPanel.add(diaryEntryPanel, "wrap");			
				//if diary entry doesn't exist, and it hasn't been dismissed and a remidner exists for it,
				//display a reminder panel for it 
				} else if (diaryTitle == null && !dismissed[0] && control.hasReminder("diary")){
					String thing = control.getDiaryLabel().toLowerCase();
					String article = "a ";
					if (thing.substring(0,1).equals("a") || thing.substring(0,1).equals("e") || 
						thing.substring(0,1).equals("i") || thing.substring(0,1).equals("o") || thing.substring(0,1).equals("u")){
						article = "an ";
					}
					diaryEntryPanel = new EntryPanel("diary", "Please submit " + article + thing + " entry", true, control);
					diaryEntryPanel.addListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							if (e.getActionCommand().equals("createEntry")) {
								//create the diary entry from the reminder panel
								new DiaryFrame("New Diary Entry", selectedDate, control);	
			               	} 
							else if (e.getActionCommand().equals("dismissReminder")){
								//dismiss the entry
			               		control.dismissEntry("diary", date);
			               	}							
						}
					});
					dayPanel.add(diaryEntryPanel, "wrap");	
					diaryItem.setEnabled(true);	
				//if a diary entry doens't exist, and 
				} else { diaryItem.setEnabled(true);	}
				
			//the code for topic, image, and place is similar to the code above for diary
			} else if (order.get(i).equals("topic")){
				if (topicCount != 0){
					topicItem.setEnabled(false);
					topicEntryPanel = new EntryPanel("topic", Integer.toString(topicCount), false, control);					
					topicEntryPanel.addListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							if(e.getActionCommand().equals("tweetTopic")) {
								if(!control.isTwitterReady()) {
									int input = JOptionPane.showConfirmDialog(frame,"You must first authorize this application to use \n" +
											"your Twitter account. Would you like to do so?", 
											"Authorize Twitter", JOptionPane.YES_NO_OPTION);
									if (input == 0)
										control.initiateTwitter(0);
								}
								else {
									control.tweetTopicEntry(selectedDate);
								}
			               	} 
							else if (e.getActionCommand().equals("viewTopic")) {
			               		control.viewTopicEntry(cal.getDate());
			               	}
							else if (e.getActionCommand().equals("deleteTopic")) {
			               		int input = JOptionPane.showConfirmDialog(null,"Are you sure you wish" +
			            				" to delete topic entry with " + topicCount + " topics?" , "Delete Topic Entry",
			            				JOptionPane.YES_NO_OPTION);
			               		if (input == 0){
			               			control.deleteTopicEntry(cal.getDate());
			               		}
			               	}
						}
					});
					
					dayPanel.add(topicEntryPanel, "wrap");
				} 
				else if (topicCount == 0 && !dismissed[1] && control.hasReminder("topic")){
					String thing = control.getTopicLabel().toLowerCase();
					String article = "a ";
					if (thing.substring(0,1).equals("a") || thing.substring(0,1).equals("e") || 
						thing.substring(0,1).equals("i") || thing.substring(0,1).equals("o") || thing.substring(0,1).equals("u")){
						article = "an ";
					}
					topicEntryPanel = new EntryPanel("topic", "Please submit " + article + thing + " entry", true, control);
					topicEntryPanel.addListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							if (e.getActionCommand().equals("createEntry")) {
								topicGuiInst = new TopicFrame("new", selectedDate, control);	
			               	} 
							else if (e.getActionCommand().equals("dismissReminder")){
			               		control.dismissEntry("topic", date);
			               	}							
						}
					});
					dayPanel.add(topicEntryPanel, "wrap");	
					topicItem.setEnabled(true);
				} else {topicItem.setEnabled(true);}
				
			} else if (order.get(i).equals("image")){
				if (imageCount != 0){
					imageItem.setEnabled(false);
					imageEntryPanel = new EntryPanel("image", Integer.toString(imageCount), false, control);					
					imageEntryPanel.addListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							if(e.getActionCommand().equals("tweetImage")) {
								if(!control.isTwitterReady()) {
									int input = JOptionPane.showConfirmDialog(frame,"You must first authorize this application to use \n" +
											"your Twitter account. Would you like to do so?", 
											"Authorize Twitter", JOptionPane.YES_NO_OPTION);
									if (input == 0)
										control.initiateTwitter(0);
								}
								else {
									control.tweetImageEntry(selectedDate);
								}
			               	} 
							else if (e.getActionCommand().equals("viewImage")) {
								control.viewImageEntry(selectedDate);
			               	}
							else if (e.getActionCommand().equals("deleteImage")) {
			               		int input = JOptionPane.showConfirmDialog(null,"Are you sure you wish" +
			            				" to delete image entry with " + imageCount + " images?" , "Delete Image Entry",
			            				JOptionPane.YES_NO_OPTION);
			               		if (input == 0){
			               			control.deleteImageEntry(cal.getDate());
			               		}
			               	}
						}
					});
					
					dayPanel.add(imageEntryPanel, "wrap");
				} else if (imageCount == 0 && !dismissed[2] && control.hasReminder("image")){
					String thing = control.getImageLabel().toLowerCase();
					String article = "a ";
					if (thing.substring(0,1).equals("a") || thing.substring(0,1).equals("e") || 
						thing.substring(0,1).equals("i") || thing.substring(0,1).equals("o") || thing.substring(0,1).equals("u")){
						article = "an ";
					}
					imageEntryPanel = new EntryPanel("image", "Please submit " + article + thing + " entry", true, control);
					imageEntryPanel.addListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							if (e.getActionCommand().equals("createEntry")) {
								setImageFrameInst(new ImageFrame("New Images Entry", cal.getDate(), control));								
			               	} 
							else if (e.getActionCommand().equals("dismissReminder")){
			               		control.dismissEntry("image", date);
			               	}							
						}
					});
					dayPanel.add(imageEntryPanel, "wrap");	
					imageItem.setEnabled(true);
				} else {imageItem.setEnabled(true);}
			} else if (order.get(i).equals("place")){
				if (placeCount != 0){
					placeItem.setEnabled(false);
					placeEntryPanel = new EntryPanel("place", Integer.toString(placeCount), false, control);					
					placeEntryPanel.addListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							if(e.getActionCommand().equals("tweetPlace")) {
								if(!control.isTwitterReady()) {
									int input = JOptionPane.showConfirmDialog(frame,"You must first authorize this application to use \n" +
											"your Twitter account. Would you like to do so?", 
											"Authorize Twitter", JOptionPane.YES_NO_OPTION);
									if (input == 0)
										control.initiateTwitter(0);
								}
								else {
									control.tweetPlaceEntry(selectedDate);
								}
			               	} 
							else if (e.getActionCommand().equals("viewPlace")) {
								control.viewPlaceEntry(cal.getDate());
								//placeGuiInst = new PlaceFrame("view", , control);		
			               	}
							else if (e.getActionCommand().equals("deletePlace")) {
			               		int input = JOptionPane.showConfirmDialog(null,"Are you sure you wish" +
			            				" to delete location entry with " + placeCount + " locations?" , "Delete Location Entry",
			            				JOptionPane.YES_NO_OPTION);
			               		if (input == 0){
			               			control.deletePlaceEntry(cal.getDate());
			               		}
			               	}
						}
					});
					
					dayPanel.add(placeEntryPanel, "wrap");
				} else if (placeCount == 0 && !dismissed[3] && control.hasReminder("place")){
					String thing = control.getPlaceLabel().toLowerCase();
					String article = "a ";
					if (thing.substring(0,1).equals("a") || thing.substring(0,1).equals("e") || 
						thing.substring(0,1).equals("i") || thing.substring(0,1).equals("o") || thing.substring(0,1).equals("u")){
						article = "an ";
					}
					placeEntryPanel = new EntryPanel("place", "Please submit " + article + thing + " entry", true, control);
					placeEntryPanel.addListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							if (e.getActionCommand().equals("createEntry")) {
								setPlaceGuiInst(new PlaceFrame("new", cal.getDate(), control));								
			               	}
							else if (e.getActionCommand().equals("dismissReminder")){
			               		control.dismissEntry("place", date);
			               	}							
						}
					});
					dayPanel.add(placeEntryPanel, "wrap");	
					placeItem.setEnabled(true);
				} else {placeItem.setEnabled(true);}
			} else if (order.get(i).equals("mood")){
				if (!moodId.equals("")){
					moodItem.setEnabled(false);
					moodEntryPanel = new EntryPanel("mood", moodId, false, control);					
					moodEntryPanel.addListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							if(e.getActionCommand().equals("tweetMood")) {
								if(!control.isTwitterReady()) {
									int input = JOptionPane.showConfirmDialog(frame,"You must first authorize this application to use \n" +
											"your Twitter account. Would you like to do so?", 
											"Authorize Twitter", JOptionPane.YES_NO_OPTION);
									if (input == 0)
										control.initiateTwitter(0);
								}
								else {
									control.tweetMoodEntry(selectedDate);
								}
			               	} 
							else if (e.getActionCommand().equals("viewMood")) {
								control.viewMoodEntry();
			               	}
							else if (e.getActionCommand().equals("deleteMood")) {
			               		int input = JOptionPane.showConfirmDialog(null,"Are you sure you wish" +
			            				" to delete the mood entry?" , "Delete Mood Entry",
			            				JOptionPane.YES_NO_OPTION);
			               		if (input == 0){
			               			control.deleteMoodEntry(selectedDate);
			               		}
			               	}
						}
					});
					
					dayPanel.add(moodEntryPanel, "wrap");
				} else if (moodId.equals("") && !dismissed[4] && control.hasReminder("mood")){
					String thing = control.getMoodLabel().toLowerCase();
					String article = "a ";
					if (thing.substring(0,1).equals("a") || thing.substring(0,1).equals("e") || 
						thing.substring(0,1).equals("i") || thing.substring(0,1).equals("o") || thing.substring(0,1).equals("u")){
						article = "an ";
					}
					moodEntryPanel = new EntryPanel("mood", "Please submit " + article + thing + " entry", true, control);
					moodEntryPanel.addListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							if (e.getActionCommand().equals("createEntry")) {
								new MoodFrame(selectedDate, control, 0);	
								frame.setEnabled(false);
				        		frame.setFocusable(false);
			               	}
							else if (e.getActionCommand().equals("dismissReminder")){
			               		control.dismissEntry("mood", date);
			               	}							
						}
					});
					dayPanel.add(moodEntryPanel, "wrap");	
					moodItem.setEnabled(true);
				} else {moodItem.setEnabled(true);}
			}
		}
	}
	
	/**
	 * Sets the Jcalendar to highlight reminders
	 * @param remDays
	 */
	public void setCalendarReminders(ArrayList<String> remDays) {
		cal.getDayChooser().setRemDays(remDays);
	}
	
	/**
	 * @return instance of the topic entry GUI
	 */
	public static TopicFrame getTopicInst(){
		return topicGuiInst;
	}

	/**
	 * Disables this frame
	 */
	public void disableMainGUI(){
		frame.setEnabled(false);
		frame.setFocusable(false);
	}
	
	public static void setNotFocusable(){
		frame.setFocusable(false);
	}
	
	/**
	 * Re-enables this frame and attempts to bring it to front (some bugs on windows 7)
	 */
	public void enableMainGUI(){
		frame.setEnabled(true);
		frame.toFront();
		frame.requestFocus();
	}
		
	public void showTweetError() {
		JOptionPane.showMessageDialog(frame, "There is nothing to tweet");
	}
	
	/**
	 * @return date currently selected on the calendar
	 */
	public Date getDate() {
		selectedDate = cal.getDate();
		return cal.getDate();
	}
	
	/**
	 * Returns an emoticon Icon object based on the smiley index passed
	 * @param smiley_no the smiley index
	 * @return ImageIcon object
	 */
	public ImageIcon getMoodSmiley(int smiley_no){
		ImageIcon image = null;
		if (smiley_no == 1){
			image = mood_one;
		}else if(smiley_no == 2){
			image = mood_two; 	
		}else if(smiley_no == 3){
			image = mood_three;
		}else if(smiley_no == 4){
			image = mood_four;
		}else if(smiley_no == 5){
			image = mood_five;
		}else if(smiley_no == 6){
			image = mood_six;
		}
		return image;
	}
	
	/**
	 * @return true if the day view is currently shown, false if only month view is shown
	 */
	public boolean dayVisible(){
		return dayVisible;
	}

	private void setLookAndFeel() {
		UIManager.put("control", new Color(240,240,240));
		try {
	        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	            if ("Nimbus".equals(info.getName())) {
	                UIManager.setLookAndFeel(info.getClassName());
	                break;
	            }
	        }
	        Insets buttonInsets = new Insets(6,9,6,8);
	        UIManager.getLookAndFeelDefaults().put("Button.contentMargins", buttonInsets);
	
	    } catch (ClassNotFoundException ex) {
	        java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	    } catch (InstantiationException ex) {
	        java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	    } catch (IllegalAccessException ex) {
	        java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	        java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	    }
	}
	
	public void enablePreferenceFrame() {
		prefFrame.enableFrame();
	}
	
	/**
	 * Sets the nearest day (a search result) to the currently selected date
	 * @param date
	 */
	public void setNearestDay(Date date){
		nearestDay = date;
	}
	
	public void setCalendarDate(Date date){
		cal.setDate(date);
		control.dayChanged(date);
	}
	
	public void setUndoEnabled(boolean b){
		undoBtn.setEnabled(b);
	}
	
	public void setRedoEnabled(boolean b){
		redoBtn.setEnabled(b);
	}
	
	public void enablePrefs() {
		prefsBtn.setEnabled(true);
		prefsBtn.setToolTipText("Preferences");
	}
	
	public static ImageFrame getImageFrameInst() {
		return imageFrameInst;
	}

	public static void setImageFrameInst(ImageFrame imageFrameInst) {
		MainGUI.imageFrameInst = imageFrameInst;
	}

	public static PlaceFrame getPlaceGuiInst() {
		return placeGuiInst;
	}

	public static void setPlaceGuiInst(PlaceFrame placeGuiInst) {
		MainGUI.placeGuiInst = placeGuiInst;
	}

	/**
	 * A listener for the search field so that when the field is clear, the highlighted
	 * days on the calendar are cleared
	 * @author Mikus Lorencs
	 *
	 */
	static class SearchFieldListener implements DocumentListener {
 
	    public void insertUpdate(DocumentEvent e) {
	    	//clear the highlighted days if theres no text
	    	if (searchField.getText().equals("")){
	    		searchText = "";
	    		cal.getDayChooser().setHighDays(new ArrayList<String>());
	    	}
	    	
	    	//resize search field if theres too much text
	    	FontMetrics fontMetrics = bottomPanel.getFontMetrics(searchField.getFont());
	        int width = fontMetrics.stringWidth(searchField.getText());	

	        int maxWidth = 0;
	        switch(type){
		        case 0: maxWidth = 220; break;
		        case 1: maxWidth = 210; break;
	        }
	        if (width > searchField.getWidth() && searchField.getWidth() < maxWidth){
	        	searchField.setSize(new Dimension(searchField.getWidth()+10,21));
	        	bottomPanel.revalidate();
	        }
	    }
	    public void removeUpdate(DocumentEvent e) {
	    	if (searchField.getText().equals("")){
	    		searchText = "";
	    		cal.getDayChooser().setHighDays(new ArrayList<String>());
	    	}
	    	
	    	//resize search field back down
	    	FontMetrics fontMetrics = bottomPanel.getFontMetrics(searchField.getFont());
	        int width = fontMetrics.stringWidth(searchField.getText());	
	        int minWidth = 100;
	        if (width < searchField.getWidth() && searchField.getWidth() > minWidth){
	        	searchField.setSize(new Dimension(searchField.getWidth()-10,21));
	        	bottomPanel.revalidate();
	        }
	    }
	    public void changedUpdate(DocumentEvent e) {
	    	if (searchField.getText().equals("")){
	    		searchText = "";
	    		cal.getDayChooser().setHighDays(new ArrayList<String>());
	    	}
	    }
	}
	
}

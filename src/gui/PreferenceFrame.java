package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.Controller;

import net.miginfocom.swing.MigLayout;

/**
 * <p>Frame which allows the user to select their preferences for the Journal such
 *  as the order in which entries appear and which entries should trigger reminders. 
 *  This class is instantiated when the user creates a new journal, and when a  user 
 *  clicks on the preferences button in from within the application.  <p>If the user 
 *  goes to preference from within the application, he is also  presented with the option 
 *  to either authenticate a twitter account, or  re-authenticate to another account. <p>
 *  This class uses an extension of the AbstractListModel in order to display  the data in 
 *  the "order" and "reminders" lists. This class is defined at the  bottom of this file. 
 *
 * @author  Mikus Lorencs
 */
public class PreferenceFrame implements ActionListener{
	private static JFrame frame;
	private static String journalName;
	private static JList list1;
	private static JList list2;
	private static ListModel model1;
	private static ListModel model2;
	private static JTextField accName;
	private static JButton reAuthBtn;
	private static JButton removeAcc;
	private int type;
	private ArrayList<String> order;
	private ArrayList<String> reminders;
	private Controller control;
	private JTextField diaryField;
	private JTextField topicField;
	private JTextField imageField;
	private JTextField placeField;
	private JTextField moodField;
	private String diaryString = "Diary";
	private String topicString = "Topic";
	private String imageString = "Image";
	private String placeString = "Place";
	private String moodString = "Mood";
	private MessageFrame messFrame;

	
	/**
	 * @param text specifies the name of the Journal
	 * @param typeArg specifies the situation in which this dialog is called; 0 for upon creation of Journal, and 1 for later preference modification
	 * @param controller reference to controller class
	 * @param orderArg order list from Preferences object
	 * @param remArg reminders list from Preferences object
	 */
	public PreferenceFrame(String text, final int typeArg, Controller controller, ArrayList<String> orderArg, ArrayList<String> remArg) {
		this.control = controller;
		journalName = text;
		this.order = orderArg;
		this.reminders = remArg;
		this.type = typeArg;
		
		//setup message frame (for help)
		messFrame = new MessageFrame("");		
		
		frame = new JFrame("Preferences");
		frame.getContentPane().setLayout(new BorderLayout(5,0));
				
		//re-enable the source frame upon window close
		frame.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		    	  if (type == 0){
		    		  JournalSelectFrame.enableFrame();
		    	  } else {
		    		  control.enableMain();
		    	  }
		      }
		     
		});
		
		
		//when the user comes back from entering the PIN (or canceling), refresh
		//the account name and button name
		if (type == 1){
			frame.addWindowFocusListener(new WindowAdapter(){
				 public void windowGainedFocus(WindowEvent e){
			    	  accName.setText(control.getAccountName());
			    	  if (control.getAccountName().equals("no account setup")){
							reAuthBtn.setText("Authenticate");
							reAuthBtn.setToolTipText("Authenticate a Twitter account");
							removeAcc.setEnabled(false);
						} else {
							removeAcc.setEnabled(true);
						}
			      }
			});
		}
		
		final JTabbedPane tabbedPane = new JTabbedPane();
		
		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				Component p =   ((JTabbedPane) e.getSource()).getSelectedComponent();
                Dimension panelDim = p.getPreferredSize();

                tabbedPane.setPreferredSize(panelDim);

                frame.pack();
			}

        });

		////////////////////////////// 
		// start order/reminders pane
		JPanel panel = new JPanel(new MigLayout("", "", "3 [] 3"));
		panel.setPreferredSize(new Dimension(300,315));
		JLabel label1 = new JLabel("Specify the order in which entries will appear:");
		
		//initiate the models and set the lists
		list1 = new JList();
		model1 = new ListModel();
				
		if (type == 0){
			model1.addElement("Diary Entry");
			model1.addElement("Topic Entry");
			model1.addElement("Image Entry");
			model1.addElement("Place Entry");	
			model1.addElement("Mood Entry");
		} else if (type == 1){			
			
			if (!control.getDiaryLabel().equals(""))
				diaryString = control.getDiaryLabel();
			if (!control.getTopicLabel().equals(""))
				topicString = control.getTopicLabel();
			if (!control.getImageLabel().equals(""))	
				imageString = control.getImageLabel();
			if (!control.getPlaceLabel().equals(""))	
				placeString = control.getPlaceLabel();
			if (!control.getMoodLabel().equals(""))	
				moodString = control.getMoodLabel();
			
			for(int i = 0; i < order.size(); i++){
				if (order.get(i).equals("diary")){
					model1.addElement(diaryString + " Entry");
				} else if (order.get(i).equals("topic")){
					model1.addElement(topicString + " Entry");
				} else if (order.get(i).equals("image")){
					model1.addElement(imageString + " Entry");
				} else if (order.get(i).equals("place")){
					model1.addElement(placeString + " Entry");
				} else if (order.get(i).equals("mood")){
					model1.addElement(moodString + " Entry");
				}
			}
		}
		
		list1.setModel(model1);	
		JScrollPane scroll1 = new JScrollPane(list1);
		scroll1.setPreferredSize(new Dimension(135,101));
		scroll1.setMinimumSize(new Dimension(135,101));
		
		//button to move the selection up
		JButton moveUpBtn = new JButton("Move Up");
		moveUpBtn.setToolTipText("Move the selected entry up the order of entries");
		moveUpBtn.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e){
            	if(!list1.isSelectionEmpty()){
            		Object item = list1.getSelectedValue();
            		model1.moveUp(item);
            		int index = model1.getIndexOf(item);
            		updateList(1, model1);
            		list1.setSelectedIndex(index);
            	}
            	
            }
		});
		
		//button to move selection down
		JButton moveDownBtn = new JButton("Move Down");
		moveDownBtn.setToolTipText("Move the selected entry down the order of entries");
		moveDownBtn.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e){
            	if(!list1.isSelectionEmpty()){
            		Object item = list1.getSelectedValue();
            		model1.moveDown(item);
            		int index = model1.getIndexOf(item);
            		updateList(1, model1);
            		list1.setSelectedIndex(index);
            	}
            	
            }
		});
		
		//add to reminders button
		JButton addToRems = new JButton("Add to Reminders");
		addToRems.setToolTipText("Add the selected entry to a list of entries that will trigger reminders");
		addToRems.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e){
            	if(!list1.isSelectionEmpty() && !isInList(list1.getSelectedValue().toString(), model2)){
            		model2.addElement(list1.getSelectedValue().toString());
            		updateList(2,model2);
            		list1.setSelectedIndex(5);
            	}
            	
            }
		});
		
		JLabel label2 = new JLabel("Entries which will trigger reminders:");
		
		list2 = new JList();
		model2 = new ListModel();
				
		if (type == 1){
			for(int i = 0; i < reminders.size(); i++){
				if (reminders.get(i).equals("diary")){
					model2.addElement(diaryString + " Entry");
				} else if (reminders.get(i).equals("topic")){
					model2.addElement(topicString + " Entry");
				} else if (reminders.get(i).equals("image")){
					model2.addElement(imageString + " Entry");
				} else if (reminders.get(i).equals("place")){
					model2.addElement(placeString + " Entry");
				} else if (reminders.get(i).equals("mood")){
					model2.addElement(moodString + " Entry");
				}
			}
		}
		list2.setModel(model2);	
		JScrollPane scroll2 = new JScrollPane(list2);
		scroll2.setPreferredSize(new Dimension(135,101));		
		scroll2.setMinimumSize(new Dimension(135,101));	
		
		JButton removeFromRems = new JButton("Remove Reminder");
		removeFromRems.setToolTipText("Remove the selected entry from list of reminders");
		removeFromRems.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e){
            	if(!list2.isSelectionEmpty()){
            		model2.removeElement(list2.getSelectedValue().toString());            		
            		updateList(2,model2);
            	}
            	
            }
		});
		
		JPanel okCancelPanel = new JPanel(new MigLayout("", "0 [] 0 [] push [] 0"));
		okCancelPanel.setPreferredSize(new Dimension(300,29));
		JButton okBtn = new JButton("Ok");
		okBtn.addActionListener(this);
		okBtn.setActionCommand("ok");
		okBtn.setToolTipText("Save preferences");
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(this);
		cancelBtn.setActionCommand("cancel");
		cancelBtn.setToolTipText("Cancel changes");
		
		JButton helpBtn = new JButton();
        helpBtn.setToolTipText("Help");
        helpBtn.setMaximumSize(new Dimension(27,27));
        helpBtn.setIcon(new ImageIcon("images/help4.png"));
        helpBtn.setActionCommand("order help");	
        helpBtn.addActionListener(this);
		
		okCancelPanel.add(okBtn);		
		okCancelPanel.add(cancelBtn);
		okCancelPanel.add(helpBtn);
		
		panel.add(label1, "span, wrap");
		panel.add(scroll1, "span 1 3");
		panel.add(moveUpBtn, "wrap");
		panel.add(moveDownBtn, "wrap");
		panel.add(addToRems, "wrap");
		panel.add(label2, "span, wrap");
		panel.add(scroll2);
		panel.add(removeFromRems, "wrap");
		panel.add(okCancelPanel, "span, wrap");	
		
		tabbedPane.add("Order/Reminders", panel);
		// end order/reminders pane
		///////////////////////////
		
		///////// twitter authorization pane (displayed when user accesses preferences later on)
		if (type == 1){
			JPanel twitterPanel = new JPanel(new MigLayout());
			twitterPanel.setPreferredSize(new Dimension(300,170));
			
			JLabel twitterLabel = new JLabel("Twitter account:");
			accName = new JTextField();
			accName.setEnabled(false);
			accName.setText(control.getAccountName());
			accName.setPreferredSize(new Dimension(135,10));
			
			removeAcc = new JButton ("Remove Account");
			removeAcc.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e){
					control.removeTwitterAccount();	
					reAuthBtn.setText("Authenticate");
					reAuthBtn.setToolTipText("Authenticate a Twitter account");
					accName.setText(control.getAccountName());
					removeAcc.setEnabled(false);
				}
			});
			removeAcc.setToolTipText("Remove twitter account from the journal");
			
			reAuthBtn = new JButton ("Re-Authenticate");
			reAuthBtn.setToolTipText("Re-Authenticate to use another Twitter account");
			if (control.getAccountName().equals("no account setup")){
				reAuthBtn.setText("Authenticate");
				reAuthBtn.setToolTipText("Authenticate a Twitter account");
				removeAcc.setEnabled(false);
			}		
			reAuthBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e){
					control.initiateTwitter(1);
					frame.setEnabled(false);
					accName.setText(control.getAccountName());
					reAuthBtn.setText("Re-Authenticate");
					reAuthBtn.setToolTipText("Authenticate a Twitter account");
					removeAcc.setEnabled(true);
				}
			});
			
			JPanel okCancelPanel2 = new JPanel(new MigLayout("", "0 [] 0 [] push [] 0"));
			okCancelPanel2.setPreferredSize(new Dimension(300,29));
			JButton okBtn2 = new JButton("Ok");
			okBtn2.setActionCommand("ok");
			okBtn2.addActionListener(this);
			okBtn2.setToolTipText("Save preferences");
			
			JButton helpBtn2 = new JButton();
	        helpBtn2.setToolTipText("Help");
	        helpBtn2.setMaximumSize(new Dimension(27,27));
	        helpBtn2.setIcon(new ImageIcon("images/help4.png"));
	        helpBtn2.setActionCommand("twitter help");	
	        helpBtn2.addActionListener(this);
			
			JButton cancelBtn2 = new JButton("Cancel");
			cancelBtn2.setActionCommand("cancel");
			cancelBtn2.addActionListener(this);
			cancelBtn2.setToolTipText("Cancel changes");
			
			okCancelPanel2.add(okBtn2);
			okCancelPanel2.add(cancelBtn2);
			okCancelPanel2.add(helpBtn2);
			
			JSeparator sep = new JSeparator();
			sep.setPreferredSize(new Dimension(300,1));
			

			twitterPanel.add(twitterLabel, "wrap");
			twitterPanel.add(accName, "span, wrap");
			twitterPanel.add(reAuthBtn);
			twitterPanel.add(removeAcc, "cell 1 2, wrap");
			twitterPanel.add(sep, "span, wrap");
			twitterPanel.add(okCancelPanel2, "span, wrap");	
			tabbedPane.add("Twitter", twitterPanel);
		}
		// end twitter authorization pane
		
		/////////////label preferences pane
		JPanel labelPanel = new JPanel(new MigLayout("","","5[]5[]0[]0[]0[]0[]5[]0"));
		labelPanel.setPreferredSize(new Dimension(300,230));
		
		JLabel mainLabel = new JLabel("Add custom entry labels (optional):");
		JLabel diaryLabel = new JLabel("Diary");
		JLabel topicLabel = new JLabel("Topic");
		JLabel imageLabel = new JLabel("Image");
		JLabel placeLabel = new JLabel("Place");
		JLabel moodLabel = new JLabel("Mood");
		
		diaryField = new JTextField(13);
		topicField = new JTextField(13);
		imageField = new JTextField(13);
		placeField = new JTextField(13);
		moodField = new JTextField(13);
		
		if (type == 1){
				diaryField.setText(control.getDiaryLabel());
				topicField.setText(control.getTopicLabel());
				imageField.setText(control.getImageLabel());
				placeField.setText(control.getPlaceLabel());
				moodField.setText(control.getMoodLabel());
				
				if (diaryField.getText().equals(""))
					diaryField.setText("Diary");				
				if (topicField.getText().equals(""))
					topicField.setText("Topic");				
				if (imageField.getText().equals(""))
					imageField.setText("Image");				
				if (placeField.getText().equals(""))
					placeField.setText("Place");				
				if (moodField.getText().equals(""))
					moodField.setText("Mood");
				
		}
		
		JPanel okCancelPanel3 = new JPanel(new MigLayout("", "0 [] 0 [] push [] 0"));
		okCancelPanel3.setPreferredSize(new Dimension(300,29));
		JButton okBtn3 = new JButton("Ok");
		okBtn3.setActionCommand("ok");
		okBtn3.addActionListener(this);
		okBtn3.setToolTipText("Save preferences");
		
		JButton helpBtn3 = new JButton();
        helpBtn3.setToolTipText("Help");
        helpBtn3.setMaximumSize(new Dimension(27,27));
        helpBtn3.setIcon(new ImageIcon("images/help4.png"));
        helpBtn3.setActionCommand("label help");	
        helpBtn3.addActionListener(this);
		
		JButton cancelBtn3 = new JButton("Cancel");
		cancelBtn3.setActionCommand("cancel");
		cancelBtn3.addActionListener(this);
		cancelBtn3.setToolTipText("Cancel changes");
		
		okCancelPanel3.add(okBtn3);
		okCancelPanel3.add(cancelBtn3);
		okCancelPanel3.add(helpBtn3);
		
		labelPanel.add(mainLabel, "span, wrap");
		labelPanel.add(diaryLabel);
		labelPanel.add(diaryField, "wrap");
		labelPanel.add(topicLabel);
		labelPanel.add(topicField, "wrap");
		labelPanel.add(imageLabel);
		labelPanel.add(imageField, "wrap");
		labelPanel.add(placeLabel);
		labelPanel.add(placeField, "wrap");
		labelPanel.add(moodLabel);
		labelPanel.add(moodField, "wrap");
		labelPanel.add(okCancelPanel3, "span, wrap");
		
		tabbedPane.add("Labels",labelPanel);
		// end label preferences
		
		frame.add(tabbedPane);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
		
	public void enableFrame(){
		frame.setEnabled(true);
		frame.requestFocus();
		frame.toFront();
	}
	
	/**
	 * Produces an array list of strings from the data in the lists in order to 
	 * feed this array list to the model
	 * @param model list model from which to obtain the strings
	 * @return an array list representing the "order" or "reminder" preferences
	 */
	private ArrayList<String> getArrayList(ListModel model) {
		ArrayList<String> array = new ArrayList<String>();
		
		for(int i=0; i<model.getSize();i++){
			if (model.getElementAt(i).toString().equals(diaryString + " Entry")){	
					array.add("diary");
			} else if (model.getElementAt(i).toString().equals(topicString + " Entry")){
				array.add("topic");				
			} else if (model.getElementAt(i).toString().equals(imageString + " Entry")){
				array.add("image");				
			} else if (model.getElementAt(i).toString().equals(placeString + " Entry")){
				array.add("place");
			} else if (model.getElementAt(i).toString().equals(moodString + " Entry")){
				array.add("mood");
			}
		}		
				
		return array;
	}

	/**
	 * Updates the JList to its model (specified by k)
	 * @param k 1 for the "order" model, 2 for the "reminders" model
	 * @param model list model to set to
	 */
	public static void updateList(int k, ListModel model){
		ListModel temp = new ListModel();
		if (k == 1){
			list1.setModel(temp);
			list1.setModel(model);
		} else if (k == 2){
			list2.setModel(temp);
			list2.setModel(model);
		}
		
	}
	
	/**
	 * Checks if a string exists in the specified list model
	 * @param text string to compare
 	 * @param model list model to check in
	 * @return true if it exists in the list, false otherwise
	 */
	protected boolean isInList(String text, ListModel model) {
		boolean retVal = false;
		
		for(int i = 0; i< model.getSize(); i++){
			if (model.getElementAt(i).toString().equals(text)){
				retVal = true;
			}
		}
		
		return retVal;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("ok")){
			// if user is creating new journal, get the label fields, and create new journal
			if (type == 0){
				//check for duplicate names for labels
				int result = checkDuplicateLabels();
				if (result != 0){
					String thing = "";
					switch (result){
						case 1: thing = "diary"; break;
						case 2: thing = "topic"; break;
						case 3: thing = "image"; break;
						case 4: thing = "place"; break;
					}
					JOptionPane.showMessageDialog(frame, "The " + thing + " label is a duplicate. Please rename it.");
					return;					
				}
				try {
					String diaryStr = "Diary";
					String topicStr = "Topic";
					String imageStr = "Image";
					String placeStr = "Place";
					String moodStr = "Mood";
					
					// check for blank labels
					if (!diaryField.getText().equals("")){
						diaryStr = diaryField.getText();
					}
					if (!topicField.getText().equals("")){
						topicStr = topicField.getText();
					}
					if (!imageField.getText().equals("")){
						imageStr = imageField.getText();
					}
					if (!placeField.getText().equals("")){
						placeStr = placeField.getText();
					}
					if (!moodField.getText().equals("")){
						moodStr = moodField.getText();
					}
					
					//add journal with the prefs
					JournalSelectFrame.addJournal(journalName, getArrayList(model1), getArrayList(model2), 
							diaryStr, topicStr, imageStr, 
							placeStr, moodStr);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
				JournalSelectFrame.enableFrame();
			//if user is updating, grab the labels, verify them, and update journal with the prefs
			} else if (type == 1){
				int result = checkDuplicateLabels();
				if (result != 0){
					String thing = "";
					switch (result){
						case 1: thing = "diary"; break;
						case 2: thing = "topic"; break;
						case 3: thing = "image"; break;
						case 4: thing = "place"; break;
					}
					JOptionPane.showMessageDialog(frame, "The " + thing + " label is a duplicate. Please rename it.");
					return;
				}
				
				String resStr = checkFieldLength();
				if (resStr != null){
					JOptionPane.showMessageDialog(frame, "The " + resStr + " label is more than 20 chars. Please shorten it.");
					return;
				}
				
				frame.dispose();
				control.enableMain();
				
				String diaryStr = "Diary";
				String topicStr = "Topic";
				String imageStr = "Image";
				String placeStr = "Place";
				String moodStr = "Mood";
				
				if (!diaryField.getText().equals("")){
					diaryStr = diaryField.getText();
				}
				if (!topicField.getText().equals("")){
					topicStr = topicField.getText();
				}
				if (!imageField.getText().equals("")){
					imageStr = imageField.getText();
				}
				if (!placeField.getText().equals("")){
					placeStr = placeField.getText();
				}
				if (!moodField.getText().equals("")){
					moodStr = moodField.getText();
				}
				
				control.updatePrefs(getArrayList(model1), getArrayList(model2), diaryStr, 
						topicStr, imageStr, placeStr, moodStr);
			}
		} else if (e.getActionCommand().equals("cancel")){
			frame.dispose();
			if (type == 0){
				JournalSelectFrame.enableFrame();
			} else if (type == 1) {
				control.enableMain();
				control.revertTwitterAccount();
			}
		} else if (e.getActionCommand().equals("order help")){
			String helpText = "<html>" +
					"This tab allows you to specify the \"Order\" and <br>" +
					"\"Reminders\" preferences. The <b>Move Up</b> and <br>" +
					"<b>Move Down</b> button will move the selected entry <br>" +
					"type up or down the list, which would change its <br>" +
					"order in the day view.<br><br> " +
					"The <b>Add to Reminders</b> button adds the selected <br>" +
					"entry type to the list of reminders below. The entry <br>" +
					"types in this list will trigger reminders in the day <br>" +
					"view as well as the calendar view.<br><br>" +
					"The <b>Remove Reminder</b> button removes the selected<br>" +
					"entry from the list of reminders." +
					"</html>";
			if (!messFrame.isVisible()){
				messFrame.dispose();
				messFrame = new MessageFrame(helpText);
			} else {
				messFrame.updateLabel(helpText);
				messFrame.requestFocus();
				messFrame.toFront();
			}
		} else if (e.getActionCommand().equals("twitter help")){
			String helpText = "<html>" +
					"This tab allows you to manage the twitter account that is <br>" +
					"authorized to be used by this application. The display <br>" +
					"shows the username of the account that is currently <br>" +
					"authorized.<br><br>" +
					"If no account is set up, you may press <b>Authenticate</b> <br>" +
					"which will open a browser prompting you to enter your account<br>" +
					"information and a PIN number. <br><br>" +
					"If an account is set up, you may <b>Re-Authenticate</b> with<br>" +
					"a different account, or press <b>Remove Account</b> to remove<br>" +
					"remove the link between the application and your Twitter account." +
					"</html>";
			if (!messFrame.isVisible()){
				messFrame.dispose();
				messFrame = new MessageFrame(helpText);
			} else {
				messFrame.updateLabel(helpText);
				messFrame.requestFocus();
				messFrame.toFront();
			}
		} else if (e.getActionCommand().equals("label help")){
			String helpText = "<html>" +
					"Here you can add custom labels to all the entry types. <br>" +
					"Changing a label will replace any reference to that entry <br>" +
					"with the custom label (of up to 20 characters). Leaving <br>" +
					"the label blank or clearing it will cause the application<br>" +
					"to revert back to the default labels." +
					"</html>";
			if (!messFrame.isVisible()){
				messFrame.dispose();
				messFrame = new MessageFrame(helpText);
			} else {
				messFrame.updateLabel(helpText);
				messFrame.requestFocus();
				messFrame.toFront();
			}
		}
	}

	/**
	 * Checks length of labels to be less than 20
	 * @return string of the entry type that is more than 20 chars (first one it finds)
	 */
	private String checkFieldLength() {
		if (diaryField.getText().length() > 20){
			return "diary";
		}
		if (topicField.getText().length() > 20){
			return "topic";
		}
		if (imageField.getText().length() > 20){
			return "image";
		}
		if (placeField.getText().length() > 20){
			return "place";
		}
		if (moodField.getText().length() > 20){
			return "mood";
		}
		return null;
	}

	/**
	 * Check for duplicate labels
	 * @return integer to specify which label is a duplicate
	 */
	private int checkDuplicateLabels() {
		String diary = diaryField.getText();
		String topic = topicField.getText();
		String image = imageField.getText();
		String place = placeField.getText();
		String mood = moodField.getText();
		
		if (!diary.equals("") && (diary.equals(topic) || diary.equals(image) || diary.equals(place) || diary.equals(mood)))
			return 1;
		if (!topic.equals("") && (topic.equals(image) || topic.equals(place) || topic.equals(mood)))
			return 2;
		if (!image.equals("") && (image.equals(place) || image.equals(mood)))
			return 3;
		if (!place.equals("") && (place.equals(mood)))
			return 4;
		
		return 0;
	}

}

/**
 * Abstract List Model that manages the order and reminders lists.
 * @author  Mikus Lorencs
 */
class ListModel extends AbstractListModel<Object>{
	private static final long serialVersionUID = 1L;
	/**
	 */
	private List<String> list = new ArrayList<String>();

    public ListModel(){
    	Collections.sort(list);
    }

    @Override
    public int getSize() {
    return list.size();
    }

    @Override
    public Object getElementAt(int index) {
    return list.get(index);
    }
    
    public int getIndexOf(Object element) {
        for (int i = 0; i< getSize(); i++){
        	if (getElementAt(i).equals(element.toString())){
        		return i;
        	}
        }
        
        // wont return this
        return 0;
    }
    
    public void addElement(String element){
    	list.add(element);
    }
    
    public void removeElement(Object element){
    	list.remove(element);
    }
    
    /**
     * Moves an element up in the list
     * @param element element to move
     */
    public void moveUp(Object element){
    	int index = getIndexOf(element);
    	if(index > 0){
    		list.remove(element);    	
    		list.add(index - 1, element.toString());
    	}
    }
    
    /**
     * Moves an element down in the list
     * @param element element to move
     */
    public void moveDown(Object element){
    	int index = getIndexOf(element);
    	if(index < 4){
	    	list.remove(element);
	    	list.add(index + 1, element.toString());
    	}
    }
    
    /**
	 * @return
	 */
    public ArrayList<String> getList(){
    	return (ArrayList<String>) list;
    }
}
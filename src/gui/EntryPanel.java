
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.border.LineBorder;

import controller.Controller;
import net.miginfocom.swing.MigLayout;

/**
 * <p>EntryPanel is the panel that is displayed in the Day View of a particular day
 * and represents an existing entry, or a reminder for a non-existing entry.
 * @author Mikus Lorencs
 */
public class EntryPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton tweet;
	private JButton viewEdit;
	private JButton delete;
	private JButton addEntry;
	private JButton dismissRem;
	private String type;
	private String setTitle = null;
	private boolean reminder;
	private Controller control;
	private int orientation;
	/**
	 * Constructs a panel that represents the specified entry. If the panel is an
	 * existing entry, the user may tweet it, edit it, or delete it. If the panel is a reminder,
	 * a grey color scheme is used, and 2 buttons are available to the user: create entry, 
	 * and dismiss reminder.
	 * @param type specifies which type of entry this panel is for 
	 * @param title specifies the title that will be displayed on the panel
	 * @param reminderarg specifies if this panel is a reminder panel or the actual entry panel 
	 * @param controlArg instance of the controller
	 */
	public EntryPanel(String type, String title, boolean reminderarg, Controller controlArg){
		this.setLayout(new MigLayout("filly", "1 [][] push []0[]0[] 1", "1 [] 1"));
		this.setPreferredSize(new Dimension(265,27));
		this.setMaximumSize(new Dimension(265,27));
		this.type = type;
		this.reminder = reminderarg;
		this.control = controlArg;
		this.orientation = control.getOrientation();
		
		//set the color scheme
		if (reminder){
			this.setBorder(new LineBorder(new Color(200,200,200), 1, true));
			this.setBackground(new Color(230,230,230));	
		} else {
			this.setBorder(new LineBorder(new Color(167+30, 172+30, 180+30), 1, true));
			this.setBackground(new Color(229, 237, 240));		
		}
		
		//set the icon of the panel
		JLabel icon = new JLabel();
		if (type.equals("diary")){
			if (reminder){
				icon.setIcon(new ImageIcon(getResourceURL("/images/Book-grey.png")));
			} else {
				icon.setIcon(new ImageIcon(getResourceURL("/images/Book.png")));
			}
		} else if (type.equals("image")){
			if (reminder){
				icon.setIcon(new ImageIcon(getResourceURL("/images/picture-grey.png")));
			} else {
				icon.setIcon(new ImageIcon(getResourceURL("/images/picture.png")));
			}
		} else if (type.equals("topic")){
			if (reminder){
				icon.setIcon(new ImageIcon(getResourceURL("/images/topic-grey.png")));
			} else {
				icon.setIcon(new ImageIcon(getResourceURL("/images/topic.png")));
			}
		} else if (type.equals("place")){
			if (reminder){
				icon.setIcon(new ImageIcon(getResourceURL("/images/Map-grey.png")));
			} else {
				icon.setIcon(new ImageIcon(getResourceURL("/images/Map.png")));
			}
		} else if (type.equals("mood")){
			if (reminder){
				icon.setIcon(new ImageIcon(getResourceURL("/images/smiley-entry-grey.png")));
			} else {
				icon.setIcon(new ImageIcon(getResourceURL("/images/emoticons/emote" + (Integer.parseInt(title) - 1) + "-entry.png")));
			}
		}
		
		//sets the title of the panel
		JLabel titleLabel = null;
		setTitle = title;
		if (!reminder){
			if (type.equals("diary")){
				setTitle = title;
			} else if (type.equals("topic")){
				setTitle = control.getTopicLabel() + " entry with " + title + " " + control.getTopicLabel().toLowerCase();
				if (Integer.parseInt(title) > 1){
					setTitle = setTitle + "s";
				}
	
			} else if (type.equals("image")){
				setTitle = control.getImageLabel() + " entry with " + title + " " + control.getImageLabel().toLowerCase();
				if (Integer.parseInt(title) > 1){
					setTitle = setTitle + "s";
				}
	
			} else if (type.equals("place")){
				setTitle = control.getPlaceLabel() + " entry with " + title + " " + control.getPlaceLabel().toLowerCase();
				if (Integer.parseInt(title) > 1){
					setTitle = setTitle + "s";
				}			
			} else if (type.equals("mood")){
				if (control.getMoodLabel().toLowerCase().equals("mood")){
					String mood = "";
					if (title.equals("6")){
						mood = "excited";
					} else if (title.equals("5")){
						mood = "happy";
					} else if (title.equals("4")){
						mood = "meh";
					} else if (title.equals("3")){
						mood = "sad";
					} else if (title.equals("2")){
						mood = "crying";
					} else if (title.equals("1")){
						mood = "angry";
					}
					setTitle = "Today I am " + mood;
				} else {
					setTitle = control.getMoodLabel() + " entry";
				}
				
				
				
			}			
		}
		
		titleLabel = new JLabel();
		titleLabel.setFont(new Font("sansserif",Font.PLAIN,12));
		
		//shorten the title label to fit the space
        FontMetrics fontMetrics = getFontMetrics(new Font("Tahoma",Font.PLAIN, 12));
        int width = fontMetrics.stringWidth(setTitle);	
        boolean tooLong = false;
        int maxWidth = 0;
                
        //set max width depending on the orientation of mainGUI, and whether it is a reminder or an entry
        if (orientation == 0){
        	maxWidth = (reminder) ? 180 : 161;
        } else if (orientation == 1){
        	maxWidth = (reminder) ? 176 : 154;
        }
        
        //backup the original title to display in tooltip
        String backupTitle = setTitle;
        
		while (width > maxWidth){
			tooLong = true;
			setTitle = setTitle.substring(0,setTitle.length() - 1);
			width = fontMetrics.stringWidth(setTitle);	
		}
		
		if (tooLong)
			setTitle = setTitle.substring(0,setTitle.length() - 1) + "...";		
		
		titleLabel.setText(setTitle);
		
		//if title is too long, set a tooltip
		if (tooLong){
			titleLabel.setToolTipText(backupTitle);
			
			//set instant tooltips
			titleLabel.addMouseListener(new MouseAdapter(){
				public void mouseEntered(MouseEvent e) {
			        ToolTipManager.sharedInstance().setInitialDelay(0);
				}
	
				public void mouseExited(MouseEvent e) {
					ToolTipManager.sharedInstance().setInitialDelay(750);
				}			
			});
		}
		
		//set color to greyish for reminders
		if(reminder){
			titleLabel.setForeground(new Color(80,80,80));
		}
		
		if (type.equals("place")){
			this.add(icon, "gapleft 1");
		} else {
			this.add(icon);
		}
		this.add(titleLabel);			
		
		//add the necessary buttons
		if (!reminder){
			tweet = new JButton();
			tweet.setIcon(new ImageIcon(getResourceURL("/images/Twitter_48.png"))); 
			tweet.setMaximumSize(new Dimension (23,23));
			tweet.setToolTipText("Tweet entry");
			viewEdit = new JButton();
			viewEdit.setIcon(new ImageIcon(getResourceURL("/images/Pencil_14.png"))); 
			viewEdit.setMaximumSize(new Dimension (23,23));
			viewEdit.setToolTipText("View or edit entry");
			delete = new JButton();
			delete.setIcon(new ImageIcon(getResourceURL("/images/Close-small.png"))); 
			delete.setMaximumSize(new Dimension (23,23));
			delete.setToolTipText("Delete entry");
			
			this.add(tweet);
			this.add(viewEdit);
			this.add(delete);
		} else {
			addEntry = new JButton();
			addEntry.setIcon(new ImageIcon(getResourceURL("/images/bullet_add-grey.png"))); 
			addEntry.setRolloverIcon(new ImageIcon(getResourceURL("/images/bullet_add.png")));
			addEntry.setMaximumSize(new Dimension (23,23));
			addEntry.setToolTipText("Create entry");
			dismissRem = new JButton();
			dismissRem.setIcon(new ImageIcon(getResourceURL("/images/Close-small-grey.png"))); 
			dismissRem.setRolloverIcon(new ImageIcon(getResourceURL("/images/Close-small.png")));
			dismissRem.setMaximumSize(new Dimension (23,23));
			dismissRem.setToolTipText("Dismiss reminder");
			this.add(addEntry);
			this.add(dismissRem);
		}
	}
		
	/**
	 * Add the listeners to the buttons (called outside of this class by mainGUI, because 
	 * the buttons need access to a lot of references, which would be too much to pass to this class)
	 */
	public void addListener(ActionListener e) {
		if (reminder){
			addEntry.addActionListener(e);
			dismissRem.addActionListener(e);
		} else {
			tweet.addActionListener(e);
			viewEdit.addActionListener(e);
			delete.addActionListener(e);
		}
		
		if(type.equals("diary")){
			if (reminder){
				addEntry.setActionCommand("createEntry");
				dismissRem.setActionCommand("dismissReminder");
			} else{
				viewEdit.setActionCommand("viewDiary");
				tweet.setActionCommand("tweetDiary");
				delete.setActionCommand("deleteDiary");
			}
		} else if(type.equals("topic")) {
			if (reminder){
				addEntry.setActionCommand("createEntry");
				dismissRem.setActionCommand("dismissReminder");
			} else{
				viewEdit.setActionCommand("viewTopic");
				tweet.setActionCommand("tweetTopic");
				delete.setActionCommand("deleteTopic");
			}
		} else if(type.equals("image")){
			if (reminder){
				addEntry.setActionCommand("createEntry");
				dismissRem.setActionCommand("dismissReminder");
			} else{
				viewEdit.setActionCommand("viewImage");
				tweet.setActionCommand("tweetImage");
				delete.setActionCommand("deleteImage");
			}
		} else if(type.equals("mood")) {
			if (reminder){
				addEntry.setActionCommand("createEntry");
				dismissRem.setActionCommand("dismissReminder");
			} else{
				viewEdit.setActionCommand("viewMood");
				tweet.setActionCommand("tweetMood");
				delete.setActionCommand("deleteMood");
			}
		} else if(type.equals("place")){
			if (reminder){
				addEntry.setActionCommand("createEntry");
				dismissRem.setActionCommand("dismissReminder");
			} else{
				viewEdit.setActionCommand("viewPlace");
				tweet.setActionCommand("tweetPlace");
				delete.setActionCommand("deletePlace");
			}
		}
	}

    public URL getResourceURL(String path){
    	return this.getClass().getResource(path);
    }
	
	/**
	 * Returns the title of the panel after it has been set
	 */
	public String getTitle() {
		return setTitle;
	}
}

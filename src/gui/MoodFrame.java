package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import controller.Controller;

/**
 * This is the GUI class for creating a new mood entry. The user can select one of the 5 smileys/emoticons for the current date. The emoticon will appear in the day view panel of the main GUI class. The user can edit the mood entry or delete the entry from that main GUI class. bugs / others : - no current bugs that we are aware of but there will be extensive testing done.
 * @author  Mihai Oprescu, Mikus Lorence
 */
public class MoodFrame implements ActionListener {

	private JFrame frame;
	private JButton emote0 = new JButton("");
    private JButton emote1 = new JButton("");
    private JButton emote2 = new JButton("");
    private JButton emote3 = new JButton("");
    private JButton emote4 = new JButton("");
    private JButton emote5 = new JButton("");
    private Date date_selected;
    private Controller control;
    private int iconChosenID;
    
    public MoodFrame(Date selected_date, Controller controller_arg, int type){
		
    	// get controller reference
    	control = controller_arg;
    	// get date reference
    	date_selected = selected_date;
        
    	control.addActiveView("mood");
    	
    	// set up frame
    	int minwidth = 200;
        int minheight = 100;
        
        frame = new JFrame();
        frame.setSize(new Dimension(minwidth, minheight));
        frame.setMinimumSize(new Dimension(minwidth,minheight));
        frame.setResizable(false);
        
        // window listener; enables main GUI when this frame is closed by X
        frame.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		    	  control.enableMain();
		    	  control.removeActiveView("mood");
		      }
		});   
        
        // init the main panel of the frame
        JPanel mainPanel = new JPanel(new MigLayout("insets 5 5 5 5"));
        
        JLabel label =  new JLabel("My " + control.getMoodLabel());  
        label.setForeground(new Color(181, 181, 181));
        
        iconChosenID = 6;
        //mainPanel.
        emote0.addActionListener(this);
        emote0.setActionCommand("moodOneButton");
        emote0.setIcon(new ImageIcon("images/emoticons/emote0.png","Angry"));
        emote0.setMaximumSize(new Dimension(30,30));
        //mainPanel.add(confused);

        emote1.addActionListener(this);
        emote1.setActionCommand("moodTwoButton");
        emote1.setIcon(new ImageIcon("images/emoticons/emote1.png","Crying"));
        emote1.setMaximumSize(new Dimension(30,30));
        //mainPanel.add(angry);
        
        emote2.addActionListener(this);
        emote2.setActionCommand("moodThreeButton");
        emote2.setIcon(new ImageIcon("images/emoticons/emote2.png","Sad"));
        emote2.setMaximumSize(new Dimension(30,30));
        //mainPanel.add(mischievous);
                
        emote3.addActionListener(this);
        emote3.setActionCommand("moodFourButton");
        emote3.setIcon(new ImageIcon("images/emoticons/emote3.png","Emotionless"));
        emote3.setMaximumSize(new Dimension(30,30));
        //mainPanel.add(star);
        
        emote4.addActionListener(this);
        emote4.setActionCommand("moodFiveButton");
        emote4.setIcon(new ImageIcon("images/emoticons/emote4.png","Happy"));
        emote4.setMaximumSize(new Dimension(30,30));
        //mainPanel.add(love);
        
        emote5.addActionListener(this);
        emote5.setActionCommand("moodSixButton");
        emote5.setIcon(new ImageIcon("images/emoticons/emote5.png","Super Happy"));
        emote5.setMaximumSize(new Dimension(30,30));
        //mainPanel.add(love2);        
        
        JPanel okCancelPanel = new JPanel( new MigLayout("insets 0 0 0 0"));
        JButton ok = new JButton("Ok");
        ok.addActionListener(this);
        if (type == 0){
        	ok.setActionCommand("submit");
        } else {
        	ok.setActionCommand("edit");
        }
        ok.setMinimumSize(new Dimension(35, 15));
        //mainPanel.add(Ok, "wrap");
        
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        cancel.setActionCommand("cancelButton");
        //mainPanel.add(cancelButton, "wrap");
        cancel.setMinimumSize(new Dimension(53, 15));
        
        okCancelPanel.add(ok);
        okCancelPanel.add(cancel);
        
        // add emoticons to the main panel
        mainPanel.add(label, "span, wrap");
        mainPanel.add(emote5);
        mainPanel.add(emote4);
        mainPanel.add(emote3);
        mainPanel.add(emote2);
        mainPanel.add(emote1);
        mainPanel.add(emote0, "wrap");
        mainPanel.add(okCancelPanel, "span");
        
        // frame adjustments, update + set frame to visible        
        frame.getContentPane().add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
	}
    	
	public void actionPerformed(ActionEvent e) {
		
		if ("submit".matches(e.getActionCommand())){
			control.removeActiveView("mood");
			control.addMoodEntry(iconChosenID, date_selected);
			control.enableMain();			
			frame.dispose();			
		} else if ("edit".matches(e.getActionCommand()))	{
			control.editMoodEntry(iconChosenID, date_selected);
			control.enableMain();
			frame.dispose();
		} else if ("cancelButton".matches(e.getActionCommand())){
			control.enableMain();
			control.removeActiveView("mood");
			frame.dispose();
		} else if ("moodOneButton".matches(e.getActionCommand())){
			iconChosenID = 1;
		} else if ("moodTwoButton".matches(e.getActionCommand())){
			iconChosenID = 2;
		} else if ("moodThreeButton".matches(e.getActionCommand())){
			iconChosenID = 3;
		} else if ("moodFourButton".matches(e.getActionCommand())){
			iconChosenID = 4;
		} else if ("moodFiveButton".matches(e.getActionCommand())){
			iconChosenID = 5;
		} else if ("moodSixButton".matches(e.getActionCommand())){
			iconChosenID = 6;
		}
		
	}

	public void disposeFrame() {
		frame.dispose();
	}
	
}

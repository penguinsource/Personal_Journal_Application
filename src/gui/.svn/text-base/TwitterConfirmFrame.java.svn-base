package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.Controller;

import net.miginfocom.swing.MigLayout;

/**
 * <p> 	Frame that is shown whenever the user presses the tweet button for any entry. This frame generates some text based on the type of entry the user is tweeting, and allow the user to edit the final tweet (and cut it down to 140 chars) before submit the tweet to the controller to tweet it.
 * @author  Mikus Lorencs
 */
public class TwitterConfirmFrame implements ActionListener {

	private JFrame frame;
	private Controller control;
	public JLabel charCount;
	public JTextArea textArea;
	public int textLength;
	JButton submitBtn = new JButton();
	public TwitterConfirmFrame(String type, String text, Controller controlArg) {
    	control = controlArg;
               
        frame = new JFrame("Confirm " + type + " tweet");
        
        frame.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
				  control.enableMain();
		      }
		});   
        JPanel panel = new JPanel(new MigLayout("insets 7 7 7 7"));
        JLabel label = new JLabel("Edit the tweet so that it is at most 140 characters:");
        
        textArea = new JTextArea();    
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);        
        JScrollPane scrollArea = new JScrollPane(textArea);
        scrollArea.setPreferredSize(new Dimension(315, 78));
        //set the textfield text to the appropriate generated text (in the case 
        // of diary it is just the diary content)
        if (type.equals("diary")){
        	textArea.setText(text);
        } else if (type.equals("topic")){
        	String array[] = text.split(" ");
        	text = "";
        	
        	for (int i = 0; i < array.length; i++){
        		text = text + array[i] + ", ";
        	}
        	text = text.substring(0, text.length() - 2);
        	textArea.setText("Here are some " + control.getTopicLabel().toLowerCase() + "s I found interesting: " + text + ".");
        } else if (type.equals("image")){
        	textArea.setText("Today I uploaded these " + control.getImageLabel().toLowerCase() + "s: " + text);
        } else if (type.equals("place")){
        	textArea.setText("Today I visited: " + text);
        } else if (type.equals("mood")){
        	if (control.getMoodLabel().toLowerCase().equals("mood")){
	        	String mood = "";
				if (text.equals("6")){
					mood = "excited :D";
				} else if (text.equals("5")){
					mood = "happy :)";
				} else if (text.equals("4")){
					mood = "meh :/";
				} else if (text.equals("3")){
					mood = "sad :(";
				} else if (text.equals("2")){
					mood = "crying :'(";
				} else if (text.equals("1")){
					mood = "angry >:(";
				}
	        	textArea.setText("Today I am " + mood);
        	} else {
        		String mood = "";
				if (text.equals("6")){
					mood = ":D";
				} else if (text.equals("5")){
					mood = ":)";
				} else if (text.equals("4")){
					mood = ":/";
				} else if (text.equals("3")){
					mood = ":(";
				} else if (text.equals("2")){
					mood = ":'(";
				} else if (text.equals("1")){
					mood = ">:(";
				}
	        	textArea.setText("My " + control.getMoodLabel().toLowerCase() + " today is: " + mood);
        	}
        }
        //add the document listener to the text area
        textArea.getDocument().addDocumentListener(new MyDocumentListener());
        
        
        JPanel countPanel  = new JPanel(new FlowLayout(FlowLayout.LEADING, 0,0));
        JLabel countLabel = new JLabel("Characters Remaining: ");
        countLabel.setForeground(new Color(150,150,150));
        charCount = new JLabel();
        updateCharCount();
        countPanel.add(countLabel);
        countPanel.add(charCount);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0,0));
        submitBtn = new JButton("Submit");
        submitBtn.addActionListener(this);
        submitBtn.setToolTipText("Submit the tweet to Twitter");
        submitBtn.setActionCommand("submit");
        
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(this);
        cancelBtn.setActionCommand("cancel");
        submitBtn.setToolTipText("Cancel the tweet");
        buttonPanel.add(submitBtn);
        buttonPanel.add(cancelBtn);
        
        panel.add(label, "span, wrap");
        panel.add(scrollArea,  "span, wrap");
        panel.add(countPanel, "wrap");
        panel.add(buttonPanel, "push");
            
        frame.setResizable(false);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
	}
	
	/**
	 * Updates the label at the bottom with the current character count in the
	 * text field area.
	 */
	public void updateCharCount() {
		textLength = textArea.getText().toCharArray().length;
        charCount.setText(Integer.toString(140 - textLength));
        if (textLength == 0){
        	charCount.setForeground(new Color(150,150,150));
        	submitBtn.setEnabled(false);
        } else if (textLength < 141){
        	charCount.setForeground(new Color(150,150,150));
        	submitBtn.setEnabled(true);
        } else if (textLength > 140){
        	charCount.setForeground(Color.RED);
        	submitBtn.setEnabled(false);
        }
	}

	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("submit")){					
			int result = control.tweetEntry(textArea.getText());
			if (result == 1){
				frame.dispose();
				control.enableMain();		
			}
		} else if (e.getActionCommand().equals("cancel")){
			frame.dispose();
			control.enableMain();
		}
		
	}
			
	/**
	 * A listener for the textarea that calls updateCharCount whenever 
	 * the content of the textarea is changed
	 * @author Mikus Lorencs	 *
	 */
	class MyDocumentListener implements DocumentListener {
 
	    public void insertUpdate(DocumentEvent e) {
	    	
	    	 updateCharCount();
	    }
	    public void removeUpdate(DocumentEvent e) {
	    	 updateCharCount();
	    }
	    public void changedUpdate(DocumentEvent e) {
	    	 updateCharCount();
	    }
	}
}

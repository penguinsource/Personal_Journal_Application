package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

import gui.JournalSelectFrame;

/**
 * Frame that prompts the user to enter a name for their new journal.
 * <br><br>
 * @author Mikus Lorencs
 *
 */
public class NewJournalFrame {
	private static JFrame frame;
	private static List<String> list;
	public NewJournalFrame(List<String> passedList){
		list = passedList;
		frame = new JFrame("New Journal");
		frame.getContentPane().setLayout(new BorderLayout(5,0));
				
		frame.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		    	  JournalSelectFrame.enableFrame();
		      }
		});
		
		JPanel panel = new JPanel(new MigLayout());
		JLabel label = new JLabel("Enter the name of your Journal:");
		final JTextField field = new JTextField(15);
		field.setMinimumSize(new Dimension(100,0));
		JButton ok = new JButton ("Ok");
		ok.setToolTipText("Continue to preferences");
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (field.getText().equals("")){
					JOptionPane.showMessageDialog(null,  
							"Please enter a name for your Journal.", 
							"Error", JOptionPane.PLAIN_MESSAGE, 
							UIManager.getIcon("OptionPane.warningIcon"));
				} else if (validName(field.getText()) == 0){
					//last 3 args are null because they're only needed when the
					//preferenceFrame is called from within the app (after loading a journal)
					new PreferenceFrame(field.getText(), 0, null, null, null);
					frame.dispose();
				} else if (validName(field.getText()) == 1){
					JOptionPane.showMessageDialog(null,  
							"A journal with that name already exists.", 
							"Error", JOptionPane.PLAIN_MESSAGE, 
							UIManager.getIcon("OptionPane.warningIcon"));
				} else if (validName(field.getText()) == 2){
					JOptionPane.showMessageDialog(null,  
							"Please enter a Journal name that doesn't exceed 25 characters.", 
							"Error", JOptionPane.PLAIN_MESSAGE, 
							UIManager.getIcon("OptionPane.warningIcon"));
				}
			}
		});
		
		JButton cancel = new JButton ("Cancel");
		ok.setToolTipText("Cancel journal creation");
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.dispose();
				JournalSelectFrame.enableFrame();
			}
		});
		
		panel.add(label, "span 3, wrap");
		panel.add(field);
		panel.add(ok);
		panel.add(cancel);
		
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	/**
	 * Checks if the name of the new journal already exists in the list of journals
	 * @param text name of the journal
	 * @return true if it's a valid name (doesn't match existing names), else false
	 */
	protected int validName(String text) {
		int retVal = 0;
		
		for(int i = 0; i< list.size(); i++){
			if (list.get(i).toString().equals(text)){
				retVal = 1;
			}
		}
		
		if (text.length() > 25){
			retVal = 2;
		}
		
		return retVal;
	}
}

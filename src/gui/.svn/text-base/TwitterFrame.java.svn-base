package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;
import controller.Controller;

/**
 * <p>This class creates a Frame so that the user can enter the PIN necessary to start using Twitter<br>
 * @author  Fernando, Mikus Lorencs
 */

public class TwitterFrame extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JTextField pinText;
	private JButton okButton;
	private JButton cancelButton;
	private JPanel panel;
	private JLabel label;
	/**
	 */
	private Controller controller;
	private int type;
	
	/**
	 * Creates all of the elements to be displayed on the frame
	 *  
	 * @param controllerArg The controller of the application
	 * @param typeArg if type is 1, the twitter confirm is being called from the preference frame
	 */
	public TwitterFrame(Controller controllerArg, int typeArg) {
		super("Twitter PIN");
		
		this.type = typeArg;
		this.controller = controllerArg;
		this.setLayout(new MigLayout());
		
		this.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		    	  controller.revertTwitterAccount();
		    	  if (type == 1){
		    		  controller.enablePreferenceFrame();
		    	  }
		      }
		});

		
		okButton = new JButton("Enter");
		cancelButton = new JButton("Cancel");
		pinText = new JTextField(6);
		panel = new JPanel(new MigLayout());
		label = new JLabel("Please enter the Twitter PIN");
		
		okButton.addActionListener(this);
		okButton.setActionCommand("enterPin");
		okButton.setToolTipText("Attempt to authenticate with the PIN");
		cancelButton.addActionListener(this);
		cancelButton.setActionCommand("cancel");
		cancelButton.setToolTipText("Cancel authentication");
		panel.setBackground(new Color (240, 240, 240));
		this.setBackground(new Color (240, 240, 240));
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
		btnPanel.add(okButton);
		btnPanel.add(cancelButton);
		
		panel.add(label);
		panel.add(pinText, "wrap");
		panel.add(btnPanel);
		this.add(panel);
		this.setSize(new Dimension(400, 400));
		this.pack();
		this.setBackground(new Color (240, 240, 240));
		this.setVisible(true);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}
	
	/**
	 *  Performs the actions depending on the buttons clicked
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("enterPin")){
			if (pinText.getText().matches("\\d{7}")){
				if (controller.addPIN(pinText.getText()) == 0){
					this.setAlwaysOnTop(false);
					JOptionPane.showMessageDialog(this, "You have entered an incorrect PIN.");
					controller.enablePreferenceFrame();

					dispose();
					controller.initiateTwitter(type);
					return;

				}
				this.dispose();
				if (type == 1){
					controller.enablePreferenceFrame();
				}
			} else {
				this.setAlwaysOnTop(false);
				int response = JOptionPane.showConfirmDialog(null, "Please make sure you enter 7 digits.", null, JOptionPane.DEFAULT_OPTION);
				if (response == 0){
					this.setAlwaysOnTop(true);
				}
			}
		}
		
		else if(e.getActionCommand().equals("cancel")) {
			controller.revertTwitterAccount();
			if (type == 1){
				controller.enablePreferenceFrame();
			}
			this.dispose();
		}
	}
}

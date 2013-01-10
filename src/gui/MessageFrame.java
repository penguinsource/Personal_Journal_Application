package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

/**
 * This class is used to display a message to the user that explains how to use the application.
 * The constructor takes an (html formatted) string and displays it in a frame.
 * 
 * @author lorencs
 */
public class MessageFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel label;

	public MessageFrame(String message){
		setLayout(new MigLayout("", "[]push[]", "[]5[]"));
		label = new JLabel(message);
		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();
			}			
		});
		
		add(label,"span, wrap");
		add(close, "cell 2 2");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		if (!message.equals("")){
			setVisible(true);
		}
		setLocationRelativeTo(null);
	}
	
	public void updateLabel(String updateText){
		label.setText(updateText);
		pack();
	}
}

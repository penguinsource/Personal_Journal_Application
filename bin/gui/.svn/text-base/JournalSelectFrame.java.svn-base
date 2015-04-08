package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

import controller.Controller;
import data.Journal;
import gui.MainGUI;

/**
 * This is the Journal Manager class which is the first graphical interface that appears when the user runs the program. It allows the user to create a new journal and specify the preferences, select a journal from the list, or delete an existing journal. This class uses an extension of an Abstract List Model, defined at the bottom of this file.
 * @author  Mikus Lorencs
 */
public class JournalSelectFrame {
	private static JFrame frame;
	private static JList list;
	private static JButton newJournal;
	private static JButton selectJournal;
	private static JButton deleteJournal;
	private static JournalListModel jlm;
	private static Controller control;
	private static Journal journal;
	
	public JournalSelectFrame (Controller controller, final File file, Journal journal_arg){
		JournalSelectFrame.journal = journal_arg;
		control = controller;
		frame = new JFrame("Journal Manager");
		frame.setLayout(new FlowLayout(FlowLayout.LEADING, 3, 3));		
		
		//upon closing of the window, write to the config file (keeps of track of existing journals)
		frame.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		    	  writeToFile(file);
		      }
		});
		
		setLookAndFeel();		
		JPanel panel = new JPanel(new MigLayout());
		
		list = new JList();
		jlm = new JournalListModel();
		list.setModel(jlm);	
		list.setBackground(new Color(240,240,240));
		JScrollPane scroll = new JScrollPane(list);
		scroll.setBorder(BorderFactory.createTitledBorder(null, "Select Journal:", 0, 0)); 
		scroll.setPreferredSize(new Dimension(200,150));

		newJournal = new JButton("New Journal");		
		newJournal.setIcon(new ImageIcon("images/bullet_add.png"));
		newJournal.setHorizontalAlignment(SwingConstants.LEADING);
		newJournal.setIconTextGap(4);
		newJournal.setToolTipText("Create a new Journal");
		
		//launch the newJournal GUI, pass the list of all existing journals, so that it can check the name of the new journal against existing journals
		newJournal.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e){
            	frame.setEnabled(false);
        		new NewJournalFrame(jlm.getList());
            }
		});

		
		selectJournal = new JButton("Select Journal");
		selectJournal.setIcon(new ImageIcon("images/bullet-go.png"));
		selectJournal.setHorizontalAlignment(SwingConstants.LEADING);
		selectJournal.setIconTextGap(4);
		selectJournal.setToolTipText("Load the selected Journal");
		selectJournal.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e){
            	if (!list.isSelectionEmpty()){
            		//instantiate a File object of the specified journal
            		File journalFile = new File("userfiles/journals/" + list.getSelectedValue().toString() + ".jrn");
            		//updat the config file (list of journals)
            		writeToFile(file);
            		//initialize the model with the chosen file, and start the main application
            		if (control.initModel(journalFile) == 0){
            			return;
            		}
            		control.startApp();            		
            		frame.dispose();
            	}
            }
		});
		
		deleteJournal = new JButton("Delete Journal");
		deleteJournal.setIcon(new ImageIcon("images/bullet-delete.png"));
		deleteJournal.setHorizontalAlignment(SwingConstants.LEADING);
		deleteJournal.setIconTextGap(4);
		deleteJournal.setToolTipText("Delete the selected Journal");
		deleteJournal.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e){
            	if (!list.isSelectionEmpty()){
            		int input = JOptionPane.showConfirmDialog(null,"Are you sure you wish to delete " + list.getSelectedValue().toString() + "?" ,
  	    				  "Delete Journal", JOptionPane.YES_NO_OPTION);
            		if (input == 0){
	            		File fileToDelete = new File("userfiles/journals/" + list.getSelectedValue().toString() + ".jrn");
	            		fileToDelete.delete();
	            		jlm.removeElement(list.getSelectedValue());
	            		updateList(jlm);
            		}
            	}
            }
		});
		
		panel.add(scroll, "dock west");
		panel.add(newJournal, "gaptop 14, wrap");
		panel.add(selectJournal, "wrap");
		panel.add(deleteJournal, "wrap");
		
		readFile(file);
		
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	/**
	 * Save the journal configuration (essentially a file that contains the names of all existing journals)
	 * @param file config file to save to
	 * @throws IOException would only happen if the user tampered with the application files
	 * 
	 */
	protected void writeToFile(File file) {

		try {
			BufferedWriter out= new BufferedWriter(new FileWriter(file));
			for(int i=0; i<jlm.getSize();i++){
				out.write(jlm.getElementAt(i).toString());
				out.newLine();		
			}
			out.close();
		} catch (IOException e){
			JOptionPane.showMessageDialog(null,"An error has occured. This is most " +
					"likely due to missing application files. Please delete the \"userfiles\" " +
					"directory and restart the application.", null, JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Reads the config file and loads the list of existing journals into the list model
	 * @param file file to read
	 */
	private void readFile(File file) {
		try {
			Scanner in = new Scanner(file);
			while (in.hasNext()){
				String text = in.nextLine();
				jlm.addElement(text);
			}
		} catch (FileNotFoundException e){
			JOptionPane.showMessageDialog(null,  
					"The file was not found.", 
					"Error", JOptionPane.PLAIN_MESSAGE, 
					UIManager.getIcon("OptionPane.warningIcon"));
		} catch (@SuppressWarnings("hiding") IOException e){
			e.printStackTrace();
		}
		
		updateList(jlm);
	}
	
	
	/**
	 * Creates a journal file with the specified preferences (called when a user finishes creating a new journal)
	 * @param name name of the journal
	 * @param order array list of order preference
	 * @param reminders array list of reminders preference
	 * @param diaryLabel label for the diary entry
	 * @param topicLabel label for the diary topic
	 * @param imageLabel label for the diary image
	 * @param placeLabel label for the diary place
	 * @param moodLabel label for the diary mood
	 * @throws IOException,FileNotFoundException only occur if the user tampers with the application files
	 */
	public static void addJournal(String name, ArrayList<String> order, ArrayList<String> reminders, 
			String diaryLabel, String topicLabel, String imageLabel, String placeLabel, String moodLabel) throws IOException{
	
		journal.setOrder(order);
		journal.setReminders(reminders);
		journal.setDiaryLabel(diaryLabel);
		journal.setTopicLabel(topicLabel);
		journal.setImageLabel(imageLabel);
		journal.setPlaceLabel(placeLabel);
		journal.setMoodLabel(moodLabel);
		
		try {
			FileOutputStream fos = new FileOutputStream("userfiles/journals/" + name + ".jrn");
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(journal);
			out.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,"An error has occured. This is most " +
					"likely due to missing application files. Please delete the \"userfiles\" " +
					"directory and restart the application.", null, JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"An error has occured. This is most " +
					"likely due to missing application files. Please delete the \"userfiles\" " +
					"directory and restart the application.", null, JOptionPane.ERROR_MESSAGE);
		}
				
		jlm.addElement(name);
		updateList(jlm);
	}
	
	private static void disableButtons() {
		selectJournal.setEnabled(false);
		deleteJournal.setEnabled(false);
	}
	
	private static void enableButtons() {
		selectJournal.setEnabled(true);
		deleteJournal.setEnabled(true);
	}
	
	public static void enableFrame() {
		frame.toFront();
		frame.setEnabled(true);
	}

	/**
	 * Updates the Journal List Model and sets the JList to display the updated one
	 * @param jlm
	 */
	public static void updateList(JournalListModel jlm){
		JournalListModel jlm2 = new JournalListModel();
		list.setModel(jlm2);
		list.setModel(jlm);
		if (jlm.getSize() == 0){
			disableButtons();
		} else {
			enableButtons();
		}
	}
	
	/**
	 * Sets the look and feel of the application to Nimbus, with some modified padding
	 * and a modified background color.
	 */
	private void setLookAndFeel() {
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            Insets buttonInsets = new Insets(6,9,6,8);
            UIManager.put("control", new Color(240,240,240));
            UIManager.getLookAndFeelDefaults().put("ScrollPane.contentBorderInsets", new Insets(6,6,6,6));
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
}

/**
 * An extension of the AbstractListModel which uses an array list to manage the 
 * list which is used for the model of the JList that displays the journals
 * @author Mikus Lorencs
 *
 */
class JournalListModel extends AbstractListModel{
	private static final long serialVersionUID = 1L;
	private List<String> journalList = new ArrayList<String>();

    public JournalListModel(){
    	Collections.sort(journalList);
    }

    @Override
    public int getSize() {
    return journalList.size();
    }

    @Override
    public Object getElementAt(int index) {
    return journalList.get(index);
    }
    
    public void addElement(String element){
    	journalList.add(element);
    }
    
    public void removeElement(Object element){
    	journalList.remove(element);
    }
    
    public List<String> getList(){
    	return journalList;
    }
}

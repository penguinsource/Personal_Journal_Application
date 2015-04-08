package gui;

import imageflow.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;
import java.util.Vector;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.filechooser.FileFilter;
import javax.swing.JSeparator;

import controller.Controller;
import controller.EditCommand;
import net.miginfocom.swing.MigLayout;

/**
 * ImageFrame is instantiated whenever the user wants to enter an image entry. The class provides methods for visually showing images on screen and uses the third-party ImageFlow library to do so.
 * @author  Fernando
 */
public class ImageFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JButton browseButton;
	private JButton submitButton;
	private JButton deleteButton;
	private JButton zoomButton;
	private JButton exitButton;
	private JButton undoButton;
	private JButton redoButton;
	private JButton helpButton;
	private JPanel panel;
	private JPanel imagePanel;
	private Vector<ImageFlowItem> itemList;
	private Vector<String> fileList;
	private ImageFlow imageFlow;
	private JFileChooser fileChooser;
	private boolean imageFlowExists;
	private boolean noImagesFound = false;
	private Controller controller;
	private Date date;
	private boolean noImages = false;
	private Vector<ImageFlowItem> otherList;
	private Vector<ImageFlowItem> helperList;
	private JLabel label;
	private JSeparator separator;
	private MessageFrame messageFrame;
	
	/**
	 * 
	 * @param frameName : Name of the frame 
	 * @param date : current date selected in the calendar
	 * @param controller : controller object to be used
	 */
	public ImageFrame(String frameName, Date date, final Controller controller) {
		this.setName(frameName);
		
		if(frameName.equals("New Images Entry")) {
			controller.disableMain();
			controller.removeActiveView("Image");
		}
		
		this.setLayout(new MigLayout());
		this.setResizable(false);
		helperList = new Vector<ImageFlowItem>();
		this.date = date;
		this.controller = controller;
		imageFlowExists = false;
		browseButton = new JButton("Browse");
		submitButton = new JButton("Submit");
		deleteButton = new JButton("Remove");
		exitButton = new JButton("Cancel");
		zoomButton = new JButton("Zoom Image");
		undoButton = new JButton("Undo");
		redoButton = new JButton("Redo");
		helpButton = new JButton("Help");
		helpButton.setIcon(new ImageIcon(getResourceURL("/images/help4.png")));
		panel = new JPanel(new MigLayout("insets 0 0 0 0"));
		imagePanel = new JPanel(new MigLayout("insets 0 0 0 0"));
		fileList = new Vector<String>();
		separator = new JSeparator();
		separator.setPreferredSize(new Dimension(600,1));
		messageFrame = new MessageFrame("");
		messageFrame.updateLabel("<html>" +
				"Here you can add or delete images to your journal<br><br>" +
				"<p>Click on the <b>Browse</b> button to search in your" +
				"<br>folders and directories for pictures to upload.<br><br> "
				+ "You can delete images using the <b>Remove</b> button," +
				"<br>the focused image will be removed<br><br>" +
				"To save your uploaded images to the journal,<br>"+
				"click on <b>Submit</b>. You cannot submit images<br>" +
				"if there are none.<br><br>"+
				"To cancel uploading pictures, click on the <br> " +
				"<b>Cancel</b> button. All changes made<br>will be lost.<br><br>" +
				"If you want to undo any changes made, click on the<br>"+
				"<b>Undo</b> button, the last change made will be reverted.<br><br>" +
				"In case you want to revert any change made by the<br>Undo"+
				"button. Click on <b>Redo</b>.</p></html>");
		messageFrame.setVisible(false);
		
		this.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		    	  controller.removeActiveView("image");
		    	  controller.clearUndoImageStack();
		    	  controller.enableMain();
		      }
		});       
		
		submitButton.addActionListener(this);
		submitButton.setActionCommand("submit");
		submitButton.setEnabled(false);
		ImageIcon icon = new ImageIcon(getResourceURL("/images/Plus-32.png"));
		Image img =  icon.getImage(); 
		Image newimg = img.getScaledInstance(18, 18,  java.awt.Image.SCALE_SMOOTH);  
		submitButton.setIcon(new ImageIcon(newimg));
		submitButton.setToolTipText("Save the uploaded images to the journal");
		
		browseButton.addActionListener(this);
		browseButton.setActionCommand("upload");
		icon = new ImageIcon(getResourceURL("/images/Glass-32.png"));
		img =  icon.getImage(); 
		newimg = img.getScaledInstance(18, 18,  java.awt.Image.SCALE_SMOOTH);
		browseButton.setIcon(new ImageIcon(newimg)); 
		browseButton.setToolTipText("Search your folders to select images");
		
		deleteButton.setEnabled(false);
		icon = new ImageIcon(getResourceURL("/images/Close-32.png"));
		img =  icon.getImage(); 
		newimg = img.getScaledInstance(18, 18,  java.awt.Image.SCALE_SMOOTH);
		deleteButton.setIcon(new ImageIcon(newimg)); 
		deleteButton.addActionListener(this);
		deleteButton.setActionCommand("delete");
		deleteButton.setToolTipText("Delete the current focused image");
		
		exitButton.addActionListener(this);
		exitButton.setActionCommand("exit");
		icon = new ImageIcon(getResourceURL("/images/Cancel-32.png"));
		img =  icon.getImage(); 
		newimg = img.getScaledInstance(18, 18,  java.awt.Image.SCALE_SMOOTH);
		exitButton.setIcon(new ImageIcon(newimg)); 
		exitButton.setToolTipText("Closes the window. No changes made will be saved");
		
		zoomButton.addActionListener(this);
		zoomButton.setActionCommand("zoom");
		icon = new ImageIcon(getResourceURL("/images/Gnome-Zoom-Fit-Best-32.png"));
		img =  icon.getImage(); 
		newimg = img.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
		zoomButton.setIcon(new ImageIcon(newimg)); 
		zoomButton.setEnabled(false);
		undoButton.addActionListener(this);
		undoButton.setActionCommand("undo");
		undoButton.setToolTipText("Undo the last change made in this window");
		undoButton.setIcon(new ImageIcon(getResourceURL("/images/undo2png")));
		
		redoButton.addActionListener(this);
		redoButton.setActionCommand("redo");
		redoButton.setToolTipText("Revert any change made by the Undo button");
		redoButton.setIcon(new ImageIcon(getResourceURL("/images/redo2.png")));
		
		helpButton.addActionListener(this);
		helpButton.setActionCommand("help");
		helpButton.setToolTipText("Open a help text");
		
		itemList = new Vector<ImageFlowItem>();
		panel.setBackground(new Color (240, 240, 240));
		this.setBackground(new Color (240, 240, 240));

		label = new JLabel("My " + controller.getImageLabel() + "s", JLabel.CENTER);
		label.setForeground(new Color(181, 181, 181));

		this.add(imagePanel,"wrap");
		panel.add(label, "wrap, span");
		panel.add(browseButton);
		panel.add(exitButton);
		panel.add(helpButton);

		this.add(panel, "wrap");
		this.setSize(new Dimension(400, 400));
		this.pack();
		this.setBackground(new Color (240, 240, 240));
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * Updates the images in the screen with the image filenames added in the vector
	 * 
	 * @param itemList : List of the name of the images to be shown
	 */
	public void updateImages(Vector<ImageFlowItem> itemList) {
		imageFlow = new ImageFlow(itemList);
		
		if(imageFlowExists) {
			panel.removeAll();
			imagePanel.removeAll();
		}
		
		imageFlow.setBackground(new Color (240, 240, 240));
		imagePanel.add(label, "wrap, span");
		imagePanel.add(imageFlow, "wrap");
		imagePanel.add(separator, "wrap");
		panel.add(browseButton);
		//panel.add(zoomButton);
		panel.add(helpButton);
		panel.add(deleteButton);
		panel.add(submitButton);
		panel.add(exitButton);
		panel.add(undoButton);
		panel.add(redoButton);
		panel.setBackground(new Color (240, 240, 240));
		this.pack();
		imageFlowExists = true;
		
		if(controller.isImageUndoStackEmpty())
			undoButton.setEnabled(false);
		else
			undoButton.setEnabled(true);
		
		if(controller.isImageRedoStackEmpty())
			redoButton.setEnabled(false);
		else
			redoButton.setEnabled(true);
		
		if(itemList.size() > 0 && !noImages) {
			deleteButton.setEnabled(true);
			submitButton.setEnabled(true);
			zoomButton.setEnabled(true);
		}
		else {
			deleteButton.setEnabled(false);
			submitButton.setEnabled(false);
			zoomButton.setEnabled(false);
		}
	}
	
	/**
	 * Adds the name of the image files to a new ImageFlowItem vector so that the
	 * respective images can later be shown in the screen.
	 * 
	 * @param imageList : Vector with the filenames of the images.
	 * @param date : Date selected in the calendar
	 */
	public void updateView(Vector<String> imageList, Date date) {
		Vector <ImageFlowItem> imageItemList = new Vector<ImageFlowItem>();
		String name;
		this.date = date;
		fileList.clear();

		if(imageList.elementAt(0).equals(getResourceURL("/images/noImage.png"))) {
			noImagesFound = true;
			noImages = true;
		}
		
		else {
			noImages = false;
			noImagesFound = false;
		}
		
		for(int i = 0; i < imageList.size(); i++) {
			name = imageList.elementAt(i);
			if(!name.equals(getResourceURL("/images/noImage.png")))
				fileList.add(imageList.elementAt(i));

			helperList.add(new ImageFlowItem(name, name)); 
			imageItemList.add(new ImageFlowItem(name, name));			
		}
		if(this.getName().equals("New Images Entry"))
			itemList.addAll(imageItemList);
		
		otherList = new Vector<ImageFlowItem>();
		otherList.addAll(imageItemList); 
		updateImages(imageItemList);
		helperList.clear();
		helperList.addAll(imageItemList);
	}
	
	/**
	 * Performs the actions of their respective buttons when they are clicked.
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("submit")) {
			controller.removeActiveView("image");
			
			if(controller.imageEntryExists(date))
				controller.editImageEntry(fileList, date);
			else
				controller.addImageEntry(fileList, date);
			controller.enableMain();
			controller.clearUndoImageStack();
			noImages = false;
			this.dispose();
		}
		
		else if(e.getActionCommand().equals("exit")) {
			controller.removeActiveView("image");
			controller.enableMain();
			controller.clearUndoImageStack();
			this.dispose();
		}
		
		else if(e.getActionCommand().equals("help")) {
			if(!messageFrame.isVisible()) { 
				messageFrame.setVisible(true);
			}
			else {
				messageFrame.requestFocus();
			}
		}
		
		else if(e.getActionCommand().equals("upload")) {
			controller.disableMain();
			fileChooser = new JFileChooser();
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.setFileFilter(new ImageFilter());
			
			int returnVal = fileChooser.showOpenDialog(this);
			
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				fileList.add(file.getAbsolutePath());
				noImages = false;
				if(!this.getName().equals("New Images Entry")) 
					itemList.addAll(otherList);
				
				ImageFlowItem item = new ImageFlowItem(file.getAbsolutePath(), file.getName());
				helperList.add(item);
				controller.addEdit(file.getAbsolutePath(), date, "insert", helperList.size() - 1);				
				
				for(int i = 0; i < helperList.size(); i++) {
					if(helperList.elementAt(i).getLabel().equals(getResourceURL("/images/noImage.png"))) {
						helperList.removeElementAt(i);
					}
				}

				updateImages(helperList);
			}
		}
		
		else if(e.getActionCommand().equals("delete")) {
			int index = imageFlow.getSelectedIndex();
						
			controller.addEdit(fileList.get(index), date, "delete", index);
			helperList.remove(index);
			fileList.remove(index);
			updateImages(helperList);
		}
		
		else if(e.getActionCommand().equals("undo")) {
			EditCommand edit = controller.getUndoAction(date);
			
			if(edit == null) {
				return;
			}

			if(edit.getAction().equals("insert")) {
				if(noImagesFound) {	
					ImageFlowItem item = new ImageFlowItem("images/noImage.png", "images/noImage.png");
					helperList.clear();
					helperList.add(item);
					updateImages(helperList);
					helperList.removeElement(0);
					submitButton.setEnabled(false);
					deleteButton.setEnabled(false);
				}
				else {
					fileList.remove(edit.getIndex());
					helperList.removeElementAt(edit.getIndex());

					updateImages(helperList);
				}
			}
			
			else if(edit.getAction().equals("delete")) {
				fileList.add(edit.getIndex(), edit.getElement());
				ImageFlowItem item = new ImageFlowItem(edit.getElement(), edit.getElement());
				helperList.add(edit.getIndex(), item);

				updateImages(helperList);
			}
		}
		
		else if(e.getActionCommand().equals("redo")) {
			
			EditCommand edit = controller.getRedoAction(date);
			
			if(edit == null)
				return;
			
			if(edit.getAction().equals("insert")) {
				if(noImagesFound) {	
					helperList.clear();
					ImageFlowItem item = new ImageFlowItem(edit.getElement(), edit.getElement());
					helperList.add(item);
					updateImages(helperList);
				}
				else {
					fileList.add(edit.getIndex(), edit.getElement());
					ImageFlowItem item = new ImageFlowItem(edit.getElement(), edit.getElement());
					helperList.add(edit.getIndex(), item);
					updateImages(helperList); 
				}	
			}
			
			else if(edit.getAction().equals("delete")) {
				fileList.remove(edit.getElement()); // may be replaced
				//fileList.remove(edit.getIndex()); //dont delete this
				helperList.removeElementAt(edit.getIndex());
				updateImages(helperList);
			}
		}
	}
	
    public URL getResourceURL(String path){
    	return this.getClass().getResource(path);
    }
	
	/**
	 * 
	 * @author Oracle.
	 *
	 */
	public class ImageFilter extends FileFilter {
		 
		public final static String jpeg = "jpeg";
	    public final static String jpg = "jpg";
	    public final static String gif = "gif";
	    public final static String tiff = "tiff";
	    public final static String tif = "tif";
	    public final static String png = "png";

	    //Accept all directories and all gif, jpg, tiff, or png files
	    
	    /**
	     * This method returns true if the file in the parameter has one of the accepted extensions,
	     * otherwise it returns null
	     * 
	     * @param f file whose extension will be analyzed.
	     * @return returns true if the file had the correct extension, otherwise false.
	     */
	    public boolean accept(File f) {
	        if (f.isDirectory()) {
	            return true;
	        }
	 
	        String extension = getExtension(f);
	        if (extension != null) {
	            if (extension.equals(tiff) ||
	                extension.equals(tif) ||
	                extension.equals(gif) ||
	                extension.equals(jpeg) ||
	                extension.equals(jpg) ||
	                extension.equals(png)) {
	                    return true;
	            } else {
	                return false;
	            }
	        }
	 
	        return false;
	    }
	   /**
	    *  This method extracts the extension of the file in the parameter
	    *  
	    * @param f File whose extension will be extracted
	    * @return The extension of the file
	    */
	    public String getExtension(File f) {
	        String ext = null;
	        String s = f.getName();
	        int i = s.lastIndexOf('.');

	        if (i > 0 &&  i < s.length() - 1) {
	            ext = s.substring(i+1).toLowerCase();
	        }
	        return ext;
	    }
	    
	    /**
	     * Returns the description of the file
	     * 
	     * @return The description of the file
	     */
	    public String getDescription() {
	    	return "";
	    }
	}
}
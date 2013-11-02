package data;

import java.io.Serializable;
import java.util.Vector;
import java.util.Date;
/**
 * ImageEntry stores the basic information of an image entry: the filenames of the images and the date of the entry.
 * @author  Fernando
 */
public class ImageEntry implements Serializable{
	private static final long serialVersionUID = -7930875026648799920L;
	Vector <String> imageList;
	Date date;
	/**
	 * Initializes the object with the parameters given.
	 * 
	 * @param imageList Vector containing the filenames of the images
	 * @param date Date selected in the calendar
	 */
	public ImageEntry(Vector <String> imageList, Date date) {
		this.imageList = imageList;
		this.date = date;
	}
	/**
	 * Returns the date of the ImageEntry
	 * @return  The date of the entry
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * Returns a vector containing the filenames of the images
	 * 
	 * @return A vector containing all of the filenames of the images in this entry
	 */
	public Vector <String> getLocation() {
		return imageList;
	}
	
	/**
	 * Sets a date for the entry
	 * @param date  The date to be set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * Sets the image filenames of this entry
	 *  
	 * @param imageList A vector containing the filenames of the images
	 */
	public void setImageLocation(Vector<String> imageList) {
		this.imageList = imageList;
	}
	
	/**
	 * Returns the number of image filenames in this entry
	 * 
	 * @return An integer with the number of image filenames
	 */
	public int getImageCount() {
		return imageList.size();
	}
	
	/**
	 * Removes the directories and file paths from the filename of the image
	 *  
	 * @param k 1 if the method is called by the search method, 0 otherwise
	 * @return The filenames of the images.
	 */
	public String getImageNames(int k){
		String retStr = "";
		
		for(int i = 0; i < imageList.size(); i++){
			String tempName = imageList.elementAt(i);
			
			if (k == 0){
				//remove extension from name
				for(int j = tempName.length() - 1; j > 0;j--){
					if (tempName.substring(j,j+1).equals(".")){
						tempName = tempName.substring(0,j);
						break;
					}
				}
			}
			
			//remove file path
			for(int j = tempName.length() - 1; j > 0;j--){
				if (tempName.substring(j,j+1).equals("/") || tempName.substring(j,j+1).equals("\\")){
					tempName = tempName.substring(j+1,tempName.length());
					break;
				}
			}
			
			if (i == imageList.size() - 1){
				retStr = retStr + "\"" + tempName + "\"" + ".";				
			} else {
				retStr = retStr + "\"" + tempName + "\", ";
			}
		}
		
		return retStr;
	}
	
	/**
	 * Searches the entry with the searchText. Retruns true if the text is found, otherwise false
	 * @param searchText
	 * @return True if the string is found, otherwise false
	 */
	public boolean search(String searchText) {
		String names = getImageNames(1).toLowerCase();
		String lowSearchText = searchText.toLowerCase();
		int index = names.indexOf(lowSearchText);
		
		if (index > -1){
			return true;
		}
		
		return false;
	}

	/**
	 * Returns a copy of this object
	 */
	public ImageEntry clone(){
		return new ImageEntry(this.getLocation(), this.getDate());
	}
}

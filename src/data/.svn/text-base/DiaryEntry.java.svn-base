package data;

import java.io.Serializable;
import java.util.Date;
/**
 * DiaryEntry stores the data of a diary entry such as the content, title and the date of the entry.
 * @author  Mihai
 */
public class DiaryEntry implements Serializable {
	
	private static final long serialVersionUID = -1037660896639765802L;
	private String content;
	private String title;
	private Date date;
	
	/**
	 * Constructor; initialize the title, content of a diary entry on Date date
	 */
	public DiaryEntry(String title, String content, Date date) {
		this.content = content;
		this.title = title;
		this.date = date;
	}
	
	/**
	 * returns the content of a DiaryEntry object
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * returns the title of a DiaryEntry object
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * returns the date of a DiaryEntry object
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * set the content of a DiaryEntry object
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * sets the title of a DiaryEntry object
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * sets the date of a DiaryEntry object
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	public boolean search(String searchText) {
		boolean retVal = false;
		String lowTitle = getTitle().toLowerCase();
		String lowSearchText = searchText.toLowerCase();
		int index = lowTitle.indexOf(lowSearchText);
		
		if (index > -1){
			return true;
		}
		
		String lowContent = getContent().toLowerCase();
		index = lowContent.indexOf(lowSearchText);
		
		if (index > -1){
			return true;
		}
		return retVal;
	}
	
	public DiaryEntry clone(){
		return new DiaryEntry(this.getTitle(), this.getContent(), this.getDate());
	}
}

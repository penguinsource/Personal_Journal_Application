package controller;
import java.io.File;
import java.io.IOException;

/**
 * This is the class that gets executed when the program is run. It creates the controller  and the necessary directories if they are not present, and launches the Journal Manager.
 * @author  Mikus Lorencs
 */
public class Main {

	private static Controller control;
	
	public static void main(String a[]) {
		control = new Controller();
		
		// check if journal config file exists (exits if program has been run before)
		File f = new File("userfiles/config/journal.config");
		if(!f.exists()){
			if (!(new File("userfiles").exists())){
				new File("userfiles/").mkdir();
				new File("userfiles/config/").mkdir();
				new File("userfiles/journals/").mkdir();
			}
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		
		control.startJournalSelect(f);	 
	}
}
	
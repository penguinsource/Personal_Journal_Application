package gui;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.*;

import controller.Controller;

/**
 * Implementing a tag cloud for topics entered by the user
 * @author Mihai, Haoran
 * 
 * 
 */
public class TagCloudFrame implements ActionListener{

	private static JPanel panel;
	private JFrame frame;
	
	private Map<String, Integer> hello;
	private Vector<String> sortedWords;
	private Iterator<String> it;
	private Controller control;
	private Map<String, Integer> mapWordFreq;	
	
	public TagCloudFrame(Controller control_arg, Date date_arg){
		
		control = control_arg;
		frame = new JFrame("Tag Cloud");
		frame.setSize(400,400);
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        //panel.setBackground(new Color(240, 240, 240));
        //panel.setBackground(new Color(51,51,51));
        panel.setBackground(new Color(241,241,241));
        panel.setMaximumSize(new Dimension(400, 2000));
        frame.setMaximumSize(new Dimension(400, 2000));
        frame.add(panel);
       
        // check if there are any topic entries in the journal
        //date_arg is the current date, IsTopicExist checks whether the topics exist on this date.
        if (control.getAllTopics()!=""){
        	this.createCloud();
        }else{
        }
        
        frame.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {

		    	    control.enableMain();
		    	    control.removeActiveView("tagcloud");
		      }
		}); 

        
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
		
	}
	
	public void createCloud(){
		
		this.sortWords();
		
        // how many times the most popular word appeared
        int maxWordCount = mapWordFreq.get(sortedWords.get(0));		
        // how many times the least popular word appeared
        int minWordCount = mapWordFreq.get(sortedWords.get(sortedWords.size() - 1));
        // the max font size that can be displayed (set here)
        int maxFontSize = 80;
        
        // init an int that will be used to calculate the FontSizeToDisplay
        int currentWordCount = 0; 
        
        // init set the font size to display
        int FontSizeToDisplay = 10;
        
        JLabel[] labels = new JLabel[50];
        
        Collections.sort(sortedWords);
        Color colorLightBlue = new Color(52,193,253);
        Color colorGreen = new Color(79,229,104);
        Color colorWhite = new Color(255,255,255);
        
        
        Color colorSet = new Color(181,181,181);
        Color colorGray = new Color(181,181,181);
        Color colorDarkGray = new Color(41,41,41);
        for (int i = 0; i < sortedWords.size(); i++){
        	currentWordCount = mapWordFreq.get(sortedWords.get(i));
        	
            if (currentWordCount > minWordCount){	
            	FontSizeToDisplay = ( maxFontSize * (currentWordCount - minWordCount) ) / (maxWordCount - minWordCount);  
            }else{ 
            	FontSizeToDisplay = 10;
            }

            if (FontSizeToDisplay < 10){
            	FontSizeToDisplay = 10;
            }
            
            if (sortedWords.size() == 1){
            	FontSizeToDisplay = 25;
            }
            
            if (FontSizeToDisplay < 20){
            	colorSet = colorDarkGray;
            }else if (FontSizeToDisplay > 20){
            	colorSet = colorLightBlue;
            }else if (FontSizeToDisplay > 40){
            	colorSet = colorLightBlue;
            }else if (FontSizeToDisplay >60){
            	colorSet = colorLightBlue;
            }
            
            Font fontSelected = new Font("Century Schoolbook L Roman", Font.PLAIN, FontSizeToDisplay);
            labels[i] = new JLabel(sortedWords.get(i));
            labels[i].setFont(fontSelected);
            labels[i].setForeground(colorSet);
            panel.add(labels[i]);
            
        }
        
	}
	
	public void sortWords(){
		
        // SORTING THE WORDS
        sortedWords = new Vector<String>();
        hello = tag_cloud(getAllTopics());

        mapWordFreq = tag_cloud(getAllTopics());
                
        it = hello.keySet().iterator();
        
        String max = it.next();
       
        while (hello.size() > 0){

        	while (it.hasNext()){
        		String key = it.next();
        		if (hello.get(key) > hello.get(max)){
        			max = key;

        		}
        	}
        	// this is adding the max from what is left in the 'hello' vector
        	sortedWords.add(max);
        	// remove the max that has been added/sorted already
        	hello.remove(max);
        		if (hello.isEmpty()){
        			break;
        		}else{
        			it = hello.keySet().iterator();
        			max = it.next();
        		}
        
		}
        
	}
	
	/**
	 * Takes a string and processes it into a hash table.
	 * @param str
	 */
	public static Map<String, Integer> tag_cloud(String str){
		Map<String, Integer> tags = new HashMap<String, Integer>();
	    Vector <String> topicLines = new Vector<String>();
	    
	    str = str.toLowerCase();
	    
	    StringTokenizer st = new StringTokenizer(str,"\n");
	    
	    // add each topic entry line to vector topicLines
	    while (st.hasMoreTokens())
	    {
	    	topicLines.add(st.nextToken());
	    }
	    // go through each topic entry line and split it word by word (saves all in wordTokenizer)
	    // while there are still words in wordTokenizer
	    // if word exists in 'tags', then increase its frequency by 1; if it doesn't exist, set its
	    // frequency to 1
	    for (int i = 0; i < topicLines.size(); i++){
	    	StringTokenizer wordTokenizer = new StringTokenizer(topicLines.get(i), " ");
	    	while (wordTokenizer.hasMoreTokens()){
	    		String one_word = wordTokenizer.nextToken();
	    		// if the word is already in the tags, increase its frequency by 1
		    	if(tags.get(one_word) != null){
		    		tags.put(one_word, tags.get(one_word)+1);
		    	}else{ 
		    		// if the word is not in the tags object, then initiate it and set its value to 1
		    		tags.put(one_word, 1);
		    	}
	    	}	    	
	    }
	    return tags;
	}
	
    public void disposeFrame(){
    	frame.dispose();
    }
    
	
	public void updateView(){
		frame.remove(panel);
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));        
        //panel.setBackground(new Color(51,51,51));
        panel.setBackground(new Color(241,241,241));
		this.createCloud();
		frame.add(panel);
		panel.revalidate();
	}
	
	public void printAllTopics(){
		control.getAllTopics();
	}
	
	public String getAllTopics(){
		return control.getAllTopics();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
}
package controller;

import javax.swing.JOptionPane;

import data.TwitterAccount;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

/**
 * This class specifies the tweeting behavior. It has methods to open the browser and tweet entries to Twitter.
 * 
 * @author  Fernando , Mikus Lorencs
 */
public class TwitterCommand {
	private TwitterAccount twitterAccount;
	private TwitterAccount oldAccount;
	private RequestToken requestToken;
	private Twitter twitter;
	private boolean twitterIsReady = false;
	private Controller control;
	
	public TwitterCommand(TwitterAccount twitterAccount, Controller control) {
		this.control = control;
		this.twitterAccount = twitterAccount;
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(twitterAccount.getConsumerKey(), twitterAccount.getConsumerSecret());
		twitter.setOAuthAccessToken(twitterAccount.accessToken);
	}
	
	/**
	 * Invoked when user wants to authenticate an account from the day view or re-authenticate
	 * another account from the preferences window.
	 * Saves the old account to a temporary pointer (in case user cancels) and instantiates a new
	 * twitter account for which it attempts to gain authentication
	 */
	public void initiate() {
		try {
			oldAccount = twitterAccount;
			twitterAccount = new TwitterAccount();
			control.setTwitterAccount(twitterAccount);
			twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(twitterAccount.getConsumerKey(), twitterAccount.getConsumerSecret());
			twitter.setOAuthAccessToken(twitterAccount.accessToken);
			requestToken = twitter.getOAuthRequestToken();
			try {
				String url = requestToken.getAuthorizationURL();
				openBrowser(url);
		    	}
			catch ( Exception e ) {
				System.err.println( e.getMessage() );
			}
		} catch(TwitterException e) {}
	}
		
	/**
	 * Opens the user's default web browser with the URL selected
	 * 
	 * @param url The address of the website
	 */
	public void openBrowser(String url) {
		String os = System.getProperty("os.name").toLowerCase();
	        Runtime rt = Runtime.getRuntime();
	 
		try{
	 
		    if (os.indexOf( "win" ) >= 0) {
		        rt.exec( "rundll32 url.dll,FileProtocolHandler " + url);
	 
		    } else if (os.indexOf( "mac" ) >= 0) {
	 
		        rt.exec( "open " + url);
	 
	            } else if (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0) {
	 
		        // Do a best guess on unix until we get a platform independent way
		        // Build a list of browsers to try, in this order.
		        String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
		       			             "netscape","opera","links","lynx"};
	 
		        // Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
		        StringBuffer cmd = new StringBuffer();
		        for (int i=0; i<browsers.length; i++)
		            cmd.append( (i==0  ? "" : " || " ) + browsers[i] +" \"" + url + "\" ");
	 
		        rt.exec(new String[] { "sh", "-c", cmd.toString() });
	 
	           } else {
	                return;
	           }
	       }catch (Exception e){
		    return;
	       }
	      return;		
	}
	
	/**
	 * Attempts to authenticate using the provided PIN and saves the info into the twitterAccount object.
	 * @param pin
	 * @return 1 if the PIN is successfully authenticated
	 */
	public int addPIN(String pin) {
		try {
			try{
				if(pin.length() > 0) {
					twitterAccount.accessToken = twitter.getOAuthAccessToken(requestToken, pin);
				}
				else {
					twitterAccount.accessToken = twitter.getOAuthAccessToken();
				}
			} catch (TwitterException te) {
				if(401 == te.getStatusCode()){
					System.out.println("Unable to get the access token.");
					return 0;
				}
				else {
					return 0;
				}
			}
			twitter.verifyCredentials();
			twitterAccount.setAccessToken(twitterAccount.accessToken.getToken());            // Save to file instead
			twitterAccount.setAccessSecret(twitterAccount.accessToken.getTokenSecret());    // Save to file instead
			twitterAccount.setAccount();
			//Setup our twitter client with the access token we just received.
			twitter.setOAuthAccessToken(twitterAccount.accessToken);
			twitterIsReady = true;
			if(twitterIsReady){ 
			}
		} catch(TwitterException te) {}
		return 1;
	}
	
	/**
	 * Tweets the specified string.
	 * @param tweetString string to tweet
	 * @return returns 1 if tweet is successful, otherwise 0
	 */
	public int tweet(String tweetString) {
		if(!twitterAccount.isAccountSet()) {
			return 0;
		}
		try {  
			twitter.setOAuthAccessToken(twitterAccount.accessToken);
			twitter.setOAuthAccessToken(twitterAccount.accessToken);
			twitter.getAuthorization();
			twitter.updateStatus(tweetString);
		} catch (TwitterException e) { 
			JOptionPane.showMessageDialog(null, e.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		return 1;
	}
	
	/**
	 * Sets the account to the previously saved twitterAccount in the case that a user cancels the authentication proccess or 
	 * doesn't save the preferences (hits cancel)
	 */
	public void resetAccount(){
		if (oldAccount != null){
			twitterAccount = oldAccount;
			control.setTwitterAccount(twitterAccount);
		}
	}
	
	/**
	 * Returns true if the twitter account is ready to be used, otherwise false
	 * 
	 * @return Returns true is the twitter account is ready, otherwise false
	 */
	public boolean isTwitterReady() {
		return twitterAccount.isAccountSet();
	}

	/**
	 * Sets the twitter account ready or not.
	 * 
	 * @param b a boolean specifying the status of the twitteraccount.
	 */
	public void setTwitterReady(boolean b) {
		twitterIsReady = b;
	}	
	
	/**
	 * Clears any account pointers and makes a new account (for when user hits "remove account" in preferences)
	 */
	public void resetAcountInfo(){
		oldAccount = null;
		twitterAccount = new TwitterAccount();
	}

	/**
	 * Backs up the current account and makes a new account object (in case user cancels authentication)
	 */
	public void backupAccount() {
		oldAccount = twitterAccount;
		twitterAccount = new TwitterAccount();
	}
}

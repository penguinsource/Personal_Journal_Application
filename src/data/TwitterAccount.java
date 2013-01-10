package data;

import java.io.Serializable;
import twitter4j.auth.AccessToken;
/**
 * This class holds the basic information about the user's Twitter login information, as well as this program's keys to use Twitter.
 * @author  Fernando
 */
public class TwitterAccount implements Serializable {

	private static final long serialVersionUID = 5581320342702306654L;
	private String CONSUMER_KEY = "3dL7DaLfm4tqbuq5n7NzQ"; 
	private String CONSUMER_SECRET = "zz8N7QeOkUg6a6bg0R7PmrLWKzHRIsVCPUxWTqXHAI";
	private String ACCESS_TOKEN;
	private String ACCESS_SECRET;
	private boolean accountSet;
	public AccessToken accessToken;
	
	/**
	 * This method returns the consumer key of this application
	 * 
	 * @return The consumer key.
	 */
	public String getConsumerKey() {
		return CONSUMER_KEY;
	}
	
	/**
	 * This method returns the consumer secret of this application.
	 * 
	 * @return The consumer secret
	 */
	public String getConsumerSecret() {
		return CONSUMER_SECRET;
	}
	
	/**
	 * This method returns the access token provided by Twitter.
	 * @return  The access token.
	 */
	public String getAccessToken() {
		return ACCESS_TOKEN;
	}
	
	/**
	 * This method returns the access secret provided by Twitter.
	 * 
	 * @return The access secret
	 */
	public String getAccessSecret() {
		return ACCESS_SECRET;
	}
	
	/**
	 * Sets the ACCESS_TOKEN constant with a value provided by Twitter.
	 * 
	 * @param accessToken The access token provided by Twitter.
	 */
	public void setAccessToken(String accessToken) {
		ACCESS_TOKEN = accessToken;
	}
	
	/**
	 * Sets the ACCESS_SECRET constant with a value provided by Twitter.
	 * 
	 * @param accessSecret The access secret provided by Twitter.
	 */
	public void setAccessSecret(String accessSecret) {
		ACCESS_SECRET = accessSecret;
		accountSet = true;
	}
	
	/**
	 * Checks if the account is ready for tweeting.
	 * @return  True if the account is ready, otherwise false.
	 */
	public boolean isAccountSet() {
		return accountSet;
	}

	/**
	 * Sets the account ready.
	 */
	public void setAccount() {
		accountSet = true;		
	}

}

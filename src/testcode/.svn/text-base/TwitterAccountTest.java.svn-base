package testcode;

import static org.junit.Assert.*;

import org.junit.*;

import data.TwitterAccount;

/**
 * This class provides test methods for the TwitterAccount class
 * @author  Fernando
 */
public class TwitterAccountTest {

	private TwitterAccount twitterAccount;
	
	@Before
	/**
	 * Instantiates the TwitterAccount object before each method is invoked
	 */
	public void setUp() {
		twitterAccount = new TwitterAccount();
	}
	@Test
	/**
	 * The key provided is expected to be equal, as it's unique for this application
	 */
	public void testGetConsumerKey() {
		assertEquals("3dL7DaLfm4tqbuq5n7NzQ", 
				twitterAccount.getConsumerKey());
	}
	
	@Test
	/**
	 * The consumer secret provided is expected to be equal, as it's unique for this application
	 */
	public void testGetConsumerSecret() {
		assertEquals("zz8N7QeOkUg6a6bg0R7PmrLWKzHRIsVCPUxWTqXHAI",
				twitterAccount.getConsumerSecret());
	}
	
	@Test
	/**
	 * 	Expects the token to be the same as the one we set
	 */
	public void testGetAccessToken() {
		twitterAccount.setAccessToken("token");
		assertEquals("token", twitterAccount.getAccessToken());
	}
	
	@Test
	/**
	 * Expects the secret to be the same as the one we set
	 */
	public void testGetAccessSecret() {
		twitterAccount.setAccessSecret("secret");
		assertEquals("secret", twitterAccount.getAccessSecret());
	}
	
	@Test
	/**
	 * Expects the account to be set after entering an access secret.
	 */
	public void testIsAccountSet() {
		assertFalse(twitterAccount.isAccountSet()); 
		twitterAccount.setAccessSecret("secret");
		assertTrue(twitterAccount.isAccountSet());
	}
}

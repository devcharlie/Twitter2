import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.tartarus.snowball.util.StemmerException;

import twitter4j.TwitterException;

public class mainClass {
	
	public static TwitterClient tw;
	
	public static void search(String qstr) {
		try {
			tw.startSeachAPI(qstr); 
			} catch (TwitterException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace(); 
			}
	}
	
	public static void userTimeline(String user) {
		try {
			tw.getUserTimeLine(user); 
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
	
	//Method to TRY and search for tweets and put them in private variable TwitterClient.tokens
	public static void tweets(String tag) throws ParseException, StemmerException {
		try {
			tw.startSeachAPI(tag);
		} catch (TwitterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException, StemmerException, ParseException {
		String CONSUMER_KEY = "H6zM8MPjSFUY3gTkPyKzwVnHe";
		String CONSUMER_KEY_SECRET = "DAfOn9skr9wsMwcqiACRuxWSEtSrN5GhX6SJc6H5NQMZuzN49A";
		tw = new TwitterClient(CONSUMER_KEY, CONSUMER_KEY_SECRET);

		tweets("mufc");
		
	}
}
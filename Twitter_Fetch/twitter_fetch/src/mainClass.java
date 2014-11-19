import java.io.IOException;
import twitter4j.TwitterException;

public class mainClass {
	
	public static TwitterClient tw;
	
	public static void search(String qstr) {
		try {
			tw.startSeachAPI(qstr); 
			} catch (TwitterException e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block 
				e.printStackTrace(); 
			}
	}
	
	public static void userTimeline(String user) {
		try {
			tw.getUserTimeLine(user); 
		} catch (TwitterException e) {
			// TODO Auto-generated catch block e.printStackTrace(); }
		}
	}
	
	//Method to TRY and search for tweets and put them in private variable TwitterClient.tokens
	public static void tweets(String tag) {
		try {
			tw.startSeachAPI(tag);
		} catch (TwitterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String CONSUMER_KEY = "H6zM8MPjSFUY3gTkPyKzwVnHe";
		String CONSUMER_KEY_SECRET = "DAfOn9skr9wsMwcqiACRuxWSEtSrN5GhX6SJc6H5NQMZuzN49A";
		tw = new TwitterClient(CONSUMER_KEY, CONSUMER_KEY_SECRET);
		//search("galway");
		//userTimeline("jteevan"); // only recent 200
		tweets("nuig");
		System.out.println(tw.toString());
	}
}
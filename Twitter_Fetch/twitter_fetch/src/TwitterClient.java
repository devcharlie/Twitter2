import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.IOException;

import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuth2Token;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterClient {
	private String CONSUMER_KEY;
	private String CONSUMER_KEY_SECRET;
	private ArrayList <String[]> tokens = new ArrayList<String[]>();
	private ConfigurationBuilder cb;
	String delim = " ";
	
	//Constructor
	public TwitterClient(String KEY, String KEY_SECRET){
		CONSUMER_KEY = KEY;
		CONSUMER_KEY_SECRET = KEY_SECRET;
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(CONSUMER_KEY)
		  .setOAuthConsumerSecret(CONSUMER_KEY_SECRET)
		  .setApplicationOnlyAuthEnabled(true);
	}
	
	//Getters
	public String getConsumerKey()
	{
		return CONSUMER_KEY;
	}	

	public String getConsumerKeySecret()
	{
		return CONSUMER_KEY_SECRET;
	}	
	
	public ArrayList<String[]> getSearchResults()
	{
		return tokens;
	}
	
	//Mutators
	public void setConsumerKey(String ck)
	{
		CONSUMER_KEY = ck;
	}
	
	public void setConsumerKeySecret(String cks)
	{
		cks =  CONSUMER_KEY_SECRET;
	}
		
	//Results toString
	public String toString()
	{
		String string = "";
		for(int i=0; i<tokens.size(); i++)
		{
			String[] temp = tokens.get(i);
			string += "This List is: ";
			for(int j=0; j<temp.length; j++)
			{
				
				string += temp[j];
			}
			string += "\n";
		}
		return string;
	}
	
	public void startSeachAPI(String qstr) throws TwitterException, IOException{
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		ArrayList<String> tweet = new ArrayList<String>();
		
		OAuth2Token token = twitter.getOAuth2Token();		
	    if (token != null) {
	        System.out.println("Token Type  : " + token.getTokenType());
	        System.out.println("Access Token: " + token.getAccessToken());
	    }
	    
	    Query query = new Query(qstr);
		query.setCount(10);
		
		QueryResult result = twitter.search(query);

	    for (Status status : result.getTweets()) {

	        //System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
	        String[] temp = status.getText().split(delim);
	        tokens.add(temp);
	    }
	}
	
	public void getUserTimeLine(String user) throws TwitterException{
			TwitterFactory tf = new TwitterFactory(cb.build());
			Twitter twitter = tf.getInstance();			
			OAuth2Token token = twitter.getOAuth2Token();
			
			if (token != null) {
		        System.out.println("Token Type  : " + token.getTokenType());
		        System.out.println("Access Token: " + token.getAccessToken());
		    }
			
			List<Status> statuses;
            statuses = twitter.getUserTimeline(user);
            System.out.println("Showing @" + user + "'s user timeline.");
            
            for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }
        }

	public void stopWordRemoval()
	{
		
	}
}
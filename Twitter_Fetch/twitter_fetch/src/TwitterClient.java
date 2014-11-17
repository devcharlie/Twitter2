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
	//String[] tokens;
	String delims=" ";
	String[] keywords;
	
	
	ArrayList <String> tokens = new ArrayList();
	
	public TwitterClient(String KEY, String KEY_SECRET){
		CONSUMER_KEY = KEY;
		CONSUMER_KEY_SECRET = KEY_SECRET;
	}
	
	public void startSeachAPI(String qstr) throws TwitterException, IOException{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(CONSUMER_KEY)
		  .setOAuthConsumerSecret(CONSUMER_KEY_SECRET)
		  .setApplicationOnlyAuthEnabled(true);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		
		OAuth2Token token = twitter.getOAuth2Token();		
	    if (token != null) {
	        System.out.println("Token Type  : " + token.getTokenType());
	        System.out.println("Access Token: " + token.getAccessToken());
	    }
	    Query query = new Query(qstr);
		query.setCount(1000);
		//long id = (long) 422204137108168705;
		//result.getSinceId();
		QueryResult result = twitter.search(query);
		int i=0;
		String[] temp;
	    for (Status status : result.getTweets()) {
	        //System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
	        temp = status.getText().split(delims);
	        for(int k=0; k<temp.length; k++)
	        {
	        	//tokens.add(temp[k]);
	        	//System.out.println(temp[k]);
	        	String tmp = temp[k].toString() + "\n";
	        	tokens.add(tmp);
	        }
	    }
	    System.out.println(tokens);
	}
	
	public void getUserTimeLine(String user) throws TwitterException{
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
			  .setOAuthConsumerKey(CONSUMER_KEY)
			  .setOAuthConsumerSecret(CONSUMER_KEY_SECRET)
			  .setApplicationOnlyAuthEnabled(true);
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
}
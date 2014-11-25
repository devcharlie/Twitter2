import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.tartarus.snowball.EnglishSnowballStemmerFactory;
import org.tartarus.snowball.util.StemmerException;

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
	private ConfigurationBuilder cb;
	
	
	// Constructor
	public TwitterClient(String KEY, String KEY_SECRET) {
		CONSUMER_KEY = KEY;
		CONSUMER_KEY_SECRET = KEY_SECRET;
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(CONSUMER_KEY)
				.setOAuthConsumerSecret(CONSUMER_KEY_SECRET)
				.setApplicationOnlyAuthEnabled(true);
	}

	// Getters
	
	public String getConsumerKey() {
		return CONSUMER_KEY;
	}

	public String getConsumerKeySecret() {
		return CONSUMER_KEY_SECRET;
	}

	// Mutators
	public void setConsumerKey(String ck) {
		CONSUMER_KEY = ck;
	}

	public void setConsumerKeySecret(String cks) {
		cks = CONSUMER_KEY_SECRET;
	}

	// Other methods
	public void startSeachAPI(String qstr) throws TwitterException, IOException, ParseException, StemmerException {
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		// ArrayList<String> tweet = new ArrayList<String>();

		OAuth2Token token = twitter.getOAuth2Token();
		if (token != null) {
			System.out.println("Token Type  : " + token.getTokenType());
			System.out.println("Access Token: " + token.getAccessToken());
		}

		Tweet tweet = null;
		Query query = new Query(qstr);
		query.setUntil("2014-11-22");
		//query.setCount(100);
		
		//query.setUntil("2014-11-23");
		
		int numberOfTweets = 2000;
		long lastID = 0;
		int count = 0;
		while (count < numberOfTweets) {
			if (numberOfTweets - count > 100){
				query.setCount(100);
			}
		    else {
		    	query.setCount(numberOfTweets - count);
		    }
			 //System.out.println("MaxID" + query.getMaxId());

			ArrayList<Status> tweets = new ArrayList<Status>();
				
		    try {
			    QueryResult result = twitter.search(query);
			    tweets.addAll(result.getTweets());
			    count += tweets.size();
			    for (Status status : tweets) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			        String date = sdf.format(status.getCreatedAt());
					String[] temp = status.getText().split(" ");
					//System.out.println("Date: " + date + " : MaxId : " + result.getMaxId() + " : id : " + status.getId());			        
					ArrayList<String> hold = new ArrayList<String>();
					for (int i = 0; i < temp.length; i++) {
						hold.add(temp[i]);
					}
					tweet = new Tweet(hold, date);
					getHashtags(tweet);
					removePunctuation(tweet);
					//Stem(tweet);
					StopWordRemoval(tweet);
					WriteFile(tweet);
				    lastID = status.getId();
				}
			    System.out.println("Gathered " + count);
	
			}catch (TwitterException te) {
		    	System.out.println("Couldn't connect: " + te);
		    }; 
		    query.setMaxId(lastID);
		}
	}

	public void getUserTimeLine(String user) throws TwitterException {
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
			System.out.println("@" + status.getUser().getScreenName() + " - "
					+ status.getText());

		}
	}

	public String readFile() throws IOException {
		String strFileContent = "";
		File file = new File("Data.txt");
		try {
			FileInputStream fin = new FileInputStream(file);

			byte fileContent[] = new byte[(int) file.length()];

			fin.read(fileContent);
			strFileContent = new String(fileContent);
			fin.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the file " + ioe);
		}
		return strFileContent;
	}

	public void WriteFile(Tweet tweet) throws IOException {
		File f = new File("tweets.csv");
		//f.exists()
		if(1==0)
		{
			FileWriter writer = new FileWriter("tweets.csv", true);
				
			writer.append(tweet.getDate());
			writer.append(',');
	
			ListIterator<String> it = tweet.getHashTags().listIterator();
			while (it.hasNext()) {
				String hold= it.next();
				writer.append(hold);
				writer.append(' ');
			}
			writer.append(',');
				
			it = tweet.getAtTags().listIterator();
			while (it.hasNext()) {
				String hold= it.next();
				writer.append(hold);
				writer.append(' ');
			}
			writer.append(',');
			
			it = tweet.getTweet().listIterator();
			while (it.hasNext()) {
				String hold= it.next();
				writer.append(hold);
				writer.append(' ');
			}
			writer.append('\n');
			writer.flush();

			writer.close();
		}
		else
		{
			FileWriter writer = new FileWriter("tweets.csv", true);
			/*writer.append("Time/Date");
			writer.append(",");
			writer.append("HashTag");
			writer.append(",");
			writer.append("AtTag");
			writer.append(",");
			writer.append("Tweet");
			writer.append(",");
			writer.append('\n');
			writer.flush();*/
			
			writer.append(tweet.getDate());
			writer.append(',');
	
			ListIterator<String> it = tweet.getHashTags().listIterator();
			while (it.hasNext()) {
				String hold= it.next();
				writer.append(hold);
				writer.append(' ');
			}
			writer.append(',');
				
			it = tweet.getAtTags().listIterator();
			while (it.hasNext()) {
				String hold= it.next();
				writer.append(hold);
				writer.append(' ');
			}
			writer.append(',');
			
			it = tweet.getTweet().listIterator();
			while (it.hasNext()) {
				String hold= it.next();
				writer.append(hold);
				writer.append(' ');
			}
			writer.append('\n');
			writer.flush();

			writer.close();
		}
	}

	public void Stem(Tweet tweet) throws StemmerException{
		ListIterator<String> iterate = tweet.getTweet().listIterator();

		while (iterate.hasNext()) {
			String string = iterate.next();
			string = string.toLowerCase();
			string = EnglishSnowballStemmerFactory.getInstance().process(string);
			iterate.set(string);
		}
	}

	public void StopWordRemoval(Tweet tweet) {
		String[] stopWordsofwordnet = { "rt", "day", "they", "you", "is", "to", "with",
				"the", "this", "a", "able", "about", "above", "abst",
				"accordance", "according", "accordingly", "across", "act",
				"actually", "added", "adj", "affected", "affecting", "affects",
				"after", "afterwards", "again", "against", "ah", "all",
				"almost", "alone", "along", "already", "also", "although",
				"always", "am", "among", "amongst", "an", "and", "announce",
				"another", "any", "anybody", "anyhow", "anymore", "anyone",
				"anything", "anyway", "anyways", "anywhere", "apparently",
				"approximately", "are", "aren", "arent", "arise", "around",
				"as", "aside", "ask", "asking", "at", "auth", "available",
				"away", "awfully", "b", "back", "be", "became", "because",
				"become", "becomes", "becoming", "been", "before",
				"beforehand", "begin", "beginning", "beginnings", "begins",
				"behind", "being", "believe", "below", "beside", "besides",
				"between", "beyond", "biol", "both", "brief", "briefly", "but",
				"by", "c", "ca", "came", "can", "cannot", "can't", "cause",
				"causes", "certain", "certainly", "co", "com", "come", "comes",
				"contain", "containing", "contains", "could", "couldnt", "d",
				"date", "did", "didn't", "different", "do", "does", "doesn't",
				"doing", "done", "don't", "down", "downwards", "due", "during",
				"e", "each", "ed", "edu", "effect", "eg", "eight", "eighty",
				"either", "else", "elsewhere", "end", "ending", "enough",
				"especially", "et", "et-al", "etc", "even", "ever", "every",
				"everybody", "everyone", "everything", "everywhere", "ex",
				"except", "f", "far", "few", "ff", "fifth", "first", "five",
				"fix", "followed", "following", "follows", "for", "former",
				"formerly", "forth", "found", "four", "from", "further",
				"furthermore", "g", "gave", "get", "gets", "getting", "give",
				"given", "gives", "giving", "go", "goes", "gone", "got",
				"gotten", "h", "had", "happens", "hardly", "has", "hasn't",
				"have", "haven't", "having", "he", "hed", "hence", "her",
				"here", "hereafter", "hereby", "herein", "heres", "hereupon",
				"hers", "herself", "hes", "hi", "hid", "him", "himself", "his",
				"hither", "home", "how", "howbeit", "however", "hundred", "i",
				"id", "ie", "if", "i'll", "im", "immediate", "immediately",
				"importance", "important", "in", "inc", "indeed", "index",
				"information", "instead", "into", "invention", "inward", "is",
				"isn't", "it", "itd", "it'll", "its", "itself", "i've", "j",
				"just", "k", "keep 	keeps", "kept", "kg", "km", "know",
				"known", "knows", "l", "largely", "last", "lately", "later",
				"latter", "latterly", "least", "less", "lest", "let", "lets",
				"like", "liked", "likely", "line", "little", "'ll", "look",
				"looking", "looks", "ltd", "m", "made", "mainly", "make",
				"makes", "many", "may", "maybe", "me", "mean", "means",
				"meantime", "meanwhile", "merely", "mg", "might", "million",
				"miss", "ml", "more", "moreover", "most", "mostly", "mr",
				"mrs", "much", "mug", "must", "my", "myself", "n", "na",
				"name", "namely", "nay", "nd", "near", "nearly", "necessarily",
				"necessary", "need", "needs", "neither", "never",
				"nevertheless", "new", "next", "nine", "ninety", "no",
				"nobody", "non", "none", "nonetheless", "noone", "nor",
				"normally", "nos", "not", "noted", "nothing", "now", "nowhere",
				"o", "obtain", "obtained", "obviously", "of", "off", "often",
				"oh", "ok", "okay", "old", "omitted", "on", "once", "one",
				"ones", "only", "onto", "or", "ord", "other", "others",
				"otherwise", "ought", "our", "ours", "ourselves", "out",
				"outside", "over", "overall", "owing", "own", "p", "page",
				"pages", "part", "particular", "particularly", "past", "per",
				"perhaps", "placed", "please", "plus", "poorly", "possible",
				"possibly", "potentially", "pp", "predominantly", "present",
				"previously", "primarily", "probably", "promptly", "proud",
				"provides", "put", "q", "que", "quickly", "quite", "qv", "r",
				"ran", "rather", "rd", "re", "readily", "really", "recent",
				"recently", "ref", "refs", "regarding", "regardless",
				"regards", "related", "relatively", "research", "respectively",
				"resulted", "resulting", "results", "right", "run", "s",
				"said", "same", "saw", "say", "saying", "says", "sec",
				"section", "see", "seeing", "seem", "seemed", "seeming",
				"seems", "seen", "self", "selves", "sent", "seven", "several",
				"shall", "she", "shed", "she'll", "shes", "should",
				"shouldn't", "show", "showed", "shown", "showns", "shows",
				"significant", "significantly", "similar", "similarly",
				"since", "six", "slightly", "so", "some", "somebody",
				"somehow", "someone", "somethan", "something", "sometime",
				"sometimes", "somewhat", "somewhere", "soon", "sorry",
				"specifically", "specified", "specify", "specifying", "still",
				"stop", "strongly", "sub", "substantially", "successfully",
				"such", "sufficiently", "suggest", "sup", "sure" };
		
		for (int j = 0; j < stopWordsofwordnet.length; j++) 
		{
			ListIterator<String> it = tweet.getTweet().listIterator();
			while (it.hasNext()) {
				String string = it.next();
				if (string.compareTo(stopWordsofwordnet[j]) == 0) {
					it.remove();
					j=0;
				}
			}
		}
	}
	
	public void removePunctuation(Tweet tweet) {
		ListIterator<String> iterate = tweet.getTweet().listIterator();
		while (iterate.hasNext()) {
			String string = iterate.next();
			string = string.replaceAll("[^0-9a-zA-Z]", "");
			string = string.toLowerCase();
			iterate.set(string);
		}
		
		iterate = tweet.getHashTags().listIterator();
		while (iterate.hasNext()) {
			String string = iterate.next();
			string = string.replaceAll("[^0-9a-zA-Z]", "");
			string = string.toLowerCase();
			iterate.set(string);
		}
		
		iterate = tweet.getAtTags().listIterator();
		while (iterate.hasNext()) {
			String string = iterate.next();
			string = string.replaceAll("[^0-9a-zA-Z]", "");
			string = string.toLowerCase();
			iterate.set(string);
		}
	}

	public void getHashtags(Tweet tweet) {
		ListIterator<String> iterate = tweet.getTweet().listIterator();
		while (iterate.hasNext()) {
			String string = iterate.next();
			if (string.contains("http")) {
				iterate.remove();
			}
			else if (string.contains("#")) {
				tweet.addHashTag(string);
				iterate.remove();
			}
			else if (string.contains("@")) {
				tweet.addAtTag(string);
				iterate.remove();
			}
		}
	}
}
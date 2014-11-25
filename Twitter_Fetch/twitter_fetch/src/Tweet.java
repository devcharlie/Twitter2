import java.util.ArrayList;
import java.util.ListIterator;

public class Tweet {
	private ArrayList<String> hashTags = new ArrayList<String>();
	private String date;
	private ArrayList<String> atTags = new ArrayList<String>();
	private ArrayList<String> tweet;
	
	public Tweet(ArrayList<String> hash, ArrayList<String> at, ArrayList<String> tweet1, String date1 )
	{
		hashTags = hash;
		tweet = tweet1;
		atTags = at;
		date = date1;
	}
	
	public Tweet(ArrayList<String> tweet1, String date1 )
	{	
		tweet = tweet1;
		date = date1;
	}
	
	public Tweet()
	{
	}
	
	public void setHashTags(ArrayList<String> hash)
	{
		hashTags = hash;
	}
	
	public void setTweet(ArrayList<String> tweet1)
	{
		tweet = tweet1;
	}
	
	public void setAtTags(ArrayList<String> at)
	{
		atTags = at;
	}
	
	public void setDate(String date1)
	{
		date = date1;
	}
	
	public ArrayList<String> getHashTags()
	{
		return hashTags;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public ArrayList<String> getAtTags()
	{
		return atTags;
	}
	
	public ArrayList<String> getTweet()
	{
		return tweet;
	}
	
	public void addHashTag(String string)
	{
		hashTags.add(string);
	}
	
	public void addAtTag(String string)
	{
		atTags.add(string);
	}
	
	public String toString() {
		String string = "";
		
		ListIterator<String> iterate = getTweet().listIterator();
		string += "Tweet is: ";
		while (iterate.hasNext()) {
			String temp = iterate.next();
			string += temp + " ";
		}

		iterate = getHashTags().listIterator();
		string += "\nHastTag: ";
		while (iterate.hasNext()) {
			String temp = iterate.next();
			string += temp + " ";
		}
		
		iterate = getAtTags().listIterator();
		string += "\nAtTag: ";
		while (iterate.hasNext()) {
			String temp = iterate.next();
			string += temp + " ";
		}
			
		string += '\n';
		return string;
	}
	
}

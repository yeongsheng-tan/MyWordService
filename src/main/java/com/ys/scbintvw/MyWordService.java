package com.ys.scbintvw;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.SortedMap;
import java.util.Set;
import java.util.TreeSet;

import org.joda.time.DateTime;

public class MyWordService implements WordService {
	private String lastIngestedWord;
	private SortedMap<DateTime, String> ingestedWordListByDateTime = Collections.synchronizedSortedMap(new TreeMap<DateTime, String>());
	
	public void ingestWord(String word) {
		lastIngestedWord = word;
		ingestedWordListByDateTime.put(DateTime.now(), word);
	}
	
	public void ingestWord(String word, DateTime now) {
		lastIngestedWord = word;
		ingestedWordListByDateTime.put(now, word);
	}
	
	public String getLastIngestedWord(){
		return lastIngestedWord;
	}

	public Set<String> mostFrequent() {
		SortedSet mostFrequent5WordsInLast5Minutes = new TreeSet();
		if(hasMoreThan5IngestedWords() && hasIngestedWordsForMoreThan5Minutes()){
			for (SortedMap.Entry<DateTime,String> entry : getAllWordsFromLast5Minutes().entrySet()) {
			    String word = entry.getValue();
			    mostFrequent5WordsInLast5Minutes.add(word);
			    if(mostFrequent5WordsInLast5Minutes.size()>=5)
			    	break;
			}
		}
		return mostFrequent5WordsInLast5Minutes;
	}
	
	private boolean hasMoreThan5IngestedWords(){
		return (ingestedWordListByDateTime !=null && ingestedWordListByDateTime.size()>5);
	}
	
	private boolean hasIngestedWordsForMoreThan5Minutes(){
		return (ingestedWordListByDateTime.lastKey().getMillis() - ingestedWordListByDateTime.firstKey().getMillis() > (5 * 60 * 1000));
	}
	
	private SortedMap<DateTime,String> getAllWordsFromLast5Minutes(){
		DateTime dateTimeKey5MinutesAgoFromLatest = ingestedWordListByDateTime.lastKey().minusMinutes(5);
		return ingestedWordListByDateTime.tailMap(dateTimeKey5MinutesAgoFromLatest);		
	}
}

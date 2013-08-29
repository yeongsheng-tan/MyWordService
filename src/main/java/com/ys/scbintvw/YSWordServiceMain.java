package com.ys.scbintvw;

import com.ys.scbintvw.WordsProvider;
import com.ys.scbintvw.WordService;
import java.util.Set;

import org.joda.time.DateTime;

public class YSWordServiceMain {
	private static final int SEVEN_MINUTES=7;
	
	public static void main(String[] args) {
		String[] topOfTheWorldWordArray = {"I", "am", "on", "the", "top", "of", "The", "World"};
		WordsProvider wordsProvider = new WordsProvider(topOfTheWorldWordArray);
		
		System.out.println("List of words array supplied to WordsProvider, that will be randomly picked for ingestion to be fed to WordService:");
		for(int wordIdx=0;wordIdx<topOfTheWorldWordArray.length;wordIdx++){
			System.out.println("Word[" + wordIdx + "]: " + topOfTheWorldWordArray[wordIdx]);
		}
		
		WordService myWordService = new MyWordService();
		
		// Run the word ingestion to MyWordService over 7 minutes
		DateTime startDateTime = DateTime.now();
		DateTime now = startDateTime;
        long endTime = now.plusMinutes(SEVEN_MINUTES).getMillis();
        
        System.out.println("Ingesting word to WordService for 7 actual elapsed minutes...[NEED TO WAIT FOR 7 MINUTES TO ELAPSE]");
        String wordForIngestion;
        do {
        	// Randomly pick a word from topOfTheWorldWordArray to ingest to MyWordService
        	wordForIngestion=wordsProvider.popRandomWordFromList();
        	myWordService.ingestWord(wordForIngestion);
        } while(endTime > DateTime.now().getMillis());
        System.out.println("Word ingestion for 7 minutes completed.");
        Set<String> mostFrequentIngestedWordsInLast5Mins = myWordService.mostFrequent();
        System.out.println("5 most frequently ingested words within the last 5 minutes:");
        for(String s : mostFrequentIngestedWordsInLast5Mins){
        	System.out.println(s);
        }
        System.out.println("\n\nDONE.\n");
	}

}

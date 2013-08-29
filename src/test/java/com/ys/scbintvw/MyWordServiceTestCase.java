package com.ys.scbintvw;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyWordServiceTestCase {
	private WordsProvider wordsProvider;
	private MyWordService myWordService;
	private static final int THIRTY_SECONDS=30;
    private static final int SIX_MINUTES=6;

	@Test
	public void ingestedWordEqualsPoppedWordFromWordsProvider(){
		wordsProvider = new WordsProvider(null);
		myWordService = new MyWordService();
		String newWord = wordsProvider.popRandomWordFromList();
		myWordService.ingestWord(newWord);
		assertEquals(myWordService.getLastIngestedWord(),newWord);
	}
	
	@Test
	public void wordsProviderShouldContainSuppliedWordsArrayWhenInValidWordArrayIsProvided(){
		System.out.println("Running test wordsProviderShouldContainSuppliedWordsArrayWhenInValidWordArrayIsProvided, which supplies WordsProvider with a String array of LESS THAN 5 unique words...");
		String[] myWordArray = {"Hello", "New", "World"};
		wordsProvider = new WordsProvider(myWordArray);
		Arrays.sort(wordsProvider.getWords());
		Arrays.sort(myWordArray);
		assertFalse(Arrays.equals(wordsProvider.getWords(), myWordArray));
		System.out.println("DONE.\n");
	}
	
	@Test
	public void wordsProviderShouldContainSuppliedWordsArrayWhenValidWordArrayIsProvided(){
		System.out.println("Running test wordsProviderShouldContainSuppliedWordsArrayWhenValidWordArrayIsProvided, which supplies WordsProvider with a String array of MORE THAN 5 unique words...");
		String[] myWordArray = {"Hello", "New", "World", "what", "a", "beautiful", "day"};
		wordsProvider = new WordsProvider(myWordArray);
		Arrays.sort(wordsProvider.getWords());
		Arrays.sort(myWordArray);
		assertTrue(Arrays.equals(wordsProvider.getWords(), myWordArray));
		System.out.println("DONE.\n");
	}
	
	
	@Test
	public void wordsProviderShouldFallBackToDefaultWordsArrayWhenNullWordArrayIsProvided(){
		System.out.println("Running test wordsProviderShouldFallBackToDefaultWordsArrayWhenNullWordArrayIsProvided, which DOES NOT supply WordsProvider with any words array...");
		wordsProvider = new WordsProvider(null);
		Arrays.sort(wordsProvider.getWords());
		Arrays.sort(wordsProvider.getDefaultWordsArray());
		assertTrue(Arrays.equals(wordsProvider.getWords(), wordsProvider.getDefaultWordsArray()));
		System.out.println("DONE.\n");
	}
	
	@Test
	public void wordsServiceMustHave5WordsFromMostFrequentMethodAfter5MinsWithFastForwardTime() {
		wordsProvider = new WordsProvider(null);
		myWordService = new MyWordService();
		DateTime startDateTime = DateTime.now();
		DateTime now = startDateTime;
        long endTime = now.plusMinutes(SIX_MINUTES).getMillis();
        System.out.println("Running test wordsServiceMustHave5WordsFromMostFrequentMethodAfter5MinsWithTimeFastForward, which exercises word ingestion for 6 minutes...[each loop fast-forwards time by 30 seconds]");
        String wordForIngestion;
        do {
        	wordForIngestion=wordsProvider.popRandomWordFromList();
        	myWordService.ingestWord(wordForIngestion,now);
			now = now.plusSeconds(THIRTY_SECONDS);
        	System.out.println("Elapsed time: " + now + "\t - word ingested:" + wordForIngestion);
        } while(endTime > now.getMillis());
        System.out.println("Word ingestion for 6 minutes completed.");
        Set<String> mostFrequentIngestedWordsInLast5Mins = myWordService.mostFrequent();
        System.out.println("5 most frequently ingested words within the last 5 minutes:");
        for(String s : mostFrequentIngestedWordsInLast5Mins){
        	System.out.println(s);
        }
        assertTrue(mostFrequentIngestedWordsInLast5Mins.size()==5);
        System.out.println("DONE.\n");
	}

	@Test
	public void wordsServiceMustHave5WordsFromMostFrequentMethodAfter5MinsWithRealTimeElapsed() {
		wordsProvider = new WordsProvider(null);
		myWordService = new MyWordService();
		DateTime startDateTime = DateTime.now();
		DateTime now = startDateTime;
        long endTime = now.plusMinutes(SIX_MINUTES).getMillis();
        System.out.println("Running test wordsServiceMustHave5WordsFromMostFrequentMethodAfter5MinsWithActualTimeElapsed, which exercises word ingestion for 6 minutes...[NEED TO WAIT FOR 6 MINUTES TO ELAPSE]");
        String wordForIngestion;
        do {
        	wordForIngestion=wordsProvider.popRandomWordFromList();
        	myWordService.ingestWord(wordForIngestion);
        } while(endTime > DateTime.now().getMillis());
        System.out.println("Word ingestion for 6 minutes completed.");
        Set<String> mostFrequentIngestedWordsInLast5Mins = myWordService.mostFrequent();
        System.out.println("5 most frequently ingested words within the last 5 minutes:");
        for(String s : mostFrequentIngestedWordsInLast5Mins){
        	System.out.println(s);
        }
        assertTrue(mostFrequentIngestedWordsInLast5Mins.size()==5);
        System.out.println("DONE.\n");
	}
}

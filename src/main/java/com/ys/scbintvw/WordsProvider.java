package com.ys.scbintvw;

import java.util.Random;

public class WordsProvider {
	private static String[] words;
	private static final String[] defaultWordsArray = {"The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"};
	private static final int randomizerMinimum=0;
	private Random randomWordIndex;
	
	public WordsProvider(String[] suppliedWordsArray){
		if(suppliedWordsArray !=null && suppliedWordsArray.length > 4){
			words = new String[suppliedWordsArray.length];
			System.arraycopy(suppliedWordsArray, 0, words, 0, suppliedWordsArray.length);
		}else if(suppliedWordsArray !=null && suppliedWordsArray.length < 5){
			System.out.println("Supplied words array MUST CONTAIN AT LEAST 5 UNIQUE WORDS. Falling back to internal default words array -> " );
			for(int i=0;i<defaultWordsArray.length;i++){
				System.out.println(defaultWordsArray[i]);
			}
			words = new String[defaultWordsArray.length];
			System.arraycopy(defaultWordsArray, 0, words, 0, defaultWordsArray.length);
		}else{
			words = new String[defaultWordsArray.length];
			System.arraycopy(defaultWordsArray, 0, words, 0, defaultWordsArray.length);
		}
	    randomWordIndex = new Random();
	}
	
	public String popRandomWordFromList(){
		int randomizerMaximum=words.length;
		return words[randomWordIndex.nextInt(randomizerMaximum)];
	}
	
	public String[] getWords(){
		return words;
	}
	
	public String[] getDefaultWordsArray(){
		return defaultWordsArray;
	}
}

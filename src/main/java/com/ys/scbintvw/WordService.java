package com.ys.scbintvw;

import java.util.Set;

public interface WordService {
	void ingestWord(final String word);
	Set<String> mostFrequent();
}

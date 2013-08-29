Word Service ->
Problem Statement:
Implement the following WordService to determine the five most frequently ingested words within the last 5 minutes.

public interface WordService {
	void ingestWord(final String word);
	Set<String> mostFrequent();
}

Assumption:
1) No i18n or non-English non-ascii code word support; All words are between ascii dec code 48 to 57 and 65 to 90 (for SortedMap comparable sort)
2) Set return of mostFrequent and ingestWord are case-sensitive - meaning an upper case word is uniquely distinguishable with a non-upper case or mixed-case word
3) The project assumes no multi-threaded handling or concurreny, though a casual use of Collections.synchronizedSortedMap(new TreeMap<DateTime, String>()) is present (probably YAGNI)
4) Project is crafted using JDK1.6 on maven 3.0.5 - basic assumption of $JAVA_HOME and $M2_HOME env var and PATH to the binaries should be present and with valid executable permission
5) I only had time to craft up the JUnit test class com.ys.scbintvw.MyWordServiceTestCase and a Main class com.ys.scbintvw.YSWordServiceMain
6) Taking the zip archive of the source and exploding it to a desired local drive location yields $PROJECT_ROOT
7) 'cd' into $PROJECT_ROOT and execute 'mvn clean test' to exercise the JUnit test cases
8) Did not complete a proper maven single executable jar with dependencies packaging
9) Current packaged executable jar is done via 'mvn clean assembly:assembly' which outputs the required executable jar to $PROJECT_ROOT/target/YSWordService-1.0-SNAPSHOT-jar-with-dependencies.jar
10) To run the main class, 'cd' into $PROJECT_ROOT/target, and execute 'java -jar YSWordService-1.0-SNAPSHOT-jar-with-dependencies.jar' after Step(8) above is completed
11) ingestWord methods is implemented with an overloaded method to take in a Joda DateTime input paramter - this is to facilitate 1 specific test case, where I attempt to fast-forward time to allow time elapsed beyond 5minutes beyond asserting output of mostFrequent method
12) The standard mostFrequent method is implemented to check for at least 5 unique words, and at least 5 minutes of word ingestion has elapsed/occurred before presenting the final output of the most frequently ingested 5 minutes over last 5 minutes

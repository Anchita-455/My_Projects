/**
 *	AnagramMaker - In this program the user inputs a word through which
 * multiple words can be created. These words do not need to be 
 * menaningful and are searched from the dictionary. The possible
 * anagrams from the dictionary are found and the required number 
 * of aangrams that the user prompted is printed to the screen.
 *
 *	Requires the WordUtilities, SortMethods, Prompt, and FileUtils classes
 *
 *	@author	Anchita Dash
 *	@since	January 19, 2022
 */
 
import java.util.ArrayList;
import java.util.List;

public class AnagramMaker {
								
	private final String FILE_NAME = "randomWords.txt";	// file containing all words
	
	private WordUtilities wu;	// the word utilities for building the word
								// database, sorting the database,
								// and finding all words that match
								// a string of characters
	
	// variables for constraining the print output of AnagramMaker
	private int numWords;		// the number of words in a phrase to print
	private int maxPhrases;		// the maximum number of phrases to print
	private int numPhrases;		// the number of phrases that have been printed
	private ArrayList<String> anagram = new ArrayList<String>();;
		
	/*	Initialize the database inside WordUtilities
	 *	The database of words does NOT have to be sorted for AnagramMaker to work,
	 *	but the output will appear in order if you DO sort.
	 */
	public AnagramMaker() {
		wu = new WordUtilities();
		wu.readWordsFromFile(FILE_NAME);
		wu.sortWords();
	}
	
	public static void main(String[] args) {
		AnagramMaker am = new AnagramMaker();
		am.run();
	}
	
	/**	The top routine that prints the introduction and runs the anagram-maker.
	 */
	public void run() {
		printIntroduction();
		runAnagramMaker();
		System.out.println("\nThanks for using AnagramMaker!\n");
	}
	
	/**
	 *	Print the introduction to AnagramMaker
	 */
	public void printIntroduction() {
		System.out.println("\nWelcome to ANAGRAM MAKER");
		System.out.println("\nProvide a word, name, or phrase and out comes their anagrams.");
		System.out.println("You can choose the number of words in the anagram.");
		System.out.println("You can choose the number of anagrams shown.");
		System.out.println("\nLet's get started!");
	}
	
	
	/**
	 *	Prompt the user for a phrase of characters, then create anagrams from those
	 *	characters.
	 */
	public void runAnagramMaker() 
	{
		String word = Prompt.getString("Word(s), name or phrase (q to quit)");
		while(!(word.equals("q")))
		{
	
			String newWord = new String("");
			for(int i = 0; i < word.length(); i++)
			{
				if(Character.isLetter(word.charAt(i))) 
				{
					newWord += word.charAt(i);
				}
			}

			numWords = Prompt.getInt("Number of words in anagram");
			maxPhrases = Prompt.getInt("Maximum number of anagrams to print");
			System.out.println();
			makeAnagrams(newWord);
			System.out.println();
			System.out.println("Stopped at " + numPhrases + " anagrams");
			System.out.println();
			word = Prompt.getString("Word(s), name or phrase (q to quit)");
			numPhrases = 0;
		}
		
	}
	
	/**
	 * This method is responsible to store all the possible anagrams 
	 * that are found in the dictionary.
	 * @param phrase 	the phrase that the user inputs.
	 */
	public void makeAnagrams(String phrase)
	{
		if(anagram.size() > numWords)
		{
			return;
		} 
			
		if(numPhrases >= maxPhrases)
		{
			return;
		}
				
		
		if(phrase.length() == 0)
		{
			if(anagram.size() < numWords)
			{
				return;
			}
			
			numPhrases++;
			for(int i = 0; i < anagram.size(); i++)
			{
				System.out.print(anagram.get(i) + " ");
			}
			System.out.println();
			return;
		}
		
		else if(anagram.size() == numWords)
		{
			return;
		}
		
		else
		{
			ArrayList<String> aWords = new ArrayList<String>();
			aWords = wu.allWords(phrase);
			String newPhrase = new String("");
			
			for(int i = 0; i < aWords.size(); i++)
			{
				anagram.add(aWords.get(i));
				newPhrase = condenseWord(phrase,aWords.get(i));
				makeAnagrams(newPhrase);	
				anagram.remove(aWords.get(i));
			}
			
		}
	}
	
	/**
	 * This is a helper method that removes the subPhrase from the 
	 * original phrase. 
	 * @param phrase 		the phrase that the user entered.
	 * @param subPhrase		the phrase that needs to be removed to create the anagram
	 */
	private String condenseWord(String phrase, String subPhrase)
	{
		phrase = phrase.toLowerCase();
		subPhrase = subPhrase.toLowerCase();
		
		for(int i = 0; i < subPhrase.length(); i++)
		{
			int index = phrase.indexOf(subPhrase.charAt(i));
			phrase = phrase.substring(0, index) + phrase.substring(index + 1);
		}
		return phrase;
	}
	
}

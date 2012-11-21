package com.wordwise.gameengine;

import java.util.List;

import com.wordwise.gameengine.dto.DTOWord;

public interface ServerCommunication {
  
	/**
	 * @param word word to be added 
	 * @return true if successful, false otherwise
	 */
	public boolean addWord(DTOWord word);

	/**
	 * @param word English text of the word to be rated
	 * @param rating user rating
	 * @return true if successful, false otherwise
	 */
	public boolean rateWord(String word, int rating);

	/**
	 * @param lang language code of the desired words 
	 * @param difficulty difficulty expected difficulty of the words
	 * @return list of words and its high quality translations
	 */
	public List<DTOWord> listWords(String lang, int difficulty);

	/**
	 * Retrieves all specified number of words in a given language
	 * and for a given difficulty
	 * @param lang language code of the desired words 
	 * @param difficulty expected difficulty of the words
	 * @param number number of words that are to be listed
	 * @return list of words and its high quality translations
	 */
	public List<DTOWord> listWords(String lang, int difficulty, int number);
		
	/**
	 * Retrieves all the words for a given language
	 * @param lang language code of the desired words 
	 * @return list of words and its high quality translations
	 */
	public List<DTOWord> listWords(String lang);
		
}

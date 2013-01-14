package com.wordwise.gameengine;

import java.util.List;

import com.wordwise.server.model.Language;
import com.wordwise.server.model.Rate;
import com.wordwise.server.model.Word;

public interface ServerCommunication {

	/**
	 * @param word
	 *            word to be added
	 * @return true if successful, false otherwise
	 */
	public boolean addWord(Word word);

	/**
	 * @param wordRating
	 *            the object which contains the word to be rated and user
	 *            ratings for it
	 * @return true if successful, false otherwise
	 */
	public boolean rateWord(Rate wordRating);

	/**
	 * @param word
	 *            of which translations are being rated
	 * @param translationRatings
	 *            the object which contains the translation to be rated and user
	 *            rating for it
	 * @return true if successful, false otherwise
	 */
	public boolean rateTranslations(Word word,
			List<Rate> translationRatings);

	/**
	 * @param lang
	 *            language code of the desired words
	 * @param difficulty
	 *            difficulty expected difficulty of the words
	 * @return list of words and its high quality translations
	 */
	public List<Word> listWords(Language lang, int difficulty);
	

	/**
	 * Retrieves all specified number of words in a given language and for a
	 * given difficulty
	 * 
	 * @param lang
	 *            language code of the desired words
	 * @param difficulty
	 *            expected difficulty of the words
	 * @param number
	 *            number of words that are to be listed
	 * @return list of words and its high quality translations
	 */
	public List<Word> listWords(Language lang, int difficulty, int number);

	/**
	 * Retrieves all the words for a given language
	 * 
	 * @param lang
	 *            language code of the desired words
	 * @return list of words and its high quality translations
	 */
	public List<Word> listWords(Language lang);

}

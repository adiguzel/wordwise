package com.wordwise.gameengine;

import java.util.List;

import com.wordwise.server.model.Difficulty;
import com.wordwise.server.model.Language;
import com.wordwise.server.model.Quality;
import com.wordwise.server.model.Rate;
import com.wordwise.server.model.Translation;
import com.wordwise.server.model.Word;

public interface ServerCommunication {

	/**
	 * @param word
	 *            word to be added
	 * @return true if successful, false otherwise
	 */
	public boolean addWord(Word word);

	/**
	 * @param translationRatings
	 *            list of the objects which contain the translation to be rated and user
	 *            ratings for it
	 * @return true if successful, false otherwise
	 */
	public boolean rateTranslations(List<Rate> translationRatings);

	/**
	 * @param wordQualities
	 *            list of the objects which contain the words to be rated and user
	 *            ratings for it
	 * @return true if successful, false otherwise
	 */
	public boolean addWordQualities(List<Quality> wordQualities);
	

	/**
	 * @param wordDifficulties
	 *            list of Difficulties that are provided for a word
	 * @return true if successful, false otherwise
	 */
	public boolean addWordDifficulties(List<Difficulty> wordDifficulties);

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
	
	/**
	 * Retrieves all specified number of translations in a given language and for a
	 * given difficulty
	 * 
	 * @param lang
	 *            language code of the desired translations
	 * @param difficulty
	 *            expected difficulty of the translations
	 * @param number
	 *            number of translations that are to be listed
	 * @param translationsAlreadyUsed
	 * 			  list of the translations already used and, therefore, should not be in the return list
	 * @return list of translations and its original words (in English)
	 */
	public List<Translation> listTranslations(Language lang, Difficulty difficulty, int number, List<Translation> translationsAlreadyUsed);

}

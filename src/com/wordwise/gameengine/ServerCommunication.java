package com.wordwise.gameengine;

import java.util.List;

import com.wordwise.server.dto.DTODifficulty;
import com.wordwise.server.dto.DTOLanguage;
import com.wordwise.server.dto.DTOQuality;
import com.wordwise.server.dto.DTORate;
import com.wordwise.server.dto.DTOTranslation;
import com.wordwise.server.dto.DTOWord;

/**
 * This class defines the abstract interface that classes that get the
 * translations and words from the server should conform
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 */
public interface ServerCommunication {

	/**
	 * @param translation
	 *            the translation object
	 * @return true if successful, false otherwise
	 */
	public boolean addTranslation(DTOTranslation translation);

	/**
	 * @param translationRatings
	 *            a rating for a certain translation
	 * @return true if successful, false otherwise
	 */
	public boolean rateTranslation(DTORate rating);

	/**
	 * @param wordQualities
	 *            a quality for a certain word
	 * @return true if successful, false otherwise
	 */
	public boolean addWordQuality(DTOQuality quality);

	/**
	 * @param wordDifficulties
	 *            a difficulty for a certain word
	 * @return true if successful, false otherwise
	 */
	public boolean addWordDifficulty(DTODifficulty difficulty);

	/**
	 * Retrieves all specified number of translations in a given language and
	 * for a given difficulty
	 * 
	 * @param lang
	 *            language code of the desired translations
	 * @param difficulty
	 *            expected difficulty of the translations
	 * @param number
	 *            number of translations that are to be listed
	 * @param translationsAlreadyUsed
	 *            list of the translations already used and, therefore, should
	 *            not be in the return list
	 * @return list of translations and its original words (in English)
	 */
	public List<DTOTranslation> listTranslations(DTOLanguage lang,
			DTODifficulty difficulty, int number,
			List<DTOTranslation> translationsAlreadyUsed);

	/**
	 * Retrieves a random list of specified number of words or less from the
	 * server
	 * 
	 * @param number
	 *            number of words to be listed
	 */
	public List<DTOWord> listWords(int number);

	/**
	 * A convenience method to use to get just one random word from the server
	 * 
	 * @link listWords should be used for its implementation
	 * */
	public DTOWord getWord();
}

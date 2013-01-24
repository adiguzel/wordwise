package com.wordwise.gameengine;

import java.util.List;

import com.wordwise.server.model.Difficulty;
import com.wordwise.server.model.Language;
import com.wordwise.server.model.Quality;
import com.wordwise.server.model.Rate;
import com.wordwise.server.model.Translation;

/**
 * @author dmiles
 *
 */
public interface ServerCommunication {
	
	/**
	 * @param translation
	 * 			  the translation object
	 * @return true if successful, false otherwise
	 */
	public boolean addTranslation(Translation translation);

	/**
	 * @param translationRatings
	 *            a rating for a certain translation
	 * @return true if successful, false otherwise
	 */
	public boolean rateTranslation(Rate rating);

	/**
	 * @param wordQualities
	 *            a quality for a certain word
	 * @return true if successful, false otherwise
	 */
	public boolean addWordQualitiy(Quality quality);
	

	/**
	 * @param wordDifficulties
	 *            a difficultiy for a certain word
	 * @return true if successful, false otherwise
	 */
	public boolean addWordDifficulty(Difficulty difficulty);
	
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

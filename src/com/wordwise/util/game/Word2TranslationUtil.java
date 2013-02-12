package com.wordwise.util.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.wordwise.server.dto.DTOTranslation;

/**
 * This is a utility class provides the common operation(s) used in the Words2Translations
 * game
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class Word2TranslationUtil {

	/**
	 * creates a random list from the given list of translations
	 * */
	public static List<DTOTranslation> mixTranslations(final List<DTOTranslation> initialList) {
		List<DTOTranslation> copyList = new ArrayList<DTOTranslation>();

		for (DTOTranslation translation : initialList) {
			copyList.add(translation);
		}

		List<DTOTranslation> randomList = new ArrayList<DTOTranslation>();

		int listSize = copyList.size();
		Random random = new Random();
		for (int i = listSize; i > 0; i--) {
			// get a random index generated up to remaining element count
			int index = random.nextInt(i);
			// add the flipper at the index to random list
			randomList.add(copyList.get(index));
			// delete the flipper at the index from the initial list
			copyList.remove(index);
		}
		return randomList;
	}

}

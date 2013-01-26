package com.wordwise.util.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.widget.TextView;

import com.wordwise.server.dto.DTOTranslation;

public class Word2TranslationUtil {

	
	/**
	 * Generates the flippers using the given translations in a non-random
	 * fashion
	 * */
	private static List<TextView> generateTranslationViews(List<DTOTranslation> translations,
			Context context) {
		if (translations == null)
			return null;

		List<TextView> flippers = new ArrayList<TextView>();

		for (DTOTranslation translation : translations) {
			
		}

		return flippers;
	}

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

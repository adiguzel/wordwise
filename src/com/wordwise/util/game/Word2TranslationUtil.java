package com.wordwise.util.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.wordwise.server.model.Translation;

public class Word2TranslationUtil {


	public static List<Translation> mixTranslations(final List<Translation> initialList) {
		List<Translation> copyList = new ArrayList<Translation>();

		for (Translation translation : initialList) {
			copyList.add(translation);
		}

		List<Translation> randomList = new ArrayList<Translation>();

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

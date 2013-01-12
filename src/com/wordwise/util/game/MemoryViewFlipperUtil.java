package com.wordwise.util.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;

import com.wordwise.server.model.Translation;
import com.wordwise.view.game.MemoryViewFlipper;

public class MemoryViewFlipperUtil {

	public static List<MemoryViewFlipper> getRandomViewFlipperList(
			List<Translation> translations, Context context) {
		List<MemoryViewFlipper> initialList = generateViewFlippers(translations,context);
		List<MemoryViewFlipper> randomList = generateViewFlippers(translations,context);
		if(initialList == null)
			return null;
		
		int remElementCount = initialList.size();
		Random random = new Random();
		for(int i = 0 ; i < initialList.size(); i++){
			MemoryViewFlipper aFlipper =initialList.get(random.nextInt(remElementCount));
			randomList.add(aFlipper);
			initialList.remove(aFlipper);
			remElementCount--;
		}
		
		return randomList;
	}

	private static List<MemoryViewFlipper> generateViewFlippers(
			List<Translation> translations, Context context) {
		if(translations == null)
			return null;
		List<MemoryViewFlipper> flippers = new ArrayList<MemoryViewFlipper>();

		for (Translation translation : translations) {
			MemoryViewFlipper flipperWord = new MemoryViewFlipper(context,
					translation, MemoryViewFlipper.USE_WORD);
			MemoryViewFlipper flipperTranslation = new MemoryViewFlipper(
					context, translation, MemoryViewFlipper.USE_TRANSLATION);
			flippers.add(flipperWord);
			flippers.add(flipperTranslation);
		}

		return flippers;
	}

}

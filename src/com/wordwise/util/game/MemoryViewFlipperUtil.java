package com.wordwise.util.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.view.View;

import com.wordwise.R;
import com.wordwise.server.model.Translation;
import com.wordwise.view.game.MemoryViewFlipper;

public class MemoryViewFlipperUtil {

	public static final int STATE_INITIAL = 0;
	public static final int STATE_MATCH_SUCCESS = 1;
	public static final int STATE_MATCH_FAIL = 2;

	/**
	 * Generates the randomly ordered flippers using the given translations
	 * */
	public static List<MemoryViewFlipper> getRandomViewFlipperList(
			List<Translation> translations, Context context) {
		// initial non-random list
		List<MemoryViewFlipper> initialList = generateViewFlippers(
				translations, context);
		List<MemoryViewFlipper> randomList = new ArrayList<MemoryViewFlipper>();
		if (initialList == null)
			return null;

		int listSize = initialList.size();
		Random random = new Random();
		for (int i = listSize; i > 0; i--) {
			// get a random index generated up to remaining element count
			int index = random.nextInt(i);
			// add the flipper at the index to random list
			randomList.add(initialList.get(index));
			// delete the flipper at the index from the initial list
			initialList.remove(index);
		}

		return randomList;
	}
	
	/**
	 * Generates the flippers using the given translations in a non-random
	 * fashion
	 * */
	private static List<MemoryViewFlipper> generateViewFlippers(
			List<Translation> translations, Context context) {
		if (translations == null)
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

	@SuppressWarnings("deprecation")
	/**
	 * Changes the state of a given square grid item to given state
	 * */
	public static void changeState(View v, int state) {
		switch (state) {
			case STATE_INITIAL :
				v.setBackgroundDrawable(v.getResources().getDrawable(
						R.drawable.memory_square_grid_item));
				break;
			case STATE_MATCH_SUCCESS :
				v.setBackgroundDrawable(v.getResources().getDrawable(
						R.drawable.memory_square_grid_item_success));
				break;
			case STATE_MATCH_FAIL :
				break;
			default :
				break;
		}
		v.invalidate();
	}
}

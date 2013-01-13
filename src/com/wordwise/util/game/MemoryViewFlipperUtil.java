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

	public static List<MemoryViewFlipper> getRandomViewFlipperList(
			List<Translation> translations, Context context) {
		List<MemoryViewFlipper> initialList = generateViewFlippers(translations,context);
		List<MemoryViewFlipper> randomList = new ArrayList<MemoryViewFlipper>();
		if(initialList == null)
			return null;
		
		int remElementCount = initialList.size();
		int listSize = initialList.size();
		Random random = new Random();
		for(int i = 0 ; i < listSize; i++){
			int index = random.nextInt(remElementCount);
			MemoryViewFlipper aFlipper =initialList.get(index);
			randomList.add(aFlipper);
			initialList.remove(index);
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

	
	@SuppressWarnings("deprecation")
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
		}
		v.invalidate();
	}
}

package com.wordwise.gameengine.level;

import com.wordwise.server.dto.DTODifficulty;

public class TranslationPromotion implements Promotion {
	@Override
	public int getPoints(DTODifficulty difficulty) {
		if(difficulty == DTODifficulty.EASY)
			return 5;
		else if(difficulty == DTODifficulty.MEDIUM)
			return 10;			
		else if(difficulty == DTODifficulty.HARD)
			return 15;
		else
			return 0;
	}
}

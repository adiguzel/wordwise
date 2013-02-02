package com.wordwise.gameengine.level;

import com.wordwise.server.dto.DTODifficulty;

public class TranslationPromotion implements Promotion {
	@Override
	public int getPoints(DTODifficulty difficulty) {
		return 15;
	}
}

package com.wordwise.gameengine.level;

import com.wordwise.server.dto.DTODifficulty;

public class GameFinishPromotion implements Promotion {
	@Override
	public int getPoints(DTODifficulty difficulty) {
		if(difficulty == DTODifficulty.EASY)
			return 3;
		else if(difficulty == DTODifficulty.MEDIUM)
			return 6;			
		else if(difficulty == DTODifficulty.HARD)
			return 10;
		else
			return 0;
	}
}

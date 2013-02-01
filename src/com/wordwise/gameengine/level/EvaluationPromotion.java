package com.wordwise.gameengine.level;

import com.wordwise.server.dto.DTODifficulty;

public class EvaluationPromotion implements Promotion{
	@Override
	public int getPoints(DTODifficulty difficulty) {
		if(difficulty == DTODifficulty.EASY)
			return 4;
		else if(difficulty == DTODifficulty.MEDIUM)
			return 8;			
		else if(difficulty == DTODifficulty.HARD)
			return 12;
		else
			return 0;
	}

}

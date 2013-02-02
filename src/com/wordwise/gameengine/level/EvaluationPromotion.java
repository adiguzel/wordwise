package com.wordwise.gameengine.level;

import com.wordwise.server.dto.DTODifficulty;

public class EvaluationPromotion implements Promotion{
	@Override
	public int getPoints(DTODifficulty difficulty) {
		return 12;
	}

}

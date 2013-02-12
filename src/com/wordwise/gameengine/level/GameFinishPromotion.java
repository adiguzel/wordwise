package com.wordwise.gameengine.level;

import com.wordwise.server.dto.DTODifficulty;

/**
 * This class represents the promotion awarded for games that be finished and
 * does not include partial point system.
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class GameFinishPromotion implements Promotion {
	@Override
	public int getPoints(DTODifficulty difficulty) {
		// points are awarded according to difficulty
		if (difficulty == DTODifficulty.EASY)
			return 3;
		else if (difficulty == DTODifficulty.MEDIUM)
			return 6;
		else if (difficulty == DTODifficulty.HARD)
			return 10;
		else
			return 0;
	}
}

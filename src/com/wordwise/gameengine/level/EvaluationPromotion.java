package com.wordwise.gameengine.level;

import com.wordwise.server.dto.DTODifficulty;

/**
 * This class represents the promotion awarded for evaluating translation and
 * words.
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class EvaluationPromotion implements Promotion {
	
	@Override
	public int getPoints(DTODifficulty difficulty) {
		// evaluation points does not depend on the difficulty
		return 12;
	}

}

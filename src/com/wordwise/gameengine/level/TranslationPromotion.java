package com.wordwise.gameengine.level;

import com.wordwise.server.dto.DTODifficulty;

/**
 * This class represents the promotion awarded for translating an English word
 * to another language
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class TranslationPromotion implements Promotion {

	@Override
	public int getPoints(DTODifficulty difficulty) {
		return 15;
	}

}

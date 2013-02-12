package com.wordwise.gameengine.level;

import com.wordwise.server.dto.DTODifficulty;

/**
 * This interface defines the main contract for the type of promotions that can
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public interface Promotion {

	/**
	 * @param difficulty
	 *            difficulty of the game played
	 * @return number of points to be awarded
	 */
	public int getPoints(DTODifficulty difficulty);
}

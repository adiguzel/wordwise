package com.wordwise.gameengine.level;

import com.wordwise.server.dto.DTODifficulty;

/**
 * This class represents the promotion awarded for games that can be partially
 * won. For instance, in a game when user managed to know A number of things out
 * of total B number things, the points awarded should be calculated partially
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class GameFinishPartialPromotion extends GameFinishPromotion {
	// number of items successfully found or dropped etc.
	private int successfulCount;
	// total number of items
	private int totalNumber;

	public GameFinishPartialPromotion(int successfulCount, int totalNumber) {
		this.successfulCount = successfulCount;
		this.totalNumber = totalNumber;
		if (successfulCount > totalNumber)
			this.successfulCount = this.totalNumber;
	}

	@Override
	public int getPoints(DTODifficulty difficulty) {
		// apply a partial pointing using the base game finish points
		int points = super.getPoints(difficulty);
		// calculate the success ratio
		float successRatio = (float) successfulCount / (float) totalNumber;
		// return the partially applied points depending on success ratio
		return (int) Math.ceil(points * successRatio);
	}

}

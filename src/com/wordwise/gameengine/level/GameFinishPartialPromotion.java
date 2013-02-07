package com.wordwise.gameengine.level;

import com.wordwise.server.dto.DTODifficulty;

public class GameFinishPartialPromotion extends GameFinishPromotion{
	private int successfulNumber;
	private int totalNumber;
	
	//not allowed
	@SuppressWarnings("unused")
	private GameFinishPartialPromotion(){}
	
	public GameFinishPartialPromotion(int successfulNumber, int totalNumber){
		this.successfulNumber = successfulNumber;
		this.totalNumber = totalNumber;
		if(successfulNumber > totalNumber)
			this.successfulNumber = this.totalNumber;
	}
	
	@Override
	public int getPoints(DTODifficulty difficulty) {
		int points = super.getPoints(difficulty);
		float successRatio = (float)successfulNumber /  (float)totalNumber;
		//return the partially applied points depending on success ratio
		return (int) Math.ceil(points * successRatio);	
	}

}

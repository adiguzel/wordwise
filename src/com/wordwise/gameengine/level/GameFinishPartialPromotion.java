package com.wordwise.gameengine.level;

import com.wordwise.server.dto.DTODifficulty;

public class GameFinishPartialPromotion extends GameFinishPromotion{
	private int successfulNumber;
	private int totalNumber;
	
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
		float successRatio = successfulNumber /  totalNumber;
		//return the partially applied points depending on success ratio
		return (int) (points * successRatio);	
	}

}
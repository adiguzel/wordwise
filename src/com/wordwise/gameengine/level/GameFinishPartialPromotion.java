package com.wordwise.gameengine.level;

import android.util.Log;

import com.wordwise.server.dto.DTODifficulty;

public class GameFinishPartialPromotion extends GameFinishPromotion{
	private int successfulCount;
	private int totalNumber;
	
	//not allowed
	@SuppressWarnings("unused")
	private GameFinishPartialPromotion(){}
	
	public GameFinishPartialPromotion(int successfulCount, int totalNumber){
		this.successfulCount = successfulCount;
		this.totalNumber = totalNumber;
		if(successfulCount > totalNumber)
			this.successfulCount = this.totalNumber;
	}
	
	@Override
	public int getPoints(DTODifficulty difficulty) {
		int points = super.getPoints(difficulty);
		float successRatio = (float)successfulCount /  (float)totalNumber;
		Log.v("sucessRatio", ""+successRatio);
		//return the partially applied points depending on success ratio
		return (int) Math.ceil(points * successRatio);	
	}

}

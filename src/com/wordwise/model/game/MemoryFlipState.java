package com.wordwise.model.game;

import android.view.View;
/**
 * @author Ugur Adigüzel
 * Keeps track of the flips and knows which view was flipped first
 */

public class MemoryFlipState {
	
	public static boolean flipON = true;
	public static boolean flipOFF = false;
	
	private boolean state = flipOFF;
	//the view which was revealed first
	private View firstFlipped = null;

	public boolean getState() {
		return state;
	}
	
	public boolean flipExist(){
		return state == flipON;
	}

	public View getFirstFlipped() {
		return firstFlipped;
	}

	public void setFirstFlipped(View firstFlipped) {
		state = flipOFF;
		this.firstFlipped = firstFlipped;
		if(firstFlipped != null){
			state = flipON;
		}
	}
}

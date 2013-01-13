package com.wordwise.model.game;

import android.view.View;

public class MemoryFlipState {
	
	public static boolean flipON = true;
	public static boolean flipOFF = false;
	
	private boolean state = flipOFF;
	//the textview which was revealed first
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

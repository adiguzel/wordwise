package com.wordwise.model.game;

import android.widget.TextView;

public class MemoryFlipState {
	
	public static boolean flipON = true;
	public static boolean flipOFF = false;
	
	private boolean state = flipOFF;
	private TextView firstFlipped;

	public boolean getState() {
		return state;
	}
	
	public boolean flipExist(){
		return state == flipON;
	}

	public TextView getFirstFlipped() {
		return firstFlipped;
	}

	public void setFirstFlipped(TextView firstFlipped) {
		state = flipOFF;
		if(firstFlipped != null){
			this.firstFlipped = firstFlipped;
			state = flipON;
		}
	}
}

package com.wordwise.model.game;

import android.util.Log;
import android.widget.TextView;

public class MemoryFlipState {
	
	public static boolean flipON = true;
	public static boolean flipOFF = false;
	
	private boolean state = flipOFF;
	//the textview which was revealed first
	private TextView firstFlipped = null;

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
		this.firstFlipped = firstFlipped;
		if(firstFlipped != null){
			Log.v("flip", "setting the flip");
			state = flipON;
		}
	}
}

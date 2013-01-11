package com.wordwise.model.game;

public class MemoryFlipState {
	
	public static boolean flipON = true;
	public static boolean flipOFF = false;
	
	private boolean state = flipOFF;

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
	public boolean flipExist(){
		return state == flipON;
	}
}

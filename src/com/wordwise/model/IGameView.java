package com.wordwise.model;

import android.view.View;

public interface IGameView {
	// called by quit button to quit the game
	public void quit(View v);

	// called by continue button to proceed to the next game
	public void continueNextGame(View v);
	
	// called by quit button to quit the game
	public void validate(View v);
}

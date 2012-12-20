package com.wordwise.activity.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.wordwise.R;
import com.wordwise.gameengine.Game;

public class Memory extends Activity implements Game {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.memory);
		this.init();
		this.start();
	}

	public void start() {
		// TODO Auto-generated method stub

	}

	public void stop() {
		// TODO Auto-generated method stub

	}

	public void pause() {
		// TODO Auto-generated method stub

	}

	public void init() {
		// TODO Auto-generated method stub

	}

	// called by quit button to quit the game
	public void quit(View v) {

	}

	// called by continue button to finish the game and continue from the next
	// game
	public void finishGame(View v) {

	}

}

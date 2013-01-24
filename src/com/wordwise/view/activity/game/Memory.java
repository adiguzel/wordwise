package com.wordwise.view.activity.game;

import android.os.Bundle;
import android.widget.Button;

import com.wordwise.R;
import com.wordwise.controller.game.MemoryManager;
import com.wordwise.server.model.Difficulty;
import com.wordwise.view.activity.WordwiseGameActivity;

public class Memory extends WordwiseGameActivity {

	private Button continueButton;

	public void performOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.memory);
		this.onGameInit();
		this.onGameStart();
	}

	public void onGameStart() {
	}

	public void onGameStop() {
	}

	public void onGamePause() {
	}

	public void onGameInit() {
		// TODO Auto-generated method stub
		continueButton = (Button) findViewById(R.id.continueButton);
		new MemoryManager(this).initMemoryGrid();
	}

	public void onGameEnd() {
		// TODO show necessary dialogs about game end
		continueButton.setEnabled(true);
	}

	public int numberOfTranslationsNeeded(Difficulty difficulty) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int numberOfWordsNeeded(Difficulty difficulty) {
		// TODO Auto-generated method stub
		return 0;
	}
}

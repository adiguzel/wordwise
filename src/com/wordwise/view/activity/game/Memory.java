package com.wordwise.view.activity.game;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wordwise.R;
import com.wordwise.controller.game.MemoryManager;
import com.wordwise.gameengine.Game;
import com.wordwise.model.game.MemoryFlipState;
import com.wordwise.view.activity.WordwiseGameActivity;

public class Memory extends WordwiseGameActivity implements Game {

	private Button continueButton;
	private final int IN_PROGRESS = 0;
	private final int FINISHED = 1;
	private int gameState = IN_PROGRESS;
	private MemoryFlipState flipState;

	private int pairCount = 6;
	
	@Override
	public void performOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.memory);
		this.onGameInit();
		this.onGameStart();
	}

	public void onGameFinish() {
		// TODO show necessary dialogs about game end
		continueButton.setEnabled(true);
	}

	public void onGameStart() {
		// TODO Auto-generated method stub

	}

	public void onGameStop() {
		// TODO Auto-generated method stub

	}

	public void onGamePause() {
		// TODO Auto-generated method stub

	}

	public void onGameInit() {
		// TODO Auto-generated method stub
		continueButton = (Button) findViewById(R.id.continueButton);
		flipState = new MemoryFlipState();
		MemoryManager mManager = new MemoryManager(this);
		mManager.initMemoryGrid();
	}

	// called by continue button to finish the game and continue from the next
	// game
	public void finishGame(View v) {

	}

	

}

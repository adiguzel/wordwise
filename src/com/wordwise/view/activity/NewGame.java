package com.wordwise.view.activity;

import android.os.Bundle;
import android.view.View;

import com.wordwise.R;
import com.wordwise.controller.RandomGameSelector;
import com.wordwise.controller.WordwiseGameManager;
import com.wordwise.gameengine.GameManager;
import com.wordwise.model.Configuration;
import com.wordwise.model.GameManagerContainer;
import com.wordwise.server.model.Difficulty;

public class NewGame extends MenuActivity {
	private GameManager gManager;
	private Configuration configuration;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.new_game);
		gManager = new WordwiseGameManager(new RandomGameSelector(), this);
		GameManagerContainer.setGameManager(gManager);
		configuration = Configuration.getInstance(this);
	}
	public void startNewGame(View view) {
		switch (view.getId()) {
			case R.id.easyNewGameButton :
				// set the difficulty
				configuration.setDifficulty(Difficulty.EASY);
				// start the game cycle
				gManager.startGameCycle();
				break;
			case R.id.mediumNewGameButton :
				configuration.setDifficulty(Difficulty.MEDIUM);
				gManager.startGameCycle();
				break;
			case R.id.hardNewGameButton :
				configuration.setDifficulty(Difficulty.HARD);
				gManager.startGameCycle();
				break;
			default :
				break;
		}
	}
}

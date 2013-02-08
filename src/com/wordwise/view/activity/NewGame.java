package com.wordwise.view.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.wordwise.R;
import com.wordwise.controller.PreferencesIOManager;
import com.wordwise.controller.RandomGameSelector;
import com.wordwise.controller.WordwiseGameManager;
import com.wordwise.gameengine.GameManager;
import com.wordwise.model.GameManagerContainer;
import com.wordwise.server.dto.DTODifficulty;

public class NewGame extends Activity {
	private GameManager gManager;
	private PreferencesIOManager configuration;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.new_game);
		gManager = new WordwiseGameManager(new RandomGameSelector(), this);
		GameManagerContainer.setGameManager(gManager);
		configuration = PreferencesIOManager.getInstance(this);
	}
	public void startNewGame(View view) {
		switch (view.getId()) {
			case R.id.easyNewGameButton :
				// set the difficulty
				configuration.setDifficulty(DTODifficulty.EASY);
				// start the game cycle
				gManager.startGameCycle();
				break;
			case R.id.mediumNewGameButton :
				configuration.setDifficulty(DTODifficulty.MEDIUM);
				gManager.startGameCycle();
				break;
			case R.id.hardNewGameButton :
				configuration.setDifficulty(DTODifficulty.HARD);
				gManager.startGameCycle();
				break;
			default :
				break;
		}
		// makes sure that this activity is not hold on the activity stack
		finish();
	}
}

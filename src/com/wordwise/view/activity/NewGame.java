package com.wordwise.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.wordwise.R;
import com.wordwise.gameengine.Game;
import com.wordwise.gameengine.GameManager;
import com.wordwise.model.Configuration;
import com.wordwise.model.GameManagerContainer;
import com.wordwise.model.RandomGameSelector;
import com.wordwise.model.WordwiseGameManager;

public class NewGame extends MenuActivity {
	private GameManager gManager;
	private Configuration configuration;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_game);
		gManager = new WordwiseGameManager(new RandomGameSelector(), this);
		GameManagerContainer.setGameManager(gManager);
		configuration = Configuration.getInstance();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);

		return true;
	}

	public void startNewGame(View view) {
		switch (view.getId()) {
			case R.id.easyNewGameButton :
				// set the difficulty
				configuration.setDifficulty(Game.EASY);
				// start the game cycle
				gManager.startGameCycle();
				break;
			case R.id.mediumNewGameButton :
				configuration.setDifficulty(Game.MEDIUM);
				gManager.startGameCycle();
				break;
			case R.id.hardNewGameButton :
				configuration.setDifficulty(Game.HARD);
				gManager.startGameCycle();
				break;
			default :
				break;
		}
	}

}

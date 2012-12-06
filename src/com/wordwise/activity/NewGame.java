package com.wordwise.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.wordwise.R;
import com.wordwise.gameengine.GameManager;

public class NewGame extends MenuActivity {
	private GameManager gManager = GameManager.getInstance();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_game);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void startNewGame(View view) {
		switch (view.getId()) {
		case R.id.easyNewGameButton:
			//TODO Prepare the DTO for easy and start the game
			gManager.startGameCycle();
			break;
		case R.id.mediumNewGameButton:
			//TODO Prepare the DTO for easy and start the game
			
			break;
		case R.id.hardNewGameButton:
			//TODO Prepare the DTO for easy and start the game
			gManager.startGameCycle();
			break;
		default:
			break;
		}
		
		// Intent intent = new Intent(this, ConfigurationWizardStep1.class);
		// startActivity(intent);
	}
	
}

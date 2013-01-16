package com.wordwise.view.activity.game;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wordwise.R;
import com.wordwise.controller.game.Words2TranslationsManager;
import com.wordwise.gameengine.Game;
import com.wordwise.util.WordwiseUtils;
import com.wordwise.view.activity.WordwiseGameActivity;

public class Words2Translations extends WordwiseGameActivity implements Game {
	private Button validateButton;
	private Words2TranslationsManager manager;

	@Override
	public void performOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.words2translations);
		this.onGameInit();
		this.onGameStart();
	}
	
	public void onGameStart() {
		// TODO Auto-generated method stub
	}

	public void onGameStop() {
		
	}

	public void onGamePause() {
		// TODO Auto-generated method stubd
	}

	public void onGameInit() {
		validateButton = (Button) findViewById(R.id.validateButton);
		manager = new Words2TranslationsManager(this);
		manager.initViews();
	}
	
	public void onGameEnd() {
		//as a dummy game end text
		WordwiseUtils.makeCustomToast(this, "Game ended");
	}
	
	public void switchValidation(boolean state){
		validateButton.setEnabled(state);
	}
	
	// called when validate button is pressed
	// validates the drag & drop answers and
	// highlights right and wrong answers
	public void validate(View v) {
		manager.validate(v);
	}
}

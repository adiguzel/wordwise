package com.wordwise.view.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.wordwise.model.GameManagerContainer;
import com.wordwise.model.IGameView;
import com.wordwise.util.WordwiseUtils;

public abstract class WordwiseGameActivity extends Activity implements IGameView {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		performOnCreate(savedInstanceState);
	}
	
	public abstract void performOnCreate(Bundle savedInstanceState);

	@Override
	public void onBackPressed() {
		onQuitPressed();
	}

	public void quit(View v) {
		onQuitPressed();
	}

	public void continueNextGame(View v) {
		GameManagerContainer.getGameManager().endGame();
	}

	public void validate(View v) {
		// TODO Auto-generated method stub

	}

	protected void onQuitPressed() {
		WordwiseUtils.makeQuitGameDialog(this);
	}

}

package com.wordwise.view.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.wordwise.gameengine.Game;
import com.wordwise.model.GameManagerContainer;
import com.wordwise.model.IGameView;
import com.wordwise.util.WordwiseUtils;

public abstract class WordwiseGameActivity extends Activity implements IGameView, Game {

	@Override
	public final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//all games have no action bar
		getActionBar().hide();
		//all games are in portrait(vertical) mode
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		performOnCreate(savedInstanceState);
	}
	
	/**
	 * Contains the necessary implementation to do when this activity is created
	 * */
	public abstract void performOnCreate(Bundle savedInstanceState);

	@Override
	public final void onBackPressed() {
		onQuitPressed();
	}

	public final void quit(View v) {
		onQuitPressed();
	}

	public final void continueNextGame(View v) {
		GameManagerContainer.getGameManager().endGame();
	}

	public void validate(View v) {
		// TODO Auto-generated method stub

	}

	protected void onQuitPressed() {
		WordwiseUtils.makeQuitGameDialog(this);
	}

}

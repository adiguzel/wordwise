package com.wordwise.view.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.wordwise.gameengine.Game;
import com.wordwise.model.GameManagerContainer;
import com.wordwise.model.IGameView;
import com.wordwise.util.LoaderHelper;
import com.wordwise.util.WordwiseUtils;

public abstract class WordwiseGameActivity extends Activity
		implements
			IGameView,
			Game {
	// flag to determine if activity should be ended and go back to the previous
	// screen
	protected boolean end = false;
	protected LoaderHelper loaderHelper;
	@Override
	public final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GameManagerContainer.getGameManager().setCurrentGame(this);
		// helper class that wraps convenience methods to get the data from
		// server
		loaderHelper = new LoaderHelper();
		// None of the games have an action bar
		getActionBar().hide();
		// all games are in portrait(vertical) mode
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		//let the children decide what else to do onCreate
		performOnCreate(savedInstanceState);
	}

	/**
	 * Contains the necessary implementation to do when this activity is created
	 * */
	protected abstract void performOnCreate(Bundle savedInstanceState);

	@Override
	public final void onBackPressed() {
		if (end) {
			super.onBackPressed();
			return;
		}
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

	public void setEndFlag(boolean endFlag) {
		this.end = endFlag;
	}

}

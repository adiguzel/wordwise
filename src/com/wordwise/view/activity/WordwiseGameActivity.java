package com.wordwise.view.activity;

import com.wordwise.model.GameManagerContainer;
import com.wordwise.model.IGameView;
import com.wordwise.util.WordwiseUtils;

import android.app.Activity;
import android.view.View;

public class WordwiseGameActivity extends Activity implements IGameView {

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

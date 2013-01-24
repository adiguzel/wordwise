package com.wordwise.view.activity.game;

import java.util.List;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wordwise.R;
import com.wordwise.controller.game.Words2TranslationsManager;
import com.wordwise.loader.TranslationLoader;
import com.wordwise.model.GameManagerContainer;
import com.wordwise.server.model.Difficulty;
import com.wordwise.server.model.Translation;
import com.wordwise.util.WordwiseUtils;
import com.wordwise.view.activity.WordwiseGameActivity;

public class Words2Translations extends WordwiseGameActivity
		implements
			LoaderCallbacks<List<Translation>> {
	private Button validateButton;
	private Words2TranslationsManager manager;
	public ProgressBar progress;

	@Override
	public void performOnCreate(Bundle savedInstanceState) {
		getLoaderManager()
				.initLoader(
						0,
						null,
						(android.app.LoaderManager.LoaderCallbacks<List<Translation>>) this)
				.forceLoad();
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
		// as a dummy game end text
		WordwiseUtils.makeCustomToast(this, "Game ended");
	}

	public void switchValidation(boolean state) {
		validateButton.setEnabled(state);
	}

	// called when validate button is pressed
	// validates the drag & drop answers and
	// highlights right and wrong answers
	public void validate(View v) {
		manager.validate(v);
	}

	public Loader<List<Translation>> onCreateLoader(int id, Bundle args) {
		setContentView(R.layout.loading_game);
		progress = (ProgressBar) findViewById(R.id.progress_bar);

		progress.setVisibility(View.VISIBLE);
		return new TranslationLoader(this);
	}

	public void onLoadFinished(Loader<List<Translation>> loader,
			List<Translation> translations) {
		Log.v("translations", "" + translations);
		
		if (translations == null) {
			Toast.makeText(this, "Oh snap. Failed to load.", Toast.LENGTH_SHORT)
					.show();
		} else if (translations.size() < GameManagerContainer.getGameManager().NumberOfTranslationsNeeded()) {
			Toast.makeText(this, "Server does not have enough translations.",
					Toast.LENGTH_SHORT).show();
		} 
		else {
			setContentView(R.layout.words2translations);
			this.onGameInit();
			this.onGameStart();
		}
	}

	public void onLoaderReset(Loader<List<Translation>> arg0) {

	}

	public int numberOfTranslationsNeeded(Difficulty difficulty) {
		if (difficulty == Difficulty.EASY)
			return 4;
		else if (difficulty == Difficulty.MEDIUM)
			return 6;
		else if (difficulty == Difficulty.HARD)
			return 8;
		else
			return -1;
	}

	public int numberOfWordsNeeded(Difficulty difficulty) {
		return 0;
	}

	public void retry(View v) {
		// TODO Auto-generated method stub
		
	}
}

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
import com.wordwise.gameengine.Game;
import com.wordwise.server.model.Translation;
import com.wordwise.util.WordwiseUtils;
import com.wordwise.view.activity.WordwiseGameActivity;

public class Words2Translations extends WordwiseGameActivity
		implements
			Game,
			LoaderCallbacks<List<Translation>> {
	private Button validateButton;
	private Words2TranslationsManager manager;
	public ProgressBar progress; // ADDED

	@Override
	public void performOnCreate(Bundle savedInstanceState) {

		// this.onGameInit();
		// this.onGameStart();
		// progress = (ProgressBar) findViewById(R.id.progress_bar); //ADDED
		getLoaderManager()
				.initLoader(
						0,
						null,
						(android.app.LoaderManager.LoaderCallbacks<List<Translation>>) this);
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
		// TODO Auto-generated method stub
		Log.v("Loader", "created");

		setContentView(R.layout.loading_game);
		progress = (ProgressBar) findViewById(R.id.progress_bar); // ADDED

		setProgressBarIndeterminateVisibility(true);
		progress.setVisibility(View.VISIBLE); // ADDED
		return new Loader<List<Translation>>(this);
	}

	public void onLoadFinished(Loader<List<Translation>> arg0,
			List<Translation> arg1) {
		Log.v("Loader", "finished");
		if (arg1 == null) {
			Toast.makeText(this, "Failed to load bro.", Toast.LENGTH_SHORT)
					.show();
		}
		// TODO Auto-generated method stub

	}

	public void onLoaderReset(Loader<List<Translation>> arg0) {
		// TODO Auto-generated method stub

	}

}

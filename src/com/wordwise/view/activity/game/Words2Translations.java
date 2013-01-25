package com.wordwise.view.activity.game;

import java.util.List;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.wordwise.R;
import com.wordwise.controller.game.Words2TranslationsManager;
import com.wordwise.model.GameManagerContainer;
import com.wordwise.server.model.Difficulty;
import com.wordwise.server.model.Translation;
import com.wordwise.util.LoaderHelper.LoaderType;
import com.wordwise.util.WordwiseUtils;
import com.wordwise.view.activity.WordwiseGameActivity;

public class Words2Translations extends WordwiseGameActivity
		implements
			LoaderCallbacks<List<Translation>> {
	private Button validateButton;
	private Words2TranslationsManager manager;
	private List<Translation> translations;
	private ProgressBar progress;
	

	@Override
	public void performOnCreate(Bundle savedInstanceState) {
		loaderHelper.initLoader(this, LoaderType.TRANSLATION_LOADER);
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

	@SuppressWarnings("unchecked")
	public Loader<List<Translation>> onCreateLoader(int id, Bundle args) {
		return (Loader<List<Translation>>) loaderHelper.onLoadCreated(this, LoaderType.TRANSLATION_LOADER);
	}

	public void onLoadFinished(Loader<List<Translation>> loader,
			List<Translation> translations) {
		Log.v("translations", "" + translations);
		
		if (translations == null) {
			loaderHelper.loadFailed("Oh snap. Failed to load!");
		} else if (translations.size() < GameManagerContainer.getGameManager().NumberOfTranslationsNeeded()) {
			loaderHelper.loadFailed("Server does not have enough words!");
		} 
		else {
			this.translations = translations;
			setContentView(R.layout.words2translations);
			this.onGameInit();
			this.onGameStart();
		}
	}

	public void onLoaderReset(Loader<List<Translation>> arg0) {
		loaderHelper.onLoaderReset(this);
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
		loaderHelper.restartLoader(this, LoaderType.TRANSLATION_LOADER);
	}
	
	public List<Translation> getTranslations(){
		return translations;
	}
}

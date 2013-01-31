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
import com.wordwise.server.dto.DTODifficulty;
import com.wordwise.server.dto.DTOTranslation;
import com.wordwise.util.LoaderHelper.LoaderType;
import com.wordwise.util.WordwiseUtils;
import com.wordwise.view.activity.WordwiseGameActivity;

public class Words2Translations extends WordwiseGameActivity
		implements
			LoaderCallbacks<List<DTOTranslation>> {
	private Button validateButton;
	private Words2TranslationsManager manager;
	private List<DTOTranslation> translations;
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
	public Loader<List<DTOTranslation>> onCreateLoader(int id, Bundle args) {
		return (Loader<List<DTOTranslation>>) loaderHelper.onLoadCreated(this, LoaderType.TRANSLATION_LOADER);
	}

	public void onLoadFinished(Loader<List<DTOTranslation>> loader,
			List<DTOTranslation> translations) {
		Log.v("translations", "" + translations);
		
		if (translations == null) {
			loaderHelper.loadFailed("Oh snap. Failed to load!");
		} else if (translations.size() < GameManagerContainer.getGameManager().NumberOfTranslationsNeeded()) {
			loaderHelper.loadFailed("Server does not have enough words!");
		} 
		else {
			this.translations = translations;
			setContentView(R.layout.game_words2translations);
			this.onGameInit();
			this.onGameStart();
		}
	}

	public void onLoaderReset(Loader<List<DTOTranslation>> arg0) {
		loaderHelper.onLoaderReset(this);
	}

	public int numberOfTranslationsNeeded(DTODifficulty difficulty) {
		if (difficulty == DTODifficulty.EASY)
			return 4;
		else if (difficulty == DTODifficulty.MEDIUM)
			return 6;
		else if (difficulty == DTODifficulty.HARD)
			return 8;
		else
			return -1;
	}

	public int numberOfWordsNeeded(DTODifficulty difficulty) {
		return 0;
	}

	public void retry(View v) {
		loaderHelper.restartLoader(this, LoaderType.TRANSLATION_LOADER);
	}
	
	public List<DTOTranslation> getTranslations(){
		return translations;
	}
}

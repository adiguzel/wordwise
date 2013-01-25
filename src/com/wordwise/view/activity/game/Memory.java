package com.wordwise.view.activity.game;

import java.util.List;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wordwise.R;
import com.wordwise.controller.game.MemoryManager;
import com.wordwise.model.GameManagerContainer;
import com.wordwise.server.model.Difficulty;
import com.wordwise.server.model.Translation;
import com.wordwise.util.LoaderHelper.LoaderType;
import com.wordwise.view.activity.WordwiseGameActivity;

public class Memory extends WordwiseGameActivity implements
LoaderCallbacks<List<Translation>> {
	
	private Button continueButton;
	private List<Translation> translations;
	
	public void performOnCreate(Bundle savedInstanceState) {
		loaderHelper.initLoader(this, LoaderType.TRANSLATION_LOADER);
	}

	public void onGameStart() {
	}

	public void onGameStop() {
	}

	public void onGamePause() {
	}

	public void onGameInit() {
		// TODO Auto-generated method stub
		continueButton = (Button) findViewById(R.id.continueButton);
		new MemoryManager(this, translations).initMemoryGrid();
	}

	public void onGameEnd() {
		// TODO show necessary dialogs about game end
		continueButton.setEnabled(true);
	}

	public int numberOfTranslationsNeeded(Difficulty difficulty) {	
		if (difficulty == Difficulty.EASY)
			return 3;
		else if (difficulty == Difficulty.MEDIUM)
			return 6;
		else if (difficulty == Difficulty.HARD)
			return 9;
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

	@SuppressWarnings("unchecked")
	public Loader<List<Translation>> onCreateLoader(int id, Bundle args) {
		return (Loader<List<Translation>>) loaderHelper.onLoadCreated(this, LoaderType.TRANSLATION_LOADER);
	}

	public void onLoadFinished(Loader<List<Translation>> arg0,
			List<Translation> translations) {
		Log.v("translations", "" + translations);
		
		if (translations == null) {
			loaderHelper.loadFailed("Oh snap. Failed to load!");
		} else if (translations.size() < GameManagerContainer.getGameManager().NumberOfTranslationsNeeded()) {
			loaderHelper.loadFailed("Server does not have enough words!");
		} 
		else {
			this.translations = translations;
			setContentView(R.layout.memory);
			this.onGameInit();
			this.onGameStart();	
		}
	}

	public void onLoaderReset(Loader<List<Translation>> arg0) {
		loaderHelper.onLoaderReset(this);	
	}
	
}

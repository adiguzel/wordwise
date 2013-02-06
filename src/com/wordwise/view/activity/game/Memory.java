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
import com.wordwise.gameengine.level.GameFinishPromotion;
import com.wordwise.gameengine.level.Promotion;
import com.wordwise.model.GameManagerContainer;
import com.wordwise.server.dto.DTODifficulty;
import com.wordwise.server.dto.DTOTranslation;
import com.wordwise.util.LoaderHelper.LoaderType;
import com.wordwise.view.activity.WordwiseGameActivity;

public class Memory extends WordwiseGameActivity
		implements
			LoaderCallbacks<List<DTOTranslation>> {

	private Button continueButton;
	private List<DTOTranslation> translations;

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
		super.onGameEnd();
		continueButton.setEnabled(true);
	}

	public int numberOfTranslationsNeeded(DTODifficulty difficulty) {
		if (difficulty == DTODifficulty.EASY)
			return 3;
		else if (difficulty == DTODifficulty.MEDIUM)
			return 6;
		else if (difficulty == DTODifficulty.HARD)
			return 9;
		else
			return -1;
	}

	public int numberOfWordsNeeded(DTODifficulty difficulty) {
		return 0;
	}

	public void retry(View v) {
		loaderHelper.restartLoader(this, LoaderType.TRANSLATION_LOADER);
	}

	public List<DTOTranslation> getTranslations() {
		return translations;
	}

	@SuppressWarnings("unchecked")
	public Loader<List<DTOTranslation>> onCreateLoader(int id, Bundle args) {
		return (Loader<List<DTOTranslation>>) loaderHelper.onLoadCreated(this,
				LoaderType.TRANSLATION_LOADER);
	}

	public void onLoadFinished(Loader<List<DTOTranslation>> arg0,
			List<DTOTranslation> translations) {
		Log.v("translations", "" + translations);
		String gameLoadFailText;
		if (translations == null) {
			gameLoadFailText = getResources().getString(R.string.fail_game_load);
			loaderHelper.loadFailed(gameLoadFailText);
		} else if (translations.size() < GameManagerContainer.getGameManager()
				.NumberOfTranslationsNeeded()) {
			gameLoadFailText = getResources().getString(R.string.fail_insufficient_translations_on_server);
			loaderHelper.loadFailed(gameLoadFailText);
		} else {
			this.translations = translations;
			initLayout();
			this.onGameStart();
		}
	}

	public void onLoaderReset(Loader<List<DTOTranslation>> arg0) {
		loaderHelper.onLoaderReset(this);
	}

	@Override
	protected View gameContent() {
		return getLayoutInflater().inflate(R.layout.game_memory, null);
	}

	@Override
	protected boolean isRealGame() {
		return true;
	}
	
	@Override
	public Promotion getPromotion() {
		return new GameFinishPromotion();
	}

}

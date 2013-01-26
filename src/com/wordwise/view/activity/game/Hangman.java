package com.wordwise.view.activity.game;

import java.util.List;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.wordwise.R;
import com.wordwise.controller.game.HangmanManager;
import com.wordwise.model.GameManagerContainer;
import com.wordwise.server.dto.DTODifficulty;
import com.wordwise.server.dto.DTOTranslation;
import com.wordwise.util.LoaderHelper.LoaderType;
import com.wordwise.util.WordwiseUtils;
import com.wordwise.view.activity.WordwiseGameActivity;

public class Hangman extends WordwiseGameActivity
		implements
			LoaderCallbacks<List<DTOTranslation>> {

	private HangmanManager hangmanManager;
	private Button continueButton;
	private List<DTOTranslation> translations;
	private boolean gameStarted = false;
	boolean started = false;
	@Override
	public void performOnCreate(Bundle savedInstanceState) {
		loaderHelper.initLoader(this, LoaderType.TRANSLATION_LOADER);
		started = true;
	}

	public void onStop() {
		super.onStop();
	}

	@Override
	protected void onQuitPressed() {
		if (gameStarted)
			hangmanManager.closeTheSoftKeyboard();
		WordwiseUtils.makeQuitGameDialog(this);
	}

	/*
	 * This is the method that checks for the uni-code characters that are
	 * accessed using multiple keys or long press(non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyMultiple(int, int, android.view.KeyEvent)
	 */
	public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
		if (gameStarted) {
			if (keyCode == KeyEvent.KEYCODE_UNKNOWN
					&& event.getAction() == KeyEvent.ACTION_MULTIPLE
					&& hangmanManager.isALetter(keyCode)) {
				String letter = event.getCharacters();
				letter = letter.toUpperCase();
				hangmanManager.validateGuess(letter.charAt(0));
			}
		}
		return true;

	}

	/*
	 * This method checks for the normal characters accessed with one click (no
	 * long, no several keys)(non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//Eliminate some cases where those keys are used for something else
		if(keyCode == KeyEvent.KEYCODE_BACK){
			onBackPressed();
			return true;
		}
		else if(keyCode == KeyEvent.KEYCODE_MENU){
			return true;
		}
		else if(keyCode == KeyEvent.KEYCODE_SEARCH){
			return true;
		}

		
		if(gameStarted){
			if (hangmanManager.isALetter(keyCode)) {
				String letter = "" + (char) event.getUnicodeChar();
				letter = letter.toUpperCase();
				hangmanManager.validateGuess(letter.charAt(0));
				return true;
			}
			
		}
		return true;
	
	}

	public void onGameStart() {
	}

	public void onGameStop() {
	}

	public void onGamePause() {
	}

	public void onGameInit() {
		hangmanManager = new HangmanManager(this, translations);
		continueButton = (Button) findViewById(R.id.continueButton);
		hangmanManager.init();
		gameStarted = true;
	}

	public void onGameEnd() {
		// TODO show bye bye information
		continueButton.setEnabled(true);
	}

	public int numberOfTranslationsNeeded(DTODifficulty difficulty) {
		return 1;
	}

	public int numberOfWordsNeeded(DTODifficulty difficulty) {
		// TODO Auto-generated method stub
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

		if (translations == null) {
			loaderHelper.loadFailed("Oh snap. Failed to load!");
		} else if (translations.size() < GameManagerContainer.getGameManager()
				.NumberOfTranslationsNeeded()) {
			loaderHelper.loadFailed("Server does not have enough words!");
		} else {
			this.translations = translations;
			setContentView(R.layout.hangman);
			this.onGameInit();
			this.onGameStart();
		}
	}

	public void onLoaderReset(Loader<List<DTOTranslation>> arg0) {
		loaderHelper.onLoaderReset(this);
	}

}

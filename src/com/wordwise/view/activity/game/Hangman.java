package com.wordwise.view.activity.game;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;

import com.wordwise.R;
import com.wordwise.controller.game.HangmanManager;
import com.wordwise.view.activity.WordwiseGameActivity;

public class Hangman extends WordwiseGameActivity {

	private HangmanManager hangmanManager = new HangmanManager(this);
	private Button continueButton;

	@Override
	public void performOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.hangman);
		
		this.onGameInit();
		this.onGameStart();
	}
	
	public void onStop() {
		super.onStop();
		this.onGameStop();
	}

	/* This is the method that checks for the uni-code characters that are 
	 * accessed using multiple keys or long press(non-Javadoc)
	 * @see android.app.Activity#onKeyMultiple(int, int, android.view.KeyEvent)
	 */
	public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_UNKNOWN
				&& event.getAction() == KeyEvent.ACTION_MULTIPLE
				&& hangmanManager.isALetter(keyCode)) {
			String letter = event.getCharacters();
			letter = letter.toUpperCase();
			hangmanManager.validateGuess(letter.charAt(0));
		}
		return true;
	}

	/* This method checks for the normal characters accessed with 
	 * one click (no long, no several keys)(non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (hangmanManager.isALetter(keyCode)) {
			String letter = "" + (char) event.getUnicodeChar();
			letter = letter.toUpperCase();
			hangmanManager.validateGuess(letter.charAt(0));
			return true;
		}
		return true;
	}

	public void onGameStart() {
	}

	public void onGameStop() {
		//hangmanManager.closeTheSoftKeyboard();
	}

	public void onGamePause() {
	}

	public void onGameInit() {
		continueButton = (Button) findViewById(R.id.continueButton);
		hangmanManager = new HangmanManager(this);	
		hangmanManager.init();
	}
	
	public void onGameEnd() {
		//TODO show bye bye information
		continueButton.setEnabled(true);
	}
}

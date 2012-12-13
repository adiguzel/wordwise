package com.wordwise.activity.game;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.wordwise.R;
import com.wordwise.gameengine.Game;

public class Hangman extends Activity implements Game {

	private ImageView hangmanImageView;

	// dummy test initialization for the mystery word
	private String mysteryWord = "LACUCARACA";
	private int numWrongGuesses;
	private TextView wrongLettersTextView;
	private TextView mysteryWordTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hangman);

		// Initializes the screen
		this.bindViews();
		this.initialHangmanImage();
		this.initWrongGuesses();
		this.initMystWord();
		
		this.openTheSoftKeyboard();
	
	}
	

	private void openTheSoftKeyboard() {
		((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
				.toggleSoftInput(InputMethodManager.SHOW_FORCED,
						InputMethodManager.HIDE_IMPLICIT_ONLY);

	}

	private void closeTheSoftKeyboard() {
		((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(mysteryWordTextView.getWindowToken(), 0);

	}

	private void bindViews() {
		hangmanImageView = (ImageView) this.findViewById(R.id.hangman_img);
		wrongLettersTextView = (TextView) this
				.findViewById(R.id.hangman_wrong_letters);
		mysteryWordTextView = (TextView) this
				.findViewById(R.id.hangman_mystery_word);

	}

	private void initWrongGuesses() {
		numWrongGuesses = 0;
		wrongLettersTextView.setText("Wrong Letters: \n\n");
	}

	private void updateWrongGuesses(char wrongLetter) {
		wrongLettersTextView.setText(wrongLettersTextView.getText().toString()
				+ "  " + Character.toString(wrongLetter));
	}

	// This method will initialize the mysteryTextView with underscores
	private String underscore(String mysteryWord) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < mysteryWord.length(); i++) {
			result.append("_ ");
		}
		return result.toString();
	}

	/*
	 * sets the Hangman image to the starting image
	 */
	private void initialHangmanImage() {
		hangmanImageView.setImageResource(R.drawable.hangman_img00);
	}

	/*
	 * updates the Hangman image based on the number of wrong guesses
	 */
	private void updateHangmanImage() {
		switch (numWrongGuesses) {
		case 0:
			hangmanImageView.setImageResource(R.drawable.hangman_img00);
			break;
		case 1:
			hangmanImageView.setImageResource(R.drawable.hangman_img01);
			break;
		case 2:
			hangmanImageView.setImageResource(R.drawable.hangman_img02);
			break;
		case 3:
			hangmanImageView.setImageResource(R.drawable.hangman_img03);
			break;
		case 4:
			hangmanImageView.setImageResource(R.drawable.hangman_img04);
			break;
		case 5:
			hangmanImageView.setImageResource(R.drawable.hangman_img05);
			break;
		case 6:
			hangmanImageView.setImageResource(R.drawable.hangman_img06);
			break;
		case 7:
			hangmanImageView.setImageResource(R.drawable.hangman_img07);
			break;
		case 8:
			hangmanImageView.setImageResource(R.drawable.hangman_img08);
			break;
		case 9:
			hangmanImageView.setImageResource(R.drawable.hangman_img09);
			break;
		}
	}

	/*
	 * sets the View of Mystery Word to a text view with underscores and spaces
	 */

	private void initMystWord() {
		mysteryWordTextView.setText(underscore(mysteryWord));
	}

	public void start() {
		// TODO Auto-generated method stub

	}

	public void stop() {
		// TODO Auto-generated method stub

	}

	public void pause() {
		// TODO Auto-generated method stub

	}

	public void init() {
		// TODO Auto-generated method stub

	}

}

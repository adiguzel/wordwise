package com.wordwise.activity.game;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

		this.init();
		
		this.start();

	}

	// TODO Implement method that listens the input from the keyboard
	// TODO Implement method to read the mysteryWord from the server
	// TODO Edit checkWin() and checkLose() and link them with GameManager
	// TODO Pause,Resume,Stop,Save Progress
	// TODO Polish the procedure with the device keyboard
	// TODO update the layout suggestion maybe ?

	private void checkWin() {
		if (mysteryWordTextView.getText().toString().indexOf("_ ") == -1) {

			this.closeTheSoftKeyboard();

			Toast msg = Toast.makeText(this, "VERY GOOD!", Toast.LENGTH_LONG);
			msg.show();
			// IMPLEMENT THE MOVE TO THE OTHER ACTIVITY
		}
	}

	private void checkLose() {
		if (numWrongGuesses == 9) {
			Toast msg = Toast.makeText(this, "MORE LUCK NEXT TIME!",
					Toast.LENGTH_LONG);
			msg.show();
		}
	}

	private void validateGuess(char guess) {
		if (mysteryWord.indexOf(guess) == -1) {
			String wrongLetters = wrongLettersTextView.getText().toString();
			if (wrongLetters.indexOf(guess) == -1) {
				if (numWrongGuesses < 9) {
					numWrongGuesses++;
					updateWrongGuesses(guess);
					updateHangmanImage();
				}
				checkLose();
			}
		} else {
			if (numWrongGuesses < 9) {
				updateMysteriousWord(guess);
				checkWin();
			} else {
				checkLose();
			}
		}
	}

	private void updateMysteriousWord(char ch) {
		char[] updatedWord = mysteryWordTextView.getText().toString()
				.toCharArray();
		for (int i = 0; i < mysteryWord.length(); i++) {
			if (ch == mysteryWord.charAt(i)) {
				updatedWord[i * 2] = mysteryWord.charAt(i);
			}
		}
		mysteryWordTextView.setText(new String(updatedWord));
	}

	private void openTheSoftKeyboard() {
		((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
				.toggleSoftInput(InputMethodManager.SHOW_FORCED,
						InputMethodManager.HIDE_IMPLICIT_ONLY);

	}

	private void closeTheSoftKeyboard() {
		((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(mysteryWordTextView.getWindowToken(),
						0);

	}

	private void linkTheViews() {
		hangmanImageView = (ImageView) this.findViewById(R.id.hangman_img);
		wrongLettersTextView = (TextView) this
				.findViewById(R.id.hangman_wrong_letters);
		mysteryWordTextView = (TextView) this
				.findViewById(R.id.hangman_mystery_word);

	}

	private void initWrongGuessesTextView() {
		numWrongGuesses = 0;
		wrongLettersTextView.setText("Wrong Letters: \n\n");
	}

	private void updateWrongGuesses(char wrongLetter) {
		wrongLettersTextView.setText(wrongLettersTextView.getText().toString()
				+ "  " + Character.toString(wrongLetter));
	}

	// This method will initialize the mysteryTextView with underscores
	private String underscoreTheMysteryWord(String mysteryWord) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < mysteryWord.length(); i++) {
			result.append("_ ");
		}
		return result.toString();
	}

	/*
	 * sets the Hangman image to the starting image
	 */
	private void initTheHangmanImage() {
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

	private void initMysteriousWord() {
		mysteryWordTextView.setText(underscoreTheMysteryWord(mysteryWord));
	}

	public void start() {
		// TODO Auto-generated method stub
		// TEST to see whether it is working
		this.validateGuess('a');
		this.validateGuess('a');
		this.validateGuess('a');
		this.validateGuess('A');
		this.validateGuess('Z');
		this.validateGuess('L');
		this.validateGuess('C');
	}

	public void stop() {
		// TODO Auto-generated method stub

	}

	public void pause() {
		// TODO Auto-generated method stub

	}

	public void init() {
		// TODO Auto-generated method stub
		// Initializes the screen
		this.linkTheViews();
		this.initTheHangmanImage();
		this.initWrongGuessesTextView();
		this.initMysteriousWord();
		this.openTheSoftKeyboard();
	}

}

package com.wordwise.view.activity.game;

import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wordwise.R;
import com.wordwise.view.activity.WordwiseGameActivity;

public class Hangman extends WordwiseGameActivity{

	private final int DOESNT_EXIST = -1;
	private final int MAXIMUM_WRONG_GUESSES = 9;

	private static boolean savedGame = false;
	private boolean wonGame = false;
	private boolean lostGame = false;

	private ImageView hangmanImageView;

	// dummy test initialization for the mystery word
	private String mysteryWord = "ÜBUNG";

	private int numWrongGuesses;
	private TextView wrongLettersTextView;
	private TextView mysteryWordTextView;
	private Button continueButton;

	private static final String PREFERENCES_MYSTERY_WORD_TEXT_VIEW = "mysteryWordTextView";
	private static final String PREFERENCES_MYSTERY_WORD = "mysteryWord";
	private static final String PREFERENCES_WRONG_LETTERS = "wrongLetters";
	private static final String PREFERENCES_NUM_WRONG_GUESSES = "numWrongGuesses";

	@Override
	public void performOnCreate(Bundle savedInstanceState) {
		// This is the fragment of the code that changes the lanuguage
		String languageToLoad = "de"; // your language
		Locale locale = new Locale(languageToLoad);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());

		setContentView(R.layout.hangman);
		this.onGameInit();
		if (Hangman.savedGame == true) {
			this.loadTheSavedGame();
		}
		this.onGameStart();
	}


	public void onStop() {
		super.onStop();
		this.onGamePause();
		this.onGameStop();
	}

	public void onDestroy() {
		super.onDestroy();
		this.onGameStop();
	}

	public void onPause() {
		super.onPause();
		this.onGamePause();
	}

	public void onResume() {
		super.onResume();
		this.openTheSoftKeyboard();
	}

	// TODO Implement method to read the mysteryWord from the server
	// TODO Edit checkWin() and checkLose() and link them with GameManager

	// This is the method that checks for the unicode characters that are
	// accessed useing multiple keys or long press
	public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_UNKNOWN
				&& event.getAction() == KeyEvent.ACTION_MULTIPLE
				&& this.isALetter(keyCode)) {
			String letter = event.getCharacters();
			letter = letter.toUpperCase();
			validateGuess(letter.charAt(0));
		}
		return true;
	}

	// This method checks for the normal characters accessed with one click (no
	// long, no several keys)
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Implement saving of the game state
			return super.onKeyDown(keyCode, event);

		} else if (this.isALetter(keyCode)) {
			String letter = "" + (char) event.getUnicodeChar();
			letter = letter.toUpperCase();
			validateGuess(letter.charAt(0));
			return true;
		}
		return true;
	}

	// This method adds constraint about which chars should not be used at all.
	// For example numbers ...
	private boolean isALetter(int keyCode) {
		// Letter ASCII constraints
		// Only numbers added
		if (((keyCode < 7) || (keyCode > 16))) {
			return true;
		} else {
			return false;
		}
	}

	private void checkWin() {
		if (mysteryWordTextView.getText().toString().indexOf("_ ") == DOESNT_EXIST) {

			this.closeTheSoftKeyboard();

			Toast msg = Toast.makeText(this, "VERY GOOD!", Toast.LENGTH_LONG);
			msg.show();

			this.wonGame = true;
			continueButton.setVisibility(Button.VISIBLE);

			// IMPLEMENT THE MOVE TO THE OTHER ACTIVITY
		}
	}

	private void checkLose() {
		if (numWrongGuesses == MAXIMUM_WRONG_GUESSES) {

			this.closeTheSoftKeyboard();

			Toast msg = Toast.makeText(this, "MORE LUCK NEXT TIME!",
					Toast.LENGTH_LONG);
			msg.show();

			this.lostGame = true;
			// IMPLEMENT THE MOVE TO THE OTHER ACTIVITY
		}
	}

	private void validateGuess(char guess) {
		if (mysteryWord.indexOf(guess) == DOESNT_EXIST) {
			String wrongLetters = wrongLettersTextView.getText().toString();
			if (wrongLetters.indexOf(guess) == DOESNT_EXIST) {
				if (numWrongGuesses < MAXIMUM_WRONG_GUESSES) {
					numWrongGuesses++;
					updateWrongGuesses(guess);
					updateHangmanImage();
				}
				checkLose();
			}
		} else {
			if (numWrongGuesses < MAXIMUM_WRONG_GUESSES) {
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

	private void loadTheSavedGame() {
		this.openTheSoftKeyboard();
		this.mysteryWord = getPreferences(MODE_PRIVATE).getString(
				PREFERENCES_MYSTERY_WORD, this.mysteryWord);
		this.mysteryWordTextView.setText(getPreferences(MODE_PRIVATE)
				.getString(PREFERENCES_MYSTERY_WORD_TEXT_VIEW,
						underscoreTheMysteryWord(this.mysteryWord)));
		this.wrongLettersTextView.setText(getPreferences(MODE_PRIVATE)
				.getString(PREFERENCES_WRONG_LETTERS, ""));
		this.numWrongGuesses = getPreferences(MODE_PRIVATE).getInt(
				PREFERENCES_NUM_WRONG_GUESSES, 0);

		this.updateHangmanImage();

		// give the information that the saved game is already initialized
		Hangman.savedGame = false;
	}

	private void linkTheViews() {
		hangmanImageView = (ImageView) this.findViewById(R.id.hangman_img);
		wrongLettersTextView = (TextView) this
				.findViewById(R.id.hangman_wrong_letters);
		mysteryWordTextView = (TextView) this
				.findViewById(R.id.hangman_mystery_word);
		
		continueButton = (Button) findViewById(R.id.continueButton);

	}

	private void initWrongGuesses() {
		numWrongGuesses = 0;
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
			case 0 :
				hangmanImageView.setImageResource(R.drawable.hangman_img00);
				break;
			case 1 :
				hangmanImageView.setImageResource(R.drawable.hangman_img01);
				break;
			case 2 :
				hangmanImageView.setImageResource(R.drawable.hangman_img02);
				break;
			case 3 :
				hangmanImageView.setImageResource(R.drawable.hangman_img03);
				break;
			case 4 :
				hangmanImageView.setImageResource(R.drawable.hangman_img04);
				break;
			case 5 :
				hangmanImageView.setImageResource(R.drawable.hangman_img05);
				break;
			case 6 :
				hangmanImageView.setImageResource(R.drawable.hangman_img06);
				break;
			case 7 :
				hangmanImageView.setImageResource(R.drawable.hangman_img07);
				break;
			case 8 :
				hangmanImageView.setImageResource(R.drawable.hangman_img08);
				break;
			case MAXIMUM_WRONG_GUESSES :
				hangmanImageView.setImageResource(R.drawable.hangman_img09);
				break;
		}
	}

	/*
	 * sets the View of Mystery Word to a text view with underscores and spaces
	 */

	private void initMysteriousWord() {
		mysteryWordTextView.setText(underscoreTheMysteryWord(mysteryWord));
		mysteryWordTextView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				openTheSoftKeyboard();
			}
		});
	}

	public void onGameStart() {
		// TODO Auto-generated method stub
		// TEST to see whether it is working
	}

	public void onGameStop() {
		// this.closeTheSoftKeyboard();
	}

	public void onGamePause() {
		if (this.lostGame == true || this.wonGame == true) {
			this.closeTheSoftKeyboard();
		} else {
			this.closeTheSoftKeyboard();
			// Store values between instances here
			SharedPreferences preferences = getPreferences(MODE_PRIVATE);
			SharedPreferences.Editor editor = preferences.edit();

			// values to store
			editor.putString(PREFERENCES_MYSTERY_WORD, mysteryWord);
			editor.putString(PREFERENCES_MYSTERY_WORD_TEXT_VIEW,
					mysteryWordTextView.getText().toString());
			editor.putString(PREFERENCES_WRONG_LETTERS, wrongLettersTextView
					.getText().toString());
			editor.putInt(PREFERENCES_NUM_WRONG_GUESSES, numWrongGuesses);

			// Commit to storage
			editor.commit();

			// give the signal that the game was saved
			Hangman.savedGame = true;
		}
	}

	public void onGameInit() {
		// Initializes the screen
		this.linkTheViews();
		this.initTheHangmanImage();
		this.initWrongGuesses();
		this.initMysteriousWord();
		this.openTheSoftKeyboard();
	}

	public void onGameEnd() {
		// TODO Auto-generated method stub
		
	}



}

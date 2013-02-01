package com.wordwise.controller.game;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wordwise.R;
import com.wordwise.model.Configuration;
import com.wordwise.server.dto.DTOLanguage;
import com.wordwise.server.dto.DTOTranslation;
import com.wordwise.util.LanguageUtils;
import com.wordwise.view.activity.game.Hangman;

public class HangmanManager {
	private final int DOESNT_EXIST = -1;
	private final int MAXIMUM_WRONG_GUESSES = 9;

	// Activity to manage
	private Hangman hangmanActivity;

	// Initialized with dummy word for practicing
	private String mysteryWord;

	// UI elements
	private int numWrongGuesses;
	private TextView wrongLettersTextView;
	private TextView mysteryWordTextView;
	private Button continueButton;
	private ImageView hangmanImageView;

	// Configuration properties
	private Configuration configuration;
	private DTOLanguage learningLanguage;
	private Locale locale;
	
	private int isFound = 0;

	public HangmanManager(Hangman hangmanActivity,
			List<DTOTranslation> translations) {
		this.hangmanActivity = hangmanActivity;
		mysteryWord = translations.get(0).getTranslation().toUpperCase();
	}

	public boolean isALetter(int keyCode) {
		// Letter ASCII constraints Only numbers added
		if (((keyCode < 7) || (keyCode > 16))) {
			return true;
		} else {
			return false;
		}
	}

	public void validateGuess(char guess) {
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

	private void updateWrongGuesses(char wrongLetter) {
		wrongLettersTextView.setText(wrongLettersTextView.getText().toString()
				+ "  " + Character.toString(wrongLetter));
	}

	private void linkTheViews() {
		hangmanImageView = (ImageView) hangmanActivity
				.findViewById(R.id.hangman_img);
		wrongLettersTextView = (TextView) hangmanActivity
				.findViewById(R.id.hangman_wrong_letters);
		mysteryWordTextView = (TextView) hangmanActivity
				.findViewById(R.id.hangman_mystery_word);
		continueButton = (Button) hangmanActivity
				.findViewById(R.id.continueButton);

	}

	private void initWrongGuesses() {
		numWrongGuesses = 0;
	}

	private void checkWin() {
		if (mysteryWordTextView.getText().toString().indexOf("_ ") == DOESNT_EXIST) {

			this.closeTheSoftKeyboard();

			Toast msg = Toast.makeText(hangmanActivity, "VERY GOOD!",
					Toast.LENGTH_SHORT);
			msg.show();

			continueButton.setVisibility(Button.VISIBLE);
			mysteryWordTextView.setOnClickListener(null);
			hangmanActivity.onGameEnd();
			
			isFound = 1;
		}
	}

	private void checkLose() {
		if (numWrongGuesses == MAXIMUM_WRONG_GUESSES) {

			this.closeTheSoftKeyboard();

			Toast msg = Toast.makeText(hangmanActivity, "MORE LUCK NEXT TIME!",
					Toast.LENGTH_SHORT);
			msg.show();
			mysteryWordTextView.setOnClickListener(null);

			hangmanActivity.onGameEnd();
		}
	}

	private void openTheSoftKeyboard() {
		if (continueButton != null)
			continueButton.setVisibility(View.GONE);
		((InputMethodManager) hangmanActivity
				.getSystemService(Context.INPUT_METHOD_SERVICE))
				.toggleSoftInput(InputMethodManager.SHOW_FORCED,
						InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	public void closeTheSoftKeyboard() {
		if (continueButton != null)
			continueButton.setVisibility(View.VISIBLE);
		Log.v("closing the keyboarad", "");
		((InputMethodManager) hangmanActivity
				.getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(mysteryWordTextView.getWindowToken(),
						0);

	}

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

	private void initMysteriousWord() {
		mysteryWordTextView.setText(underscoreTheMysteryWord(mysteryWord));
		mysteryWordTextView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				openTheSoftKeyboard();
			}
		});
	}

	// This method will initialize the mysteryTextView with underscores
	private String underscoreTheMysteryWord(String mysteryWord) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < mysteryWord.length(); i++) {
			result.append("_ ");
		}
		return result.toString();
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

	private void initTheHangmanImage() {
		hangmanImageView.setImageResource(R.drawable.hangman_img00);
	}

	private void getConfigurationDetails() {
		this.configuration = Configuration.getInstance(hangmanActivity);
		this.learningLanguage = configuration.getLearningLanguage();
	}

	public void init() {
		getConfigurationDetails();
		linkTheViews();
		initTheHangmanImage();
		initWrongGuesses();
		initMysteriousWord();

		String languageToLoad = learningLanguage.getCode();
		LanguageUtils.setLocale(locale, languageToLoad, hangmanActivity);

		openTheSoftKeyboard();
	}
	
	public int isFound(){
		return isFound;
	}
	

}

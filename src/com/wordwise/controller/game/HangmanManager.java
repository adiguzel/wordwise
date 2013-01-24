package com.wordwise.controller.game;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wordwise.R;
import com.wordwise.client.RESTfullServerCommunication;
import com.wordwise.gameengine.GameManager;
import com.wordwise.model.Configuration;
import com.wordwise.model.GameManagerContainer;
import com.wordwise.server.model.Difficulty;
import com.wordwise.server.model.Language;
import com.wordwise.server.model.Translation;
import com.wordwise.util.LanguageUtils;
import com.wordwise.view.activity.game.Hangman;

public class HangmanManager {

	private GameManager gameManager;
	private Hangman hangmanActivity;

	private final int DOESNT_EXIST = -1;
	private final int MAXIMUM_WRONG_GUESSES = 9;

	private ImageView hangmanImageView;

	// Initialized with dummy word for practicing
	private String mysteryWord;

	private int numWrongGuesses;
	private TextView wrongLettersTextView;
	private TextView mysteryWordTextView;
	private Button continueButton;

	// Configuration properties
	private Configuration configuration;
	private Language learningLanguage;
	private Locale locale;
	private RESTfullServerCommunication server;
	private Difficulty difficulty;

	public HangmanManager(Hangman hangmanActivity) {
		this.hangmanActivity = hangmanActivity;
	}

	private void getMysteryWordFromServer() {
		server = new RESTfullServerCommunication();
		List<Translation> translation = server.listTranslations(learningLanguage, this.difficulty, 1, null);
		this.mysteryWord = translation.get(1).getTranslation();
	}

	public boolean isALetter(int keyCode) {
		/*
		 * Letter ASCII constraints Only numbers added
		 */
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

			hangmanActivity.onGameEnd();
		}
	}

	private void checkLose() {
		if (numWrongGuesses == MAXIMUM_WRONG_GUESSES) {

			this.closeTheSoftKeyboard();

			Toast msg = Toast.makeText(hangmanActivity, "MORE LUCK NEXT TIME!",
					Toast.LENGTH_SHORT);
			msg.show();

			hangmanActivity.onGameEnd();
		}
	}

	private void openTheSoftKeyboard() {
		((InputMethodManager) hangmanActivity
				.getSystemService(Context.INPUT_METHOD_SERVICE))
				.toggleSoftInput(InputMethodManager.SHOW_FORCED,
						InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	public void closeTheSoftKeyboard() {
		((InputMethodManager) hangmanActivity
				.getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(mysteryWordTextView.getWindowToken(),
						0);

	}

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
		case MAXIMUM_WRONG_GUESSES:
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
		this.difficulty = this.configuration.getDifficulty();
		this.learningLanguage = configuration.getLearningLanguage();
	}

	public void init() {
		gameManager = GameManagerContainer.getGameManager();
		this.getConfigurationDetails();
		this.getMysteryWordFromServer();
		this.linkTheViews();
		this.initTheHangmanImage();
		this.initWrongGuesses();
		this.initMysteriousWord();

		String languageToLoad = learningLanguage.getCode();
		LanguageUtils.setLocale(locale, languageToLoad, hangmanActivity);

		this.openTheSoftKeyboard();
	}

}

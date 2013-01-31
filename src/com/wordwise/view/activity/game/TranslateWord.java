package com.wordwise.view.activity.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wordwise.R;
import com.wordwise.model.Configuration;
import com.wordwise.model.SubmitListener;
import com.wordwise.server.dto.DTODifficulty;
import com.wordwise.server.dto.DTOLanguage;
import com.wordwise.server.dto.DTOTranslation;
import com.wordwise.server.dto.DTOWord;
import com.wordwise.task.game.TranslationSubmitTask;
import com.wordwise.util.LanguageUtils;
import com.wordwise.util.WordwiseUtils;
import com.wordwise.view.activity.WordwiseGameActivity;

public class TranslateWord extends WordwiseGameActivity
		implements
			SubmitListener {

	private Configuration configuration;
	private Set<DTOLanguage> proficientLanguagesSet;
	private List<DTOLanguage> proficientLanguagesList = new ArrayList<DTOLanguage>();

	private DTOWord englishWord;
	private DTOTranslation translation;

	private DTOLanguage randomProficientLanguage;
	private Locale englishLocale;
	private Locale proficientLanguageLocale;

	private EditText wordToBeTranslated;
	private EditText wordTranslation;
	private Button submitTranslation;
	private Button continueButton;

	private String wordToBeTranslatedBuffer;
	private String wordTranslationBuffer;

	@Override
	public void performOnCreate(Bundle savedInstanceState) {
		initLayout();
		onGameStart();
	}

	@SuppressLint("NewApi")
	public void onGameInit() {
		wordToBeTranslated = (EditText) this
				.findViewById(R.id.wordToBeTranslated);
		wordTranslation = (EditText) this.findViewById(R.id.wordTranslation);
		submitTranslation = (Button) this.findViewById(R.id.submitTranslation);
		continueButton = (Button) this.findViewById(R.id.continueButton);

		// Setting English locale on the first EditText
		this.englishLocale = new Locale("en");
		// this.wordToBeTranslated.setTextLocale(this.englishLocale);

		configuration = Configuration.getInstance(this);
		proficientLanguagesSet = configuration.getProficientLanguages();
		Log.v("set", "prof.  : " + proficientLanguagesSet);
		proficientLanguagesList = LanguageUtils
				.getProficientLanguages(proficientLanguagesSet);
		Log.v("set", "prof. list  : " + proficientLanguagesList);
		randomProficientLanguage = chooseRandomProficientLanguage();

		// Setting ProfLanguage Locale on the second EditText
		this.proficientLanguageLocale = new Locale(
				randomProficientLanguage.getCode());
		// this.wordTranslation.setTextLocale(this.proficientLanguageLocale);

		String translationEditText = wordTranslation.getHint().toString() + " "
				+ randomProficientLanguage.getLanguage();
		wordTranslation.setHint(translationEditText);

		this.englishWord = new DTOWord();
		this.translation = new DTOTranslation();

		this.continueButton.setEnabled(false);
		this.continueButton.setVisibility(View.INVISIBLE);
		this.submitTranslation.setEnabled(true);
		this.submitTranslation.setVisibility(View.VISIBLE);
	}

	private DTOLanguage chooseRandomProficientLanguage() {
		// removing English since this is the language from which the words are
		// being translated
		/*
		 * if (proficientLanguagesList
		 * .contains(LanguageUtils.getByName("English"))) {
		 * proficientLanguagesList.remove(proficientLanguagesList
		 * .indexOf(LanguageUtils.getByName("English"))); }
		 */

		DTOLanguage randomLanguage = LanguageUtils
				.getRandomProficientLanguage(proficientLanguagesList);

		return randomLanguage;
	}

	public void onGameStart() {
		// TODO Auto-generated method stub

	}

	public void onGameStop() {
		// TODO Auto-generated method stub

	}

	public void onGamePause() {
		// TODO Auto-generated method stub

	}

	public void onGameEnd() {
		submitTranslation.setEnabled(false);
		submitTranslation.setVisibility(View.INVISIBLE);
		// Tell to the game manager that the translation is submitted
		wordToBeTranslated.setEnabled(false);
		wordTranslation.setEnabled(false);

		continueButton.setEnabled(true);
		continueButton.setVisibility(View.VISIBLE);
	}

	private void sendDataToServer(String word, String translation) {
		englishWord.setWord(word);
		this.translation.setTranslation(translation);
		this.translation.setWord(englishWord);
		this.translation.setLanguage(randomProficientLanguage);

		new TranslationSubmitTask(this, this, this.translation).execute();
	}

	public void submitTranslation(View v) {
		wordToBeTranslatedBuffer = wordToBeTranslated.getText().toString();
		wordTranslationBuffer = wordTranslation.getText().toString();

		// check whether the fields are not empty
		if (wordToBeTranslatedBuffer.equalsIgnoreCase("")
				|| wordTranslationBuffer.equalsIgnoreCase("")) {
			String message = "Please fill both: The word and the translation!";
			WordwiseUtils.makeCustomToast(this, message);
		} else {
			sendDataToServer(wordToBeTranslatedBuffer, wordTranslationBuffer);
		}
	}

	public int numberOfTranslationsNeeded(DTODifficulty difficulty) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int numberOfWordsNeeded(DTODifficulty difficulty) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void retry(View v) {
		// TODO Auto-generated method stub

	}

	public void onSubmitResult(boolean result) {
		if (result) {
			String successMessage = "Thank you for your translation.";
			WordwiseUtils.makeCustomToast(this, successMessage,
					Toast.LENGTH_LONG);
			this.onGameEnd();
		} else {
			String failMessage = "Your translation could not be submitted. Check your internet connection and please try again later.";
			WordwiseUtils.makeCustomToast(this, failMessage);
		}
	}

	@Override
	protected View gameContent() {
		return getLayoutInflater().inflate(R.layout.game_translate_word, null);
	}

	@Override
	protected boolean isRealGame() {
		return false;
	}

	@Override
	public List<DTOTranslation> getTranslations() {
		return null;
	}
}

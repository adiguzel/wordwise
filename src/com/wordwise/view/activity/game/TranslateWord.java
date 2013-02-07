package com.wordwise.view.activity.game;

import java.util.List;
import java.util.Locale;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wordwise.R;
import com.wordwise.gameengine.level.Promotion;
import com.wordwise.gameengine.level.TranslationPromotion;
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
			SubmitListener,
			TextWatcher {
	// a random language among proficient languages in which translation will be
	// made
	private DTOLanguage randomProficientLanguage;

	private Locale englishLocale;
	private Locale proficientLanguageLocale;
	// UI elements
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

	public void onGameInit() {
		wordToBeTranslated = (EditText) this
				.findViewById(R.id.wordToBeTranslated);
		wordTranslation = (EditText) this.findViewById(R.id.wordTranslation);
		submitTranslation = (Button) this.findViewById(R.id.submitTranslation);
		continueButton = (Button) this.findViewById(R.id.continueButton);

		wordToBeTranslated.addTextChangedListener(this);
		wordTranslation.addTextChangedListener(this);

		// Setting English locale on the first EditText
		this.englishLocale = new Locale("en");
		// this.wordToBeTranslated.setTextLocale(this.englishLocale);

		randomProficientLanguage = chooseRandomProficientLanguage();

		// Setting ProfLanguage Locale on the second EditText
		this.proficientLanguageLocale = new Locale(
				randomProficientLanguage.getCode());
		// this.wordTranslation.setTextLocale(this.proficientLanguageLocale);

		String translationEditText = wordTranslation.getHint().toString() + " "
				+ randomProficientLanguage.getLanguage();
		wordTranslation.setHint(translationEditText);

		this.continueButton.setEnabled(false);
	}

	private DTOLanguage chooseRandomProficientLanguage() {
		List<DTOLanguage> proficientLanguagesList = LanguageUtils
				.getProficientLanguages(configuration.getProficientLanguages());

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
		super.onGameEnd();
		submitTranslation.setEnabled(false);
		submitTranslation.setVisibility(View.GONE);
		// Tell to the game manager that the translation is submitted
		wordToBeTranslated.setEnabled(false);
		wordTranslation.setEnabled(false);

		continueButton.setEnabled(true);
		continueButton.setVisibility(View.VISIBLE);
	}
	
	// sends the translation to the server
	private void submit(String word, String translation) {
		DTOWord englishWordDTO = new DTOWord();
		DTOTranslation translationDTO = new DTOTranslation();
		englishWordDTO.setWord(word);
		translationDTO.setTranslation(translation);
		translationDTO.setWord(englishWordDTO);
		translationDTO.setLanguage(randomProficientLanguage);

		new TranslationSubmitTask(this, this, translationDTO).execute();
	}

	public void submitTranslation(View v) {
		wordToBeTranslatedBuffer = wordToBeTranslated.getText().toString();
		wordTranslationBuffer = wordTranslation.getText().toString();

		submit(wordToBeTranslatedBuffer, wordTranslationBuffer);

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
			String successMessage = getResources().getString(
					R.string.success_translation_submit);
			WordwiseUtils.makeCustomToast(this, successMessage,
					Toast.LENGTH_LONG);
			this.onGameEnd();
		} else {
			String failMessage = getResources().getString(
					R.string.fail_translation_submit);
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
	public Promotion getPromotion() {
		return new TranslationPromotion();
	}

	@Override
	public List<DTOTranslation> getTranslations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		String word = wordToBeTranslated.getText().toString();
		String translation = wordTranslation.getText().toString();
		submitTranslation.setEnabled(true);
		if (word.isEmpty() && translation.isEmpty())
			submitTranslation.setEnabled(false);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

}

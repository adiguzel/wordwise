package com.wordwise.view.activity.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wordwise.R;
import com.wordwise.client.RESTfullServerCommunication;
import com.wordwise.model.Configuration;
import com.wordwise.server.model.Language;
import com.wordwise.server.model.Translation;
import com.wordwise.server.model.Word;
import com.wordwise.util.LanguageUtils;
import com.wordwise.view.activity.WordwiseGameActivity;

public class TranslateWord extends WordwiseGameActivity {

	private Configuration configuration;
	private Set<Language> proficientLanguagesSet;
	private List<Language> proficientLanguagesList = new ArrayList<Language>();

	private RESTfullServerCommunication server;
	private Word englishWord;
	private Translation translation;

	private Language randomProficientLanguage;
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
		setContentView(R.layout.translate_word);

		this.onGameInit();

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
		this.wordToBeTranslated.setTextLocale(this.englishLocale);

		configuration = Configuration.getInstance(this);
		proficientLanguagesSet = configuration.getProficientLanguages();
		proficientLanguagesList = LanguageUtils
				.getProficientLanguages(proficientLanguagesSet);

		randomProficientLanguage = chooseRandomProficientLanguage();

		// Setting ProfLanguage Locale on the second EditText
		this.proficientLanguageLocale = new Locale(
				randomProficientLanguage.getCode());
		this.wordTranslation.setTextLocale(this.proficientLanguageLocale);

		String translationEditText = wordTranslation.getHint().toString() + " "
				+ randomProficientLanguage.getLanguage();
		wordTranslation.setHint(translationEditText);
		
		this.englishWord = new Word();
		this.translation = new Translation();
		
		this.continueButton.setEnabled(false);
		this.continueButton.setVisibility(View.INVISIBLE);
		this.submitTranslation.setEnabled(true);
		this.submitTranslation.setVisibility(View.VISIBLE);
	}

	private Language chooseRandomProficientLanguage() {
		// removing English since this is the language from which the words are
		// being translated
		if (proficientLanguagesList
				.contains(LanguageUtils.getByName("English"))) {
			proficientLanguagesList.remove(proficientLanguagesList
					.indexOf(LanguageUtils.getByName("English")));
		}

		Language randomLanguage = LanguageUtils
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
		// TODO Auto-generated method stub
		continueButton.setEnabled(true);
		continueButton.setVisibility(View.VISIBLE);
	}

	private void sendDataToServer(String word, String translation) {
		this.server = new RESTfullServerCommunication();
		
		this.englishWord.setWord(word);
		this.translation.setTranslation(translation);
	
		server.addWord(this.englishWord);
		server.addTranslation(englishWord, randomProficientLanguage, this.translation);
		
	}

	public void submitTranslation(View v) {
		wordToBeTranslatedBuffer = wordToBeTranslated.getText().toString();
		wordTranslationBuffer = wordTranslation.getText().toString();

		// check whether the fields are not empty
		if (wordToBeTranslatedBuffer.equalsIgnoreCase("")
				|| wordTranslationBuffer.equalsIgnoreCase("")) {
			Toast msg = Toast.makeText(this,
					"Please fill both: The word and the translation!",
					Toast.LENGTH_SHORT);
			msg.show();
		} else {
			// Remove the words from the edit fields when the data is submitted
			this.wordToBeTranslated.setText("");
			this.wordTranslation.setText("");

			// TODO Show feedback while sending ...

			this.sendDataToServer(wordToBeTranslatedBuffer,
					wordTranslationBuffer);
			Toast msg = Toast.makeText(this,
					"Your translation was submitted successfully!",
					Toast.LENGTH_SHORT);
			msg.show();

			this.submitTranslation.setEnabled(false);
			this.submitTranslation.setVisibility(View.INVISIBLE);
			
			// Tell to the game manager that the translation is submitted
			this.onGameEnd();
		}

	}

}

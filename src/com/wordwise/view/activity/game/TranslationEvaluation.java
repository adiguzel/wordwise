package com.wordwise.view.activity.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wordwise.R;
import com.wordwise.client.RESTfullServerCommunication;
import com.wordwise.model.Configuration;
import com.wordwise.server.model.Language;
import com.wordwise.server.model.Word;
import com.wordwise.util.LanguageUtils;
import com.wordwise.view.activity.WordwiseGameActivity;

public class TranslationEvaluation extends WordwiseGameActivity {

	private Configuration configuration;
	private Set<Language> proficientLanguagesSet;
	private List<Language> proficientLanguagesList = new ArrayList<Language>();

	private List<Word> listOfEnglishWords;

	private RESTfullServerCommunication serverCommunication;
	private Word englishWord;
	private Word translation;

	private Button submitRating;
	private Button continueButton;
	private TextView wordInEnglish;
	private TextView translationLanguageTitle;
	private TextView translationToRate;
	private RatingBar translationRating;

	private Language englishLanugage = new Language("English", "en");
	private Language languageOfTranslation;

	@Override
	public void performOnCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.rate_translation);
		onGameInit();
		onGameStart();

	}

	public void onGameInit() {
		submitRating = (Button) findViewById(R.id.submitRatingButton);
		continueButton = (Button) findViewById(R.id.continueButton);
		wordInEnglish = (TextView) findViewById(R.id.wordInEnglish);
		translationLanguageTitle = (TextView) findViewById(R.id.translationTitle);
		translationToRate = (TextView) findViewById(R.id.translationToRate);
		translationRating = (RatingBar) findViewById(R.id.translationRatingBar);

		configuration = Configuration.getInstance(this);
		proficientLanguagesSet = this.configuration.getProficientLanguages();
		proficientLanguagesList = LanguageUtils
				.getProficientLanguages(this.proficientLanguagesSet);
		
		languageOfTranslation = chooseRandomProficientLanguage();
		
		englishWord = this.retrieveRandomEnglishWord();
//		translation = 

		this.setTextViews();

		submitRating.setVisibility(View.VISIBLE);
		continueButton.setVisibility(View.INVISIBLE);
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
		submitRating.setVisibility(View.INVISIBLE);
		continueButton.setVisibility(View.VISIBLE);
	}

	private void setTextViews() {
		this.wordInEnglish.setText(this.englishWord.getWord());
		this.translationToRate.setText(this.translation.getWord());
		this.translationLanguageTitle.setText(this.translationLanguageTitle.getText() + " " + this.languageOfTranslation.getLanguage());
	}

	private Word retrieveRandomEnglishWord() {
		Word randomEnglishWord = new Word();
		this.serverCommunication = new RESTfullServerCommunication();
		//maybe should be this.serverCommunication.listWords(englishLanugage); should be changed by adding difficulty
		this.listOfEnglishWords = this.serverCommunication.listWords(englishLanugage);
		int randomPosition = this.randomNumber(listOfEnglishWords.size());
		randomEnglishWord = this.listOfEnglishWords.get(randomPosition);
		return randomEnglishWord;
	}

	private int randomNumber(int max) {
		Random randomGenerator = new Random();
		int randomNumber = randomGenerator.nextInt(max);
		return randomNumber;
	}

	private Language chooseRandomProficientLanguage() {
		// removing English since this is the language from which the words are
		// being translated
		if (this.proficientLanguagesList.contains(LanguageUtils
				.getByName("English"))) {
			this.proficientLanguagesList.remove(this.proficientLanguagesList
					.indexOf(LanguageUtils.getByName("English")));
		}

		Language randomLanguage = LanguageUtils
				.getRandomProficientLanguage(this.proficientLanguagesList);

		return randomLanguage;
	}

	private void submitRating(View v) {
		//send the rating
		this.onGameEnd();
	}

}

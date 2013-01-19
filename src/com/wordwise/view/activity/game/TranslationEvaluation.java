package com.wordwise.view.activity.game;

import java.util.ArrayList;
import java.util.List;
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

	private RESTfullServerCommunication serverCommunication;
	private Word englishWord;

	private Button submitRating;
	private Button continueButton;
	private TextView wordInEnglish;
	private TextView translationLanguageTitle;
	private TextView translationToRate;
	private RatingBar translationRating;
	
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
		
		languageOfTranslation = chooseRandomProficientLanguage();
		submitRating.setEnabled(true);
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
		submitRating.setEnabled(false);
		continueButton.setEnabled(true);
	}
	
	private void submitRating(View v) {
		
	}

	private Language chooseRandomProficientLanguage() {

		configuration = Configuration.getInstance(this);
		proficientLanguagesSet = configuration.getProficientLanguages();
		proficientLanguagesList = LanguageUtils
				.getProficientLanguages(proficientLanguagesSet);

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

}

package com.wordwise.view.activity.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.wordwise.R;
import com.wordwise.client.RESTfullServerCommunication;
import com.wordwise.model.Configuration;
import com.wordwise.server.model.Difficulty;
import com.wordwise.server.model.Language;
import com.wordwise.server.model.Rate;
import com.wordwise.server.model.Translation;
import com.wordwise.util.LanguageUtils;
import com.wordwise.view.activity.WordwiseGameActivity;

public class TranslationEvaluation extends WordwiseGameActivity {

	private Configuration configuration;
	private Set<Language> proficientLanguagesSet;
	private List<Language> proficientLanguagesList = new ArrayList<Language>();

	private Rate currentRating;
	private Difficulty difficulty;

	private RESTfullServerCommunication server;
	private Translation translation;

	private Button submitRating;
	private Button continueButton;
	private TextView wordInEnglish;
	private TextView translationLanguageTitle;
	private TextView translationToRate;
	private RatingBar translationRatingBar;
	
	private Boolean translationRated;
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
		translationRatingBar = (RatingBar) findViewById(R.id.translationRatingBar);

		configuration = Configuration.getInstance(this);
		proficientLanguagesSet = this.configuration.getProficientLanguages();
		proficientLanguagesList = LanguageUtils
				.getProficientLanguages(this.proficientLanguagesSet);
		
		languageOfTranslation = chooseRandomProficientLanguage();
		difficulty = configuration.getDifficulty();
		
		translation = this.retrieveRandomTranslation();
		currentRating = new Rate();

		this.setChangeableTextViews();
		
		translationRatingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {	
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				translationRated = false;
				if(rating > 0.0f){
					translationRated = true;
				}
				checkSubmitCondition();
			}
		});

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
	

	private void submitRating(View v) {
		int translationRating = Math.round(translationRatingBar.getRating());
		this.currentRating.setRate(translationRating);
		this.currentRating.setTranslation(translation);
		this.server.rateTranslation(currentRating);
		Toast.makeText(
				this,
				"Your rating score: " + translationRating + ", was submitted to successfully!", Toast.LENGTH_SHORT).show();
		this.onGameEnd();
	}
	
	private void checkSubmitCondition(){
		if(translationRated){
			submitRating.setEnabled(true);
		}
		else
			submitRating.setEnabled(false);		
	}

	private void setChangeableTextViews() {
		this.wordInEnglish.setText(this.translation.getWord().getWord());
		this.translationToRate.setText(this.translation.getTranslation());
		this.translationLanguageTitle.setText(this.translationLanguageTitle.getText() + " " + this.languageOfTranslation.getLanguage());
	}
	
	private Translation retrieveRandomTranslation() {
		List<Translation> translationList = new ArrayList<Translation>();
		this.server = new RESTfullServerCommunication();
		translationList = this.server.listTranslations(languageOfTranslation, difficulty, 1, null);
		return translationList.get(1);
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

	public int numberOfTranslationsNeeded(Difficulty difficulty) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int numberOfWordsNeeded(Difficulty difficulty) {
		// TODO Auto-generated method stub
		return 0;
	}

}

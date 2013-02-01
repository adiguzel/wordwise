package com.wordwise.view.activity.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.wordwise.R;
import com.wordwise.client.RESTfullServerCommunication;
import com.wordwise.gameengine.level.EvaluationPromotion;
import com.wordwise.gameengine.level.Promotion;
import com.wordwise.model.Configuration;
import com.wordwise.model.GameManagerContainer;
import com.wordwise.server.dto.DTODifficulty;
import com.wordwise.server.dto.DTOLanguage;
import com.wordwise.server.dto.DTORate;
import com.wordwise.server.dto.DTOTranslation;
import com.wordwise.util.LanguageUtils;
import com.wordwise.util.LoaderHelper.LoaderType;
import com.wordwise.view.activity.WordwiseGameActivity;

public class TranslationEvaluation extends WordwiseGameActivity
		implements
			LoaderCallbacks<List<DTOTranslation>> {

	private Configuration configuration;
	private Set<DTOLanguage> proficientLanguagesSet;
	private List<DTOLanguage> proficientLanguagesList = new ArrayList<DTOLanguage>();

	private DTORate currentRating;
	private RESTfullServerCommunication server;
	private DTOTranslation translation;

	private Button submitRating;
	private Button continueButton;
	private TextView wordInEnglish;
	private TextView translationLanguageTitle;
	private TextView translationToRate;
	private RatingBar translationRatingBar;

	private Boolean translationRated;
	private DTOLanguage languageOfTranslation;

	@Override
	public void performOnCreate(Bundle savedInstanceState) {
		loaderHelper.initLoader(this, LoaderType.TRANSLATION_LOADER);
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

		currentRating = new DTORate();

		this.setChangeableTextViews();

		translationRatingBar
				.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
					public void onRatingChanged(RatingBar ratingBar,
							float rating, boolean fromUser) {
						translationRated = false;
						if (rating > 0.0f) {
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
		submitRating.setVisibility(View.INVISIBLE);
		continueButton.setVisibility(View.VISIBLE);
	}

	public void submitRating(View v) {
		int translationRating = Math.round(translationRatingBar.getRating());
		this.currentRating.setRate(translationRating);
		this.currentRating.setTranslation(translation);
		this.server.rateTranslation(currentRating);
		Toast.makeText(
				this,
				"Your rating score: " + translationRating
						+ ", was submitted to successfully!",
				Toast.LENGTH_SHORT).show();
		this.onGameEnd();
	}

	private void checkSubmitCondition() {
		if (translationRated) {
			submitRating.setEnabled(true);
		} else
			submitRating.setEnabled(false);
	}

	private void setChangeableTextViews() {
		this.wordInEnglish.setText(this.translation.getWord().getWord());
		this.translationToRate.setText(this.translation.getTranslation());
		this.translationLanguageTitle.setText(this.translationLanguageTitle
				.getText() + " " + this.languageOfTranslation.getLanguage());
	}

	private DTOLanguage chooseRandomProficientLanguage() {
		// removing English since this is the language from which the words are
		// being translated
		if (this.proficientLanguagesList.contains(LanguageUtils
				.getByName("English"))) {
			this.proficientLanguagesList.remove(this.proficientLanguagesList
					.indexOf(LanguageUtils.getByName("English")));
		}

		DTOLanguage randomLanguage = LanguageUtils
				.getRandomProficientLanguage(this.proficientLanguagesList);

		return randomLanguage;
	}

	public int numberOfTranslationsNeeded(DTODifficulty difficulty) {
		return 1;
	}

	public int numberOfWordsNeeded(DTODifficulty difficulty) {
		return 0;
	}

	public void retry(View v) {
		loaderHelper.restartLoader(this, LoaderType.TRANSLATION_LOADER);
	}

	@SuppressWarnings("unchecked")
	public Loader<List<DTOTranslation>> onCreateLoader(int id, Bundle args) {
		return (Loader<List<DTOTranslation>>) loaderHelper.onLoadCreated(this,
				LoaderType.TRANSLATION_LOADER);
	}

	public void onLoadFinished(Loader<List<DTOTranslation>> arg0,
			List<DTOTranslation> translations) {
		Log.v("translations", "" + translations);

		if (translations == null) {
			loaderHelper.loadFailed("Oh snap. Failed to load!");
		} else if (translations.size() < GameManagerContainer.getGameManager()
				.NumberOfTranslationsNeeded()) {
			loaderHelper.loadFailed("Server does not have enough words!");
		} else {
			this.translation = translations.get(0);
			initLayout();
			onGameStart();
		}

	}

	public void onLoaderReset(Loader<List<DTOTranslation>> arg0) {
		loaderHelper.onLoaderReset(this);

	}

	@Override
	protected View gameContent() {
		return getLayoutInflater().inflate(R.layout.game_translation_evaluation, null);
	}

	@Override
	protected boolean isRealGame() {
		return false;
	}

	@Override
	public List<DTOTranslation> getTranslations() {
		return null;
	}
	
	@Override
	public Promotion getPromotion() {
		return new EvaluationPromotion();
	}

}

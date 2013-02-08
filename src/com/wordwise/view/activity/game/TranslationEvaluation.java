package com.wordwise.view.activity.game;

import java.util.List;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.wordwise.R;
import com.wordwise.gameengine.level.EvaluationPromotion;
import com.wordwise.gameengine.level.Promotion;
import com.wordwise.model.SubmitListener;
import com.wordwise.server.dto.DTODifficulty;
import com.wordwise.server.dto.DTOLanguage;
import com.wordwise.server.dto.DTORate;
import com.wordwise.server.dto.DTOTranslation;
import com.wordwise.task.game.RatingSubmitTask;
import com.wordwise.util.LanguageUtils;
import com.wordwise.util.LoaderHelper.LoaderType;
import com.wordwise.util.WordwiseUtils;
import com.wordwise.view.activity.WordwiseGameActivity;

public class TranslationEvaluation extends WordwiseGameActivity
		implements
			LoaderCallbacks<List<DTOTranslation>>,
			SubmitListener {
	// the translation to be made by user
	private DTOTranslation translation;
	// language in which user is asked to make a translation
	private DTOLanguage languageOfTranslation;
	// a flag to check rating state and submit button's state
	private Boolean translationRated;

	// UI elements
	private Button submitRating;
	private Button continueButton;
	private TextView wordInEnglish;
	private TextView translationLanguageTitle;
	private TextView translationToRate;
	private RatingBar translationRatingBar;

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

		languageOfTranslation = chooseRandomProficientLanguage();

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
	
	public DTOLanguage getLanguage()
	{
		return chooseRandomProficientLanguage();
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
		submitRating.setVisibility(View.INVISIBLE);
		continueButton.setVisibility(View.VISIBLE);
	}

	public void submitRating(View v) {
		int translationRating = Math.round(translationRatingBar.getRating());
		DTORate rating = new DTORate();
		rating.setRate(translationRating);
		rating.setTranslation(translation);
		// let the submission be handled in the background
		new RatingSubmitTask(this, this, rating).execute();
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
		List<DTOLanguage> proficientLanguagesList = LanguageUtils
				.getProficientLanguages(prefIOManager.getProficientLanguages());
		// removing English since this is the language from which the words are
		// being translated
		if (proficientLanguagesList
				.contains(LanguageUtils.getByName("English"))) {
			proficientLanguagesList.remove(proficientLanguagesList
					.indexOf(LanguageUtils.getByName("English")));
		}

		DTOLanguage randomLanguage = LanguageUtils
				.getRandomProficientLanguage(proficientLanguagesList);

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
		if (loaderHelper.translationLoadSuccessfulOrShowError(this,
				translations)) {
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
		return getLayoutInflater().inflate(
				R.layout.game_translation_evaluation, null);
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

	@Override
	public void onSubmitResult(boolean result) {
		if (result)
			onGameEnd();
		else {
			String failMessage = getResources().getString(
					R.string.fail_feedback_submit);
			WordwiseUtils.makeCustomToast(this, failMessage);
		}

	}
}

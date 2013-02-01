package com.wordwise.view.activity.game;

import java.util.List;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.wordwise.R;
import com.wordwise.client.RESTfullServerCommunication;
import com.wordwise.gameengine.level.EvaluationPromotion;
import com.wordwise.gameengine.level.Promotion;
import com.wordwise.model.GameManagerContainer;
import com.wordwise.model.SubmitListener;
import com.wordwise.server.dto.DTODifficulty;
import com.wordwise.server.dto.DTOQuality;
import com.wordwise.server.dto.DTOTranslation;
import com.wordwise.server.dto.DTOWord;
import com.wordwise.task.game.WordEvaluationSubmitTask;
import com.wordwise.util.LoaderHelper.LoaderType;
import com.wordwise.util.WordwiseUtils;
import com.wordwise.view.activity.WordwiseGameActivity;

public class WordEvaluation extends WordwiseGameActivity
		implements
			LoaderCallbacks<List<DTOWord>>,
			SubmitListener {

	private TextView wordToEvaluateText;
	private RatingBar wordDifficultyRating;
	private Button continueButton;
	private Button submitEvaluation;

	private CheckBox isAWord;
	private CheckBox isNotWord;
	private CheckBox iDontKnow;

	private DTODifficulty difficulty;
	private DTOQuality quality;
	private DTOWord word;
	private RESTfullServerCommunication server;

	private boolean difficultyRated = false;
	private boolean isWord;

	@Override
	public void performOnCreate(Bundle savedInstanceState) {
		loaderHelper.initLoader(this, LoaderType.WORD_LOADER);
	}

	public void onGameInit() {
		wordToEvaluateText = (TextView) findViewById(R.id.wordToEvaluate);
		wordDifficultyRating = (RatingBar) findViewById(R.id.wordDifficultyRating);
		continueButton = (Button) findViewById(R.id.continueButton);
		submitEvaluation = (Button) findViewById(R.id.submitTranslation);

		isAWord = (CheckBox) findViewById(R.id.is_a_word);
		isNotWord = (CheckBox) findViewById(R.id.is_not_a_word);
		iDontKnow = (CheckBox) findViewById(R.id.i_dont_know);
		word = retrieveWord();
		wordToEvaluateText.setText(word.getWord());

		submitEvaluation.setVisibility(View.VISIBLE);
		continueButton.setVisibility(View.INVISIBLE);

		wordDifficultyRating.setEnabled(false);
	}

	public void onGameStart() {
	}

	public void onGameStop() {
	}

	public void onGamePause() {
	}

	public void onGameEnd() {
		wordDifficultyRating.setEnabled(false);
		submitEvaluation.setVisibility(View.INVISIBLE);
		continueButton.setVisibility(View.VISIBLE);
	}

	private void enableRatingBar() {
		wordDifficultyRating.setEnabled(true);
		wordDifficultyRating
				.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
					public void onRatingChanged(RatingBar ratingBar,
							float rating, boolean fromUser) {
						difficultyRated = false;
						if (rating > 0.0f) {
							difficultyRated = true;
						}
						checkSubmitCondition();
					}
				});
	}

	private void checkSubmitCondition() {
		if (difficultyRated) {
			submitEvaluation.setEnabled(true);
		} else
			submitEvaluation.setEnabled(false);
	}

	public DTOWord retrieveWord() {
		this.server = new RESTfullServerCommunication();
		return server.getWord();
	}

	public void onCheckboxYes(View view) {
		this.isAWord.setEnabled(false);
		this.isNotWord.setEnabled(false);
		this.iDontKnow.setEnabled(false);
		this.isWord = true;
		this.enableRatingBar();
	}

	public void onCheckboxNo(View view) {
		this.isAWord.setEnabled(false);
		this.isNotWord.setEnabled(false);
		this.iDontKnow.setEnabled(false);
		this.isWord = false;
		this.submitEvaluation.setEnabled(true);
	}

	public void onCheckboxDontKnow(View view) {
		this.isAWord.setEnabled(false);
		this.isNotWord.setEnabled(false);
		this.iDontKnow.setEnabled(false);
		this.submitEvaluation.setEnabled(true);
	}

	public void submitEvaluation(View v) {
		this.server = new RESTfullServerCommunication();
		if (!isWord) {
			this.quality = new DTOQuality();
			quality.setWord(this.word);
			quality.setQuality(-1); // setting up a bad quality
			// this.server.addWordQualitiy(this.quality);
		} else if (isWord) {
			int difficultyRating = Math.round(wordDifficultyRating.getRating());
			if (difficultyRating == 0) {
				Toast.makeText(
						this,
						R.string.word_evaluation_provide_difficulty_rating_dialog,
						Toast.LENGTH_LONG).show();
				return;
			}

			DTOQuality quality = new DTOQuality();
			quality.setWord(this.word);
			quality.setQuality(1); // setting up a good quality

			this.difficulty = DTODifficulty.getByDifficulty(difficultyRating);
			if (difficulty != null) {
				difficulty.setWord(this.word);
			}
			// this.server.addWordQualitiy(this.quality);
			// this.server.addWordDifficulty(this.difficulty);
		} else {
			this.quality = new DTOQuality();
			quality.setWord(this.word);
			quality.setQuality(0); // setting up I don't know
			// this.server.addWordQualitiy(this.quality);
		}
		new WordEvaluationSubmitTask(this, this, quality, difficulty).execute();
		// this.onGameEnd();
	}

	public void onSubmitResult(boolean submitResult) {
		if (submitResult) {
			String successMessage = "Thank you for your feedback.";
			WordwiseUtils.makeCustomToast(this, successMessage,
					Toast.LENGTH_LONG);
			this.onGameEnd();
		} else {
			String failMessage = "Your feedback could not be submitted. Check your internet connection and please try again later.";
			WordwiseUtils.makeCustomToast(this, failMessage);
		}
	}

	public int numberOfTranslationsNeeded(DTODifficulty difficulty) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int numberOfWordsNeeded(DTODifficulty difficulty) {
		// TODO Auto-generated method stub
		return 1;
	}

	@SuppressWarnings("unchecked")
	public Loader<List<DTOWord>> onCreateLoader(int id, Bundle args) {
		return (Loader<List<DTOWord>>) loaderHelper.onLoadCreated(this,
				LoaderType.WORD_LOADER);
	}

	public void onLoadFinished(Loader<List<DTOWord>> arg0, List<DTOWord> words) {
		// TODO Auto-generated method stub
		Log.v("words", "" + words);

		if (words == null) {
			loaderHelper.loadFailed("Oh snap. Failed to load!");
		} else if (words.size() < GameManagerContainer.getGameManager()
				.NumberOfTranslationsNeeded()) {
			loaderHelper.loadFailed("Server does not have enough words!");
		} else if (words.get(0) != null) {
			word = words.get(0);
			initLayout();
			this.onGameStart();
		}
	}

	public void onLoaderReset(Loader<List<DTOWord>> arg0) {
		loaderHelper.onLoaderReset(this);
	}

	public void retry(View v) {
		loaderHelper.restartLoader(this, LoaderType.WORD_LOADER);
	}

	@Override
	protected View gameContent() {
		return getLayoutInflater().inflate(R.layout.game_word_evaluation, null);
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
		// TODO Auto-generated method stub
		return new EvaluationPromotion();
	}
}

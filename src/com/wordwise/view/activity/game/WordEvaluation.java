package com.wordwise.view.activity.game;

import java.util.ArrayList;
import java.util.List;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.wordwise.R;
import com.wordwise.client.RESTfullServerCommunication;
import com.wordwise.loader.WordLoader;
import com.wordwise.model.GameManagerContainer;
import com.wordwise.server.model.Difficulty;
import com.wordwise.server.model.Quality;
import com.wordwise.server.model.Translation;
import com.wordwise.server.model.Word;
import com.wordwise.view.activity.WordwiseGameActivity;

public class WordEvaluation extends WordwiseGameActivity
		implements
			LoaderCallbacks<List<Word>> {

	private TextView wordToEvaluateText;
	private RatingBar wordDifficultyRating;
	private Button continueButton;
	private Button submitEvaluation;

	private CheckBox isAWord;
	private CheckBox isNotWord;
	private CheckBox iDontKnow;

	private Difficulty difficulty;
	private Quality quality;
	private Word word;
	private List<Translation> translation = new ArrayList<Translation>(1);
	private RESTfullServerCommunication server;

	private boolean difficultyRated = false;
	private boolean isWord;

	@Override
	public void performOnCreate(Bundle savedInstanceState) {
		// setContentView(R.layout.word_evaluation);
		// onGameInit();
		// onGameStart();
		getLoaderManager().initLoader(0, null,
				(android.app.LoaderManager.LoaderCallbacks<List<Word>>) this)
				.forceLoad();
	}

	public void onGameInit() {
		wordToEvaluateText = (TextView) findViewById(R.id.wordToEvaluate);
		wordDifficultyRating = (RatingBar) findViewById(R.id.wordDifficultyRating);
		continueButton = (Button) findViewById(R.id.continueButton);
		submitEvaluation = (Button) findViewById(R.id.submitTranslation);

		isAWord = (CheckBox) findViewById(R.id.is_a_word);
		isNotWord = (CheckBox) findViewById(R.id.is_not_a_word);
		iDontKnow = (CheckBox) findViewById(R.id.i_dont_know);
		// word = retrieveWord();
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

	public Word retrieveWord() {
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
			this.quality = new Quality();
			quality.setWord(this.word);
			quality.setQuality(-1); // setting up a bad quality
			this.server.addWordQualitiy(this.quality);
		} else if (isWord) {
			int difficultyRating = Math.round(wordDifficultyRating.getRating());
			if (difficultyRating == 0) {
				Toast.makeText(
						this,
						R.string.word_evaluation_provide_difficulty_rating_dialog,
						Toast.LENGTH_LONG).show();
				return;
			}
			Toast.makeText(this, "Difficulty: " + difficultyRating,
					Toast.LENGTH_LONG).show();

			Quality quality = new Quality();
			quality.setWord(this.word);
			quality.setQuality(1); // setting up a good quality

			this.difficulty = Difficulty.getByDifficulty(difficultyRating);
			if (difficulty != null) {
				difficulty.setWord(this.word);
			}
			this.server.addWordQualitiy(this.quality);
			this.server.addWordDifficulty(this.difficulty);
		} else {
			this.quality = new Quality();
			quality.setWord(this.word);
			quality.setQuality(0); // setting up I don't know
			this.server.addWordQualitiy(this.quality);
		}
		this.onGameEnd();
	}

	public int numberOfTranslationsNeeded(Difficulty difficulty) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int numberOfWordsNeeded(Difficulty difficulty) {
		// TODO Auto-generated method stub
		return 1;
	}

	public Loader<List<Word>> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		setContentView(R.layout.loading_game);
		ProgressBar progress = (ProgressBar) findViewById(R.id.progress_bar);

		progress.setVisibility(View.VISIBLE);
		return new WordLoader(this);
	}

	public void onLoadFinished(Loader<List<Word>> arg0, List<Word> words) {
		// TODO Auto-generated method stub
		Log.v("words", "" + words);

		LinearLayout loadingFailed = (LinearLayout) findViewById(R.id.loadingFailed);
		TextView loadingFailedText = (TextView) findViewById(R.id.loadingFailedText);
		TextView loadingText = (TextView) findViewById(R.id.loadingText);
		Button retryButton = (Button) findViewById(R.id.retryButton);
		LinearLayout loading = (LinearLayout) findViewById(R.id.loading);

		if (words == null) {
			loading.setVisibility(View.INVISIBLE);
			loadingFailed.setVisibility(View.VISIBLE);
			loadingFailedText.setVisibility(View.VISIBLE);
			retryButton.setVisibility(View.VISIBLE);
			loadingFailedText.setText("Oh snap. Failed to load.");
			/*
			 * Toast.makeText(this, "Oh snap. Failed to load.",
			 * Toast.LENGTH_SHORT) .show();
			 */
		} else if (words.size() < GameManagerContainer.getGameManager()
				.NumberOfTranslationsNeeded()) {
			loading.setVisibility(View.VISIBLE);
			loadingFailed.setVisibility(View.VISIBLE);
			loadingFailedText.setText("Server does not have enough words.");
			/*
			 * Toast.makeText(this, "Server does not have enough words.",
			 * Toast.LENGTH_SHORT).show();
			 */
		} else if (words.get(0) != null) {
			word = words.get(0);
			setContentView(R.layout.words2translations);
			this.onGameInit();
			this.onGameStart();
		}
	}

	public void onLoaderReset(Loader<List<Word>> arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.loading_game);
		ProgressBar progress = (ProgressBar) findViewById(R.id.progress_bar);

		progress.setVisibility(View.VISIBLE);
	}

	public void retry(View v) {
		// TODO Auto-generated method stub
		getLoaderManager().restartLoader(0, null,
				(android.app.LoaderManager.LoaderCallbacks<List<Word>>) this)
				.forceLoad();
	}

}

package com.wordwise.view.activity.game;

import java.util.List;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.wordwise.R;
import com.wordwise.gameengine.level.EvaluationPromotion;
import com.wordwise.gameengine.level.Promotion;
import com.wordwise.model.SubmitListener;
import com.wordwise.server.dto.DTODifficulty;
import com.wordwise.server.dto.DTOQuality;
import com.wordwise.server.dto.DTOTranslation;
import com.wordwise.server.dto.DTOWord;
import com.wordwise.task.game.WordEvaluationSubmitTask;
import com.wordwise.util.LoaderHelper.LoaderType;
import com.wordwise.util.WordwiseUtils;
import com.wordwise.view.activity.WordwiseGameActivity;

/**
 * The class that defines the implementation for WordEvaluation game
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class WordEvaluation extends WordwiseGameActivity
		implements
			LoaderCallbacks<List<DTOWord>>,
			SubmitListener {
	// UI elements
	private TextView wordToEvaluateText;
	private RatingBar wordDifficultyRating;
	private Button continueButton;
	private Button submitEvaluation;
	private RadioGroup qualityGroup;

	private DTODifficulty difficulty;
	private DTOQuality quality;
	private DTOWord word;

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
		qualityGroup = (RadioGroup) findViewById(R.id.qualityGroup);

		wordToEvaluateText.setText(word.getWord());

		submitEvaluation.setVisibility(View.VISIBLE);
		continueButton.setVisibility(View.INVISIBLE);

		wordDifficultyRating.setEnabled(false);

		qualityGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.is_a_word)
					onYesSelected();
				else if (checkedId == R.id.is_not_word)
					onNoSelected();
				else if (checkedId == R.id.i_dont_know)
					onDontKnowSelected();
			}
		});
	}

	public void onGameStart() {
	}

	public void onGameStop() {
	}

	public void onGamePause() {
	}

	public void onGameEnd() {
		String successMessage = getResources().getString(
				R.string.feedback_successful_text);
		WordwiseUtils.makeCustomToast(this, successMessage, Toast.LENGTH_LONG);
		super.onGameEnd();
		// disable the UI elements
		qualityGroup.setEnabled(false);
		// disable the children of radio group too
		for (int i = 0; i < qualityGroup.getChildCount(); i++) {
			qualityGroup.getChildAt(i).setEnabled(false);
		}
		wordDifficultyRating.setEnabled(false);
		// arrange tehe buttons
		submitEvaluation.setVisibility(View.GONE);
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

	public void onYesSelected() {
		this.isWord = true;
		this.enableRatingBar();
	}

	public void onNoSelected() {
		// reset the rating bar
		wordDifficultyRating.setRating(0.0f);
		wordDifficultyRating.setEnabled(false);
		this.isWord = false;
		this.submitEvaluation.setEnabled(true);
	}

	public void onDontKnowSelected() {
		// reset the rating bar
		wordDifficultyRating.setRating(0.0f);
		wordDifficultyRating.setEnabled(false);
		this.submitEvaluation.setEnabled(true);
	}

	public void submitEvaluation(View v) {
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
		} else {
			this.quality = new DTOQuality();
			quality.setWord(this.word);
			quality.setQuality(0); // setting up I don't know
		}
		new WordEvaluationSubmitTask(this, this, quality, difficulty).execute();
	}

	public void onSubmitResult(boolean submitResult) {
		if (submitResult) {
			onGameEnd();
		} else {
			String failMessage = getResources().getString(
					R.string.fail_feedback_submit);
			WordwiseUtils.makeCustomToast(this, failMessage);
		}
	}

	public int numberOfTranslationsNeeded(DTODifficulty difficulty) {

		return 0;
	}

	public int numberOfWordsNeeded(DTODifficulty difficulty) {

		return 1;
	}

	@SuppressWarnings("unchecked")
	public Loader<List<DTOWord>> onCreateLoader(int id, Bundle args) {
		return (Loader<List<DTOWord>>) loaderHelper.onLoadCreated(this,
				LoaderType.WORD_LOADER);
	}

	public void onLoadFinished(Loader<List<DTOWord>> arg0, List<DTOWord> words) {
		if (loaderHelper.wordLoadSuccessfulOrShowError(this, words)) {
			if (words.get(0) != null) {
				word = words.get(0);
				initLayout();
				this.onGameStart();
			}
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
		// false because it is an evaluation type of game
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

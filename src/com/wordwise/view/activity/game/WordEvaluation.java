package com.wordwise.view.activity.game;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.wordwise.R;
import com.wordwise.client.RESTfullServerCommunication;
import com.wordwise.gameengine.Game;
import com.wordwise.server.model.Difficulty;
import com.wordwise.server.model.Quality;
import com.wordwise.server.model.Translation;
import com.wordwise.server.model.Word;
import com.wordwise.view.activity.WordwiseGameActivity;

public class WordEvaluation extends WordwiseGameActivity implements Game {

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
		setContentView(R.layout.word_evaluation);
		onGameInit();
		onGameStart();
	}

	public void onGameInit() {
		wordToEvaluateText = (TextView) findViewById(R.id.wordToEvaluate);
		wordDifficultyRating = (RatingBar) findViewById(R.id.wordDifficultyRating);
		continueButton = (Button) findViewById(R.id.continueButton);
		submitEvaluation = (Button) findViewById(R.id.submitTranslation);

		isAWord = (CheckBox) findViewById(R.id.is_a_word);
		isNotWord = (CheckBox) findViewById(R.id.is_not_a_word);
		iDontKnow = (CheckBox) findViewById(R.id.i_dont_know);
		
		wordToEvaluateText.setText(this.retrieveWord().getWord());

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
		this.translation = this.server.listTranslations(null, null, 1, null);
		return this.translation.get(0).getWord();
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
			quality.setQuality(-1); //setting up a bad quality
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
			quality.setQuality(1); //setting up a good quality

			this.difficulty = Difficulty
					.getByDifficulty(difficultyRating);
			if (difficulty != null) {
				difficulty.setWord(this.word);
			}
			this.server.addWordQualitiy(this.quality);
			this.server.addWordDifficulty(this.difficulty);
		} else {
			this.quality = new Quality();
			quality.setWord(this.word);
			quality.setQuality(0); //setting up I don't know
			this.server.addWordQualitiy(this.quality);
		}
		this.onGameEnd();
	}
}

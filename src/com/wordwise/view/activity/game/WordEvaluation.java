package com.wordwise.view.activity.game;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;

import android.os.AsyncTask;
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
import com.wordwise.gameengine.Game;
import com.wordwise.server.model.Difficulty;
import com.wordwise.server.model.Quality;
import com.wordwise.server.model.Word;
import com.wordwise.util.WordwiseUtils;
import com.wordwise.view.activity.WordwiseGameActivity;

public class WordEvaluation extends WordwiseGameActivity implements Game {

	private final String DIALOG_TITLE = "You can contribute! :)";

	private TextView wordToEvaluateText;
	private RatingBar wordDifficultyRating;
	private RatingBar wordQualityRating;
	private Button continueButton;
	private Button submitTranslation;
	// word to evaluate
	private Word word;
	private RESTfullServerCommunication serverCommunication;
	Quality quality;
	Difficulty difficulty;

	private boolean qualityRated, difficultyRated = false;

	@Override
	public void performOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.word_evaluation);
		onGameInit();
		onGameStart();
	}

	private void initScreen() {
		wordToEvaluateText = (TextView) findViewById(R.id.wordToEvaluate);
		wordDifficultyRating = (RatingBar) findViewById(R.id.wordDifficultyRating);
		wordQualityRating = (RatingBar) findViewById(R.id.wordQualityRating);
		continueButton = (Button) findViewById(R.id.continueButton);
		submitTranslation = (Button) findViewById(R.id.submitTranslation);

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

		wordQualityRating
				.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
					public void onRatingChanged(RatingBar ratingBar,
							float rating, boolean fromUser) {
						qualityRated = false;
						if (rating > 0.0f) {
							qualityRated = true;
						}
						checkSubmitCondition();
					}
				});
	}

	private void checkSubmitCondition() {
		if (qualityRated && difficultyRated) {
			submitTranslation.setEnabled(true);
		} else
			submitTranslation.setEnabled(false);
	}

	public Word retrieveWord() {
		serverCommunication = new RESTfullServerCommunication();
		// TODO get Word from the server
		Word word = new Word();
		word.setWord("test");
		return word;
	}

	public void submitEvaluation(View v) {
		int qualityRating = Math.round(wordQualityRating.getRating());
		int difficultyRating = Math.round(wordDifficultyRating.getRating());

		if (difficultyRating == 0) {
			Toast.makeText(this,
					R.string.word_evaluation_provide_difficulty_rating_dialog,
					Toast.LENGTH_LONG).show();
			return;
		}
		Toast.makeText(
				this,
				"quality : " + qualityRating + " difficulty: "
						+ difficultyRating, Toast.LENGTH_LONG).show();

		quality = new Quality();
		quality.setWord(word);
		quality.setQuality(qualityRating);

		difficulty = Difficulty.getByDifficulty(difficultyRating);
		if (difficulty != null) {
			difficulty.setWord(word);
		}
		submitTranslation.setEnabled(false);
		new WordRatingsSaveTask().execute();
	}

	public void onSubmitSuccessful() {
		onGameEnd();
	}

	public void onSubmitFailed() {
		Toast.makeText(
				this,
				"Your evaluation could not be submitted to the server. Please try again.",
				Toast.LENGTH_LONG).show();

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

	public void onGameInit() {
		this.initScreen();
		word = retrieveWord();
		if (word == null) {
			// TODO show that word could not be retrieved
		} else {
			wordToEvaluateText.setText(word.getWord());
		}
	}

	public void onGameEnd() {
		submitTranslation.setVisibility(View.INVISIBLE);
		continueButton.setVisibility(View.VISIBLE);
	}

	public class WordRatingsSaveTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// TODO call either onSubmitSuccessful or onSubmitFailed
			// depending on the connection result
			// for now only
			onGameEnd();
			checkSubmitCondition();
		}

		@Override
		protected Void doInBackground(Void... params) {
			Log.v("Word Ratings save task", "server conn started");

			List<Difficulty> difficulties = new ArrayList<Difficulty>();
			difficulties.add(difficulty);
			serverCommunication.addWordDifficulties(difficulties);

			List<Quality> qualities = new ArrayList<Quality>();
			qualities.add(quality);
			serverCommunication.addWordQualities(qualities);

			Log.v("Word Ratings save task", "finished");
	
			return null;
		}
	}
}

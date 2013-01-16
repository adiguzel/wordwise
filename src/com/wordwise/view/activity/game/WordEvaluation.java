package com.wordwise.view.activity.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wordwise.R;
import com.wordwise.client.RESTfullServerCommunication;
import com.wordwise.gameengine.Game;
import com.wordwise.model.Configuration;
import com.wordwise.server.model.Language;
import com.wordwise.server.model.Word;
import com.wordwise.util.LanguageUtils;
import com.wordwise.view.activity.WordwiseGameActivity;

public class WordEvaluation extends WordwiseGameActivity implements Game {

	private final String DIALOG_MESSAGE = "In this screen you will be asked to do a small contribution for this application. Please rate a word on your preffered language...";

	private TextView wordToEvaluateText;
	private RatingBar wordDifficultyRating;
	private RatingBar wordQualityRating;
	private Button continueButton;

	// word to evaluate
	private Word word;
	private RESTfullServerCommunication serverCommunication;

	private Configuration configuration;
	private Set<Language> proficientLanguagesSet;
	private List<Language> proficientLanguagesList = new ArrayList<Language>();
	private int dificulty;

	@Override
	public void performOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.word_evaluation);

		this.infoMessageOnStart();

		word = retrieveWord();
		if (word == null) {
			// TODO show that word could not be retrieved
		} else {
			wordToEvaluateText.setText(word.getWord());
		}
	}
	
	private void initScreen() {
		wordToEvaluateText = (TextView) findViewById(R.id.wordToEvaluate);
		wordDifficultyRating = (RatingBar) findViewById(R.id.wordDifficultyRating);
		wordQualityRating = (RatingBar) findViewById(R.id.wordQualityRating);
		continueButton = (Button) findViewById(R.id.continueButton);
	}

	// Dialog that explains to the user what he will be asked to do in the next
	// screen
	private void infoMessageOnStart() {

		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

		dlgAlert.setMessage(DIALOG_MESSAGE);
		dlgAlert.setTitle("You can contribute :)");
		dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// dismiss the dialog
			}
		});
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}

	private Language chooseRandomProficientLanguage() {

		configuration = Configuration.getInstance(this);
		proficientLanguagesSet = configuration.getProficientLanguages();
		proficientLanguagesList = LanguageUtils
				.getProficientLanguages(proficientLanguagesSet);

		Language randomLanguage = LanguageUtils
				.getRandomProficientLanguage(proficientLanguagesList);

		return randomLanguage;
	}

	public Word retrieveWord() {
		// TODO get Word from the server
		return null;
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

		// TODO Implement submitting evaluation and showing a toast if it was
		// not successful
		// TODO DTOWordRating rating = new DTOWordRating(difficultyRating,
		// qualityRating, word);
		// TODO wordServerComm.rateWord(rating);
		
		this.onGameEnd();
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
	}

	public void onGameEnd() {
		this.continueButton.setEnabled(true);
	}

}

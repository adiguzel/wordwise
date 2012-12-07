package com.wordwise.activity.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wordwise.R;
import com.wordwise.activity.NewGame;
import com.wordwise.gameengine.Game;

public class WordEvaluation extends Activity implements Game {
	private TextView wordToEvaluateText;
	private RatingBar wordDifficultyRating;
	private RatingBar wordQualityRating;
	private String wordToEvaluate = "";

	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.word_evaluation);

		wordToEvaluateText = (TextView) findViewById(R.id.wordToEvaluate);
		wordDifficultyRating = (RatingBar) findViewById(R.id.wordDifficultyRating);
		wordQualityRating = (RatingBar) findViewById(R.id.wordQualityRating);
		getWordAndTranslations();	
	}
	
	public void getWordAndTranslations(){
		wordToEvaluate = "Test Word";
		wordToEvaluateText.setText(wordToEvaluate);		
	}
	
	public void submitEvaluation(View v){
		/*Intent intent = new Intent(this, NewGame.class);
		startActivity(intent);*/
	}

	public void start() {
		// TODO Auto-generated method stub

	}

	public void stop() {
		// TODO Auto-generated method stub

	}

	public void pause() {
		// TODO Auto-generated method stub

	}

	public void init() {
		// TODO Auto-generated method stub

	}

}

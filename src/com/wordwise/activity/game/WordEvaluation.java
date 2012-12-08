package com.wordwise.activity.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wordwise.R;
import com.wordwise.gameengine.Game;
import com.wordwise.gameengine.ServerCommunication;
import com.wordwise.gameengine.dto.DTOWord;

public class WordEvaluation extends Activity implements Game {
	private TextView wordToEvaluateText;
	private RatingBar wordDifficultyRating;
	private RatingBar wordQualityRating;
	//word to evaluate
	private DTOWord word;
	private ServerCommunication wordServerComm;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.word_evaluation);

		wordToEvaluateText = (TextView) findViewById(R.id.wordToEvaluate);
		wordDifficultyRating = (RatingBar) findViewById(R.id.wordDifficultyRating);
		wordQualityRating = (RatingBar) findViewById(R.id.wordQualityRating);
		retrieveWord();	
	}
	
	public boolean retrieveWord(){
		//wordServerComm.listWords(lang, difficulty, number);
		return true;
	}
	
	public void submitEvaluation(View v){
		float qualityRating = wordQualityRating.getRating();
		float difficultyRating = wordDifficultyRating.getRating();
		
		if(difficultyRating == 0.0f){
			Toast.makeText(this, R.string.word_evaluation_provide_difficulty_rating_dialog, Toast.LENGTH_LONG).show();
			return;
		}
		Toast.makeText(this, "quality : "+qualityRating + " difficulty "+difficultyRating, Toast.LENGTH_LONG).show();
		//TODO Implement submitting evaluation and showing a toast if it was not successful
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

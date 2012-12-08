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
import com.wordwise.gameengine.dto.DTOGameConfiguration;
import com.wordwise.gameengine.dto.DTOWord;

public class WordEvaluation extends Activity implements Game {
	private TextView wordToEvaluateText;
	private RatingBar wordDifficultyRating;
	private RatingBar wordQualityRating;
	//word to evaluate
	private DTOWord word;
	private ServerCommunication wordServerComm;
	private DTOGameConfiguration gameConf;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.word_evaluation);

		wordToEvaluateText = (TextView) findViewById(R.id.wordToEvaluate);
		wordDifficultyRating = (RatingBar) findViewById(R.id.wordDifficultyRating);
		wordQualityRating = (RatingBar) findViewById(R.id.wordQualityRating);
		//TODO get the game conf which is saved before being sent here gameConf = ...
		word = retrieveWord();	
		if(word == null){
			//TODO show that word could not be retrieved 
		}
		else{
			wordToEvaluateText.setText(word.getText());
		}
	}
	
	public DTOWord retrieveWord(){
		//TODO uncomment the implementation after having ServerComm implementation and game conf. object
		/*List<DTOWord> words = wordServerComm.listWords(gameConf.getLearningLanguage(), gameConf.getDifficulty(), 1);
		if(!words.isEmpty()){
			return words.get(0);
		}*/
		return null;
	}
	
	public void submitEvaluation(View v){
		int qualityRating = Math.round(wordQualityRating.getRating());
		int difficultyRating = Math.round(wordDifficultyRating.getRating());
		
		if(difficultyRating == 0){
			Toast.makeText(this, R.string.word_evaluation_provide_difficulty_rating_dialog, Toast.LENGTH_LONG).show();
			return;
		}
		Toast.makeText(this, "quality : "+qualityRating + " difficulty: "+difficultyRating, Toast.LENGTH_LONG).show();
			
		//TODO Implement submitting evaluation and showing a toast if it was not successful
		//TODO DTOWordRating rating = new DTOWordRating(difficultyRating, qualityRating, word);
		//TODO wordServerComm.rateWord(rating);
		
		
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

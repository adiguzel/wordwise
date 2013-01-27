package com.wordwise.task.game;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import com.wordwise.client.RESTfullServerCommunication;
import com.wordwise.server.dto.DTODifficulty;
import com.wordwise.server.dto.DTOQuality;
import com.wordwise.view.activity.game.WordEvaluation;

public class WordEvaluationSubmitTask extends AsyncTask<Void, Void, Boolean> {
	private WordEvaluation evaluationActivity;
	private DTOQuality quality;
	private DTODifficulty difficulty;
	private RESTfullServerCommunication server = new RESTfullServerCommunication();
	private ProgressDialog dialog;
	
	public WordEvaluationSubmitTask(WordEvaluation evaluationActivity, DTOQuality quality, DTODifficulty difficulty){
		this.evaluationActivity = evaluationActivity;
		this.quality = quality;
		this.difficulty = difficulty;
	}
	
	@Override
	protected void onPreExecute() {
		dialog = new ProgressDialog(evaluationActivity) {
			public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
			}
		};
		dialog.setMessage("Submitting...");
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		// show the progress bar
		dialog.show();
	}
	
	protected void onPostExecute(Boolean result) {
		dialog.cancel();
		// operation successful
		evaluationActivity.onSubmitResult(result);
	}
	
	@Override
	protected Boolean doInBackground(Void... params) {
		boolean qualityResult = true;
		boolean difficultyResult = true;
		
		if(quality != null)
			qualityResult = server.addWordQualitiy(this.quality);
		if(difficulty != null)
			difficultyResult = server.addWordDifficulty(this.difficulty);
		
		return (Boolean) qualityResult && difficultyResult;
	}

}

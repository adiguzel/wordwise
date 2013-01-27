package com.wordwise.task.game;

import android.app.Activity;

import com.wordwise.model.SubmitListener;
import com.wordwise.server.dto.DTODifficulty;
import com.wordwise.server.dto.DTOQuality;

public class WordEvaluationSubmitTask extends SubmitTask {
	private DTOQuality quality;
	private DTODifficulty difficulty;
	
	public WordEvaluationSubmitTask(Activity activity,  SubmitListener submitListener, DTOQuality quality, DTODifficulty difficulty){
		super(activity, submitListener);
		this.quality = quality;
		this.difficulty = difficulty;
	}
	
	
	@Override
	protected Boolean doInBackground(Void... params) {
		boolean qualityResult = true;
		boolean difficultyResult = true;
		
		if(quality != null)
			qualityResult = serverComm.addWordQuality(this.quality);
		if(difficulty != null)
			difficultyResult = serverComm.addWordDifficulty(this.difficulty);
		
		return (Boolean) qualityResult && difficultyResult;
	}

}

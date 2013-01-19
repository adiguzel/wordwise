package com.wordwise.view.activity.game;

import android.os.Bundle;

import com.wordwise.R;
import com.wordwise.util.WordwiseUtils;
import com.wordwise.view.activity.WordwiseGameActivity;

public class TranslationEvaluation extends WordwiseGameActivity {
	
	private final String DIALOG_MESSAGE = "In this screen you will be asked to do a small contribution for this application. Please rate a word translation on your preffered language...";
	private final String DIALOG_TITLE = "You can contribute! :)";

	@Override
	public void performOnCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.rate_translation);
		onGameInit();
		onGameStart();
		WordwiseUtils.infoDialogOnStart(DIALOG_TITLE,DIALOG_MESSAGE,this);
	}

	public void onGameInit() {
		// TODO Auto-generated method stub
		
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

	public void onGameEnd() {
		// TODO Auto-generated method stub
		
	}

	
}

package com.wordwise.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wordwise.R;
import com.wordwise.view.activity.game.Hangman;

public class AboutActivity extends Activity {
	
	private Button howToPlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_view);
		
		howToPlay = (Button) findViewById(R.id.aboutButton);
	}

	public void showHowToPlay(View view) {
		//TODO HowToPlay activity should be implemented and 
		//DRAGAN USES THIS FOR HANGMAN TESTING
		Intent intent = new Intent(this, Hangman.class);
		startActivity(intent);
	}
	
}

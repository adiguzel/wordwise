package com.wordwise.activity.game;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.wordwise.R;
import com.wordwise.gameengine.Game;

public class Hangman extends Activity implements Game {
	
	private ImageView hangmanImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hangman);

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

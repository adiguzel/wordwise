package com.wordwise.activity.game;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.wordwise.R;
import com.wordwise.gameengine.Game;

public class Hangman extends Activity implements Game {

	private ImageView hangmanImage;
	private String mysteryWord;
	private int numWrongGuesses = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hangman);
		
		//Initializes the screen
		this.initialHangmanImage();

	}

	
	//This method will initialize the mysteryTextView with underscores
	private String underscore() {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < mysteryWord.length(); i++) {
			result.append("_ ");
		}
		return result.toString();
	}

	/*
	 * sets the Hangman image to the starting image
	 */
	private void initialHangmanImage() {
		hangmanImage.setImageResource(R.drawable.hangman_img00);
	}

	/*
	 * updates the Hangman image based on the number of wrong guesses
	 */
	private void updateHangmanImage() {
		switch (numWrongGuesses) {
		case 0:
			hangmanImage.setImageResource(R.drawable.hangman_img00);
			break;
		case 1:
			hangmanImage.setImageResource(R.drawable.hangman_img01);
			break;
		case 2:
			hangmanImage.setImageResource(R.drawable.hangman_img02);
			break;
		case 3:
			hangmanImage.setImageResource(R.drawable.hangman_img03);
			break;
		case 4:
			hangmanImage.setImageResource(R.drawable.hangman_img04);
			break;
		case 5:
			hangmanImage.setImageResource(R.drawable.hangman_img05);
			break;
		case 6:
			hangmanImage.setImageResource(R.drawable.hangman_img06);
			break;
		case 7:
			hangmanImage.setImageResource(R.drawable.hangman_img07);
			break;
		case 8:
			hangmanImage.setImageResource(R.drawable.hangman_img08);
			break;
		case 9:
			hangmanImage.setImageResource(R.drawable.hangman_img09);
			break;
		}
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

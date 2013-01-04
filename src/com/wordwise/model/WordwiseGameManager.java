package com.wordwise.model;

import android.content.Context;

import com.wordwise.activity.game.Hangman;
import com.wordwise.activity.game.LetterBox;
import com.wordwise.activity.game.Memory;
import com.wordwise.activity.game.Words2Translations;
import com.wordwise.gameengine.GameManager;
import com.wordwise.gameengine.GameSelector;

public class WordwiseGameManager extends GameManager{
	private Context context;

	public WordwiseGameManager(GameSelector gameSelector, Context context) {
		super(gameSelector);
		
		gameSelector.registerGame(new LetterBox());
		gameSelector.registerGame(new Hangman());
		gameSelector.registerGame(new Words2Translations());
		gameSelector.registerGame(new Memory());
		
		this.context = context;
	}

	@Override
	public Context getContext()
	{
		return context;
	}

}

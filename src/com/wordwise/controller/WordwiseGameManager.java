package com.wordwise.controller;

import android.content.Context;
import android.content.Intent;

import com.wordwise.MainActivity;
import com.wordwise.gameengine.Game;
import com.wordwise.gameengine.GameManager;
import com.wordwise.gameengine.GameSelector;
import com.wordwise.view.activity.game.Hangman;
import com.wordwise.view.activity.game.LetterBox;
import com.wordwise.view.activity.game.Memory;
import com.wordwise.view.activity.game.TranslationEvaluation;
import com.wordwise.view.activity.game.WordEvaluation;
import com.wordwise.view.activity.game.Words2Translations;

public class WordwiseGameManager extends GameManager{
	private Context context;

	public WordwiseGameManager(GameSelector gameSelector, Context context) {
		super(gameSelector);
		
		gameSelector.registerGame(new LetterBox());
		gameSelector.registerGame(new Hangman());
	    gameSelector.registerGame(new Words2Translations());
		gameSelector.registerGame(new Memory());
		gameSelector.registerGame(new WordEvaluation());
		gameSelector.registerGame(new TranslationEvaluation());
		
		this.context = context;
	}

	@Override
	public void startGame(Game game)
	{
		Intent gameIntent = new Intent(context, game.getClass());
		context.startActivity(gameIntent);
	}

	@Override
	public void endGame(Game game) {
		Intent gameIntent = new Intent(context, MainActivity.class);
		context.startActivity(gameIntent);
	}
}

package com.wordwise.controller;

import android.content.Context;
import android.content.Intent;

import com.wordwise.gameengine.Game;
import com.wordwise.gameengine.GameConfiguration;
import com.wordwise.gameengine.GameManager;
import com.wordwise.gameengine.GameSelector;
import com.wordwise.view.activity.WordwiseGameActivity;
import com.wordwise.view.activity.game.Hangman;
import com.wordwise.view.activity.game.LetterBox;
import com.wordwise.view.activity.game.Memory;
import com.wordwise.view.activity.game.TranslateWord;
import com.wordwise.view.activity.game.TranslationEvaluation;
import com.wordwise.view.activity.game.WordEvaluation;
import com.wordwise.view.activity.game.Words2Translations;

/**
 * This class is the implementation of GameManager interface for Android type of
 * devices. It knows how to start an activity etc.
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class WordwiseGameManager extends GameManager {
	private Context context;

	public WordwiseGameManager(GameSelector gameSelector, Context context) {
		super(gameSelector);
		// register all the games
		gameSelector.registerGame(new Hangman().getClass());
		gameSelector.registerGame(new Words2Translations().getClass());
		gameSelector.registerGame(new TranslateWord().getClass());
		gameSelector.registerGame(new Memory().getClass());
		gameSelector.registerGame(new WordEvaluation().getClass());
		gameSelector.registerGame(new LetterBox().getClass());
		gameSelector.registerGame(new TranslationEvaluation().getClass());

		this.context = context;
	}

	@Override
	public void startGame(Class<? extends Game> gameClass) {
		if (currentGame != null) {
			WordwiseGameActivity currentGameAct = (WordwiseGameActivity) currentGame;
			// makes sure that it is not kept on the activity stack
			currentGameAct.finish();
		}
		Intent gameIntent = new Intent(context, gameClass);
		context.startActivity(gameIntent);
	}

	@Override
	public void endGame(Game game) {
		WordwiseGameActivity gameAct = (WordwiseGameActivity) game;
		gameAct.setEndFlag(true);
		gameAct.onBackPressed();
		// make sure the activity is not kept on the activity stack
		gameAct.finish();
	}

	@Override
	public GameConfiguration getConfiguration() {
		PreferencesIOManager prefIOManager = PreferencesIOManager
				.getInstance(context);
		return prefIOManager.getCurrentGameConfiguration();
	}
}

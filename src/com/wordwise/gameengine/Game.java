package com.wordwise.gameengine;

import com.wordwise.server.model.Difficulty;

public interface Game {

	/**
	 * Initializes the game and its resources
	 **/
	public void onGameInit();

	/**
	 * Starts the game. It can start the timer if game has got a time limit and
	 * takes any similar actions.
	 * 
	 **/
	public void onGameStart();

	/**
	 * Executes the necessary steps to stop a game, such as saving statistics,
	 * saving game status etc. It is called by in the places where the game
	 * stop/destroy is detected
	 **/
	public void onGameStop();

	/**
	 * Executes the necessary steps to pause a game. It is called by in the
	 * places where the game pause is detected
	 **/
	public void onGamePause();

	/**
	 * Executes the necessary steps to when a game is finished such as showing
	 * success or fail message to user, game statistics and enabling continue
	 * button
	 **/
	public void onGameEnd();

	/**
	 * Number of translations that should be present for this game to load and
	 * be playable with the given difficulty
	 * */
	public int numberOfTranslationsNeeded(Difficulty difficulty);

	/**
	 * Number of words that should be present for this game to load and be
	 * playable with the given difficulty
	 * */
	public int numberOfWordsNeeded(Difficulty difficulty);
}

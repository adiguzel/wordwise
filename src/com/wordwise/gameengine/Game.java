package com.wordwise.gameengine;

public interface Game {
	// difficulty levels for games
	public static final int EASY = 1;
	public static final int MEDIUM = 2;
	public static final int HARD = 3;

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
}

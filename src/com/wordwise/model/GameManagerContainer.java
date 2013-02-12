package com.wordwise.model;

import com.wordwise.gameengine.GameManager;

/**
 * This static class holds an object of GameManager that is being accessed by
 * the games
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class GameManagerContainer {
	private static GameManager gameManager = null;

	public static GameManager getGameManager() {
		return gameManager;
	}

	public static void setGameManager(GameManager manager) {
		gameManager = manager;
	}
}

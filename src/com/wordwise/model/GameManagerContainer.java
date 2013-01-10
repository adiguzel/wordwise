package com.wordwise.model;

import com.wordwise.gameengine.GameManager;

public class GameManagerContainer {
	private static GameManager gameManager = null;
	
	public static GameManager getGameManager(){
		return gameManager;
	}
	
	public static void setGameManager(GameManager manager ){
		gameManager = manager;
	}
}

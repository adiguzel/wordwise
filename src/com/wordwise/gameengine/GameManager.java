package com.wordwise.gameengine;


public final class GameManager {
	private static GameManager instance = null;
	private Game currentGame;
	private GameSelectionStrategy gameFactory;

	private GameManager() {}

	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		return instance;
	}

	public void endGame() {
		currentGame.stop();
		startGameCycle();
	}

	public void startGameCycle() {
		startNextGame();
	}

	public void continueGameCycle() {
		currentGame.start();
	}

	public void endGameCycle() {
		currentGame.stop();
		
		//AND CALL THE INTENT TO GO BACK OR EXIT
	}
	
	public void startNextGame(){
		//IMPLEMENT A GAME SELECTION MECHANISM
		//FIND THE NEXT GAME
		//MAKE IT CURRENT AND START IT
	}

	public GameSelectionStrategy getGameFactory() {
		return gameFactory;
	}

	public void setGameFactory(GameSelectionStrategy gameFactory) {
		this.gameFactory = gameFactory;
	}
}

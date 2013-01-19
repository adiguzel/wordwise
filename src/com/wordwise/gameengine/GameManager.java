package com.wordwise.gameengine;


public abstract class GameManager {
	protected Game currentGame;
	protected GameSelector gameSelector;
	protected GameConfiguration configuration;
	
	public GameManager(GameSelector gameSelector){
		this.gameSelector = gameSelector;
	}

	public final void endGame() {
		currentGame.onGameStop();
		startGameCycle();
	}

	public final void startGameCycle() {
		startNextGame();
	}

	public final void continueGameCycle() {
		currentGame.onGameStart();
	}

	public final void endGameCycle() {
		currentGame.onGameStop();
		endGame(currentGame);
	}
	
	public final void startNextGame()
	{
		currentGame = gameSelector.nextGame();
		if(currentGame != null )
		{
			currentGame.onGameStart();
			startGame(currentGame);
		}		
		else
			System.out.println("Game is null");
	}
	
	public GameConfiguration getConfiguration() {
		return configuration;
	}
	
	public abstract void startGame(Game game);
	
	public abstract void endGame(Game game);

	public void setConfiguration(GameConfiguration configuration) {
		this.configuration = configuration;
	}
	
}

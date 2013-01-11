package com.wordwise.gameengine;


public abstract class GameManager {
	protected Game currentGame;
	protected GameSelector gameSelector;
	protected GameConfiguration configuration;
	
	public GameManager(GameSelector gameSelector){
		this.gameSelector = gameSelector;
	}

	public final void endGame() {
		currentGame.stop();
		startGameCycle();
	}

	public final void startGameCycle() {
		startNextGame();
	}

	public final void continueGameCycle() {
		currentGame.start();
	}

	public final void endGameCycle() {
		currentGame.stop();
		endGame(currentGame);
	}
	
	public final void startNextGame()
	{
		currentGame = gameSelector.nextGame();
		if(currentGame != null )
		{
			currentGame.start();
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

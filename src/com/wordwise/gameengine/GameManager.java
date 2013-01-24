package com.wordwise.gameengine;


public abstract class GameManager {
	protected Game currentGame;
	protected GameSelector gameSelector;
	
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
	
	public abstract GameConfiguration getConfiguration() ;
	
	public abstract void startGame(Game game);
	
	public abstract void endGame(Game game);

	/**
	 * Returns how many words the currents game needs to load 
	 * */
	public int NumberOfWordsNeeded(){
		if(currentGame == null)
			return -1;
		else return currentGame.numberOfWordsNeeded(getConfiguration().getDifficulty());
	}
	
	/**
	 * Returns how many translations the currents game needs to load 
	 * */
	public int NumberOfTranslationsNeeded(){
		if(currentGame == null)
			return -1;
		else return currentGame.numberOfTranslationsNeeded(getConfiguration().getDifficulty());
	}
}

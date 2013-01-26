package com.wordwise.gameengine;


public abstract class GameManager {
	protected Class<? extends Game> currentGameClass;
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
		currentGameClass = gameSelector.nextGame();
		if(currentGameClass != null )
		{
			startGame(currentGameClass);
		}		
		else
			System.out.println("Game class is null");
	}
	
	public abstract GameConfiguration getConfiguration() ;
	
	public abstract void startGame(Class<? extends Game> gameClass);
	
	public abstract void endGame(Game game);

	/**
	 * Returns how many words the current game needs to load 
	 * */
	public int NumberOfWordsNeeded(){
		if(currentGame == null)
			return -1;
		else return currentGame.numberOfWordsNeeded(getConfiguration().getDifficulty());
	}
	
	/**
	 * Returns how many translations the current game needs to load 
	 * */
	public int NumberOfTranslationsNeeded(){
		if(currentGame == null)
			return -1;
		else return currentGame.numberOfTranslationsNeeded(getConfiguration().getDifficulty());
	}
	
	public void setCurrentGame(Game game){
		currentGame = game;
		currentGame.onGameStart();
	}
}

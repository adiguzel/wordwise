package com.wordwise.gameengine;

/**
 * This class defines the abstract interface and basic draft implementation of
 * game cycle management. It uses the abstract {@link Game} interface without
 * knowing their sub type and implementation
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public abstract class GameManager {
	/** the class of the current game */
	protected Class<? extends Game> currentGameClass;
	/** current game that is being played */
	protected Game currentGame;
	/** selector which will choose the next game */
	protected GameSelector gameSelector;

	public GameManager(GameSelector gameSelector) {
		this.gameSelector = gameSelector;
	}

	/**
	 * Ends the current game and starts the game cycle
	 * */
	public final void endGame() {
		currentGame.onGameStop();
		startGameCycle();
	}

	/**
	 * Starts the game cycle by starting the next game
	 * */
	public final void startGameCycle() {
		startNextGame();
	}

	/**
	 * Continues the game cycle
	 * */
	public final void continueGameCycle() {
		currentGame.onGameStart();
	}

	/**
	 * Ends the current game and the game cycle
	 * */
	public final void endGameCycle() {
		currentGame.onGameStop();
		endGame(currentGame);
	}

	/**
	 * Selects the next game and starts it
	 * */
	public final void startNextGame() {
		currentGameClass = gameSelector.nextGame();
		if (currentGameClass != null) {
			startGame(currentGameClass);
		} else
			System.out.println("Game class is null");
	}

	/**
	 * @return game configuration such as learning language, proficient
	 *         languages etc.
	 * */
	public abstract GameConfiguration getConfiguration();

	/**
	 * Starts the game. The hook to be implemented by the subclasses. 
	 * 
	 * @param class of the Game to be started
	 * */
	public abstract void startGame(Class<? extends Game> gameClass);

	/**
	 * Ends the game. The hook to be implemented by the subclasses. 
	 * */
	public abstract void endGame(Game game);

	/**
	 * Returns how many words the current game needs to load
	 * */
	public int NumberOfWordsNeeded() {
		if (currentGame == null)
			return -1;
		else
			return currentGame.numberOfWordsNeeded(getConfiguration()
					.getDifficulty());
	}

	/**
	 * Returns how many translations the current game needs to load
	 * */
	public int NumberOfTranslationsNeeded() {
		if (currentGame == null)
			return -1;
		else
			return currentGame.numberOfTranslationsNeeded(getConfiguration()
					.getDifficulty());
	}

	public void setCurrentGame(Game game) {
		currentGame = game;
		currentGame.onGameStart();
	}

	public Game getCurrentGame() {
		return currentGame;
	}
}

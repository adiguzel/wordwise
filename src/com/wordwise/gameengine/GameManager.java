package com.wordwise.gameengine;

import android.content.Context;
import android.content.Intent;


public abstract class GameManager {
	private Game currentGame;
	private GameSelector gameSelector;
	private GameConfiguration configuration;
	
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
		
		//AND CALL THE INTENT TO GO BACK OR EXIT
	}
	
	public final void startNextGame()
	{
		//IMPLEMENT A GAME SELECTION MECHANISM
		//FIND THE NEXT GAME
		//MAKE IT CURRENT AND START IT
		currentGame = gameSelector.nextGame();
		if(currentGame != null )
		{
			currentGame.start();
			Intent game = new Intent(getContext(), currentGame.getClass());
			getContext().startActivity(game);
		}		
		else
			System.out.println("Game is null");
	}
	
	public GameConfiguration getConfiguration() {
		return configuration;
	}
	
	public abstract Context getContext();

	public void setConfiguration(GameConfiguration configuration) {
		this.configuration = configuration;
	}

}

package com.wordwise.controller;

import com.wordwise.gameengine.Game;
import com.wordwise.gameengine.GameSelector;

public class QueueGameSelector extends GameSelector 
{
	private static int nextGame = 0;

	@Override
	public Class<? extends Game> nextGame()
	{
		Class<? extends Game> game = games.get(nextGame);
		nextGame++;
		nextGame = nextGame % games.size();
		return game;
	}

}

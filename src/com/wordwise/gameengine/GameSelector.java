package com.wordwise.gameengine;

import java.util.ArrayList;
import java.util.List;

public abstract class GameSelector
{
	protected List<Game> games = new ArrayList<Game>();
	
	public void registerGame(Game game)
	{
		games.add(game);
	}
			
	public abstract Game nextGame();
			
}

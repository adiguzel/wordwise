package com.wordwise.gameengine;

import java.util.ArrayList;
import java.util.List;

public abstract class GameSelector
{
	protected List<Class<? extends Game>> games = new ArrayList<Class<? extends Game>>();
	
	public void registerGame(Class<? extends Game> gameClass)
	{
		games.add(gameClass);
	}
			
	public abstract Class<? extends Game> nextGame();
			
}

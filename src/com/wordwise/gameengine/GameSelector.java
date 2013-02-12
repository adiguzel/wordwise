package com.wordwise.gameengine;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines the abstract interface for game selector which should
 * choose the next game to be played. Implementations could be sequential
 * selector, random selector etc. {@link GameManager} uses it to choose the next
 * game it should start
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public abstract class GameSelector {
	/** List of available games */
	protected List<Class<? extends Game>> games = new ArrayList<Class<? extends Game>>();

	/**
	 * Registers a game to the list of games that are available
	 * */
	public void registerGame(Class<? extends Game> gameClass) {
		games.add(gameClass);
	}

	/**
	 * Chooses the next game to be played among the list of available games
	 * */
	public abstract Class<? extends Game> nextGame();

}

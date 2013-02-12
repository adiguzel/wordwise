package com.wordwise.controller;

import java.util.Random;

import com.wordwise.gameengine.Game;
import com.wordwise.gameengine.GameSelector;

/**
 * This class is the implementation of {@link GameSelector} interface for random
 * selection
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class RandomGameSelector extends GameSelector {
	@Override
	public Class<? extends Game> nextGame() {
		Random random = new Random();
		return games.get(random.nextInt(games.size()));
	}
}

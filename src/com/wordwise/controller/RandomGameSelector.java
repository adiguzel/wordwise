package com.wordwise.controller;

import java.util.Random;

import com.wordwise.gameengine.Game;
import com.wordwise.gameengine.GameSelector;

public class RandomGameSelector extends GameSelector
{
	@Override
	public Game nextGame()
	{
		Random random = new Random();
		return games.get(random.nextInt(games.size()));
	}
}

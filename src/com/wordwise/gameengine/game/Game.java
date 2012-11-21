package com.wordwise.gameengine.game;

import com.wordwise.gameengine.GameManager;

public interface Game {
    GameManager gManager = GameManager.getInstance();

	public void start();

	public void stop();

	public void pause();
}

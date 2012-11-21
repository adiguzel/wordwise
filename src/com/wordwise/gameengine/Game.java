package com.wordwise.gameengine;


public interface Game {
    GameManager gManager = GameManager.getInstance();
    
	public void start();

	public void stop();

	public void pause();
}

package com.wordwise.gameengine;

public interface Game {
    public static final GameManager gManager = GameManager.getInstance();
    
    public void init();
    
	public void start();

	public void stop();

	public void pause();
}

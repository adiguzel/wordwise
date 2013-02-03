package com.wordwise.gameengine.level;

public class Level {
	private final static int ONE_LEVEL_POINT = 200;
	private int level;
	
	private Level(int level){
		this.level = level;
	}
	
	public static Level getByPoint(int currentPoints){
		if(currentPoints <= 0 )
			return new Level(1);
		else{
			int level = ((int) Math.ceil(currentPoints / ONE_LEVEL_POINT)) + 1;
			return new Level(level);
		}
	}
		
	public int getLevel(){
		return level;
	}
	
	public int getLevelProgress(int currentPoints){
		return currentPoints - (ONE_LEVEL_POINT * (level - 1));
	}
	
	public int getMax(){
		return ONE_LEVEL_POINT;
	}
}

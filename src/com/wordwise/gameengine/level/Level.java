package com.wordwise.gameengine.level;

public class Level {
	final static int ONE_LEVEL_POINT = 200;
	int level;
	
	private Level(int level){
		this.level = level;
	}
	
	public static Level getByPoint(int currentPoints){
		if(currentPoints <= 0 )
			return new Level(1);
		else{
			int level = (int) Math.ceil(currentPoints / ONE_LEVEL_POINT);
			return new Level(level);
		}
	}
	public int getLevel(){
		return level;
	}
}

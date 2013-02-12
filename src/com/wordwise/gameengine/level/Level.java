package com.wordwise.gameengine.level;

/**
 * This class is defines the level that is reached given the total points
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class Level {
	/** number of points that every level can reach up to */
	private final static int POINTS_PER_LEVEL = 200;
	/** number of the level */
	private int level;

	private Level(int level) {
		this.level = level;
	}

	public static Level getByPoint(int currentPoints) {
		if (currentPoints <= 0)
			return new Level(1);
		else {
			int level = ((int) Math.ceil(currentPoints / POINTS_PER_LEVEL)) + 1;
			return new Level(level);
		}
	}

	public int getLevel() {
		return level;
	}

	/**
	 * calculates the point progress in the current level
	 * 
	 * @return the point progress in the current level
	 * */
	public int getLevelProgress(int currentPoints) {
		return currentPoints - (POINTS_PER_LEVEL * (level - 1));
	}

	/**
	 * maximum number of points that a level can reach
	 * */
	public int getMax() {
		return POINTS_PER_LEVEL;
	}
}

package com.wordwise.util.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.graphics.Point;

/**
 * This is a utility class provides the common operations used in the Letterbox
 * game
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class LetterBoxPositionUtils {
	// defines the possible list of directions that the translation to search
	// for to be places
	public enum Direction {
		VERTICAL, HORIZONTAL, DIAGONAL_SLASH, DIAGONAL_BACK_SLASH;

		public static List<Direction> getDirections() {
			List<Direction> returnList = new ArrayList<LetterBoxPositionUtils.Direction>();
			for (Direction direction : values()) {
				returnList.add(direction);
			}
			return returnList;
		}
	}

	public enum Way {
		FORWARD, BACKWARD;

		public static List<Way> getWays() {
			List<Way> returnList = new ArrayList<LetterBoxPositionUtils.Way>();
			for (Way way : values()) {
				returnList.add(way);
			}
			return returnList;
		}
	}

	// number of columns in the grid
	private static int gridNumberOfColumns = 8;
	public static int getGridSize() {
		return gridNumberOfColumns;
	}

	/*
	 * checks whether two given positions are adjacent or not
	 * */
	public static boolean ArePositionsAdjacent(int position1, int position2) {
		Point point1 = GetPoint(position1);
		Point point2 = GetPoint(position2);

		if (point1.x == point2.x && point1.y == point2.y) {
			return false;
		}

		if ((point1.y == point2.y || point1.y == point2.y + 1 || point1.y == point2.y - 1)
				&& (point1.x == point2.x || point1.x == point2.x + 1 || point1.x == point2.x - 1)) {
			return true;
		}
		return false;
	}

	/*
	 * calculates the direction of the movement from one place to another
	 * */
	public static Direction GetDirection(int position1, int position2) {
		Point point1 = GetPoint(position1);
		Point point2 = GetPoint(position2);

		if (point1.y == point2.y) {
			return Direction.HORIZONTAL;
		}
		if (point1.x == point2.x) {
			return Direction.VERTICAL;
		}

		if ((point1.x < point2.x) && (point1.y > point2.y)
				|| (point1.x > point2.x) && (point1.y < point2.y)) {
			return Direction.DIAGONAL_SLASH;
		}

		return Direction.DIAGONAL_BACK_SLASH;
	}

	public static Point GetPoint(int position) {
		Point resultPoint = new Point();
		resultPoint.x = position % gridNumberOfColumns;
		resultPoint.y = (int) Math.floor(position / gridNumberOfColumns);
		return resultPoint;
	}

	public static int GetPosition(Point point) {
		int resultPosition = 0;
		resultPosition += point.y * gridNumberOfColumns;
		resultPosition += point.x;
		return resultPosition;
	}

	/*
	 * Inserts a translation starting from the a position in the letterbox grid
	 * */
	public static boolean InsertTranslationIntoPosition(String word,
			int positionOfFirstLetter, List<String> letterBox) {
		Random random = new Random();
		List<Direction> directions = Direction.getDirections();
		Direction direction = null;

		List<Way> ways = Way.getWays();
		Way way = null;

		while (directions.size() > 0) {
			direction = directions.get(random.nextInt(directions.size()));
			while (ways.size() > 0) {
				way = ways.get(random.nextInt(ways.size()));
				if (Insert(word, positionOfFirstLetter, direction, way,
						letterBox)) {
					return true;
				}

				ways.remove(way);
			}
			directions.remove(direction);
		}
		return false;
	}

	/*
	 * Inserts a translation starting from the a position in the letterbox grid
	 * */
	@SuppressLint("DefaultLocale")
	private static boolean Insert(String word, int positionOfFirstLetter,
			Direction direction, Way way, List<String> letterBox) {
		if (!IsPossibleToInsert(word, positionOfFirstLetter, direction, way,
				letterBox)) {
			return false;
		}
		Point point = GetPoint(positionOfFirstLetter);

		for (int i = 0; i < word.length(); i++) {
			letterBox.set(GetPosition(point), String.valueOf(word.charAt(i))
					.toLowerCase());
			point = GetNextPoint(point, direction, way);
		}
		return true;
	}

	private static Point GetNextPoint(Point initialPoint, Direction direction,
			Way way) {
		Point finalPoint = initialPoint;
		if (direction == Direction.HORIZONTAL) {
			if (way == Way.FORWARD) {
				finalPoint.x += 1;
			}
			if (way == Way.BACKWARD) {
				finalPoint.x -= 1;
			}
		}

		if (direction == Direction.VERTICAL) {
			if (way == Way.FORWARD) {
				finalPoint.y += 1;
			}
			if (way == Way.BACKWARD) {
				finalPoint.y -= 1;
			}
		}

		if (direction == Direction.DIAGONAL_SLASH) {
			if (way == Way.FORWARD) {
				finalPoint.x += 1;
				finalPoint.y -= 1;
			}
			if (way == Way.BACKWARD) {
				finalPoint.x -= 1;
				finalPoint.y += 1;
			}
		}

		if (direction == Direction.DIAGONAL_BACK_SLASH) {
			if (way == Way.FORWARD) {
				finalPoint.x += 1;
				finalPoint.y += 1;
			}
			if (way == Way.BACKWARD) {
				finalPoint.x -= 1;
				finalPoint.y -= 1;
			}
		}

		return finalPoint;
	}

	private static boolean IsPossibleToInsert(String word,
			int positionOfFirstLetter, Direction direction, Way way,
			List<String> letterBox) {
		Point point = GetPoint(positionOfFirstLetter);
		for (int i = 0; i < word.length(); i++) {
			if (!IsPossibleToInsert(word.charAt(i), point, letterBox)) {
				return false;
			}
			point = GetNextPoint(point, direction, way);
		}

		return true;
	}

	private static boolean IsPossibleToInsert(char letter, Point point,
			List<String> letterBox) {
		if (!IsPointInsideLetterBox(point, letterBox)) {
			return false;
		}
		if (letterBox.get(GetPosition(point)).charAt(0) == letterBox
				.get(GetPosition(point)).toLowerCase().charAt(0)
				&& letterBox.get(GetPosition(point)).charAt(0) != letter) {
			return false;
		}
		return true;
	}

	private static boolean IsPointInsideLetterBox(Point point,
			List<String> letterBox) {
		Point limitPoint = GetPoint(letterBox.size() - 1);
		if (point.x < 0 || point.x > limitPoint.x || point.y < 0
				|| point.y > limitPoint.y) {
			return false;
		}
		return true;
	}

}

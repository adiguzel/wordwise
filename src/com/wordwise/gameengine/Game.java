package com.wordwise.gameengine;

import com.wordwise.gameengine.level.Promotion;
import com.wordwise.server.dto.DTODifficulty;
import com.wordwise.server.dto.DTOLanguage;
import com.wordwise.server.dto.DTOTranslation;

/**
 * This interface defines the main contract interface that every concrete games
 * should conform. It defines the callback methods that will either be called
 * within or outside of the games. {@link GameManager} manages the game activity
 * life cycle using callback methods defined in this interface.
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public interface Game {

	/**
	 * Initializes the game and its resources
	 **/
	public void onGameInit();

	/**
	 * Starts the game. It can start the timer if game has got a time limit and
	 * takes any similar actions.
	 * 
	 **/
	public void onGameStart();

	/**
	 * Executes the necessary steps to stop a game, such as saving statistics,
	 * saving game status etc. It is called by in the places where the game
	 * stop/destroy is detected
	 **/
	public void onGameStop();

	/**
	 * Executes the necessary steps to pause a game. It is called by in the
	 * places where the game pause is detected
	 **/
	public void onGamePause();

	/**
	 * Executes the necessary steps to when a game is finished such as showing
	 * success or fail message to user, game statistics and enabling continue
	 * button
	 **/
	public void onGameEnd();

	/**
	 * Number of translations that should be present for this game to load and
	 * be playable with the given difficulty
	 * 
	 * @param difficulty
	 *            the object which encapsulates difficulty of the game
	 * @return number of translations needed for the game to load
	 */
	public int numberOfTranslationsNeeded(DTODifficulty difficulty);

	/**
	 * Determines if the game can use the given translation in its logic or not
	 * 
	 * @param translation
	 *            translation that should be tested if whether or not it can be
	 *            used in the game
	 * @return true if it can use, false otherwise
	 * */
	public boolean canUse(DTOTranslation translation);

	/**
	 * Returns the Language of the translations needed by the game
	 * 
	 * @return the language of the translations that will be used in the game
	 * */
	public DTOLanguage getLanguage();

	/**
	 * Number of words that should be present for this game to load and be
	 * playable with the given difficulty
	 * 
	 * @param difficulty
	 *            the object which encapsulates difficulty of the game
	 * @return number of words needed for the game to load
	 * */
	public int numberOfWordsNeeded(DTODifficulty difficulty);

	/**
	 * The promotion that the game provides when the game finishes
	 * 
	 * @return the promotion the game promotes when the game finishes
	 * */
	public Promotion getPromotion();

}

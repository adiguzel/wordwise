package com.wordwise.model;

/**
 * This interface defines the contract for games that submits data and listens
 * for a result
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public interface SubmitListener {

	/**
	 * Callback function to let the children of the interface know about submit
	 * result to server
	 * */
	public void onSubmitResult(boolean result);

}

package com.wordwise.model;

public interface SubmitListener {

	/**
	 * Callback function to let the children of the interface know about submit
	 * result to server
	 * */
	public void onSubmitResult(boolean result);

}

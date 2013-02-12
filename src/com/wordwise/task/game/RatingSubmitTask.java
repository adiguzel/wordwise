package com.wordwise.task.game;

import android.app.Activity;

import com.wordwise.model.SubmitListener;
import com.wordwise.server.dto.DTORate;

/**
 * This class is used to submit rating asynchronously to server
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class RatingSubmitTask extends SubmitTask {
	// rating to be submitted
	private DTORate rating;

	public RatingSubmitTask(Activity activity, SubmitListener submitListener,
			DTORate rating) {
		super(activity, submitListener);
		this.rating = rating;
	}

	@Override
	protected Boolean doInBackground(Void... arg0) {
		boolean result = true;

		if (rating != null)
			result = serverComm.rateTranslation(rating); // request to send the rating to server
		return result;
	}
}

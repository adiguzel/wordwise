package com.wordwise.task.game;

import android.app.Activity;

import com.wordwise.model.SubmitListener;
import com.wordwise.server.dto.DTORate;

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
			result = serverComm.rateTranslation(rating);
		return result;
	}
}

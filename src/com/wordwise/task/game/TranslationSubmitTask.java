package com.wordwise.task.game;

import android.app.Activity;

import com.wordwise.model.SubmitListener;
import com.wordwise.server.dto.DTOTranslation;

/**
 * This class is used to submit translations asynchronously to server
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class TranslationSubmitTask extends SubmitTask {
	private DTOTranslation translation;

	public TranslationSubmitTask(Activity activity,
			SubmitListener submitListener, DTOTranslation translation) {
		super(activity, submitListener);
		this.translation = translation;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		boolean result = true;

		if (translation != null)
			result = serverComm.addTranslation(this.translation);
		return result;
	}

}

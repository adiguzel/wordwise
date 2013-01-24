package com.wordwise.view.activity.game;

import java.util.List;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.wordwise.client.RESTfullServerCommunication;
import com.wordwise.model.Configuration;
import com.wordwise.server.model.Translation;


public class TranslationLoader extends AsyncTaskLoader<List<Translation>> {
	private RESTfullServerCommunication serverComm;
	private Configuration configuration;

	public TranslationLoader(Context context) {
		super(context);
	    serverComm = new RESTfullServerCommunication();
	    configuration = Configuration.getInstance(context);
	}

	@Override
	public List<Translation> loadInBackground() {
		return serverComm.listTranslations(configuration.getLearningLanguage(), configuration.getDifficulty(), 1, null);
	}

}

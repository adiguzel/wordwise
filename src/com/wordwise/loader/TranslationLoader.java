package com.wordwise.loader;

import java.util.List;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.wordwise.client.RESTfullServerCommunication;
import com.wordwise.gameengine.GameConfiguration;
import com.wordwise.gameengine.GameManager;
import com.wordwise.model.Configuration;
import com.wordwise.model.GameManagerContainer;
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
		GameManager gManager = GameManagerContainer.getGameManager();
		int translationsNeeded = gManager.NumberOfTranslationsNeeded();
		Log.v("translationsNeed", "" + translationsNeeded);
		if (translationsNeeded == -1 || translationsNeeded == 0)
			return null;
		GameConfiguration gameConf = configuration.getCurrentGameConfiguration();
		List<Translation>  translations = serverComm.listTranslations(gameConf.getLearningLanguage(),
				gameConf.getDifficulty(), translationsNeeded, null);
		Log.v("loadInBackground - trans", "" + translations);
		return translations;
	}
}

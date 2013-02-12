package com.wordwise.loader;

import java.util.ArrayList;
import java.util.List;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.wordwise.client.RESTfullServerCommunication;
import com.wordwise.controller.PreferencesIOManager;
import com.wordwise.gameengine.GameConfiguration;
import com.wordwise.gameengine.GameManager;
import com.wordwise.model.GameManagerContainer;
import com.wordwise.server.dto.DTOTranslation;

/**
 * This class is loads translations asynchronously from the server
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class TranslationLoader extends AsyncTaskLoader<List<DTOTranslation>> {
	private RESTfullServerCommunication serverComm;
	private PreferencesIOManager configuration;

	public TranslationLoader(Context context) {
		super(context);
		serverComm = new RESTfullServerCommunication(context);
		configuration = PreferencesIOManager.getInstance(context);
	}

	@Override
	public List<DTOTranslation> loadInBackground() {
		GameManager gManager = GameManagerContainer.getGameManager();
		int translationsNeeded = gManager.NumberOfTranslationsNeeded();
		if (translationsNeeded == -1 || translationsNeeded == 0)
			return null;
		GameConfiguration gameConf = configuration
				.getCurrentGameConfiguration();
		// get the translation from the server
		List<DTOTranslation> translations = serverComm.listTranslations(
				gManager.getCurrentGame().getLanguage(),
				gameConf.getDifficulty(), translationsNeeded, null);

		List<DTOTranslation> finalTranslations = new ArrayList<DTOTranslation>();

		if (translations == null || translations.size() < translationsNeeded) {
			// let the game know that the translations returned null
			return translations;
		}

		// make the final checks on the list of the translations returned and
		// request new translations for the ones that does not conform to game's
		// need
		while (finalTranslations.size() < translationsNeeded) {
			for (DTOTranslation dtoTranslation : translations) {
				if (gManager.getCurrentGame().canUse(dtoTranslation)) {
					finalTranslations.add(dtoTranslation);
				}
			}
			translations.removeAll(finalTranslations);
			if (translations.size() > 0) {
				translations = serverComm.listTranslations(
						gameConf.getLearningLanguage(),
						gameConf.getDifficulty(), translations.size(),
						translations);
			}
			if (translations.size() == 0) {
				break;
			}
		}

		return finalTranslations;
	}
}

package com.wordwise.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.restlet.Context;
import org.restlet.engine.Engine;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.resource.ClientResource;

import android.content.res.Resources;
import android.util.Log;

import com.wordwise.R;
import com.wordwise.gameengine.ServerCommunication;
import com.wordwise.server.dto.DTODifficulty;
import com.wordwise.server.dto.DTOLanguage;
import com.wordwise.server.dto.DTOQuality;
import com.wordwise.server.dto.DTORate;
import com.wordwise.server.dto.DTOTranslation;
import com.wordwise.server.dto.DTOWord;
import com.wordwise.server.dto.parameter.ListTranslationParameters;
import com.wordwise.server.dto.parameter.ListWordParameters;
import com.wordwise.server.resource.DifficultyResource;
import com.wordwise.server.resource.QualityResource;
import com.wordwise.server.resource.RateResource;
import com.wordwise.server.resource.TranslationResource;
import com.wordwise.server.resource.WordResource;

/**
 * This class is the implementation of server communication interface in Restful
 * architecture using Restlet API
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class RESTfullServerCommunication implements ServerCommunication {

	private String BASE_CLIENT_URL;
	// timeout is set to 15 seconds
	private Integer timeout = 15000;
	private String timeoutString = timeout.toString();

	public RESTfullServerCommunication(android.content.Context context) {
		BASE_CLIENT_URL = baseClientURL(context);
	}

	/*
	 * gets the configuration for the server from the properties file located
	 * under res/ folder
	 */
	public String baseClientURL(android.content.Context context) {
		Resources resources = context.getResources();
		InputStream rawResource = resources
				.openRawResource(R.raw.wordwise_server);
		Properties prop = new Properties();
		try {
			prop.load(rawResource);
			String protocol = prop.getProperty("protocol", "http");
			String serverBaseAddress = prop.getProperty("server-base-address",
					"");
			String appName = prop.getProperty("appName", "WordWiseServer");

			return protocol + "://" + serverBaseAddress + "/" + appName + "/";

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Prepares the translation resource that will be used to access translation
	 * related web services on the server by using Restlet framework
	 */
	private TranslationResource getTranslationResource() {
		Engine.getInstance().getRegisteredConverters()
				.add(new JacksonConverter());

		Context context = new Context();
		context.getParameters().add("controllerSleepTimeMs", timeoutString);
		context.getParameters().add("maxIoIdleTimeMs", timeoutString);
		context.getParameters().add("socketTimeout", timeoutString);
		ClientResource clientResource = new ClientResource(context,
				BASE_CLIENT_URL + TranslationResource.RESOURCE_NAME);
		clientResource.setRetryOnError(false);

		return clientResource.wrap(TranslationResource.class);
	}

	/*
	 * Prepares the rate resource that will be used to access rate related web
	 * services on the server by using Restlet framework
	 */
	private RateResource getRateResource() {
		Engine.getInstance().getRegisteredConverters()
				.add(new JacksonConverter());

		Context context = new Context();
		context.getParameters().add("controllerSleepTimeMs", timeoutString);
		context.getParameters().add("maxIoIdleTimeMs", timeoutString);
		context.getParameters().add("socketTimeout", timeoutString);
		ClientResource clientResource = new ClientResource(context,
				BASE_CLIENT_URL + RateResource.RESOURCE_NAME);
		clientResource.setRetryOnError(false);

		return clientResource.wrap(RateResource.class);
	}

	/*
	 * Prepares the quality resource that will be used to access quality related
	 * web services on the server by using Restlet framework
	 */
	private QualityResource getQualityResource() {
		Engine.getInstance().getRegisteredConverters()
				.add(new JacksonConverter());

		Context context = new Context();
		context.getParameters().add("controllerSleepTimeMs", timeoutString);
		context.getParameters().add("maxIoIdleTimeMs", timeoutString);
		context.getParameters().add("socketTimeout", timeoutString);
		ClientResource clientResource = new ClientResource(context,
				BASE_CLIENT_URL + QualityResource.RESOURCE_NAME);
		clientResource.setRetryOnError(false);

		return clientResource.wrap(QualityResource.class);
	}

	/*
	 * Prepares the difficulty resource that will be used to access difficulty
	 * related web services on the server by using Restlet framework
	 */
	private DifficultyResource getDifficultyResource() {
		Engine.getInstance().getRegisteredConverters()
				.add(new JacksonConverter());

		Context context = new Context();
		context.getParameters().add("controllerSleepTimeMs", timeoutString);
		context.getParameters().add("maxIoIdleTimeMs", timeoutString);
		context.getParameters().add("socketTimeout", timeoutString);
		ClientResource clientResource = new ClientResource(context,
				BASE_CLIENT_URL + DifficultyResource.RESOURCE_NAME);
		clientResource.setRetryOnError(false);

		return clientResource.wrap(DifficultyResource.class);
	}

	/*
	 * Prepares the word resource that will be used to access word related web
	 * services on the server by using Restlet framework
	 */
	private WordResource getWordResource() {
		Engine.getInstance().getRegisteredConverters()
				.add(new JacksonConverter());

		Context context = new Context();
		context.getParameters().add("controllerSleepTimeMs", timeoutString);
		context.getParameters().add("maxIoIdleTimeMs", timeoutString);
		context.getParameters().add("socketTimeout", timeoutString);
		ClientResource clientResource = new ClientResource(context,
				BASE_CLIENT_URL + WordResource.RESOURCE_NAME);
		clientResource.setRetryOnError(false);

		return clientResource.wrap(WordResource.class);
	}

	/*
	 * Adds a new translation to the server by accessing its exposed web
	 * services
	 */
	public boolean addTranslation(DTOTranslation translation) {
		try {
			getTranslationResource().add(translation);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * Adds a new rating for a translation to the server by accessing its
	 * exposed web services
	 */
	public boolean rateTranslation(DTORate rating) {
		try {
			getRateResource().add(rating);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * Adds a new quality evaluation for a word to the server by accessing its
	 * exposed web services
	 */
	public boolean addWordQuality(DTOQuality quality) {
		try {
			getQualityResource().add(quality);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * Adds a new English word to the server by accessing its exposed web
	 * services
	 */
	public boolean addWordDifficulty(DTODifficulty difficulty) {
		try {
			getDifficultyResource().add(difficulty);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * Requests and gets a list of translations from the server by accessing its
	 * exposed web services. Clients of this method should check whether null
	 * returned or not.
	 */
	public List<DTOTranslation> listTranslations(DTOLanguage language,
			DTODifficulty difficulty, int numberOfTranslations,
			List<DTOTranslation> translationsAlreadyUsed) {
		try {
			List<DTOTranslation> translations = getTranslationResource().list(
					new ListTranslationParameters(language, difficulty,
							numberOfTranslations, translationsAlreadyUsed))
					.getDTOTranslationList();
			return translations;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/*
	 * Requests and gets a list of words from the server by accessing its
	 * exposed web services. Clients of this method should check whether null
	 * returned or not.
	 */
	public List<DTOWord> listWords(int number) {
		try {
			List<DTOWord> words = getWordResource().list(
					new ListWordParameters(number)).getDTOWordList();
			Log.v("RESTFul - words", "" + words);
			return words;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * Requests and gets a single word from the server by accessing its exposed
	 * web services. Clients of this method should check whether null returned
	 * or not.
	 */
	public DTOWord getWord() {
		List<DTOWord> words = listWords(1);
		if (words == null)
			return null;
		else if (words.size() == 0)
			return null;
		else
			return words.get(0);
	}

}

package com.wordwise.client;

import java.util.List;

import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.resource.ClientResource;

import android.util.Log;

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

public class RESTfullServerCommunication implements ServerCommunication {

	private static final String BASE_CLIENT_URL = "http://192.168.1.100:8080/WordWiseServer/";

	private static final TranslationResource translationResource = getTranslationResource();
	private static final RateResource rateResource = getRateResource();
	private static final QualityResource qualityResource = getQualityResource();
	private static final DifficultyResource difficultyResource = getDifficultyResource();
	private static final WordResource wordResource = getWordResource();
	
	private static Integer timeout = 1000;
	private static String timeoutString = timeout.toString();
	
	private static TranslationResource getTranslationResource() {
		Engine.getInstance().getRegisteredConverters().add(new JacksonConverter());
		
		ClientResource clientResource = new ClientResource(BASE_CLIENT_URL
				+ TranslationResource.RESOURCE_NAME);
		
		Context context = new Context();
		context.getParameters().add("socketTimeout", timeoutString);
		clientResource.setNext(new Client(context, Protocol.HTTP));
		clientResource.setRetryOnError(false);

		return clientResource.wrap(TranslationResource.class);
	}

	private static RateResource getRateResource() {
		Engine.getInstance().getRegisteredConverters().add(new JacksonConverter());
		
		ClientResource clientResource = new ClientResource(BASE_CLIENT_URL
				+ RateResource.RESOURCE_NAME);
		
		Context context = new Context();
		context.getParameters().add("socketTimeout", timeoutString);
		clientResource.setNext(new Client(context, Protocol.HTTP));
		clientResource.setRetryOnError(false);

		return clientResource.wrap(RateResource.class);
	}

	private static QualityResource getQualityResource() {
		Engine.getInstance().getRegisteredConverters().add(new JacksonConverter());
		
		ClientResource clientResource = new ClientResource(BASE_CLIENT_URL
				+ QualityResource.RESOURCE_NAME);
		
		Context context = new Context();
		context.getParameters().add("socketTimeout", timeoutString);
		clientResource.setNext(new Client(context, Protocol.HTTP));
		clientResource.setRetryOnError(false);

		return clientResource.wrap(QualityResource.class);
	}

	private static DifficultyResource getDifficultyResource() {
		Engine.getInstance().getRegisteredConverters().add(new JacksonConverter());
		
		ClientResource clientResource = new ClientResource(BASE_CLIENT_URL
				+ DifficultyResource.RESOURCE_NAME);
		
		Context context = new Context();
		context.getParameters().add("socketTimeout", timeoutString);
		clientResource.setNext(new Client(context, Protocol.HTTP));
		clientResource.setRetryOnError(false);

		return clientResource.wrap(DifficultyResource.class);
	}
	
	private static WordResource getWordResource() {
		Engine.getInstance().getRegisteredConverters().add(new JacksonConverter());
		
		ClientResource clientResource = new ClientResource(BASE_CLIENT_URL
				+ WordResource.RESOURCE_NAME);
		
		Context context = new Context();
		context.getParameters().add("socketTimeout", timeoutString);
		clientResource.setNext(new Client(context, Protocol.HTTP));
		clientResource.setRetryOnError(false);

		return clientResource.wrap(WordResource.class);
	}

	public boolean addTranslation(DTOTranslation translation) {
		try{
			translationResource.add(translation);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean rateTranslation(DTORate rating) {
		try{
			rateResource.add(rating);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	public boolean addWordQualitiy(DTOQuality quality) {
		try{
			qualityResource.add(quality);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	public boolean addWordDifficulty(DTODifficulty difficulty) {
		try{
			difficultyResource.add(difficulty);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Clients of this method should check whether null returned or not.
	 * */
	public List<DTOTranslation> listTranslations(DTOLanguage language,
			DTODifficulty difficulty, int numberOfTranslations,
			List<DTOTranslation> translationsAlreadyUsed) {
		Log.v("RESTFul - list trans", "Starting to load translations");
		try{
			List<DTOTranslation> translations = translationResource.list(new ListTranslationParameters(language,
					difficulty, numberOfTranslations, translationsAlreadyUsed)).getDTOTranslationList();
			Log.v("RESTFul - list trans", ""+translations);
			return translations;
		}catch(Exception e){
			e.printStackTrace();
			Log.v("RESTFul - list trans", "Connection problem");
			return null;
		}
		
	}

	public List<DTOWord> listWords(int number) {
		try{			
			List<DTOWord> words = wordResource.list(new ListWordParameters(number)).getDTOWordList();
			Log.v("RESTFul - words", ""+words);
			return words;
		}
		catch(Exception e){
			e.printStackTrace();
			Log.v("RESTFul - list words", "Connection problem");
			return null;
		}
	}

	public DTOWord getWord() {
		List<DTOWord> words = listWords(1);
		if(words == null)
			return null;
		else if(words.size() == 0)
		   return null;
		else
			return words.get(0);
	}

}

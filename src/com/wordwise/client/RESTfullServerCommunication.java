package com.wordwise.client;

import java.util.List;

import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;

import android.util.Log;

import com.wordwise.gameengine.ServerCommunication;
import com.wordwise.server.model.Difficulty;
import com.wordwise.server.model.Language;
import com.wordwise.server.model.Quality;
import com.wordwise.server.model.Rate;
import com.wordwise.server.model.Translation;
import com.wordwise.server.model.Word;
import com.wordwise.server.model.parameter.ListTranslationParameters;
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
	
	private static TranslationResource getTranslationResource() {
		ClientResource clientResource = new ClientResource(BASE_CLIENT_URL
				+ TranslationResource.RESOURCE_NAME);
		
		Context context = new Context();
		context.getParameters().add("socketTimeout", "1000");
		clientResource.setNext(new Client(context, Protocol.HTTP));
		clientResource.setRetryOnError(false);

		return clientResource.wrap(TranslationResource.class);
	}

	private static RateResource getRateResource() {
		ClientResource clientResource = new ClientResource(BASE_CLIENT_URL
				+ RateResource.RESOURCE_NAME);
		
		Context context = new Context();
		context.getParameters().add("socketTimeout", "1000");
		clientResource.setNext(new Client(context, Protocol.HTTP));
		clientResource.setRetryOnError(false);

		return clientResource.wrap(RateResource.class);
	}

	private static QualityResource getQualityResource() {
		ClientResource clientResource = new ClientResource(BASE_CLIENT_URL
				+ QualityResource.RESOURCE_NAME);
		
		Context context = new Context();
		context.getParameters().add("socketTimeout", "1000");
		clientResource.setNext(new Client(context, Protocol.HTTP));
		clientResource.setRetryOnError(false);

		return clientResource.wrap(QualityResource.class);
	}

	private static DifficultyResource getDifficultyResource() {
		ClientResource clientResource = new ClientResource(BASE_CLIENT_URL
				+ DifficultyResource.RESOURCE_NAME);
		
		Context context = new Context();
		context.getParameters().add("socketTimeout", "1000");
		clientResource.setNext(new Client(context, Protocol.HTTP));
		clientResource.setRetryOnError(false);

		return clientResource.wrap(DifficultyResource.class);
	}
	
	private static WordResource getWordResource() {
		ClientResource clientResource = new ClientResource(BASE_CLIENT_URL
				+ WordResource.RESOURCE_NAME);
		
		Context context = new Context();
		context.getParameters().add("socketTimeout", "1000");
		clientResource.setNext(new Client(context, Protocol.HTTP));
		clientResource.setRetryOnError(false);

		return clientResource.wrap(WordResource.class);
	}

	public boolean addTranslation(Translation translation) {
		try{
			translationResource.add(translation);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean rateTranslation(Rate rating) {
		try{
			rateResource.add(rating);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	public boolean addWordQualitiy(Quality quality) {
		try{
			qualityResource.add(quality);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	public boolean addWordDifficulty(Difficulty difficulty) {
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
	public List<Translation> listTranslations(Language language,
			Difficulty difficulty, int numberOfTranslations,
			List<Translation> translationsAlreadyUsed) {
		Log.v("RESTFul - list trans", "Starting to load translations");
		try{
			Log.v("RESTFul - list trans", "Connection problem");
			List<Translation> translations = translationResource.list(new ListTranslationParameters(language,
					difficulty, numberOfTranslations, translationsAlreadyUsed));
			Log.v("RESTFul - list trans", "Translations liaded");
			return translations;
		}catch(Exception e){
			e.printStackTrace();
			Log.v("RESTFul - list trans", "Connection problem");
			return null;
		}
		
	}

	public List<Word> listWords(int number) {
		// TODO Auto-generated method stub
		try{
			return wordResource.list(number);
		}
		catch(Exception e){
			e.printStackTrace();
			Log.v("RESTFul - list trans", "Connection problem");
			return null;
		}
	}

	public Word getWord() {
		// TODO Auto-generated method stub
		List<Word> words = listWords(1);
		Log.v("RESTFul - words", ""+words);
		if(words == null)
			return null;
		else if(words.size() == 0)
		   return null;
		else
			return words.get(0);
	}

}

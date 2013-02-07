package com.wordwise.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.Protocol;
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

public class RESTfullServerCommunication implements ServerCommunication {
	
	private String BASE_CLIENT_URL;
	
	private Integer timeout = 5000;
	private String timeoutString = timeout.toString();
	
	public RESTfullServerCommunication(android.content.Context context){
		BASE_CLIENT_URL = baseClientURL(context);
	}
	
	public String baseClientURL(android.content.Context context){
		Resources resources = context.getResources();
		InputStream rawResource = resources.openRawResource(R.raw.wordwise_server);
		Properties prop = new Properties();        
		try {
		    prop.load(rawResource);
			String protocol = prop.getProperty("protocol", "http");
			String serverBaseAddress = prop.getProperty("server-base-address", "");
			String appName = prop.getProperty("appName", "WordWiseServer");
			
			return protocol + "://" + serverBaseAddress + "/" + appName + "/";
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private TranslationResource getTranslationResource() {
		Engine.getInstance().getRegisteredConverters().add(new JacksonConverter());
		
		Context context = new Context();
        context.getParameters().add("controllerSleepTimeMs", timeoutString);
        context.getParameters().add("maxIoIdleTimeMs", timeoutString);
        context.getParameters().add("socketTimeout", timeoutString);
        Client client = new Client(context, Protocol.HTTP);
        ClientResource clientResource = new ClientResource(BASE_CLIENT_URL + TranslationResource.RESOURCE_NAME);
		clientResource.setRetryOnError(false);
		clientResource.setNext(client);

		return clientResource.wrap(TranslationResource.class);
	}

	private RateResource getRateResource() {
		Engine.getInstance().getRegisteredConverters().add(new JacksonConverter());
		
		Context context = new Context();
        context.getParameters().add("controllerSleepTimeMs", timeoutString);
        context.getParameters().add("maxIoIdleTimeMs", timeoutString);
        context.getParameters().add("socketTimeout", timeoutString);
        Client client = new Client(context, Protocol.HTTP);
        ClientResource clientResource = new ClientResource(BASE_CLIENT_URL + RateResource.RESOURCE_NAME);
		clientResource.setRetryOnError(false);
		clientResource.setNext(client);

		return clientResource.wrap(RateResource.class);
	}

	private QualityResource getQualityResource() {
		Engine.getInstance().getRegisteredConverters().add(new JacksonConverter());
		
		Context context = new Context();
        context.getParameters().add("controllerSleepTimeMs", timeoutString);
        context.getParameters().add("maxIoIdleTimeMs", timeoutString);
        context.getParameters().add("socketTimeout", timeoutString);
        Client client = new Client(context, Protocol.HTTP);
        ClientResource clientResource = new ClientResource(BASE_CLIENT_URL + QualityResource.RESOURCE_NAME);
		clientResource.setRetryOnError(false);
		clientResource.setNext(client);

		return clientResource.wrap(QualityResource.class);
	}

	private DifficultyResource getDifficultyResource() {
		Engine.getInstance().getRegisteredConverters().add(new JacksonConverter());
		
		Context context = new Context();
        context.getParameters().add("controllerSleepTimeMs", timeoutString);
        context.getParameters().add("maxIoIdleTimeMs", timeoutString);
        context.getParameters().add("socketTimeout", timeoutString);
        Client client = new Client(context, Protocol.HTTP);
        ClientResource clientResource = new ClientResource(BASE_CLIENT_URL + DifficultyResource.RESOURCE_NAME);
		clientResource.setRetryOnError(false);
		clientResource.setNext(client);

		return clientResource.wrap(DifficultyResource.class);
	}
	
	private WordResource getWordResource() {
		Engine.getInstance().getRegisteredConverters().add(new JacksonConverter());
		
		Context context = new Context();
        context.getParameters().add("controllerSleepTimeMs", timeoutString);
        context.getParameters().add("maxIoIdleTimeMs", timeoutString);
        context.getParameters().add("socketTimeout", timeoutString);
        Client client = new Client(context, Protocol.HTTP);
        ClientResource clientResource = new ClientResource(BASE_CLIENT_URL + WordResource.RESOURCE_NAME);
		clientResource.setRetryOnError(false);
		clientResource.setNext(client);

		return clientResource.wrap(WordResource.class);
	}

	public boolean addTranslation(DTOTranslation translation) {
		try{
			getTranslationResource().add(translation);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean rateTranslation(DTORate rating) {
		try{
			getRateResource().add(rating);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	public boolean addWordQuality(DTOQuality quality) {
		try{
			getQualityResource().add(quality);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	public boolean addWordDifficulty(DTODifficulty difficulty) {
		try{
			getDifficultyResource().add(difficulty);
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
			List<DTOTranslation> translations = getTranslationResource().list(new ListTranslationParameters(language,
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
			List<DTOWord> words = getWordResource().list(new ListWordParameters(number)).getDTOWordList();
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

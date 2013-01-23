package com.wordwise.client;

import java.util.List;

import org.restlet.resource.ClientResource;

import com.wordwise.gameengine.ServerCommunication;
import com.wordwise.server.model.Difficulty;
import com.wordwise.server.model.Language;
import com.wordwise.server.model.Quality;
import com.wordwise.server.model.Rate;
import com.wordwise.server.model.Translation;
import com.wordwise.server.model.parameter.ListTranslationParameters;
import com.wordwise.server.resource.DifficultyResource;
import com.wordwise.server.resource.QualityResource;
import com.wordwise.server.resource.RateResource;
import com.wordwise.server.resource.TranslationResource;

public class RESTfullServerCommunication implements ServerCommunication
{
	private static final String BASE_CLIENT_URL = "http://192.168.112.1:8080/WordWiseServer/";
	
	private static final TranslationResource translationResource = getTranslationResource();
	private static final RateResource rateResource = getRateResource();
	private static final QualityResource qualityResource = getQualityResource();
	private static final DifficultyResource difficultyResource = getDifficultyResource();
	
	private static TranslationResource getTranslationResource()
	{
		ClientResource clientResource = new ClientResource(BASE_CLIENT_URL+TranslationResource.RESOURCE_NAME);
		
		clientResource.setRetryOnError(false);
		clientResource.getContext().getParameters().add("socketTimeout", "1000");
		
		return clientResource.wrap(TranslationResource.class);
	}
	
	private static RateResource getRateResource()
	{
		ClientResource clientResource = new ClientResource(BASE_CLIENT_URL+RateResource.RESOURCE_NAME);
		
		clientResource.setRetryOnError(false);
		clientResource.getContext().getParameters().add("socketTimeout", "1000");
		
		return clientResource.wrap(RateResource.class);
	}
	
	private static QualityResource getQualityResource()
	{
		ClientResource clientResource = new ClientResource(BASE_CLIENT_URL+QualityResource.RESOURCE_NAME);
		
		clientResource.setRetryOnError(false);
		clientResource.getContext().getParameters().add("socketTimeout", "1000");

		return clientResource.wrap(QualityResource.class);
	}
	
	private static DifficultyResource getDifficultyResource()
	{
		ClientResource clientResource = new ClientResource(BASE_CLIENT_URL+DifficultyResource.RESOURCE_NAME);
		
		clientResource.setRetryOnError(false);
		clientResource.getContext().getParameters().add("socketTimeout", "1000");

		return clientResource.wrap(DifficultyResource.class);
	}
	
	public boolean addTranslation(Translation translation)
	{
		translationResource.add(translation);
		return true;
	}
	
	public boolean rateTranslation(Rate rating)
	{
		rateResource.add(rating);
		return true;
	}
	public boolean addWordQualitiy(Quality quality)
	{
		qualityResource.add(quality);
		return true;
	}
	public boolean addWordDifficulty(Difficulty difficulty)
	{
		difficultyResource.add(difficulty);
		return true;
	}
	public List<Translation> listTranslations(Language language, Difficulty difficulty, int numberOfTranslations,	List<Translation> translationsAlreadyUsed)
	{
		return translationResource.list(new ListTranslationParameters(language, difficulty, numberOfTranslations, translationsAlreadyUsed));
	}

}

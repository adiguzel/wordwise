package com.wordwise.client;

import java.util.List;

import org.restlet.resource.ClientResource;

import com.wordwise.gameengine.ServerCommunication;
import com.wordwise.server.model.Difficulty;
import com.wordwise.server.model.Language;
import com.wordwise.server.model.Quality;
import com.wordwise.server.model.Rate;
import com.wordwise.server.model.Translation;
import com.wordwise.server.model.Word;
import com.wordwise.server.resource.QualityResource;
import com.wordwise.server.resource.RateResource;
import com.wordwise.server.resource.TranslationResource;
import com.wordwise.server.resource.WordResource;

public class RESTfullServerCommunication implements ServerCommunication
{
	private static final String BASE_CLIENT_URL = "http://localhost:8080/WordWiseServer/";
	private static final ClientResource WORD_CLIENT_RESOURCE = new ClientResource(BASE_CLIENT_URL+WordResource.RESOURCE_NAME);
	private static final ClientResource TRANSLATION_CLIENT_RESOURCE = new ClientResource(BASE_CLIENT_URL+TranslationResource.RESOURCE_NAME);
	private static final ClientResource RATE_CLIENT_RESOURCE = new ClientResource(BASE_CLIENT_URL+RateResource.RESOURCE_NAME);
	private static final ClientResource QUALITY_CLIENT_RESOURCE = new ClientResource(BASE_CLIENT_URL+QualityResource.RESOURCE_NAME);

	public boolean addWord(Word word)
	{
		WordResource resource = WORD_CLIENT_RESOURCE.wrap(WordResource.class);
		resource.add(word);
		return true;
	}

	public boolean rateWords(List<Rate> wordRatings)
	{
		RateResource resource = RATE_CLIENT_RESOURCE.wrap(RateResource.class);
		resource.add(wordRatings);
		return true;
	}

	public boolean rateTranslations(List<Quality> translationQualities)
	{
		QualityResource resource = QUALITY_CLIENT_RESOURCE.wrap(QualityResource.class);
		resource.add(translationQualities);
		return true;
	}

	public List<Word> listWords(Language lang, int difficulty)
	{
		WordResource resource = WORD_CLIENT_RESOURCE.wrap(WordResource.class);
		return resource.list(lang, Difficulty.EASY, 0);
	}

	public List<Word> listWords(Language lang, int difficulty, int number)
	{
		WordResource resource = WORD_CLIENT_RESOURCE.wrap(WordResource.class);
		return resource.list(lang, Difficulty.EASY, number);
	}

	public List<Word> listWords(Language lang)
	{
		WordResource resource = WORD_CLIENT_RESOURCE.wrap(WordResource.class);
		return resource.list(lang, null, 0);
	}

	public List<Translation> listTranslations(Language lang, int difficulty, int number)
	{
		TranslationResource resource = TRANSLATION_CLIENT_RESOURCE.wrap(TranslationResource.class);
		return resource.list(lang, null, number); 	
	}

}

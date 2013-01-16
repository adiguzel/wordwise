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
import com.wordwise.server.model.parameter.ListTranslationParameters;
import com.wordwise.server.model.parameter.ListWordParameters;
import com.wordwise.server.resource.QualityResource;
import com.wordwise.server.resource.RateResource;
import com.wordwise.server.resource.TranslationResource;
import com.wordwise.server.resource.WordResource;

public class RESTfullServerCommunication implements ServerCommunication
{
	private static final String BASE_CLIENT_URL = "http://192.168.112.1:8080/WordWiseServer/";
	
	private static final WordResource wordResource = new ClientResource(BASE_CLIENT_URL+WordResource.RESOURCE_NAME).wrap(WordResource.class);
	private static final TranslationResource translationResource = new ClientResource(BASE_CLIENT_URL+TranslationResource.RESOURCE_NAME).wrap(TranslationResource.class);
	private static final RateResource rateResource = new ClientResource(BASE_CLIENT_URL+RateResource.RESOURCE_NAME).wrap(RateResource.class);
	private static final QualityResource qualityResource = new ClientResource(BASE_CLIENT_URL+QualityResource.RESOURCE_NAME).wrap(QualityResource.class);

	public boolean addWord(Word word)
	{
		wordResource.add(word);
		return true;
	}

	public boolean rateWords(List<Rate> wordRatings)
	{
		rateResource.add(wordRatings);
		return true;
	}

	public boolean rateTranslations(List<Quality> translationQualities)
	{
		qualityResource.add(translationQualities);
		return true;
	}

	public List<Word> listWords(Language lang, int difficulty)
	{
		return wordResource.list(new ListWordParameters(lang, Difficulty.getByDifficulty(difficulty), 0, null));
	}

	public List<Word> listWords(Language lang, int difficulty, int number)
	{
		return wordResource.list(new ListWordParameters(lang, Difficulty.getByDifficulty(difficulty), number, null));
	}

	public List<Word> listWords(Language lang)
	{
		return wordResource.list(new ListWordParameters(lang, null, 0, null));
	}

	public List<Translation> listTranslations(Language lang, int difficulty, int number)
	{
		return translationResource.list(new ListTranslationParameters(lang, Difficulty.getByDifficulty(difficulty), number, null)); 	
	}

}

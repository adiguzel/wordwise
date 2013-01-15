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
import com.wordwise.server.resource.TranslationResource;
import com.wordwise.server.resource.WordResource;

public class RESTfullServerCommunication implements ServerCommunication
{
	private static final String BASE_CLIENT_URL = "http://localhost:8080/WordWiseServer/";
	private static final ClientResource WORD_CLIENT_RESOURCE = new ClientResource(BASE_CLIENT_URL+"words");
	private static final ClientResource TRANSLATION_CLIENT_RESOURCE = new ClientResource(BASE_CLIENT_URL+"translations");

	public boolean addWord(Word word)
	{
		WordResource resource = WORD_CLIENT_RESOURCE.wrap(WordResource.class);
		resource.add(word);
		return true;
	}

	public boolean rateWords(List<Rate> wordRatings)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean rateTranslations(List<Quality> translationQualities)
	{
		// TODO Auto-generated method stub
		return false;
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

package com.wordwise.client;

import java.util.List;

import org.restlet.resource.ClientResource;

import com.wordwise.gameengine.Language;
import com.wordwise.gameengine.ServerCommunication;
import com.wordwise.gameengine.dto.DTOTranslationRating;
import com.wordwise.gameengine.dto.DTOWord;
import com.wordwise.gameengine.dto.DTOWordRating;
import com.wordwise.server.model.Word;
import com.wordwise.server.resource.WordResource;

public class RESTfullServerCommunication implements ServerCommunication
{
	private static final String BASE_CLIENT_URL = "http://localhost:8080/WordWiseServer/";
	private static final ClientResource WORD_CLIENT_RESOURCE = new ClientResource(BASE_CLIENT_URL+"words"); 

	public boolean addWord(Word word)
	{
		WordResource resource = WORD_CLIENT_RESOURCE.wrap(WordResource.class);
		resource.addWord(word);
		return true;
	}

	public boolean rateWord(DTOWordRating wordRating)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean rateTranslations(DTOWord word, List<DTOTranslationRating> translationRatings)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public List<DTOWord> listWords(Language lang, int difficulty)
	{
		return new ClientResource(BASE_CLIENT_URL+"word").get(List.class);
	}

	public List<DTOWord> listWords(Language lang, int difficulty, int number)
	{
		return new ClientResource(BASE_CLIENT_URL+"word").get(List.class);
	}

	public List<DTOWord> listWords(Language lang)
	{
		return new ClientResource(BASE_CLIENT_URL+"word").get(List.class);
	}

}

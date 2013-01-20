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

	public List<Word> listWords(Language lang, int difficulty)
	{
		return wordResource.list(new ListWordParameters(Difficulty.getByDifficulty(difficulty), 0, null));
	}

	public List<Word> listWords(Language lang, int difficulty, int number)
	{
		return wordResource.list(new ListWordParameters(Difficulty.getByDifficulty(difficulty), number, null));
	}

	public List<Word> listWords(Language lang)
	{
		return wordResource.list(new ListWordParameters(null, 0, null));
	}

	public List<Translation> listTranslations(Language lang, Difficulty difficulty, int number, List<Translation> translationsAlreadyUsed)
	{
		return translationResource.list(new ListTranslationParameters(lang, difficulty, number, translationsAlreadyUsed)); 	
	}

	public boolean rateTranslations(List<Rate> translationRatings) {
		// TODO Auto-generated method stub
		rateResource.add(translationRatings);
		return true;
	}

	public boolean addWordQualities(List<Quality> wordQualities) {
		// TODO Auto-generated method stub
		qualityResource.add(wordQualities);
		return true;
	}

	public boolean addWordDifficulties(List<Difficulty> wordDifficulties) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Translation> listWordSpecificTranslations(Word word,Language language) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Rate> listRatingsForTranslation(Translation translation) {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.wordwise.loader;

import java.util.List;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.wordwise.client.RESTfullServerCommunication;
import com.wordwise.gameengine.GameManager;
import com.wordwise.model.GameManagerContainer;
import com.wordwise.server.dto.DTOWord;

/**
 * This class is loads words asynchronously from the server
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class WordLoader extends AsyncTaskLoader<List<DTOWord>> {
	private RESTfullServerCommunication serverComm;

	public WordLoader(Context context) {
		super(context);
		serverComm = new RESTfullServerCommunication(context);
	}

	@Override
	public List<DTOWord> loadInBackground() {
		GameManager gManager = GameManagerContainer.getGameManager();
		int wordsNeeded = gManager.NumberOfWordsNeeded();
		if (wordsNeeded == -1 || wordsNeeded == 0)
			return null;
		// get a request to get the words from the server
		List<DTOWord> words = serverComm.listWords(wordsNeeded);
		return words;
	}
}

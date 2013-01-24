package com.wordwise.loader;

import java.util.List;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.wordwise.client.RESTfullServerCommunication;
import com.wordwise.gameengine.GameManager;
import com.wordwise.model.GameManagerContainer;
import com.wordwise.server.model.Word;

public class WordLoader extends AsyncTaskLoader<List<Word>> {
	private RESTfullServerCommunication serverComm;

	public WordLoader(Context context) {
		super(context);
		serverComm = new RESTfullServerCommunication();
	}

	@Override
	public List<Word> loadInBackground() {
		GameManager gManager = GameManagerContainer.getGameManager();
		int wordsNeeded = gManager.NumberOfWordsNeeded();
		Log.v("wordsNeeded", "" + wordsNeeded);
		if (wordsNeeded == -1 || wordsNeeded == 0)
			return null;
		List<Word>  words = serverComm.listWords(wordsNeeded);
		Log.v("loadInBackground - words", "" + words);
		return words;
	}
}

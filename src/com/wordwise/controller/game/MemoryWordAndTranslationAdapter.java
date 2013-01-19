package com.wordwise.controller.game;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wordwise.gameengine.ServerCommunication;
import com.wordwise.model.Configuration;
import com.wordwise.server.model.Difficulty;
import com.wordwise.server.model.Translation;
import com.wordwise.server.model.Word;
import com.wordwise.util.game.MemoryViewFlipperUtil;
import com.wordwise.view.game.MemoryViewFlipper;

public class MemoryWordAndTranslationAdapter extends BaseAdapter {
	private Context mContext;
	// list of flippers which has word/translation on one side and no text on
	// the other
	private List<MemoryViewFlipper> flippers;
	private Configuration conf;
	private ServerCommunication serverComm;

	public MemoryWordAndTranslationAdapter(Context c) {
		super();
		mContext = c;
		flippers = MemoryViewFlipperUtil.getRandomViewFlipperList(
				generateTranslations(), mContext);
		conf = Configuration.getInstance(mContext);
	}

	public List<MemoryViewFlipper> getFlippers() {
		return flippers;
	}

	public int getCount() {
		return flippers.size();
	}

	public Object getItem(int index) {
		return null;
	}

	public long getItemId(int index) {
		return 0;
	}

	public View getView(int position, View view, ViewGroup parent) {
		return flippers.get(position);
	}

	// it will be used when the server is up and running to get the translations
	// from the server
	private List<Translation> getTranslationsFromServer() {
		return serverComm.listTranslations(conf.getLearningLanguage(),
				conf.getDifficulty(), getPairCount(), null);

	}

	private int getPairCount()
	{
		if (conf.getDifficulty() == Difficulty.EASY)
			return 3;
		if (conf.getDifficulty() == Difficulty.MEDIUM)
			return 6;
		if (conf.getDifficulty() == Difficulty.HARD)
			return 9;
		return 6;
	}

	private List<Translation> generateTranslations() {
		// TODO Get Translations from the server
		List<Translation> returnList = new ArrayList<Translation>();

		Translation translation = new Translation();
		translation.setTranslation("cadeira");
		Word word = new Word();
		word.setWord("chair");
		translation.setWord(word);
		returnList.add(translation);

		translation = new Translation();
		translation.setTranslation("porta");
		word = new Word();
		word.setWord("door");
		translation.setWord(word);
		returnList.add(translation);

		translation = new Translation();
		translation.setTranslation("mesa");
		word = new Word();
		word.setWord("table");
		translation.setWord(word);
		returnList.add(translation);

		translation = new Translation();
		translation.setTranslation("cama");
		word = new Word();
		word.setWord("bed");
		translation.setWord(word);
		returnList.add(translation);

		translation = new Translation();
		translation.setTranslation("chão");
		word = new Word();
		word.setWord("floor");
		translation.setWord(word);
		returnList.add(translation);

		translation = new Translation();
		translation.setTranslation("luz");
		word = new Word();
		word.setWord("light");
		translation.setWord(word);
		returnList.add(translation);
		
		return returnList;
	}

}

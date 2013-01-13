package com.wordwise.controller.game;

import java.util.ArrayList;
import java.util.List;

import com.wordwise.server.model.Translation;
import com.wordwise.server.model.Word;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class Words2TranslationAdapter extends BaseAdapter{
	private Context mContext;
	private List<TextView> translationViews;
	private List<Translation> translations;
	
	public Words2TranslationAdapter(Context c) {
		super();
		mContext = c;
		translations = generateTranslations();
	}
	
	public int getCount() {
		return translationViews.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		return translationViews.get(position);
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
		translation.setTranslation("luz");
		word = new Word();
		word.setWord("light");
		translation.setWord(word);
		returnList.add(translation);

		return returnList;
	}

	public List<Translation> getTranslations() {
		return translations;
	}
}

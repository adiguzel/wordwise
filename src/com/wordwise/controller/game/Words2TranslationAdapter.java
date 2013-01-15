package com.wordwise.controller.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wordwise.server.model.Translation;
import com.wordwise.server.model.Word;
import com.wordwise.util.game.Word2TranslationUtil;
import com.wordwise.view.game.Word2TranslationsTextView;

public class Words2TranslationAdapter extends BaseAdapter {
	private Context mContext;
	private List<Word2TranslationsTextView> translationViews = new ArrayList<Word2TranslationsTextView>();
	private List<Translation> translations;
	private int wordAmount = 4;

	public Words2TranslationAdapter(Context c, View.OnLongClickListener listener) {
		super();
		mContext = c;
		
		List<Translation> mixedGenerated = Word2TranslationUtil.mixTranslations(generateTranslations());
		translations = mixedGenerated.subList(0, wordAmount);
		//get a mixed(random) version of the list
		//translations = Word2TranslationUtil.mixTranslations(translationsForWords);
		for (Translation trans : Word2TranslationUtil.mixTranslations(mixedGenerated)) {
			Word2TranslationsTextView view = new Word2TranslationsTextView(
					mContext, trans, Word2TranslationsTextView.USE_TRANSLATION);
			view.setOnLongClickListener(listener);
			translationViews.add(view);
		}
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
		
		translation = new Translation();
		translation.setTranslation("computador");
		word = new Word();
		word.setWord("computer");
		translation.setWord(word);
		returnList.add(translation);
		
		translation = new Translation();
		translation.setTranslation("garfo");
		word = new Word();
		word.setWord("fork");
		translation.setWord(word);
		returnList.add(translation);

		return returnList;
	}

	public List<Translation> getTranslations() {
		return translations;
	}

	public Map<Translation, Word2TranslationsTextView> toTranslationToViewMap() {
		Map<Translation, Word2TranslationsTextView> translationToTranslationView = new HashMap<Translation, Word2TranslationsTextView>();
		for (Word2TranslationsTextView view : translationViews) {
			translationToTranslationView.put(view.getTranslation(), view);
		}
		return translationToTranslationView;
	}
	
	public void removeViewListeners(){
		for(Word2TranslationsTextView v : translationViews){
			v.setOnLongClickListener(null);
		}
	}
}

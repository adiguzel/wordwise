package com.wordwise.controller.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wordwise.server.dto.DTOTranslation;
import com.wordwise.util.game.Word2TranslationUtil;
import com.wordwise.view.game.Word2TranslationsTextView;

public class Words2TranslationAdapter extends BaseAdapter {
	private Context mContext;
	private List<Word2TranslationsTextView> translationViews = new ArrayList<Word2TranslationsTextView>();
	private List<DTOTranslation> translations;
	private int wordAmount = 4;

	public Words2TranslationAdapter(Context c, View.OnLongClickListener listener, List<DTOTranslation> downloadedTranslations) {
		super();
		mContext = c;
		
		List<DTOTranslation> mixed = Word2TranslationUtil.mixTranslations(downloadedTranslations);
		translations = mixed.subList(0, wordAmount);
		//get a mixed(random) version of the list
		//translations = Word2TranslationUtil.mixTranslations(translationsForWords);
		for (DTOTranslation trans : Word2TranslationUtil.mixTranslations(mixed)) {
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

	public List<DTOTranslation> getTranslations() {
		return translations;
	}

	public Map<DTOTranslation, Word2TranslationsTextView> toTranslationToViewMap() {
		Map<DTOTranslation, Word2TranslationsTextView> translationToTranslationView = new HashMap<DTOTranslation, Word2TranslationsTextView>();
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

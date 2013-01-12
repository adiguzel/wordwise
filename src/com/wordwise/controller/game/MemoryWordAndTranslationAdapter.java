package com.wordwise.controller.game;

import java.util.ArrayList;
import java.util.List;

import com.wordwise.server.model.Translation;
import com.wordwise.server.model.Word;
import com.wordwise.util.game.MemoryViewFlipperUtil;
import com.wordwise.view.game.MemoryViewFlipper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ViewFlipper;

public class MemoryWordAndTranslationAdapter extends BaseAdapter {
	private int processedCount = 0;
	private Context mContext;
	List<MemoryViewFlipper> flippers;
	//private List<Translation> translations;
	
	public MemoryWordAndTranslationAdapter(Context c) {
		mContext = c;
		flippers = MemoryViewFlipperUtil.getRandomViewFlipperList(generateTranslations(), mContext);
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return flippers.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewFlipper flipper;
		if (view == null) { // if it's not recycled, initialize some								// attributes
			flipper = flippers.get(position);
		} else {
			flipper = (ViewFlipper) view;
		}
		return flipper;
	}
	
	
	private List<Translation> generateTranslations()
	{
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

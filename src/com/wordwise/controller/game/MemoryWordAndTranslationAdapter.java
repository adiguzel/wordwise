package com.wordwise.controller.game;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wordwise.server.model.Translation;
import com.wordwise.util.game.MemoryViewFlipperUtil;
import com.wordwise.view.game.MemoryViewFlipper;

public class MemoryWordAndTranslationAdapter extends BaseAdapter {
	private Context mContext;
	// list of flippers which has word/translation on one side and no text on
	// the other
	private List<MemoryViewFlipper> flippers;

	public MemoryWordAndTranslationAdapter(Context c,
			List<Translation> translations) {
		super();
		mContext = c;
		flippers = MemoryViewFlipperUtil.getRandomViewFlipperList(translations,
				mContext);
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
}

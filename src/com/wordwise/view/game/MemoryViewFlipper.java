package com.wordwise.view.game;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.wordwise.server.model.Translation;
import com.wordwise.util.game.MemoryViewFlipperUtil;

public class MemoryViewFlipper extends ViewFlipper {
	//translation that is associated with that view
	public static boolean USE_WORD = true;
	public static boolean USE_TRANSLATION = false;

	private Translation translation;

	
	public MemoryViewFlipper(Context context) {
		super(context);
	}
	
	public MemoryViewFlipper(Context context,Translation translation, boolean isWord) {
		this(context);
		TextView face = new TextView(context);
		TextView bottom = new TextView(context);
		String text = "";
		
		if(isWord == USE_WORD)
			text = translation.getWord().getWord();
		else // Use translation
			text = translation.getTranslation();
		
		bottom.setText(text);
		bottom.setTextSize(16);
		bottom.setGravity(Gravity.CENTER_VERTICAL
				| Gravity.CENTER_HORIZONTAL);
		//arrange the initial states
		MemoryViewFlipperUtil.changeState(face, MemoryViewFlipperUtil.STATE_INITIAL);
		MemoryViewFlipperUtil.changeState(bottom, MemoryViewFlipperUtil.STATE_INITIAL);
		addView(face);
		addView(bottom);
		this.translation = translation;
	}

	public Translation getTranslation() {
		return translation;
	}
	
	public boolean matches(MemoryViewFlipper aFlipper){
		//different objects, sharing the same translation means a memory game match
		return (!this.equals(aFlipper)) && (translation.equals(aFlipper.getTranslation()));	
	}
	
}

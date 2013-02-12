package com.wordwise.view.game;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.wordwise.server.dto.DTOTranslation;
import com.wordwise.util.game.MemoryViewFlipperUtil;

/**
 * This class represents a flipper which has two sides, one side being an
 * English word and other side being its translation.
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class MemoryViewFlipper extends ViewFlipper {
	// translation that is associated with that view
	public static boolean USE_WORD = true;
	public static boolean USE_TRANSLATION = false;
	// translation that is matched with that flipper
	private DTOTranslation translation;
	private boolean isWord;

	public MemoryViewFlipper(Context context) {
		super(context);
	}

	public MemoryViewFlipper(Context context, DTOTranslation translation,
			boolean isWord) {
		this(context);
		this.isWord = isWord;
		TextView face = new TextView(context);
		TextView bottom = new TextView(context);
		String text = "";

		if (isWord == USE_WORD)
			text = translation.getWord().getWord();
		else
			// Use translation
			text = translation.getTranslation();

		bottom.setText(text);
		bottom.setTextSize(16);
		bottom.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		// setup the initial states
		MemoryViewFlipperUtil.changeState(face,
				MemoryViewFlipperUtil.STATE_INITIAL);
		MemoryViewFlipperUtil.changeState(bottom,
				MemoryViewFlipperUtil.STATE_INITIAL);
		// add the children views representing both face and bottom states
		addView(face);
		addView(bottom);
		this.translation = translation;
	}

	public DTOTranslation getTranslation() {
		return translation;
	}

	public boolean matches(MemoryViewFlipper aFlipper) {
		// different objects, sharing the same translation means a memory game
		// match
		// the same object
		return (!this.equals(aFlipper))
		// one is different and the other is a translation(xor operation)
				&& ((isWord && !aFlipper.isWord) || (!isWord && aFlipper.isWord))
				// and translations match as well
				&& (translation.getTranslation().equalsIgnoreCase(aFlipper
						.getTranslation().getTranslation()));
	}

	public boolean isWord() {
		return isWord;
	}
}

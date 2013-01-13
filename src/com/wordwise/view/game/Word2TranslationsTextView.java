package com.wordwise.view.game;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wordwise.R;
import com.wordwise.server.model.Translation;

public class Word2TranslationsTextView extends TextView {
	//translation that is associated with that view
	public static boolean USE_WORD = true;
	public static boolean USE_TRANSLATION = false;

	private Translation translation; 
	
	public Word2TranslationsTextView(Context context) {
		super(context);
	}
	
	public Word2TranslationsTextView(Context context,Translation translation, boolean isWord,View.OnLongClickListener longClickListener ){
		super(context);
		String text = "";
		
		if(isWord == USE_WORD)
			text = translation.getWord().getWord();
		else // Use translation
			text = translation.getTranslation();
		
		setText(text);
		setOnLongClickListener(longClickListener);
		setTextSize(16);
		// textView.setSingleLine(false);
		setGravity(Gravity.CENTER_VERTICAL
				| Gravity.CENTER_HORIZONTAL);
		// we are using the deprecated method instead of setBackground
		// because it is introduced in API level 16
		setBackgroundDrawable(getResources().getDrawable(
				R.drawable.word2translations_translations));
		this.translation = translation;
	}
}

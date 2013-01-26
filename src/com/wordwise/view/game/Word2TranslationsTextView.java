package com.wordwise.view.game;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.wordwise.R;
import com.wordwise.server.dto.DTOTranslation;

public class Word2TranslationsTextView extends TextView {
	//translation that is associated with that view
	public static boolean USE_WORD = true;
	public static boolean USE_TRANSLATION = false;

	private DTOTranslation translation; 
	
	public Word2TranslationsTextView(Context context) {
		super(context);
	}
	
	public Word2TranslationsTextView(Context context, AttributeSet arg1){
		super(context, arg1);
	}
	
	public Word2TranslationsTextView(Context context, AttributeSet arg1, int arg2){
		super(context, arg1, arg2);
	}
	

	public void init(Context context, DTOTranslation translation, boolean isWord){
		String text = "";
		
		if(isWord == USE_WORD){
			text = translation.getWord().getWord();
			setBackgroundDrawable(getResources().getDrawable(
					R.drawable.word2translations_word));
		}	
		else{ // Use translation
			text = translation.getTranslation();
			setBackgroundDrawable(getResources().getDrawable(
					R.drawable.word2translations_translations));
		}
		
		setText(text);
		setTextSize(16);
		// textView.setSingleLine(false);
		setGravity(Gravity.CENTER_VERTICAL
				| Gravity.CENTER_HORIZONTAL);
		// we are using the deprecated method instead of setBackground
		// because it is introduced in API level 16

		this.translation = translation;
	}

	
	public Word2TranslationsTextView(Context context, DTOTranslation translation, boolean isWord){
		super(context);
		init(context, translation, isWord);
	}

	public DTOTranslation getTranslation() {
		return translation;
	}
}

package com.wordwise.view.game;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewAnimator;
import android.widget.ViewFlipper;

import com.tekle.oss.android.animation.AnimationFactory;
import com.tekle.oss.android.animation.AnimationFactory.FlipDirection;
import com.wordwise.R;
import com.wordwise.server.model.Translation;

public class MemoryViewFlipper extends ViewFlipper {
	//translation that is associated with that view
	public static boolean USE_WORD = true;
	public static boolean USE_TRANSLATION = false;
	
	private final int STATE_INITIAL = 0;
	private final int STATE_MATCH_SUCCESS = 1;
	private final int STATE_MATCH_FAIL = 2;
	
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
		else
			text = translation.getTranslation();
		
		bottom.setTag(text);
		bottom.setText(text);
		bottom.setTextSize(16);
		// textView.setSingleLine(false);
		bottom.setGravity(Gravity.CENTER_VERTICAL
				| Gravity.CENTER_HORIZONTAL);
		changeState(face, STATE_INITIAL);
		changeState(bottom, STATE_INITIAL);
		addView(face);
		addView(bottom);
		this.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Retrieve the values
				flip(v);
			}
		});
		this.translation = translation;
	}

	public Translation getTranslation() {
		return translation;
	}
	
	public boolean matches(MemoryViewFlipper aFlipper){
		//different objects, sharing the same translation means a memory game match
		return (!this.equals(aFlipper)) && (translation.equals(aFlipper.getTranslation()));	
	}
	
	@SuppressWarnings("deprecation")
	private void changeState(View v, int state) {
		switch (state) {
			case STATE_INITIAL :
				v.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.memory_square_grid_item));
				break;
			case STATE_MATCH_SUCCESS :
				v.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.memory_square_grid_item_success));
				break;
			case STATE_MATCH_FAIL :
				break;
		}
		v.invalidate();
	}
	
	private void flip( View viewFlipper) {
		AnimationFactory.flipTransition((ViewAnimator) viewFlipper, FlipDirection.LEFT_RIGHT);
	}
	
}

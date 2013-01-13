package com.wordwise.controller.game;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

import com.wordwise.model.game.MemoryFlipState;

public class MemoryAnimationListener implements AnimationListener {

	public static final boolean REVEAL = true;
	public static final boolean HIDE = false;

	private boolean action = REVEAL;
	private TextView view;
	private MemoryFlipState flipState;

	// so that it can't be initiated with that constructor
	@SuppressWarnings("unused")
	private MemoryAnimationListener() {

	}

	public MemoryAnimationListener(TextView view, boolean action,
			MemoryFlipState flipState) {
		this.view = view;
		this.action = action;
		this.flipState = flipState;
	}

	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		onEnd();
	}

	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		onStart();
	}

	private void onEnd() {

		if (action == REVEAL) {
			if (!flipState.flipExist()) {
				flipState.setFirstFlipped(view);
			}
		} else
			flipState.setFirstFlipped(null);

	}

	private void onStart() {

	}

}

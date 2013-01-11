package com.wordwise.controller.game;

import android.animation.Animator;
import android.util.Log;
import android.widget.TextView;

import com.wordwise.model.game.MemoryFlipState;

public class MemoryAnimationListener implements Animator.AnimatorListener {

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

	public void onAnimationCancel(Animator animation) {
		// TODO Auto-generated method stub

	}

	public void onAnimationEnd(Animator animation) {
		// TODO Auto-generated method stub
		onEnd();
	}

	public void onAnimationRepeat(Animator animation) {
		// TODO Auto-generated method stub

	}

	public void onAnimationStart(Animator animation) {
		// TODO Auto-generated method stub
		onStart();
	}

	private void onEnd() {
		if (action == REVEAL) {
			String tag = (String) view.getTag();
			view.setText(tag);
			if (!flipState.flipExist())
				flipState.setFirstFlipped(view);
			// flipLock.unlock();
			Log.v("app", "Lock released");
		} else {
			view.setText("");
			flipState.setFirstFlipped(null);
		}
	}
	private void onStart() {

	}
}

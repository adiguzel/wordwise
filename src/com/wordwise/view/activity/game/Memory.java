package com.wordwise.view.activity.game;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.tekle.oss.android.animation.AnimationFactory;
import com.wordwise.R;
import com.wordwise.R.color;
import com.wordwise.controller.game.MemoryAnimationListener;
import com.wordwise.gameengine.Game;
import com.wordwise.model.game.MemoryFlipState;
import com.wordwise.view.activity.WordwiseGameActivity;

public class Memory extends WordwiseGameActivity implements Game {
	private final int STATE_INITIAL = 0;
	private final int STATE_MATCH_SUCCESS = 1;
	private final int STATE_MATCH_FAIL = 2;

	private Button continueButton;
	private final int IN_PROGRESS = 0;
	private final int FINISHED = 1;
	private int gameState = IN_PROGRESS;
	private MemoryFlipState flipState;

	private int pairCount = 6;
	
	@Override
	public void performOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.memory);
		this.onGameInit();
		this.onGameStart();
	}

	private boolean isFinished() {
		return pairCount == 0;
	}

	private void onGameFinish() {
		// TODO show necessary dialogs about game end
		continueButton.setEnabled(true);
	}
	
	private void checkOrAdjustGameState(){
		pairCount --;
		if(isFinished()){
			onGameFinish();
		}
	}

	public void onGameStart() {
		// TODO Auto-generated method stub

	}

	public void onGameStop() {
		// TODO Auto-generated method stub

	}

	public void onGamePause() {
		// TODO Auto-generated method stub

	}

	public void onGameInit() {
		// TODO Auto-generated method stub
		continueButton = (Button) findViewById(R.id.continueButton);
		initMemoryGrid();
		flipState = new MemoryFlipState();
	}

	private void initMemoryGrid() {
		GridView translationsGrid = (GridView) findViewById(R.id.memoryGrid);
		translationsGrid.setAdapter(new WordAndTranslationAdapter(this));
	}

	// called by continue button to finish the game and continue from the next
	// game
	public void finishGame(View v) {

	}

	private boolean matches(TextView v1, TextView v2) {
		//condition : they are not the same object but their tags are the same
		return (v1 != v2)
				&& ((String) v1.getTag()).equals((String) v2.getTag());
		
	}

	private void flipFaceUp(final TextView v) {

		flip(v, new MemoryAnimationListener(v, MemoryAnimationListener.REVEAL,
				flipState));
	}

	private void flipFaceDown(final TextView v) {
		flip(v, new MemoryAnimationListener(v, MemoryAnimationListener.HIDE,
				flipState));
	}

	// applies a flipping animation to a given view
	private void flip(final View v, AnimationListener animListener) {
	
		Animation anim = AnimationUtils.loadAnimation(
				this, R.animator.flipping);
		
		anim.setDuration(1000);
		anim.setAnimationListener(animListener);
		//anim.start();
		v.startAnimation(anim);
		v.postDelayed(new Runnable() {
			public void run() {
				if (flipState.flipExist()) {
					TextView firstFlipped = flipState.getFirstFlipped();
					if (matches(flipState.getFirstFlipped(), (TextView) v)) {
						changeState(firstFlipped, STATE_MATCH_SUCCESS);
						changeState(v, STATE_MATCH_SUCCESS);
						v.setEnabled(false);
						firstFlipped.setEnabled(false);
						flipState.setFirstFlipped(null);
				checkOrAdjustGameState();
						
						/*
						 * try { Thread.sleep(1000); } catch
						 * (InterruptedException e) { }
						 */

						// firstFlipped.setVisibility(View.INVISIBLE);
						// v.setVisibility(View.INVISIBLE);

					} else if(firstFlipped != (TextView) v){
						Log.v("flip", "no success");
						flipFaceDown(firstFlipped);
						flipFaceDown((TextView) v);
						flipState.setFirstFlipped(null);
					}
				}

			}
		}, 1200);
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

	// TODO use real translations of the words that we got from the server
	private class WordAndTranslationAdapter extends BaseAdapter {
		private Context mContext;

		public WordAndTranslationAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return mThumbIds.length;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		@SuppressWarnings("deprecation")
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView;
			if (convertView == null) { // if it's not recycled, initialize some
										// attributes
				textView = new TextView(mContext);
			} else {
				textView = (TextView) convertView;
			}

			textView.setTag(mThumbIds[position]);
			textView.setTextSize(16);
			// textView.setSingleLine(false);
			textView.setGravity(Gravity.CENTER_VERTICAL
					| Gravity.CENTER_HORIZONTAL);
			// we are using the deprecated method instead of setBackground
			// because it is introduced in API level 16
			changeState(textView, STATE_INITIAL);
			textView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// Retrieve the values
					flipFaceUp((TextView) v);
				}
			});

			return textView;
		}

		// TODO change this to random mix of the translations of the words from
		// server
		// TODO possibly change this to an ArrayList
		private String[] mThumbIds = {"besuchen", "besuchen", "Komputer",
				"Komputer", "Test", "Test2", "Test", "Test2", "Test", "Test2",
				"Test", "Test2"};

	}

}

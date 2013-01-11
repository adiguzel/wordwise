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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.wordwise.R;
import com.wordwise.R.color;
import com.wordwise.controller.game.MemoryAnimationListener;
import com.wordwise.gameengine.Game;
import com.wordwise.model.game.Lock;
import com.wordwise.model.game.MemoryFlipState;
import com.wordwise.view.activity.WordwiseGameActivity;

public class Memory extends WordwiseGameActivity implements Game {

	private Button continueButton;
	private final int IN_PROGRESS = 0;
	private final int FINISHED = 1;
	private int gameState = IN_PROGRESS;
	private MemoryFlipState flipState;
	private TextView firstFlipped = null;
	private Lock flipLock = new Lock();

	@Override
	public void performOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.memory);
		this.onGameInit();
		this.onGameStart();
	}
	
	private boolean isFinished(){
		return gameState == FINISHED;
	}
	
	private void onGameFinish(){
		//TODO show necessary dialogs about game end
		
		continueButton.setEnabled(true);
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
		return ((String) v1.getTag()).equals((String) v2.getTag());
	}

	private void flipFaceUp(final TextView v) {
				
		Runnable r = new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				flip(v, new MemoryAnimationListener(v, MemoryAnimationListener.REVEAL, flipState));
			}
		};
		r .run();

		Log.v("Lock","is " + flipLock.isLocked() );
		try {
			flipLock.lock();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*Log.v("Alt LOCk","is " + flipLock.isLocked() );
		if (firstFlipped != null) {
			if (matches(firstFlipped, v)) {
				firstFlipped
						.setBackgroundColor(color.wordwise_main_green_disabled_end);
				firstFlipped.invalidate();
				v.setBackgroundColor(color.wordwise_main_green_disabled_end);
				v.invalidate();
				 try { Thread.sleep(1000); } catch
				 (InterruptedException e) { }

				 firstFlipped.setVisibility(View.INVISIBLE);
				 v.setVisibility(View.INVISIBLE);

			} else {
				flipFaceDown(firstFlipped);
				flipFaceDown(v);
				firstFlipped = null;
			}
		}*/
	}

	private void flipFaceDown(final TextView v) {
		flip(v, new MemoryAnimationListener(v, MemoryAnimationListener.HIDE, flipState));
	}

	// applies a flipping animation to a given view
	private void flip(View v, Animator.AnimatorListener animListener) {
		try {
			flipLock.lock();
			Log.v("app", "Lock acquired");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(
				this, R.animator.flipping);
		anim.setTarget(v);
		anim.setDuration(1000);
		anim.addListener(animListener);
		anim.start();
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
			textView.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.memory_square_grid_item));
			textView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// Retrieve the values

					flipFaceUp((TextView) v);
					try {
						flipLock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Log.v("flip", "flip completed");
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

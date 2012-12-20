package com.wordwise.activity.game;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.wordwise.R;
import com.wordwise.gameengine.Game;

public class Memory extends Activity implements Game {
	
	private Button continueButton;
	private final int IN_PROGRESS = 0;
	private final int FINISHED = 1;
	private int gameState = IN_PROGRESS; 


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.memory);
		this.init();
		this.start();
	}

	public void start() {
		// TODO Auto-generated method stub

	}

	public void stop() {
		// TODO Auto-generated method stub

	}

	public void pause() {
		// TODO Auto-generated method stub

	}

	public void init() {
		// TODO Auto-generated method stub
		continueButton = (Button) findViewById(R.id.continueButton);
		initMemoryGrid();
	}
	
	private void initMemoryGrid(){
		GridView translationsGrid = (GridView) findViewById(R.id.memoryGrid);
		translationsGrid.setAdapter(new WordAndTranslationAdapter(this));
	}

	// called by quit button to quit the game
	public void quit(View v) {

	}

	// called by continue button to finish the game and continue from the next
	// game
	public void finishGame(View v) {

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

			//textView.setText(mThumbIds[position]);
			textView.setTag(mThumbIds[position]);
			//textView.setOnLongClickListener(new TranslationLongClickListener());
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
					// TODO Auto-generated method stub
					ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(mContext, R.animator.flipping); 
					anim.setTarget(v);
					anim.setDuration(1000);
					anim.start();
				}
			});
			
			return textView;
		}

		// TODO change this to random mix of the translations of the words from
		// server
		// TODO possibly change this to an ArrayList
		private String[] mThumbIds = { "besuchen", "rauchen", "Komputer",
				"Schnee", "Test", "Test2", "Test", "Test2", "Test", "Test2", "Test", "Test2"};

	}

}

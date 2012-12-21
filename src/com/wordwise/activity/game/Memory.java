package com.wordwise.activity.game;

import java.util.HashSet;
import java.util.Set;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.wordwise.gameengine.Game;

public class Memory extends Activity implements Game {
	
	private Button continueButton;
	private final int IN_PROGRESS = 0;
	private final int FINISHED = 1;
	private int gameState = IN_PROGRESS; 
	private TextView firstFlipped = null;
	SharedPreferences SP;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.memory);
		this.init();
		this.start();
		SP = PreferenceManager.getDefaultSharedPreferences(this);
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
	
	private void flipFaceUp(TextView v){
		
		String tag = (String) v.getTag();
		v.setText(tag);
		flip(v);
		if(firstFlipped  == null){
			firstFlipped = v;
		}
		else{ 
			if(((String)firstFlipped.getTag()).equals((String)v.getTag())){
				firstFlipped.setBackgroundColor(color.wordwise_main_green_disabled_start);
				v.setBackgroundColor(color.wordwise_main_green_disabled_start);
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				firstFlipped.setVisibility(View.INVISIBLE);
				v.setVisibility(View.INVISIBLE);
				
			}
			else{
				flipFaceDown(firstFlipped);
				flipFaceDown(v);
			}
			firstFlipped = null;
		}
	}
	
	private void flipFaceDown(TextView v){
		v.setText("");
		flip(v);
	}
	
	private void flip(TextView v){
		ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.flipping); 
		anim.setTarget(v);
		anim.setDuration(1000);
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
			final int positon = position;
			textView.setOnClickListener(new View.OnClickListener() {
			
				final int asd = positon;
				public void onClick(View v) {
					//Retrieve the values
					Set<String> set = new HashSet<String>();
					set.add("AM");
					Log.v("AL BAKALIM>>> ",  (SP.getString("learning_language", "AM")).toString());
					flipFaceUp((TextView)v);
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

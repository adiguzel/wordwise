package com.wordwise.view.activity.game;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.wordwise.R;
import com.wordwise.gameengine.Game;
import com.wordwise.gameengine.GameManager;
import com.wordwise.model.GameManagerContainer;
import com.wordwise.server.model.Translation;
import com.wordwise.util.WordwiseUtils;

public class LetterBox extends Activity implements Game {
	private LetterBoxManager letterBoxManager = new LetterBoxManager(this);
	private GameManager gameManager;
	private Button continueButton;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.letterbox);

		this.init();
		this.start();
	}
	
	@Override
	public void onBackPressed() {
		WordwiseUtils.makeQuitGameDialog(this);
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

	public void quit(View view) {
	}
	

	public void continueNextGame(View v) {
		gameManager.endGame();
	}
	
	public void init() {
		initWordsGrid();
		initLettersGrid();
		gameManager = GameManagerContainer.getGameManager();
		continueButton = (Button) findViewById(R.id.continueButton);
	}

	private void initLettersGrid() {
		final GridView lettersGrid = (GridView) findViewById(R.id.gridViewLetters);
		lettersGrid.setAdapter(new LettersAdapter(this, letterBoxManager
				.getLetters()));

		lettersGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				letterBoxManager.onLetterClick((TextView) v, position);
			}
		});
	}

	public void markTranslationAsFound(Translation translation) {
		GridView wordsGrid = (GridView) findViewById(R.id.gridViewWords);
		TextView textView = null;
		for (int i = 0; i < wordsGrid.getChildCount(); i++) {
			textView = (TextView) wordsGrid.getChildAt(i);
			if (textView.getText().toString()
					.equalsIgnoreCase(translation.getTranslation())) {
				break;
			}
		}
		textView.setPaintFlags(textView.getPaintFlags()
				| Paint.STRIKE_THRU_TEXT_FLAG);
	}
	
	public void onGameOver(){
		//TODO show information screen that the game is over
		
		//set the continue button enabled
		continueButton.setEnabled(true);
	}
	
	private void initWordsGrid() {
		final GridView wordsGrid = (GridView) findViewById(R.id.gridViewWords);
		wordsGrid.setAdapter(new WordsAdapter(this, letterBoxManager
				.getTranslations()));
		final Activity activity = this;
		wordsGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Translation translation = (Translation) wordsGrid.getAdapter()
						.getItem(position);
			WordwiseUtils.makeCustomToast(activity, translation.getWord().getWord());
				/*Toast.makeText(LetterBox.this, translation.getWord().getWord(),
						Toast.LENGTH_SHORT).show();*/
			}
		});
	}

	private class WordsAdapter extends BaseAdapter {
		private Context mContext;
		List<Translation> translations;

		public WordsAdapter(Context c, List<Translation> translations) {
			mContext = c;
			this.translations = translations;
		}

		public int getCount() {
			return translations.size();
		}

		public Object getItem(int position) {
			return translations.get(position);
		}

		public long getItemId(int position) {
			return 0;
		}

		// create a new TextView for each item referenced by the Adapter
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView;
			if (convertView == null) { // if it's not recycled, initialize some
										// attributes
				textView = new TextView(mContext);
			} else {
				textView = (TextView) convertView;
			}

			textView.setText(translations.get(position).getTranslation());
			textView.setTextSize(18);
			textView.setGravity(Gravity.CENTER);
			return textView;
		}
	}

	private class LettersAdapter extends BaseAdapter {
		private Context mContext;
		List<String> letters;

		public LettersAdapter(Context c, List<String> letters) {
			mContext = c;
			this.letters = letters;
		}

		public int getCount() {
			return letters.size();
		}

		public Object getItem(int position) {
			return letters.get(position);
		}

		public long getItemId(int position) {
			return 0;
		}

		// create a new TextView for each item referenced by the Adapter
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView;
			if (convertView == null) { // if it's not recycled, initialize some
										// attributes
				textView = new TextView(mContext);
			} else {
				textView = (TextView) convertView;
			}

			textView.setText(letters.get(position));
			textView.setTextSize(25);
			textView.setGravity(Gravity.CENTER);
			return textView;
		}
	}
}

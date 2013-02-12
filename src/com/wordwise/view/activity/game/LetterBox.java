package com.wordwise.view.activity.game;

import java.util.List;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.wordwise.R;
import com.wordwise.controller.game.LetterBoxManager;
import com.wordwise.gameengine.level.GameFinishPromotion;
import com.wordwise.gameengine.level.Promotion;
import com.wordwise.server.dto.DTODifficulty;
import com.wordwise.server.dto.DTOTranslation;
import com.wordwise.util.LoaderHelper.LoaderType;
import com.wordwise.util.WordwiseUtils;
import com.wordwise.util.game.LetterBoxPositionUtils;
import com.wordwise.view.activity.WordwiseGameActivity;

/**
 * The class that defines the implementation for Letterbox game
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class LetterBox extends WordwiseGameActivity
		implements
			LoaderCallbacks<List<DTOTranslation>> {
	private LetterBoxManager letterBoxManager;
	private Button continueButton;
	private List<DTOTranslation> translations;
	@Override
	public void performOnCreate(Bundle savedInstanceState) {
		// start the loader
		loaderHelper.initLoader(this, LoaderType.TRANSLATION_LOADER);
	}

	public void onGameStart() {
	}

	public void onGameStop() {
	}

	public void onGamePause() {
	}

	public void onGameInit() {
		letterBoxManager = new LetterBoxManager(this, translations);
		initWordsGrid();
		initLettersGrid();
		continueButton = (Button) findViewById(R.id.continueButton);
	}

	/*
	 * initializes the letterbox grid with the real translation data
	 */
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

	public void markTranslationAsFound(DTOTranslation translation) {
		GridView wordsGrid = (GridView) findViewById(R.id.gridViewWords);
		TextView textView = null;
		for (int i = 0; i < wordsGrid.getChildCount(); i++) {
			textView = (TextView) wordsGrid.getChildAt(i);
			if (textView.getText().toString()
					.equalsIgnoreCase(translation.getWord().getWord())) {
				break;
			}
		}
		textView.setPaintFlags(textView.getPaintFlags()
				| Paint.STRIKE_THRU_TEXT_FLAG);
	}

	public void onGameEnd() {
		super.onGameEnd();
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
				DTOTranslation translation = (DTOTranslation) wordsGrid
						.getAdapter().getItem(position);
				WordwiseUtils.makeCustomToast(activity,
						translation.getTranslation());
			}
		});
	}

	private class WordsAdapter extends BaseAdapter {
		private Context mContext;
		List<DTOTranslation> translations;

		public WordsAdapter(Context c, List<DTOTranslation> translations) {
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

			textView.setText(translations.get(position).getWord().getWord());
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

	public int numberOfTranslationsNeeded(DTODifficulty difficulty) {
		if (difficulty == DTODifficulty.EASY)
			return 6;
		else if (difficulty == DTODifficulty.MEDIUM)
			return 6;
		else if (difficulty == DTODifficulty.HARD)
			return 6;
		else
			return -1;
	}

	public boolean canUse(DTOTranslation translation) {
		if (translation.getTranslation().trim().contains(" ")
				|| (translation.getTranslation().length() > LetterBoxPositionUtils
						.getGridSize())) {
			return false;
		}
		return true;
	}

	public int numberOfWordsNeeded(DTODifficulty difficulty) {
		return 0;
	}

	public void retry(View v) {
		loaderHelper.restartLoader(this, LoaderType.TRANSLATION_LOADER);
	}

	public List<DTOTranslation> getTranslations() {
		return translations;
	}

	@SuppressWarnings("unchecked")
	public Loader<List<DTOTranslation>> onCreateLoader(int id, Bundle args) {
		return (Loader<List<DTOTranslation>>) loaderHelper.onLoadCreated(this,
				LoaderType.TRANSLATION_LOADER);
	}

	public void onLoadFinished(Loader<List<DTOTranslation>> arg0,
			List<DTOTranslation> translations) {
		if (loaderHelper.translationLoadSuccessfulOrShowError(this,
				translations)) {
			this.translations = translations;
			initLayout();
			this.onGameStart();
		}
	}

	public void onLoaderReset(Loader<List<DTOTranslation>> arg0) {
		loaderHelper.onLoaderReset(this);
	}

	@Override
	protected View gameContent() {
		return getLayoutInflater().inflate(R.layout.game_letterbox, null);
	}

	@Override
	protected boolean isRealGame() {
		return true;
	}

	@Override
	public Promotion getPromotion() {
		return new GameFinishPromotion();
	}
}

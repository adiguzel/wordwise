package com.wordwise.view.activity;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;
import android.widget.ViewFlipper;

import com.tekle.oss.android.animation.AnimationFactory;
import com.tekle.oss.android.animation.AnimationFactory.FlipDirection;
import com.wordwise.R;
import com.wordwise.controller.PreferencesIOManager;
import com.wordwise.gameengine.Game;
import com.wordwise.model.GameManagerContainer;
import com.wordwise.model.IGameView;
import com.wordwise.server.dto.DTOLanguage;
import com.wordwise.server.dto.DTOTranslation;
import com.wordwise.util.LoaderHelper;
import com.wordwise.util.WordwiseUtils;

public abstract class WordwiseGameActivity extends Activity
		implements
			IGameView,
			Game,
			OnInitListener {

	protected PreferencesIOManager prefIOManager;
	// flag to determine if activity should be ended and go back to the previous
	// screen
	protected boolean end = false;
	protected LoaderHelper loaderHelper;
	protected ViewFlipper flipper;
	protected ListView reviewTable;
	protected TextToSpeech tts;
	protected boolean ttsSuccessful = false;

	@Override
	public final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GameManagerContainer.getGameManager().setCurrentGame(this);
		tts = new TextToSpeech(this, this);
		// helper class that wraps convenience methods to get the data from
		// server
		loaderHelper = new LoaderHelper();
		// None of the games have an action bar
		getActionBar().hide();
		// all games are in portrait(vertical) mode
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		prefIOManager = PreferencesIOManager.getInstance(this);
		// let the children decide what else to do onCreate
		performOnCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// Don't forget to shutdown tts!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}

	@Override
	public void onInit(int status) {

		if (status == TextToSpeech.SUCCESS) {
			Locale locale = new Locale(prefIOManager.getLearningLanguage()
					.getCode());
			int result = tts.setLanguage(locale);

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.v("TTS", "This Language is not supported");
			} else {
				ttsSuccessful = true;
				Log.v("TTS", "It is supported baby");
			}

		} else {
			Log.v("TTS", "Initilization Failed!");
		}
	}

	protected void speakOut(String text) {
		if (ttsSuccessful)
			tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
		else
			WordwiseUtils.makeCustomToast(this,
					"Text to speech is not available");
	}

	/**
	 * Contains the necessary implementation to do when this activity is created
	 * */
	protected abstract void performOnCreate(Bundle savedInstanceState);

	protected abstract View gameContent();

	/**
	 * Indicates whether this game is a concrete game played with translations
	 * or only a review/input game
	 * */
	protected abstract boolean isRealGame();

	public void onGameEnd() {
		int pointsEarned = getPromotion().getPoints(
				prefIOManager.getDifficulty());
		int newPoints = prefIOManager.getPoints() + pointsEarned;
		prefIOManager.setPoints(newPoints);
		WordwiseUtils.updateGameTopPanel(this);
		// as a dummy game end text
		WordwiseUtils.makeCustomToast(this, "Game ended. You have just earned "
				+ pointsEarned + " more points.", Toast.LENGTH_LONG);
	}

	protected void initLayout() {
		setContentView(R.layout.game_wrapper);
		flipper = (ViewFlipper) findViewById(R.id.flipper);
		flipper.setClipToPadding(true);
		flipper.addView(gameContent());
		if (isRealGame()) {
			flipper.addView(getLayoutInflater().inflate(R.layout.game_review,
					null));
			initReview();
		}
		WordwiseUtils.updateGameTopPanel(this);
		this.onGameInit();
	}

	public abstract List<DTOTranslation> getTranslations();

	public boolean canUse(DTOTranslation translation) {
		// by default, every game can use any given translation
		return true;
	}

	public DTOLanguage getLanguage() {
		// by default, every game needs the language being learned
		return prefIOManager.getCurrentGameConfiguration()
				.getLearningLanguage();
	}

	private void initReview() {
		reviewTable = (ListView) findViewById(R.id.review_table);
		TextView headerWord = (TextView) findViewById(R.id.header_word);
		TextView headerTranslation = (TextView) findViewById(R.id.header_translation);
		DTOLanguage lang = PreferencesIOManager.getInstance(this)
				.getLearningLanguage();
		headerWord.setText("English");
		headerTranslation.setText(lang.getLanguage());
		reviewTable.setAdapter(new TableListAdapter(getTranslations()));
	}

	@Override
	public final void onBackPressed() {
		if (end) {
			super.onBackPressed();
			return;
		}
		onQuitPressed();
	}

	public final void quit(View v) {
		onQuitPressed();
	}

	public final void review(View v) {
		AnimationFactory.flipTransition((ViewAnimator) flipper,
				FlipDirection.LEFT_RIGHT);
	}

	public final void continueNextGame(View v) {
		GameManagerContainer.getGameManager().endGame();
	}

	public void validate(View v) {
		// empty implementation by default
	}

	protected void onQuitPressed() {
		WordwiseUtils.makeQuitGameDialog(this);
	}

	public void setEndFlag(boolean endFlag) {
		this.end = endFlag;
	}

	public class TableListAdapter extends BaseAdapter {
		private List<DTOTranslation> translations;

		public TableListAdapter(List<DTOTranslation> translations) {
			this.translations = translations;
		}

		@Override
		public int getCount() {
			return translations.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View row = getLayoutInflater().inflate(R.layout.game_table_row,
					null);

			final DTOTranslation translation = translations.get(position);
			for (int i = 0; i < ((ViewGroup) row).getChildCount(); ++i) {
				if (i == 0) {
					TextView child = (TextView) ((ViewGroup) row).getChildAt(i);
					child.setText(translation.getWord().getWord());
				} else if (i == 1) {
					TextView child = (TextView) ((ViewGroup) row).getChildAt(i);
					child.setText(translation.getTranslation());
				} else if (i == 2) {
					if (!ttsSuccessful) {
						((ViewGroup) row).removeViewAt(i);
					} else {
						ImageButton ttsButton = (ImageButton) ((ViewGroup) row)
								.getChildAt(i);

						ttsButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								speakOut(translation.getTranslation());
							}
						});

					}

				}
			}

			return row;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

}

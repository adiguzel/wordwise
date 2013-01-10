package com.wordwise.view.activity.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.wordwise.R;
import com.wordwise.gameengine.Game;
import com.wordwise.gameengine.GameManager;
import com.wordwise.model.GameManagerContainer;
import com.wordwise.model.RandomGameSelector;
import com.wordwise.model.WordwiseGameManager;

public class Words2Translations extends Activity implements Game {
	private List<TextView> translationPlaceHolders;
	private List<TextView> words;
	private View dragged = null;
	private Map<String, TextView> idToTranslationView = new HashMap<String, TextView>();
	private int droppedCount = 0;
	private Button validateButton;
	// used for the workaround for the initial translation view not being reset
	// to visible
	private View firstTranslation = null;
	private GameManager gameManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.words2translations);
		this.init();
		this.start();
	}

	public void start() {
		// TODO Auto-generated method stub
	}

	public void stop() {
		
	}

	public void pause() {
		// TODO Auto-generated method stubd
	}

	public void init() {
		validateButton = (Button) findViewById(R.id.validateButton);
		initTranslationPlaceHolders();
		initWords();
		initTranslationsGrid();
		gameManager = GameManagerContainer.getGameManager();
	}

	private void initTranslationsGrid() {
		GridView translationsGrid = (GridView) findViewById(R.id.translationsGrid);
		translationsGrid.setAdapter(new TranslationAdapter(this));
	}

	// TODO get the real words from the server
	@SuppressWarnings("deprecation")
	private void initWords() {
		List<String> wordList = new ArrayList<String>();
		wordList.add("computer");
		wordList.add("to visit");
		wordList.add("snow");
		wordList.add("to smoke");

		words = new ArrayList<TextView>();
		words.add((TextView) findViewById(R.id.word1));
		words.add((TextView) findViewById(R.id.word2));
		words.add((TextView) findViewById(R.id.word3));
		words.add((TextView) findViewById(R.id.word4));

		if (wordList.size() == words.size()) {
			for (int i = 0; i < words.size(); i++) {
				TextView v = words.get(i);
				v.setText(wordList.get(i));
				v.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.word2translations_word));
			}
		}
	}

	private void initTranslationPlaceHolders() {
		translationPlaceHolders = new ArrayList<TextView>();
		translationPlaceHolders
				.add((TextView) findViewById(R.id.translation1_placeholder));
		translationPlaceHolders
				.add((TextView) findViewById(R.id.translation2_placeholder));
		translationPlaceHolders
				.add((TextView) findViewById(R.id.translation3_placeholder));
		translationPlaceHolders
				.add((TextView) findViewById(R.id.translation4_placeholder));

		for (TextView v : translationPlaceHolders) {
			v.setTag("");
			v.setOnDragListener(new TranslationDragAdapter());
			v.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					String tag = (String) v.getTag();
					if (!tag.isEmpty()) {
						TextView translationView = idToTranslationView.get(tag);
						if (translationView != null) {
							translationView.setVisibility(View.VISIBLE);
							((TextView) v).setText("");
							((TextView) v).setTag("");
							droppedCount--;
							validateButton.setEnabled(false);
							if (tag.equals((String) firstTranslation.getTag())) {
								// firstTranslation = dragged;
								dragged.setVisibility(View.VISIBLE);
							}
						}

					}
				}
			});
		}
	}

	// called by quit button to quit the game
	public void quit(View v) {

	}

	// called when validate button is pressed
	// validates the drag & drop answers and
	// highlights right and wrong answers
	@SuppressWarnings("deprecation")
	public void validate(View v) {
		// TODO use real translation of a word instead of this
		// DUMMY LIST FOR VERIFICATION
		List<String> translations = new ArrayList<String>();
		translations.add("Komputer");
		translations.add("besuchen");
		translations.add("Schnee");
		translations.add("rauchen");

		for (int i = 0; i < words.size(); i++) {
			TextView t = translationPlaceHolders.get(i);
			String trans = translations.get(i);

			if (((String) t.getText()).equals(trans)) {
				t.setBackgroundDrawable(getResources()
						.getDrawable(
								R.drawable.word2translations_translation_placeholder_match_success));
			} else {
				t.setBackgroundDrawable(getResources()
						.getDrawable(
								R.drawable.word2translations_translation_placeholder_match_failed));
			}
			/*
			 * v.setBackgroundDrawable(getResources().getDrawable(
			 * R.drawable.word2translations_word));
			 */
		}
		v.setVisibility(Button.INVISIBLE);
		Button continueButton =  (Button) findViewById(R.id.continueButton);
		continueButton.setVisibility(Button.VISIBLE);

	}
	
	public void continueNextGame(View v) {
		gameManager.endGame();
	}

	// TODO use real translations of the words that we got from the server
	private class TranslationAdapter extends BaseAdapter {
		private Context mContext;

		public TranslationAdapter(Context c) {
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

		// create a new ImageView for each item referenced by the Adapter
		@SuppressWarnings("deprecation")
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView;
			if (convertView == null) { // if it's not recycled, initialize some
										// attributes
				textView = new TextView(mContext);
			} else {
				textView = (TextView) convertView;
			}

			textView.setText(mThumbIds[position]);
			idToTranslationView.put(mThumbIds[position], textView);
			textView.setTag(textView.getText());
			textView.setOnLongClickListener(new TranslationLongClickListener());
			textView.setTextSize(16);
			// textView.setSingleLine(false);
			textView.setGravity(Gravity.CENTER_VERTICAL
					| Gravity.CENTER_HORIZONTAL);
			// we are using the deprecated method instead of setBackground
			// because it is introduced in API level 16
			textView.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.word2translations_translations));
			if (position == 0) {
				firstTranslation = textView;
			}
			return textView;
		}

		// TODO change this to random mix of the translations of the words from
		// server
		// TODO possibly change this to an ArrayList
		private String[] mThumbIds = { "besuchen", "rauchen", "Komputer",
				"Schnee" };

	}

	private class TranslationLongClickListener implements
			View.OnLongClickListener {
		public boolean onLongClick(View v) {
			// Create a new ClipData.Item from the ImageView object's tag
			ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());

			ClipData dragData = new ClipData((CharSequence) v.getTag(),
					new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN }, item);
			// Instantiates the drag shadow builder.
			View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
			v.startDrag(dragData, // the data to be dragged
					myShadow, // the drag shadow builder
					null, // no need to use local data
					0 // flags (not currently used, set to 0)
			);
			dragged = v;
			return true;
		}
	}

	private class TranslationDragAdapter implements View.OnDragListener {
		private final int TRANSLATION_PLACEHOLDER_INITIAL_STATE = R.drawable.word2translations_translation_placeholder_initial;
		private final int TRANSLATION_PLACEHOLDER_DROPPABLE = R.drawable.word2translations_translation_placeholder_droppable;
		private final int TRANSLATION_PLACEHOLDER_DRAG_ENTERED = R.drawable.word2translations_translation_placeholder_drag_entered;
		private final int TRANSLATION_PLACEHOLDER_DRAGGED = R.drawable.word2translations_translation_placeholder_drag_entered;

		public boolean onDrag(View v, DragEvent event) {
			// Defines a variable to store the action type for the incoming
			// event
			final int action = event.getAction();

			// Handles each of the expected events
			switch (action) {

			case DragEvent.ACTION_DRAG_STARTED:
				// Determines if this View can accept the dragged data
				if (event.getClipDescription().hasMimeType(
						ClipDescription.MIMETYPE_TEXT_PLAIN)) {
					changeState(v, TRANSLATION_PLACEHOLDER_DROPPABLE);

					// returns true to indicate that the View can accept the
					// dragged data.
					return (true);
				} else {
					// Returns false. During the current drag and drop
					// operation, this View will
					// not receive events again until ACTION_DRAG_ENDED is
					// sent.
					return (false);
				}
			case DragEvent.ACTION_DRAG_ENTERED:
				changeState(v, TRANSLATION_PLACEHOLDER_DRAG_ENTERED);
				// Return true; the return
				// value is ignored.
				return (true);
			case DragEvent.ACTION_DRAG_LOCATION:
				// Ignore the event
				return (true);
			case DragEvent.ACTION_DRAG_EXITED:
				// Re-sets the color tint to blue. Returns true; the return
				// value is ignored.
				changeState(v, TRANSLATION_PLACEHOLDER_DROPPABLE);
				// Invalidate the view to force a redraw in the new tint
				v.invalidate();
				return (true);
			case DragEvent.ACTION_DROP:
				// Gets the item containing the dragged data
				ClipData.Item item = event.getClipData().getItemAt(0);
				// Gets the text data from the item.
				String dragData = (String) item.getText();
				if (v instanceof TextView) {
					((TextView) v).setText(dragData);
					((TextView) v).setTag(dragData);
				}
				changeState(v, TRANSLATION_PLACEHOLDER_DRAGGED);
				dragged.setVisibility(View.INVISIBLE);
				dragged.invalidate();
				droppedCount++;
				if (droppedCount == words.size()) {
					validateButton.setEnabled(true);
				}
				// Returns true. DragEvent.getResult() will return true.
				return (true);

			case DragEvent.ACTION_DRAG_ENDED:
				changeState(v, TRANSLATION_PLACEHOLDER_INITIAL_STATE);
				// returns true; the value is ignored.
				return (true);
				// An unknown action type was received.
			default:
				Log.e("DragDrop Example",
						"Unknown action type received by OnDragListener.");
				break;

			}
			return false;
		}

		@SuppressWarnings("deprecation")
		public void changeState(View v, int newState) {
			v.setBackgroundDrawable(getResources().getDrawable(newState));
			v.invalidate();
		}
	}

}

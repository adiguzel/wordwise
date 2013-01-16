package com.wordwise.controller.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ClipData;
import android.content.ClipDescription;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.wordwise.R;
import com.wordwise.server.model.Translation;
import com.wordwise.view.activity.game.Words2Translations;
import com.wordwise.view.game.Word2TranslationsTextView;

public class Words2TranslationsManager
		implements
			View.OnDragListener,
			View.OnLongClickListener {

	private final int TRANSLATION_PLACEHOLDER_INITIAL_STATE = R.drawable.word2translations_translation_placeholder_initial;
	private final int TRANSLATION_PLACEHOLDER_DROPPABLE = R.drawable.word2translations_translation_placeholder_droppable;
	private final int TRANSLATION_PLACEHOLDER_DRAG_ENTERED = R.drawable.word2translations_translation_placeholder_drag_entered;
	private final int TRANSLATION_PLACEHOLDER_DRAGGED = R.drawable.word2translations_translation_placeholder_drag_entered;

	private final int PAIR_MATCH_SUCCESS_STATE = R.drawable.word2translations_translation_placeholder_match_success;
	private final int PAIR_MATCH_FAIL_STATE = R.drawable.word2translations_translation_placeholder_match_failed;

	private final int NUM_PAIRS = 4;
	// Word2Translations game instance that it manages
	private Words2Translations activity;
	// The view that is being dragged
	private Word2TranslationsTextView dragged;
	// Number of views that were dropped into the placeholders
	private int droppedCount = 0;
	private List<Translation> translations = new ArrayList<Translation>();
	private Map<Translation, Word2TranslationsTextView> translationToTranslationView = new HashMap<Translation, Word2TranslationsTextView>();

	// Adapter that provides translations
	private Words2TranslationAdapter adapter;

	// Word views that should match
	private List<Word2TranslationsTextView> words = new ArrayList<Word2TranslationsTextView>();
	private List<Word2TranslationsTextView> translationPlaceHolders;
	// A list of place holders that are already filled
	private List<Word2TranslationsTextView> filledPlaceHolders = new ArrayList<Word2TranslationsTextView>();

	// So that it cannot be instantiated that way
	@SuppressWarnings("unused")
	private Words2TranslationsManager() {
	}

	public Words2TranslationsManager(Words2Translations activity) {
		this.activity = activity;
	}

	public void initViews() {
		initTranslations();
		initWords();
		initTranslationPlaceHolders();
	}

	private void initTranslations() {
		adapter = new Words2TranslationAdapter(activity, this);
		translations = adapter.getTranslations();
		GridView translationsGrid = (GridView) activity
				.findViewById(R.id.translationsGrid);
		translationsGrid.setAdapter(adapter);
		translationToTranslationView = adapter.toTranslationToViewMap();
	}

	private void initWords() {
		words.add(((Word2TranslationsTextView) activity
				.findViewById(R.id.word1)));
		words.add((Word2TranslationsTextView) activity.findViewById(R.id.word2));
		words.add((Word2TranslationsTextView) activity.findViewById(R.id.word3));
		words.add((Word2TranslationsTextView) activity.findViewById(R.id.word4));

		int i = 0;
		for (Translation translation : translations) {
			words.get(i).init(activity, translation,
					Word2TranslationsTextView.USE_WORD);
			i++;
		}
	}

	private void initTranslationPlaceHolders() {
		translationPlaceHolders = new ArrayList<Word2TranslationsTextView>();

		translationPlaceHolders.add((Word2TranslationsTextView) activity
				.findViewById(R.id.translation1_placeholder));
		translationPlaceHolders.add((Word2TranslationsTextView) activity
				.findViewById(R.id.translation2_placeholder));
		translationPlaceHolders.add((Word2TranslationsTextView) activity
				.findViewById(R.id.translation3_placeholder));
		translationPlaceHolders.add((Word2TranslationsTextView) activity
				.findViewById(R.id.translation4_placeholder));

		for (Word2TranslationsTextView v : translationPlaceHolders) {
			// Initially the tag is null
			v.setTag(null);
			v.setOnDragListener(this);
			v.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					Word2TranslationsTextView view = (Word2TranslationsTextView) v;
					Translation tag = (Translation) view.getTag();
					if (tag != null) {
						Word2TranslationsTextView translationView = translationToTranslationView
								.get(tag);
						if (translationView != null) {
							translationView.setVisibility(View.VISIBLE);
							view.setText("");
							droppedCount--;
							view.setTag(null);
							filledPlaceHolders.remove(view);
							if (droppedCount < NUM_PAIRS) {
								activity.switchValidation(false);
							}
						}
					}
				}
			});
		}
	}
	
	public void validate(View button) {

		for (int i = 0; i < words.size(); i++) {
			Word2TranslationsTextView p = translationPlaceHolders.get(i);

			if (pairsMatch(p, translations.get(i))) {
				setBackground(p, PAIR_MATCH_SUCCESS_STATE);

			} else {
				setBackground(p, PAIR_MATCH_FAIL_STATE);
			}
			// Remove the listeners
			p.setOnDragListener(null);
			p.setOnClickListener(null);
		}
		// remove listeners for translation views
		adapter.removeViewListeners();
		// check button is removed
		button.setVisibility(Button.INVISIBLE);
		Button continueButton = (Button) activity
				.findViewById(R.id.continueButton);
		// continue button fills the place of check button
		continueButton.setVisibility(Button.VISIBLE);
		// let the activity to operate its on end func.
		activity.onGameEnd();
	}

	public void setDragged(Word2TranslationsTextView dragged) {
		this.dragged = dragged;
	}

	public boolean onDrag(View v, DragEvent event) {
		// Defines a variable to store the action type for the incoming
		// event
		final int action = event.getAction();

		// Handles each of the expected events
		switch (action) {

			case DragEvent.ACTION_DRAG_STARTED :
				// Determines if this View can accept the dragged data
				Word2TranslationsTextView view = (Word2TranslationsTextView) v;
				if (!filledPlaceHolders.contains(view)) {
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
			case DragEvent.ACTION_DRAG_ENTERED :
				changeState(v, TRANSLATION_PLACEHOLDER_DRAG_ENTERED);
				// Return true; the return
				// value is ignored.
				return (true);
			case DragEvent.ACTION_DRAG_LOCATION :
				// Ignore the event
				return (true);
			case DragEvent.ACTION_DRAG_EXITED :
				// Re-sets the color tint to blue. Returns true; the return
				// value is ignored.
				changeState(v, TRANSLATION_PLACEHOLDER_DROPPABLE);
				// Invalidate the view to force a redraw in the new tint
				v.invalidate();
				return (true);
			case DragEvent.ACTION_DROP :
				// Gets the text data from the item.
				if (v instanceof Word2TranslationsTextView) {
					((Word2TranslationsTextView) v).setText(dragged
							.getTranslation().getTranslation());
					// ((TextView) v).setTag(dragData);
				}
				v.setTag(dragged.getTranslation());
				changeState(v, TRANSLATION_PLACEHOLDER_DRAGGED);
				dragged.setVisibility(View.INVISIBLE);
				dragged.invalidate();
				droppedCount++;
				filledPlaceHolders.add((Word2TranslationsTextView) v);
				checkOrAdjustGameState();
				// Returns true. DragEvent.getResult() will return true.
				return (true);

			case DragEvent.ACTION_DRAG_ENDED :
				// revert back to initial view state
				changeState(v, TRANSLATION_PLACEHOLDER_INITIAL_STATE);
				// returns true; the value is ignored.
				return (true);
				// An unknown action type was received.
			default :
				Log.e(this.getClass().getName(),
						"Unknown action type received by OnDragListener.");
				break;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public void changeState(View v, int newState) {
		v.setBackgroundDrawable(activity.getResources().getDrawable(newState));
		v.invalidate();
	}

	private boolean isFinished() {
		return droppedCount == NUM_PAIRS;
	}

	private void checkOrAdjustGameState() {
		if (isFinished()) {
			activity.switchValidation(true);
		}
	}

	@SuppressWarnings("deprecation")
	private void setBackground(View v, int drawableId) {
		v.setBackgroundDrawable(activity.getResources().getDrawable(drawableId));
	}

	private boolean pairsMatch(Word2TranslationsTextView placeHolder,
			Translation trans) {
		return (placeHolder.getTag()).equals(trans);
	}

	public boolean onLongClick(View v) {
		// Create a new ClipData.Item from the ImageView object's tag
		ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());

		ClipData dragData = new ClipData((CharSequence) v.getTag(),
				new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
		// Instantiates the drag shadow builder.
		View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
		v.startDrag(dragData, // the data to be dragged
				myShadow, // the drag shadow builder
				null, // no need to use local data
				0 // flags (not currently used, set to 0)
		);
		setDragged((Word2TranslationsTextView) v);
		return true;
	}
}

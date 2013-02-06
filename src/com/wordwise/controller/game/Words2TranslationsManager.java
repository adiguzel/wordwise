package com.wordwise.controller.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.wordwise.R;
import com.wordwise.server.dto.DTOTranslation;
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
	private int numMatchedPairs = 0;
	// Word2Translations game instance that it manages
	private Words2Translations game;
	// The view that is being dragged
	private Word2TranslationsTextView dragged;
	// Number of views that were dropped into the placeholders
	private int droppedCount = 0;
	private List<DTOTranslation> translations = new ArrayList<DTOTranslation>();
	private Map<DTOTranslation, Word2TranslationsTextView> translationToTranslationView = new HashMap<DTOTranslation, Word2TranslationsTextView>();
	// indicates whether a drop happened. used to redraw the dragged view if no
	// drop happened
	private boolean isDropped = false;
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

	public Words2TranslationsManager(Words2Translations game) {
		this.game = game;
		translations = game.getTranslations();
	}

	public void initViews() {
		initTranslations();
		initWords();
		initTranslationPlaceHolders();
	}

	private void initTranslations() {
		adapter = new Words2TranslationAdapter(game, this, translations);
		GridView translationsGrid = (GridView) game
				.findViewById(R.id.translationsGrid);
		translationsGrid.setAdapter(adapter);
		translationToTranslationView = adapter.toTranslationToViewMap();
	}

	private void initWords() {
		words.add(((Word2TranslationsTextView) game.findViewById(R.id.word1)));
		words.add((Word2TranslationsTextView) game.findViewById(R.id.word2));
		words.add((Word2TranslationsTextView) game.findViewById(R.id.word3));
		words.add((Word2TranslationsTextView) game.findViewById(R.id.word4));

		int i = 0;
		for (DTOTranslation translation : translations) {
			if (i < words.size()) {
				words.get(i).init(game, translation,
						Word2TranslationsTextView.USE_WORD);
			}
			i++;
		}
	}

	private void initTranslationPlaceHolders() {
		translationPlaceHolders = new ArrayList<Word2TranslationsTextView>();

		translationPlaceHolders.add((Word2TranslationsTextView) game
				.findViewById(R.id.translation1_placeholder));
		translationPlaceHolders.add((Word2TranslationsTextView) game
				.findViewById(R.id.translation2_placeholder));
		translationPlaceHolders.add((Word2TranslationsTextView) game
				.findViewById(R.id.translation3_placeholder));
		translationPlaceHolders.add((Word2TranslationsTextView) game
				.findViewById(R.id.translation4_placeholder));

		for (Word2TranslationsTextView v : translationPlaceHolders) {
			// Initially the tag is null
			v.setTag(null);
			v.setOnDragListener(this);
			v.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					Word2TranslationsTextView view = (Word2TranslationsTextView) v;
					DTOTranslation tag = (DTOTranslation) view.getTag();
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
								game.switchValidation(false);
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
				numMatchedPairs++;
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
		Button continueButton = (Button) game.findViewById(R.id.continueButton);
		// continue button fills the place of check button
		continueButton.setVisibility(Button.VISIBLE);
		// let the game to operate its on end func.
		game.onGameEnd();
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
				isDropped = false;
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
					dragged.setVisibility(View.INVISIBLE);
				}
				v.setTag(dragged.getTranslation());
				changeState(v, TRANSLATION_PLACEHOLDER_DRAGGED);
				dragged.setVisibility(View.INVISIBLE);
				dragged.invalidate();
				droppedCount++;
				filledPlaceHolders.add((Word2TranslationsTextView) v);
				checkOrAdjustGameState();
				isDropped = true;
				// Returns true. DragEvent.getResult() will return true.
				return (true);

			case DragEvent.ACTION_DRAG_ENDED :
				// revert back to initial view state
				changeState(v, TRANSLATION_PLACEHOLDER_INITIAL_STATE);
				if (!isDropped)
					dragged.setVisibility(View.VISIBLE);
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
		//using the deprecated method to support lower API versions
		v.setBackgroundDrawable(game.getResources().getDrawable(newState));
		v.invalidate();
	}

	private boolean isFinished() {
		return droppedCount == NUM_PAIRS;
	}

	private void checkOrAdjustGameState() {
		if (isFinished()) {
			//make the validation available 
			game.switchValidation(true);
		}
	}

	@SuppressWarnings("deprecation")
	private void setBackground(View v, int drawableId) {
		v.setBackgroundDrawable(game.getResources().getDrawable(drawableId));
	}

	private boolean pairsMatch(Word2TranslationsTextView placeHolder,
			DTOTranslation trans) {
		DTOTranslation placeHolderTrans = (DTOTranslation) (placeHolder
				.getTag());
		// some other translation objects can have the same string, so condition
		// of pairs matching should depend on the match in translation strings
		return (placeHolderTrans.getTranslation()).equalsIgnoreCase(trans
				.getTranslation());
	}

	public boolean onLongClick(View v) {
		// Instantiates the drag shadow builder.
		View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
		v.startDrag(null, // the data to be dragged
				myShadow, // the drag shadow builder
				null, // no need to use local data
				0 // flags (not currently used, set to 0)
		);
		setDragged((Word2TranslationsTextView) v);
		dragged.setVisibility(View.INVISIBLE);
		return true;
	}

	public int getNumMatchedPairs() {
		return numMatchedPairs;
	}

	public int getNumTotalPairs() {
		return NUM_PAIRS;
	}
}

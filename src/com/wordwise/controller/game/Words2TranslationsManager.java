package com.wordwise.controller.game;

import java.util.List;

import android.content.ClipData;
import android.content.ClipDescription;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

import com.wordwise.R;
import com.wordwise.server.model.Translation;
import com.wordwise.view.activity.game.Memory;
import com.wordwise.view.activity.game.Words2Translations;
import com.wordwise.view.game.Word2TranslationsTextView;

public class Words2TranslationsManager implements View.OnDragListener {

	private final int TRANSLATION_PLACEHOLDER_INITIAL_STATE = R.drawable.word2translations_translation_placeholder_initial;
	private final int TRANSLATION_PLACEHOLDER_DROPPABLE = R.drawable.word2translations_translation_placeholder_droppable;
	private final int TRANSLATION_PLACEHOLDER_DRAG_ENTERED = R.drawable.word2translations_translation_placeholder_drag_entered;
	private final int TRANSLATION_PLACEHOLDER_DRAGGED = R.drawable.word2translations_translation_placeholder_drag_entered;

	private Words2Translations activity;
	private Word2TranslationsTextView dragged;
	private int droppedCount = 0;
	private List<Translation> translations;

	public Words2TranslationsManager(Words2Translations activity) {
		this.activity = activity;
	}

	public void initViews() {

	}
	public boolean onDrag(View v, DragEvent event) {
		// Defines a variable to store the action type for the incoming
		// event
		final int action = event.getAction();

		// Handles each of the expected events
		switch (action) {

			case DragEvent.ACTION_DRAG_STARTED :
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
				checkOrAdjustGameState();
				// Returns true. DragEvent.getResult() will return true.
				return (true);

			case DragEvent.ACTION_DRAG_ENDED :
				changeState(v, TRANSLATION_PLACEHOLDER_INITIAL_STATE);
				// returns true; the value is ignored.
				return (true);
				// An unknown action type was received.
			default :
				Log.e("DragDrop Example",
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
		return droppedCount == 4;
	}

	private void checkOrAdjustGameState() {
		if (isFinished()) {
			activity.onGameEnd();
		}
	}

	public void validate(){
	//	List<Translation>
	}
	
	public class TranslationLongClickListener
			implements
				View.OnLongClickListener {
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
			dragged = (Word2TranslationsTextView) v;
			return true;
		}
	}
}

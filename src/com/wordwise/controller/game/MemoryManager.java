package com.wordwise.controller.game;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.GridView;
import android.widget.ViewAnimator;

import com.tekle.oss.android.animation.AnimationFactory;
import com.tekle.oss.android.animation.AnimationFactory.FlipDirection;
import com.wordwise.R;
import com.wordwise.model.game.MemoryFlipState;
import com.wordwise.server.dto.DTOTranslation;
import com.wordwise.util.WordwiseUtils;
import com.wordwise.util.game.MemoryViewFlipperUtil;
import com.wordwise.view.activity.game.Memory;
import com.wordwise.view.game.MemoryViewFlipper;

public class MemoryManager {
	// activity to work on
	private Memory memoryActivity;
	private MemoryFlipState flipState = new MemoryFlipState();
	private List<MemoryViewFlipper> foundFlippers;
	private List<MemoryViewFlipper> flippers;
	private List<DTOTranslation> translations;

	public MemoryManager(Memory memoryActivity,
			List<DTOTranslation> translations) {
		this.memoryActivity = memoryActivity;
		this.translations = translations;
	}

	public void initMemoryGrid() {
		GridView translationsGrid = (GridView) memoryActivity
				.findViewById(R.id.memoryGrid);
		MemoryFlipperAdapter translationAdapter = new MemoryFlipperAdapter(
				memoryActivity, translations);
		translationsGrid.setAdapter(translationAdapter);
		foundFlippers = new ArrayList<MemoryViewFlipper>();
		flippers = translationAdapter.getFlippers();
		injectOnClickListeners();
	}

	private void injectOnClickListeners() {
		for (MemoryViewFlipper flipper : flippers) {
			flipper.setOnClickListener(new View.OnClickListener() {
				public void onClick(View flipper) {
					MemoryViewFlipper viewFlipper = (MemoryViewFlipper) flipper;
					if (viewFlipper.equals((MemoryViewFlipper) flipState
							.getFirstFlipped())) {
						WordwiseUtils.makeCustomToast(
								memoryActivity,
								memoryActivity.getResources().getString(
										R.string.memory_same_flip_error));
						return;
					}
					flip(flipper);
				}
			});
		}
	}

	private void removeOnClickListeners() {
		for (MemoryViewFlipper flipper : flippers) {
			flipper.setOnClickListener(null);
		}
	}

	private void turn(final View viewFlipper) {
		AnimationFactory.flipTransition((ViewAnimator) viewFlipper,
				FlipDirection.LEFT_RIGHT);
	}

	private void disappearOutToLeft(final View viewFlipper) {
		viewFlipper.startAnimation(AnimationFactory.outToLeftAnimation(500,
				null));
		viewFlipper.postDelayed(new Runnable() {
			public void run() {
				viewFlipper.setVisibility(View.INVISIBLE);
			}
		}, 500);
	}

	private void flip(final View flipper) {
		// time it takes to flip a view
		final int flipDuration = 500;
		// time in miliseconds that we let users to observe match / unmatch
		final int matchObservationDur = 1000;
		// time to see the colors
		final int colorChangeDur = 500;
		// final int
		// remove all onclick listeners until we finish all the flip and
		// disappear animations
		removeOnClickListeners();
		final MemoryViewFlipper viewFlipper = (MemoryViewFlipper) flipper;
		// perform the flip for the current view
		AnimationFactory.flipTransition((ViewAnimator) flipper,
				FlipDirection.LEFT_RIGHT);
		// set a delayed run op. to run after the flip ends
		viewFlipper.postDelayed(new Runnable() {
			public void run() {
				if (!flipState.flipExist()) {
					flipState.setFirstFlipped(flipper);
					// set the listeners back
					injectOnClickListeners();

				} else {
					flipper.postDelayed(new Runnable() {
						public void run() {
							if (flipState.flipExist()) {
								final MemoryViewFlipper firstFlipped = (MemoryViewFlipper) flipState
										.getFirstFlipped();

								if (firstFlipped
										.matches((MemoryViewFlipper) viewFlipper)) {
									MemoryViewFlipperUtil
											.changeState(
													firstFlipped.getChildAt(1),
													MemoryViewFlipperUtil.STATE_MATCH_SUCCESS);
									MemoryViewFlipperUtil
											.changeState(
													((MemoryViewFlipper) viewFlipper)
															.getChildAt(1),
													MemoryViewFlipperUtil.STATE_MATCH_SUCCESS);
									viewFlipper.setEnabled(false);
									firstFlipped.setEnabled(false);
									flipState.setFirstFlipped(null);

									viewFlipper.postDelayed(new Runnable() {
										public void run() {
											disappearOutToLeft(viewFlipper);
											disappearOutToLeft(firstFlipped);
											foundFlippers.add(firstFlipped);
											foundFlippers.add(viewFlipper);
											checkOrAdjustGameState();
											// set the listeners back
											injectOnClickListeners();
										}
									}, colorChangeDur);

								} else if (firstFlipped != (MemoryViewFlipper) viewFlipper) {
									turn(firstFlipped);
									turn(viewFlipper);
									flipState.setFirstFlipped(null);
									// set the listeners back
									injectOnClickListeners();
								}
							}

						}
					}, matchObservationDur);
				}

			}
		}, flipDuration);// flip anim. end
	}

	private void checkOrAdjustGameState() {
		if (isFinished()) {
			memoryActivity.onGameEnd();
		}
	}

	private boolean isFinished() {
		return foundFlippers.size() == flippers.size();
	}
}

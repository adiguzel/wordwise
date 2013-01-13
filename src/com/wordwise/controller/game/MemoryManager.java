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
import com.wordwise.util.game.MemoryViewFlipperUtil;
import com.wordwise.view.activity.game.Memory;
import com.wordwise.view.game.MemoryViewFlipper;

public class MemoryManager {
	//activity to work on
	private Memory memoryActivity;
	private MemoryFlipState flipState = new MemoryFlipState();
	private List<MemoryViewFlipper> foundFlippers;
	private List<MemoryViewFlipper> flippers;
	
	public MemoryManager(Memory memoryActivity){
		this.memoryActivity = memoryActivity;
	}
	
	public void initMemoryGrid(){
		GridView translationsGrid = (GridView) memoryActivity.findViewById(R.id.memoryGrid);
		MemoryWordAndTranslationAdapter translationAdapter = new MemoryWordAndTranslationAdapter(memoryActivity);
		translationsGrid.setAdapter(translationAdapter);
		injectOnClickListeners(translationAdapter.getFlippers());
		foundFlippers = new ArrayList<MemoryViewFlipper>();
		flippers =  translationAdapter.getFlippers();
	}
	
	private void injectOnClickListeners(List<MemoryViewFlipper> flippers){
		for(MemoryViewFlipper flipper : flippers){
			flipper.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					flip(v);
				}
			});
		}
	}
	
	private void turn( final View viewFlipper){
		AnimationFactory.flipTransition((ViewAnimator) viewFlipper, FlipDirection.LEFT_RIGHT);
	}
	
	private void disappearOutToLeft( final View viewFlipper){
		viewFlipper.startAnimation(AnimationFactory.outToLeftAnimation(500, null));
		viewFlipper.postDelayed(new Runnable() {
			public void run() {
				viewFlipper.setVisibility(View.INVISIBLE);
			}
		}, 500);
	}
	
	private void flip(final View flipper) {
		AnimationFactory.flipTransition((ViewAnimator) flipper, FlipDirection.LEFT_RIGHT);
		if (!flipState.flipExist()){
			flipState.setFirstFlipped(flipper);
		}
		else{
			flipper.postDelayed(new Runnable() {
				public void run() {
					if (flipState.flipExist()) {
						final MemoryViewFlipper firstFlipped = (MemoryViewFlipper)flipState.getFirstFlipped();
						final MemoryViewFlipper viewFlipper = (MemoryViewFlipper) flipper;
						if (firstFlipped.matches((MemoryViewFlipper) viewFlipper)) {
							MemoryViewFlipperUtil.changeState(firstFlipped.getChildAt(1), MemoryViewFlipperUtil.STATE_MATCH_SUCCESS);
							MemoryViewFlipperUtil.changeState(((MemoryViewFlipper)viewFlipper).getChildAt(1), MemoryViewFlipperUtil.STATE_MATCH_SUCCESS);
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
								}
							}, 500);
					//checkOrAdjustGameState();
							
					
							
							// firstFlipped.setVisibility(View.INVISIBLE);
							// v.setVisibility(View.INVISIBLE);

						} else if(firstFlipped != (MemoryViewFlipper)viewFlipper){
							turn(firstFlipped);
							turn( viewFlipper);
							flipState.setFirstFlipped(null);
						}
					}

				}
			}, 1500);
		}
	}
	
	private void checkOrAdjustGameState(){
		if(isFinished()){
			memoryActivity.onGameFinish();
		}
	}
	
	private boolean isFinished() {
		return foundFlippers.size() == flippers.size();
	}
	

}

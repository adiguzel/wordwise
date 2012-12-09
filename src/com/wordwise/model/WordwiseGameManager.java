package com.wordwise.model;

import android.content.Context;

import com.wordwise.gameengine.GameManager;
import com.wordwise.gameengine.GameSelector;

public class WordwiseGameManager extends GameManager{
	private Context context;

	public WordwiseGameManager(GameSelector gameSelector, Context context) {
		super(gameSelector);
		this.context = context;
	}

}

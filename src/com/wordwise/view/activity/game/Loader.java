package com.wordwise.view.activity.game;

import java.util.List;

import com.wordwise.server.model.Translation;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;


public class Loader  extends AsyncTaskLoader<List<Translation>> {

	public Loader(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Translation> loadInBackground() {
		// TODO Auto-generated method stub
		Log.v("Loader", "Loading...");
		return null;
	}

}

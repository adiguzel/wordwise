package com.wordwise.view.activity;

import android.app.Activity;
import android.view.View;

public abstract class ConfigurationStep extends Activity {

	@Override
	public final void onBackPressed() {
	}
	
	public abstract boolean isFinished();
	
	// Calls to this function is configured in the layout res file
	public abstract void back(View view);

	// Calls to this function is configured in the layout res file
	public abstract void next(View view);
}

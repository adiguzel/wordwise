package com.wordwise.view.activity;

import android.app.Activity;

public abstract class ConfigurationStep extends Activity {

	@Override
	public final void onBackPressed() {
	}
	
	public abstract boolean isFinished();
}

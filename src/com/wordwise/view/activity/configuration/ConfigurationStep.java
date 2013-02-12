package com.wordwise.view.activity.configuration;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.wordwise.controller.ConfigurationProcess;

/**
 * This class defines the interface for the configuration steps that users go
 * through
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public abstract class ConfigurationStep extends Activity {
	protected ConfigurationProcess process;

	@Override
	public final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// None of the steps have an action bar
		getActionBar().hide();
		process = ConfigurationProcess.getInstance(this);
		performOnCreate();
	}

	/**
	 * Contains the necessary implementation to do when this activity is created
	 * */
	protected abstract void performOnCreate();

	@Override
	public final void onBackPressed() {
		if (process.hasPrevious()) {
			process.previousStep();
			finish();
		} else
			super.onBackPressed();
	}

	public abstract boolean isFinished();

	// Calls to this function is configured in the layout res file
	public final void back(View view) {
		process.previousStep();
	}

	// Calls to this function is configured in the layout res file
	public final void next(View view) {
		if (isFinished()) {
			process.nextStep();
			// make sure the activity is not kept in the activity stack
			finish();
		}
	}
}

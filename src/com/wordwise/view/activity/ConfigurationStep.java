package com.wordwise.view.activity;

import com.wordwise.controller.ConfigurationProcess;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public abstract class ConfigurationStep extends Activity {
	protected ConfigurationProcess process;
	
	@Override
	public final void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		process = ConfigurationProcess.getInstance(this);
		performOnCreate();
	}
	
	/**
	 * Contains the necessary implementation to do when this activity is created
	 * */
	protected abstract void performOnCreate();
	
	@Override
	public final void onBackPressed() {
	}
	
	public abstract boolean isFinished();
	
	// Calls to this function is configured in the layout res file
	public final void back(View view) {
		// TODO Auto-generated method stub
		process.previousStep();
	}

	// Calls to this function is configured in the layout res file
	public final void next(View view) {
		if (isFinished())
			process.nextStep();
	}
}

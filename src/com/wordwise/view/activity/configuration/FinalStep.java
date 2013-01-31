package com.wordwise.view.activity.configuration;

import com.wordwise.R;
import com.wordwise.view.activity.ConfigurationStep;

public class FinalStep extends ConfigurationStep{

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	protected void performOnCreate() {
		setContentView(R.layout.conf_step_final);		
	}

}
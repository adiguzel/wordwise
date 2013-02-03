package com.wordwise.view.activity.configuration;

import com.wordwise.R;

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
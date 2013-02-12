package com.wordwise.view.activity.configuration;

import com.wordwise.R;

/**
 * This class represents the initial step where user is told that the game
 * should be configured
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class InitialStep extends ConfigurationStep {

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	protected void performOnCreate() {
		setContentView(R.layout.conf_step_initial);
	}
}

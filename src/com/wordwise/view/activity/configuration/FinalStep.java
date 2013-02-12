package com.wordwise.view.activity.configuration;

import com.wordwise.R;

/**
 * This class represents the final step where user is notified that the
 * configuration finished
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class FinalStep extends ConfigurationStep {

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	protected void performOnCreate() {
		setContentView(R.layout.conf_step_final);
	}

}
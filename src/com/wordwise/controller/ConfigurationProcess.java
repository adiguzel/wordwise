package com.wordwise.controller;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;

import com.wordwise.view.activity.ConfigurationStep;

public class ConfigurationProcess {
	private ConfigurationProcess instance = null;
	private Context context;

	private int currentStep = 0;
	private List<Class<? extends ConfigurationStep>> steps = new ArrayList<Class<? extends ConfigurationStep>>();

	private Class<? extends ConfigurationStep> initialStep;
	private Class<? extends ConfigurationStep> finalStep;
	
	// not allowed
	private ConfigurationProcess() {
	}

	private ConfigurationProcess(Context context) {
		this.context = context;
		configureSteps();
	}

	public ConfigurationProcess getInstance(Context context) {
		if (instance == null) {
			instance = new ConfigurationProcess(context);
		}
		return instance;
	}
	
	private void configureSteps() {
		// initial step first
		steps.add(initialStep);
		// add the intermidate(real configuration) steps in the middle
		steps.addAll(getIntermediateSteps());
		// final step is at the end
		steps.add(finalStep);
	}

	private List<Class<? extends ConfigurationStep>> getIntermediateSteps() {
		List<Class<? extends ConfigurationStep>> intermediateSteps = new ArrayList<Class<? extends ConfigurationStep>>();

		// add all the intermediate steps here

		return intermediateSteps;
	}
	
	public void nextStep() {
		if (currentStep < steps.size()) {
			currentStep++;
			startStep(steps.get(currentStep));
		}
	}
	
	public void start(){
		if(steps.size() > 0)
			startStep(steps.get(0));
	}

	public void previousStep() {
		if (currentStep > 0) {
			currentStep--;
			startStep(steps.get(currentStep));
		}
	}

	private void startStep(Class<? extends ConfigurationStep> stepClass) {
		Intent intent = new Intent(context, stepClass);
		context.startActivity(intent);
	}

}

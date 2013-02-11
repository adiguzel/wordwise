package com.wordwise.controller;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.wordwise.WordwiseApplication;
import com.wordwise.view.activity.configuration.ConfigurationStep;
import com.wordwise.view.activity.configuration.FinalStep;
import com.wordwise.view.activity.configuration.InitialStep;
import com.wordwise.view.activity.configuration.LearningLanguageStep;
import com.wordwise.view.activity.configuration.NameStep;
import com.wordwise.view.activity.configuration.ProficientLanguagesStep;

public class ConfigurationProcess {
	private static ConfigurationProcess instance = null;
	private Context context;

	private int currentStepIndex = 0;
	private List<Class<? extends ConfigurationStep>> steps = new ArrayList<Class<? extends ConfigurationStep>>();

	private Class<? extends ConfigurationStep> initialStep = InitialStep.class;
	private Class<? extends ConfigurationStep> finalStep = FinalStep.class;
	private Class<? extends Activity> finishTarget = WordwiseApplication.getMainActivity().getClass();

	private ConfigurationProcess(Context context) {
		this.context = context;
		configureSteps();
	}

	public static ConfigurationProcess getInstance(Context context) {
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

		// add all the intermediate steps here (ordered)
		intermediateSteps.add(NameStep.class);
		intermediateSteps.add(LearningLanguageStep.class);
		intermediateSteps.add(ProficientLanguagesStep.class);
		
		return intermediateSteps;
	}

	public void nextStep() {
		if (hasNext()) {
			currentStepIndex++;
			startStep(steps.get(currentStepIndex));
		} else
			onConfigurationFinish();
	}

	public void onConfigurationFinish() {
		Intent intent = new Intent(context, finishTarget);
		context.startActivity(intent);
	}

	public void startProcess() {
		if (steps.size() > 0)
			startStep(steps.get(0));
	}

	public void previousStep() {
		if (hasPrevious()) {
			currentStepIndex--;
			startStep(steps.get(currentStepIndex));
		}
	}
	
	public boolean hasPrevious(){
		return currentStepIndex > 0;
	}
	
	public boolean hasNext(){
		return currentStepIndex < steps.size() - 1;
	}

	private void startStep(Class<? extends ConfigurationStep> stepClass) {
		Intent intent = new Intent(context, stepClass);
		context.startActivity(intent);
	}
}

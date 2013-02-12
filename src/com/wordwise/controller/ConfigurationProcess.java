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

/**
 * This class encapsulates the steps that should be taken during the initial
 * configuration and their management
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class ConfigurationProcess {
	private static ConfigurationProcess instance = null;
	private Context context;

	private int currentStepIndex = 0;
	private List<Class<? extends ConfigurationStep>> steps = new ArrayList<Class<? extends ConfigurationStep>>();

	private Class<? extends ConfigurationStep> initialStep = InitialStep.class;
	private Class<? extends ConfigurationStep> finalStep = FinalStep.class;
	// the activity to go after initial configuration
	private Class<? extends Activity> finishTarget = WordwiseApplication
			.getMainActivity().getClass();

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

	// all the configuration steps are added here on the steps list
	private void configureSteps() {
		// initial step first
		steps.add(initialStep);
		// add the intermidate(real configuration) steps in the middle
		steps.addAll(getIntermediateSteps());
		// final step is at the end
		steps.add(finalStep);
	}

	// returns a list of steps which come after initial and before the final
	// step
	private List<Class<? extends ConfigurationStep>> getIntermediateSteps() {
		List<Class<? extends ConfigurationStep>> intermediateSteps = new ArrayList<Class<? extends ConfigurationStep>>();

		// add all the intermediate steps here (ordered)
		intermediateSteps.add(NameStep.class);
		intermediateSteps.add(LearningLanguageStep.class);
		intermediateSteps.add(ProficientLanguagesStep.class);

		return intermediateSteps;
	}

	// starts the next step
	public void nextStep() {
		if (hasNext()) {
			currentStepIndex++;
			startStep(steps.get(currentStepIndex));
		} else
			onConfigurationFinish();
	}

	// goes to the finish target upon configuration finish
	public void onConfigurationFinish() {
		Intent intent = new Intent(context, finishTarget);
		context.startActivity(intent);
	}

	// starts the initial conf. process
	public void startProcess() {
		if (steps.size() > 0)
			startStep(steps.get(0));
	}

	// goes back to the previous step
	public void previousStep() {
		if (hasPrevious()) {
			currentStepIndex--;
			startStep(steps.get(currentStepIndex));
		}
	}

	// checks whether or not there is a previous step
	public boolean hasPrevious() {
		return currentStepIndex > 0;
	}

	// checks whether or not there is a next step
	public boolean hasNext() {
		return currentStepIndex < steps.size() - 1;
	}

	// starts the next step activity
	private void startStep(Class<? extends ConfigurationStep> stepClass) {
		Intent intent = new Intent(context, stepClass);
		context.startActivity(intent);
	}
}

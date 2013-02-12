package com.wordwise.view.activity.setting;

import com.wordwise.R;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * This fragment associates itself with preferences file where the type of
 * preference actions are defined
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class SettingsFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences);
	}

}

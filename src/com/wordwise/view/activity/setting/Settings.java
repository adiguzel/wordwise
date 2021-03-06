package com.wordwise.view.activity.setting;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * The activity class which creates the settings screen using the settings
 * fragment(using xml)
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class Settings extends PreferenceActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// all the screens in Wordwise has portrait orientation
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// Display the fragment as the main content.
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SettingsFragment()).commit();
	}
}

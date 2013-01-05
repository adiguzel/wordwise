package com.wordwise.view.activity.setting;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.wordwise.R;

public class Settings extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
}

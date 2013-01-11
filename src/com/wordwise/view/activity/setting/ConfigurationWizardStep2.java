package com.wordwise.view.activity.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.wordwise.MainActivity;
import com.wordwise.R;
import com.wordwise.model.Configuration;
import com.wordwise.server.model.Language;
import com.wordwise.util.LanguageUtils;

public class ConfigurationWizardStep2 extends Activity {
	private ListView listView;
	private Configuration configuration;
	private Button finish;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuration_step2);

		configuration = Configuration.getInstance(getApplicationContext());
		listView = (ListView) findViewById(R.id.list);
		finish = (Button) findViewById(R.id.finish);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice,
				LanguageUtils.toLanguageNameArray());
		listView.setAdapter(adapter);
		listView.setItemsCanFocus(false);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				CheckedTextView tv = (CheckedTextView) arg1;
				String langName = tv.getText().toString();
				Language language = LanguageUtils.getByName(langName);
				configuration.setLearningLanguage(language);
				if (!finish.isEnabled())
					finish.setEnabled(true);
			}

		});
	}
	// Calls to this function is configured in the layout res file
	public void back(View view) {
		Intent intent = new Intent(this, ConfigurationWizardStep1.class);
		startActivity(intent);
	}

	// Calls to this function is configured in the layout res file
	public void finishStep(View view) {
		// TODO check if a lang is selected
		boolean confSuccessful = configuration.finishInitialConfiguration();
		if (confSuccessful) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
	}
}
package com.wordwise;

import com.wordwise.model.Configuration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ConfigurationWizardStep2 extends Activity {
	private ListView listView;
	private Configuration configuration = Configuration.getInstance();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuration_step2);
		listView = (ListView) findViewById(R.id.list);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, LANGUAGES);
		listView.setAdapter(adapter);
		// setListAdapter();
		// final ListView listView = getListView();
		listView.setItemsCanFocus(false);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				CheckedTextView tv = (CheckedTextView) arg1;
				String language = tv.getText().toString();
				configuration.setLearningLanguage(language);
			}

		});
	}

	private static final String[] LANGUAGES = new String[] { "English",
			"German", "Portugese", "Turkish", "Bulgarian", "Macedonian",
			"Spanish" };

	// Calls to this function is configured in the layout res file
	public void back(View view) {
		Intent intent = new Intent(this, ConfigurationWizardStep1.class);
		startActivity(intent);
	}

	// Calls to this function is configured in the layout res file
	public void finishStep(View view) {
	}

}
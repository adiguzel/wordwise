package com.wordwise;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.wordwise.model.Configuration;
import com.wordwise.model.LanguageManager;
import com.wordwise.model.MultiSelectAdapter;

public class ConfigurationWizardStep1 extends Activity {
	String selectedLanguagesText;
	ListView list;
	TextView selectedLanguages;
	LanguageManager lManager = LanguageManager.getInstance();
	private static final String[] LANGUAGES = new String[] { "English",
			"German", "Portugese", "Turkish", "Bulgarian", "Macedonian",
			"Spanish" };
	/*
	 * private static final Selectable[] LANGUAGES = new Selectable[] { new
	 * Selectable("English", true), new Selectable("German", false) };
	 */
	MultiSelectAdapter adapter;
	List<String> languages = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.configuration_step1);

		list = (ListView) findViewById(R.id.list);
		selectedLanguages = (TextView) findViewById(R.id.numberOfSelectedLanguages);
		selectedLanguagesText = selectedLanguages.getText().toString();
		setSelectedLanguageCountText(0);
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, lManager.toLanguageNameArray());
		list.setAdapter(adapter);
		list.setItemsCanFocus(false);
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		list.setItemChecked(2, true);
		list.setItemChecked(3, true);
		/*
		 * adapter = new MultiSelectAdapter(this,R.layout.checked_text_view,
		 * LANGUAGES); list.setAdapter(adapter);
		 */
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				CheckedTextView tv = (CheckedTextView) arg1;
				toggle(tv);
			}
		});
	}

	public void finishStep(View view) {
		/*
		 * Log.i("listview", "" + list.getChildCount()); for (int i = 0; i <
		 * list.getChildCount(); i++) { View view = list.getChildAt(i);
		 * CheckedTextView cv = (CheckedTextView) view
		 * .findViewById(R.id.checkList); if (cv.isChecked()) {
		 * Log.i("listview", cv.getText().toString()); } }
		 */

		Intent intent = new Intent(this, ConfigurationWizardStep2.class);
		startActivity(intent);
	}

	public void toggle(CheckedTextView v) {
		String language = v.getText().toString();
		if (v.isChecked()) {
			v.setChecked(false);
			Configuration.removeLanguage(language);
		} else {
			v.setChecked(true);
			Configuration.addLanguage(language);
		}
		setSelectedLanguageCountText(Configuration.getProficientLanguages()
				.size());
	}

	private void setSelectedLanguageCountText(int count) {
		selectedLanguages.setText(String.format(selectedLanguagesText, count));
	}
}

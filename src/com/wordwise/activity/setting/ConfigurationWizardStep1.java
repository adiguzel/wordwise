package com.wordwise.activity.setting;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.wordwise.R;
import com.wordwise.model.Configuration;
import com.wordwise.model.LanguageManager;

public class ConfigurationWizardStep1 extends FragmentActivity {
	private String selectedLanguagesText;
	private ListView list;
	private TextView selectedLanguages;
	private LanguageManager lManager = LanguageManager.getInstance();
	private Configuration configuration = Configuration.getInstance();
	/*private static final String[] LANGUAGES = new String[] { "English",
			"German", "Portugese", "Turkish", "Bulgarian", "Macedonian",
			"Spanish" };*/
	/*
	 * private static final Selectable[] LANGUAGES = new Selectable[] { new
	 * Selectable("English", true), new Selectable("German", false) };
	 */
	//private MultiSelectAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.configuration_step1);

		list = (ListView) findViewById(R.id.list);
		selectedLanguages = (TextView) findViewById(R.id.numberOfSelectedLanguages);
		selectedLanguagesText = selectedLanguages.getText().toString();
		setSelectedLanguageCountText(0);
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		/*
		 * MessageDialog.newInstance(test).show(this.getSupportFragmentManager(),
		 * "");
		 */

		System.out.println(">>>> " + lManager.toLanguageNameArray().length);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice,
				lManager.toLanguageNameArray());
		list.setAdapter(adapter);
		list.setItemsCanFocus(false);
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

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
		setSelectedIndexes();
	}

	public void finishStep(View view) {
		/*
		 * Log.i("listview", "" + list.getChildCount()); for (int i = 0; i <
		 * list.getChildCount(); i++) { View view = list.getChildAt(i);
		 * CheckedTextView cv = (CheckedTextView) view
		 * .findViewById(R.id.checkList); if (cv.isChecked()) {
		 * Log.i("listview", cv.getText().toString()); } }
		 */
		//TODO Check if at least one lang is selected
		
		//Save changes
		SharedPreferences SP = getPreferences(Context.MODE_PRIVATE);
		System.out.println("AL BAKALIM>>> " + SP.getAll().toString());
		configuration.saveProficientLanguages();
		Intent intent = new Intent(this, ConfigurationWizardStep2.class);
		startActivity(intent);
	}

	public void toggle(CheckedTextView v) {
		String language = v.getText().toString();
		if (v.isChecked()) {
			v.setChecked(false);
			configuration.removeLanguage(language);
		} else {
			v.setChecked(true);
			configuration.addLanguage(language);
		}
		setSelectedLanguageCountText(configuration.getProficientLanguages()
				.size());
	}

	private void setSelectedLanguageCountText(int count) {
		selectedLanguages.setText(String.format(selectedLanguagesText, count));
	}

	public void setSelectedIndexes() {
		List<Integer> selectedIndexes = getSelectedIndexes();
		for (Integer i : selectedIndexes) {
			list.setItemChecked(i, true);
		}
		setSelectedLanguageCountText(selectedIndexes.size());
	}

	// DUMMY IMPLEMENTATION
	public List<Integer> getSelectedIndexes() {
		List<Integer> selectedIndexes = new ArrayList<Integer>();
		for (String langCode : configuration.getProficientLanguages()) {
			Integer index = lManager.langCodeToIndex(langCode);
			if (index >= 0)
				selectedIndexes.add(index);
		}
		return selectedIndexes;
	}

}
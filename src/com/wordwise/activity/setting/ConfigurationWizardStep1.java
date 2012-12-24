package com.wordwise.activity.setting;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
	private LanguageManager lManager;
	private Configuration configuration;
	private Button next;
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
		
		configuration = Configuration.getInstance(getApplicationContext());
		lManager = LanguageManager.getInstance();
		
		list = (ListView) findViewById(R.id.list);
		next = (Button) findViewById(R.id.next);
		if(!configuration.getProficientLanguages().isEmpty())
			next.setEnabled(true);
		selectedLanguages = (TextView) findViewById(R.id.numberOfSelectedLanguages);
		selectedLanguagesText = selectedLanguages.getText().toString();
		
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
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setSelectedLanguageCountText(configuration.getProficientLanguages().size());		
	}
	
	

	public void finishStep(View view) {
		//TODO Check if at least one lang is selected	
		Intent intent = new Intent(this, ConfigurationWizardStep2.class);
		startActivity(intent);
	}

	public void toggle(CheckedTextView v) {
		String language = v.getText().toString();
		String code = lManager.fromName(language).getCode();
		if (v.isChecked()) {
			v.setChecked(false);
			configuration.removeLanguage(code);
		} else {
			v.setChecked(true);
			configuration.addLanguage(code);
		}
		if(configuration.getProficientLanguages()
				.size() > 0)
			next.setEnabled(true);
		else 
			next.setEnabled(false);
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
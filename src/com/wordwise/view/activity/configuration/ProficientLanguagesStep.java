package com.wordwise.view.activity.configuration;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
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
import com.wordwise.server.dto.DTOLanguage;
import com.wordwise.util.LanguageUtils;

public class ProficientLanguagesStep extends ConfigurationStep {
	private String selectedLanguagesText;
	private ListView list;
	private TextView selectedLanguages;
	private Configuration configuration;
	private Button next;

	@Override
	protected void performOnCreate() {
		setContentView(R.layout.conf_step_proficient_langs);

		configuration = Configuration.getInstance(getApplicationContext());
		list = (ListView) findViewById(R.id.list);
		next = (Button) findViewById(R.id.next);
		selectedLanguages = (TextView) findViewById(R.id.numberOfSelectedLanguages);
		initListView();
		selectedLanguagesText = selectedLanguages.getText().toString();	
		if (!configuration.getProficientLanguages().isEmpty()){
			next.setEnabled(true);
			setSelectedIndexes();
		}	
	}

	@Override
	protected void onResume() {
		super.onResume();
		setSelectedLanguageCountText(configuration.getProficientLanguages()
				.size());
	}

	public void initListView() {
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice,
				LanguageUtils.toLanguageNameArray());
		list.setAdapter(adapter);
		list.setItemsCanFocus(false);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				CheckedTextView tv = (CheckedTextView) arg1;
				toggle(tv);
			}
		});
	}

	public void toggle(CheckedTextView v) {
		String langName = v.getText().toString();
		DTOLanguage l = LanguageUtils.getByName(langName);
		
		if(configuration.getProficientLanguages().contains(l)){
			configuration.removeLanguage(l);
		}
		else{
			configuration.addLanguage(l);
		}
		
		//check the state of the next button
		if (configuration.getProficientLanguages().size() > 0){
				next.setEnabled(true);
		}		
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
		for (DTOLanguage l : configuration.getProficientLanguages()) {
			Integer index = LanguageUtils.getIndex(l);
			if (index >= 0)
				selectedIndexes.add(index);
		}
		return selectedIndexes;
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return !configuration.getProficientLanguages().isEmpty();
	}
}

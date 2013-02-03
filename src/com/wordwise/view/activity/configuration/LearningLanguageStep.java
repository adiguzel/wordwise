package com.wordwise.view.activity.configuration;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.wordwise.R;
import com.wordwise.model.Configuration;
import com.wordwise.server.dto.DTOLanguage;
import com.wordwise.util.LanguageUtils;

public class LearningLanguageStep extends ConfigurationStep {
	private ListView listView;
	private Configuration configuration;
	private Button next;

	@Override
	protected void performOnCreate() {
		setContentView(R.layout.conf_step_learning_lang);

		configuration = Configuration.getInstance(getApplicationContext());
		listView = (ListView) findViewById(R.id.list);
		next = (Button) findViewById(R.id.next);

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
				DTOLanguage language = LanguageUtils.getByName(langName);
				configuration.setLearningLanguage(language);
				if (!next.isEnabled())
					next.setEnabled(true);
			}

		});
		DTOLanguage learningLanguage = configuration.getLearningLanguage();
		if(learningLanguage != null){
			listView.setItemChecked(LanguageUtils.getIndex(learningLanguage), true);
			next.setEnabled(true);
		}

	}

	@Override
	public boolean isFinished() {
		return true;
	}

}

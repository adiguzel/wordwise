package com.wordwise.view.activity.configuration;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.wordwise.R;
import com.wordwise.model.Configuration;
import com.wordwise.server.model.Language;
import com.wordwise.util.LanguageUtils;
import com.wordwise.view.activity.ConfigurationStep;

public class LearningLanguageStep extends ConfigurationStep {
	private ListView listView;
	private Configuration configuration;
	private Button finish;

	@Override
	protected void performOnCreate() {
		setContentView(R.layout.conf_step_learning_lang);

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
		Language learningLanguage = configuration.getLearningLanguage();
		if(learningLanguage != null){
			listView.setItemChecked(LanguageUtils.getIndex(learningLanguage), true);
			finish.setEnabled(true);
		}

	}

	@Override
	public boolean isFinished() {
		return configuration.finishInitialConfiguration();
	}

}

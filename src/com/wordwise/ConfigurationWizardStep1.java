package com.wordwise;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.wordwise.model.MultiSelectAdapter;
import com.wordwise.model.Selectable;

public class ConfigurationWizardStep1 extends Activity {
	String selectedLanguagesText;
	ListView list;
	Button next;
	TextView selectedLanguages;
	Selectable[] LANGUAGES = new Selectable[] { new Selectable("English"),
			new Selectable("German") };
	MultiSelectAdapter adapter;
	List<String> languages = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.configuration_step1);

		list = (ListView) findViewById(R.id.list);
		next = (Button) findViewById(R.id.next);
		selectedLanguages = (TextView) findViewById(R.id.numberOfSelectedLanguages);
		selectedLanguagesText = selectedLanguages.getText().toString();
		setSelectedLanguageCountText(0);

		// list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		adapter = new MultiSelectAdapter(this, LANGUAGES);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				CheckedTextView tv = (CheckedTextView) arg1;
				toggle(tv);
			}

		});

		next.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("listview", "" + list.getChildCount());
				for (int i = 0; i < list.getChildCount(); i++) {
					View view = list.getChildAt(i);
					CheckedTextView cv = (CheckedTextView) view
							.findViewById(R.id.checkList);
					if (cv.isChecked()) {
						Log.i("listview", cv.getText().toString());
					}
				}
			}
		});

	}

	public void toggle(CheckedTextView v) {
		String language = v.getText().toString();
		if (v.isChecked()) {
			v.setChecked(false);
			if (languages.contains(language))
				languages.remove(language);

		} else {
			v.setChecked(true);
			if (!languages.contains(language)) {
				languages.add(language);
			}
		}
		setSelectedLanguageCountText(languages.size());
	}

	private void setSelectedLanguageCountText(int count) {
		selectedLanguages.setText(String.format(selectedLanguagesText, count));
	}
}

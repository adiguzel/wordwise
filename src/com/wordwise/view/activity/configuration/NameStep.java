package com.wordwise.view.activity.configuration;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.wordwise.R;
import com.wordwise.model.Configuration;

public class NameStep extends ConfigurationStep implements TextWatcher {
	private EditText name;
	private Configuration configuration;
	private Button next;

	@Override
	protected void performOnCreate() {
		setContentView(R.layout.conf_step_name);

		configuration = Configuration.getInstance(getApplicationContext());
		name = (EditText) findViewById(R.id.name);
		next = (Button) findViewById(R.id.next);
		
		name.setText(configuration.getName());
		name.addTextChangedListener(this);
		if(!name.getText().toString().isEmpty())
			next.setEnabled(true);	
	}

	@Override
	public boolean isFinished() {
		if (!name.getText().toString().isEmpty()) {
			//save the name
			configuration.setName(name.getText().toString());
			return true;
		}
		return false;
	}

	@Override
	public void afterTextChanged(Editable s) {
		if(s.toString().isEmpty())
			next.setEnabled(false);
		else 
			next.setEnabled(true);	
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {		
	}
}

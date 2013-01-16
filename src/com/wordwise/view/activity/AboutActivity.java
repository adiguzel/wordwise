package com.wordwise.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.wordwise.R;

public class AboutActivity extends Activity {
	
	private Button howToPlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_view);
	}
	
}

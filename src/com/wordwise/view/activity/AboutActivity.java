package com.wordwise.view.activity;

import android.app.Activity;
import android.os.Bundle;

import com.wordwise.R;

/**
 * This activity class represents the about screen in the user interface.
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_view);
	}

}

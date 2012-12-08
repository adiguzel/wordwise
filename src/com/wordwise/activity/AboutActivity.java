package com.wordwise.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.wordwise.R;

public class AboutActivity extends Activity {
	
	Button howToPlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_view);
		
		howToPlay = (Button) findViewById(R.id.aboutButton);
	}

	public void showHowToPlay(View view) {
		//TODO HowToPlay activity should be implemented and 
		
//		Intent intent = new Intent(this, HowToPlayActivity.class);
//		startActivity(intent);
	}
	
}

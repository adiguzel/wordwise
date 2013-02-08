package com.wordwise;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageView;

import com.tekle.oss.android.animation.AnimationFactory;
import com.wordwise.controller.ConfigurationProcess;
import com.wordwise.controller.PreferencesIOManager;
import com.wordwise.util.LanguageUtils;

public class WordwiseIntro extends Activity {
	private ImageView wordwiseLogo;
	private PreferencesIOManager configuration;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.wordwise_intro);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		wordwiseLogo = (ImageView) findViewById(R.id.wordwiseLogo);
		wordwiseLogo.startAnimation(AnimationFactory.inFromLeftAnimation(1000,
				null));
		wordwiseLogo.postDelayed(new Runnable() {

			public void run() {
				LanguageUtils.init(getResources());
				configuration = PreferencesIOManager
						.getInstance(getApplicationContext());
				if (!configuration.isConfigured())
					configure();
				else openMainActivity();
			}
		}, 3000);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	public void openMainActivity() {
		Intent intent = new Intent(this, WordwiseApplication.getMainActivity().getClass());
		startActivity(intent);
		finish();
	}

	private void configure() {
		ConfigurationProcess.getInstance(this).startProcess(); 
		finish();
	}
}

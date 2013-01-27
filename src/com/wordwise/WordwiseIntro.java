package com.wordwise;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import com.tekle.oss.android.animation.AnimationFactory;
import com.wordwise.controller.ConfigurationProcess;
import com.wordwise.model.Configuration;
import com.wordwise.util.LanguageUtils;

public class WordwiseIntro extends Activity {
	private TextView introText;
	private Configuration configuration;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.wordwise_intro);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		introText = (TextView) findViewById(R.id.wordwiseIntroText);
		introText.startAnimation(AnimationFactory.inFromLeftAnimation(1000,
				null));
		introText.postDelayed(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				LanguageUtils.init(getResources());
				configuration = Configuration
						.getInstance(getApplicationContext());
				if (!configuration.isConfigured())
					configure();
				else openMainScreen();
			}
		}, 3000);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	public void openMainScreen() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	private void configure() {
		ConfigurationProcess.getInstance(this).startProcess(); 
	}

}

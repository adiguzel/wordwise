package com.wordwise;

import com.tekle.oss.android.animation.AnimationFactory;
import com.wordwise.model.Configuration;
import com.wordwise.util.LanguageUtils;
import com.wordwise.view.activity.setting.ConfigurationWizardStep1;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

public class WordwiseIntro extends Activity {
	private TextView introText;
	private Configuration configuration;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wordwise_intro);
		getActionBar().hide();
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
				openMainScreen();
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
		Intent intent = new Intent(this, ConfigurationWizardStep1.class);
		startActivity(intent);
	}

}

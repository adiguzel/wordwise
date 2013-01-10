package com.wordwise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wordwise.model.Configuration;
import com.wordwise.util.LanguageUtils;
import com.wordwise.view.activity.AboutActivity;
import com.wordwise.view.activity.MainGameScreen;
import com.wordwise.view.activity.MenuActivity;
import com.wordwise.view.activity.setting.ConfigurationWizardStep1;

public class MainActivity extends MenuActivity {
	private Configuration configuration;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LanguageUtils.init(getResources());
		configuration = Configuration.getInstance(getApplicationContext());
		if (!configuration.isConfigured())
			configure();
		else
			loadMainScreen();

		/*
		 * Setting of the content View should be at the last position in order
		 * to receive configuration screens at first and than avoid having an
		 * empty activity if the user presses back button
		 */
		setContentView(R.layout.activity_main);
	}

	/*
	 * It calls the about activity where the user can get some info about the
	 * game or learn how to play as well
	 */
	public void showAbout(View view) {
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}

	private void configure() {
		Intent intent = new Intent(this, ConfigurationWizardStep1.class);
		startActivity(intent);
	}

	private void loadMainScreen() {
		Intent intent = new Intent(this, MainGameScreen.class);
		startActivity(intent);
	}
}

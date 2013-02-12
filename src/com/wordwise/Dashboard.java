package com.wordwise;

import android.app.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wordwise.controller.PreferencesIOManager;
import com.wordwise.gameengine.level.Level;
import com.wordwise.server.dto.DTOLanguage;
import com.wordwise.view.activity.AboutActivity;
import com.wordwise.view.activity.NewGame;
import com.wordwise.view.activity.setting.Settings;

/**
 * This activity class represents the dashboard in the user interface. It
 * injects all the data to the user interface elements defined at its layout
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class Dashboard extends Activity {
	// UI elements
	private TextView username, language, currentLevel, nextLevel, progressInfo,
			points;
	private ProgressBar levelProgress;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// orientation is portrait througout the game
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// use the layout define at layout folder
		setContentView(R.layout.dashboard);
		initUI();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// make sure possible changes done at Settings are shown here(ie.
		// learning language change)
		updateUIElements();
	}

	private void initUI() {
		username = (TextView) findViewById(R.id.username);
		language = (TextView) findViewById(R.id.language);
		currentLevel = (TextView) findViewById(R.id.currentLevel);
		nextLevel = (TextView) findViewById(R.id.nextLevel);
		progressInfo = (TextView) findViewById(R.id.progressInfo);
		points = (TextView) findViewById(R.id.points);
		levelProgress = (ProgressBar) findViewById(R.id.levelProgress);
		// update all the elements
		updateUIElements();
	}

	public void updateUIElements() {
		PreferencesIOManager configuration = PreferencesIOManager
				.getInstance(this);
		int currentPoints = configuration.getPoints();
		String name = configuration.getName();
		DTOLanguage lang = configuration.getLearningLanguage();
		Level level = Level.getByPoint(currentPoints);

		// get the level progress and calculate the progress on the current
		// level
		float progress = level.getLevelProgress(currentPoints);
		float progressRatio = (float) (progress / (float) level.getMax());
		float progressPercentage = (progressRatio * levelProgress.getMax());

		String levelInfoPrefixProgress = String.format(getResources()
				.getString(R.string.level_info_prefix1),
				"" + level.getLevelProgress(currentPoints));
		String levelInfoPrefixTotal = String.format(
				getResources().getString(R.string.level_info_prefix2), ""
						+ level.getMax());
		String levelInfoPostfix = String.format(
				getResources().getString(R.string.level_info_postfix), ""
						+ level.getLevel());

		String progressString = levelInfoPrefixProgress + " "
				+ levelInfoPrefixTotal + " " + levelInfoPostfix;

		username.setText(name);
		language.setText(lang.getLanguage());
		currentLevel.setText("" + level.getLevel());
		nextLevel.setText("" + (level.getLevel() + 1));
		progressInfo.setText(progressString);
		levelProgress.setProgress((int) progressPercentage);
		points.setText("" + currentPoints);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.about :
				showAbout();
				return true;
			case R.id.menu_settings :
				showSettings();
				return true;
			default :
				return super.onOptionsItemSelected(item);
		}
	}

	public void showAbout() {
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}

	public void showSettings() {
		Intent intent = new Intent(this, Settings.class);
		startActivity(intent);
	}

	public void startGame(View view) {
		Intent intent = new Intent(this, NewGame.class);
		startActivity(intent);
	}

	public void showHowToPlay(View view) {
	}

	public void about(View view) {
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}
}

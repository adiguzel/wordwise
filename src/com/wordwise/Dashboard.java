package com.wordwise;

import org.restlet.data.Language;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wordwise.gameengine.level.Level;
import com.wordwise.model.Configuration;
import com.wordwise.server.dto.DTOLanguage;
import com.wordwise.view.activity.AboutActivity;
import com.wordwise.view.activity.NewGame;
import com.wordwise.view.activity.setting.Settings;

public class Dashboard extends Activity {
	private TextView username, language, currentLevel, nextLevel, progressInfo;
	private ProgressBar levelProgress;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.dashboard);
		initUI();
	}
	private void initUI() {
		username = (TextView) findViewById(R.id.username);
		language = (TextView) findViewById(R.id.language);
		currentLevel = (TextView) findViewById(R.id.currentLevel);
		nextLevel = (TextView) findViewById(R.id.nextLevel);
		progressInfo = (TextView) findViewById(R.id.progressInfo);
		levelProgress = (ProgressBar) findViewById(R.id.levelProgress);

		Configuration configuration = Configuration.getInstance(this);
		int points = configuration.getPoints();
		String name = configuration.getName();
		DTOLanguage lang = configuration.getLearningLanguage();
		Level level = Level.getByPoint(points);
		int progress = level.getLevelProgress(points);
		int progressPercentage = (int) ((progress / level.getMax()) * levelProgress
				.getMax());

		;
		String levelInfoPrefixProgress = String.format(getResources()
				.getString(R.string.level_info_prefix1), "" + progress);
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
		levelProgress.setProgress(progressPercentage);
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

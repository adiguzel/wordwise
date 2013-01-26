package com.wordwise;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.wordwise.model.Configuration;
import com.wordwise.util.LanguageUtils;
import com.wordwise.view.activity.AboutActivity;
import com.wordwise.view.activity.MenuActivity;
import com.wordwise.view.activity.NewGame;
import com.wordwise.view.activity.setting.ConfigurationWizardStep1;

public class MainActivity extends MenuActivity {
	private Configuration configuration;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        LanguageUtils.init(getResources());
		configuration = Configuration.getInstance(getApplicationContext());
		if (!configuration.isConfigured())
			configure();
        setContentView(R.layout.game_main_screen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    } 
    
    public void startOrContinueGame(View view) {
		Intent intent = new Intent(this, NewGame.class);
		startActivity(intent);
	}
    
    public void showHowToPlay(View view) {
	}
  /*  
    @Override
	public void onBackPressed() {
    	WordwiseUtils.makeQuitApplicationDialog(this);
	}*/
    
	public void about(View view) {
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}
    
	private void configure() {
		Intent intent = new Intent(this, ConfigurationWizardStep1.class);
		startActivity(intent);
	}
}

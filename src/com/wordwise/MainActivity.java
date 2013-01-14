package com.wordwise;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.wordwise.model.Configuration;
import com.wordwise.util.LanguageUtils;
import com.wordwise.util.WordwiseUtils;
import com.wordwise.view.activity.MenuActivity;
import com.wordwise.view.activity.NewGame;
import com.wordwise.view.activity.game.Hangman;
import com.wordwise.view.activity.game.TranslateWord;
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
    	
    	//Used by Dragan for testing purpose of TranslateWord activity
    	//To be removed on a later stage ...
		Intent intent = new Intent(this, TranslateWord.class);
		startActivity(intent);
	}
        
    public void quit(View view) {
    	WordwiseUtils.makeQuitApplicationDialog(this);
    } 
    
    @Override
	public void onBackPressed() {
		//do nothing
	}
    
	private void configure() {
		Intent intent = new Intent(this, ConfigurationWizardStep1.class);
		startActivity(intent);
	}
}

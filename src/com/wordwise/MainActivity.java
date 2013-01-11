package com.wordwise;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.wordwise.model.Configuration;
import com.wordwise.util.LanguageUtils;
import com.wordwise.view.activity.MenuActivity;
import com.wordwise.view.activity.NewGame;
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
    	/*
         * Notify the system to finalize and collect all objects of the app
         * on exit so that the virtual machine running the app can be killed
         * by the system without causing issues. NOTE: If this is set to
         * true then the virtual machine will not be killed until all of its
         * threads have closed.
         */
        System.runFinalizersOnExit(true);

        /*
         * Force the system to close the app down completely instead of
         * retaining it in the background. The virtual machine that runs the
         * app will be killed. The app will be completely created as a new
         * app in a new virtual machine running in a new process if the user
         * starts the app again.
         */
        System.exit(0);
    	//android.os.Process.killProcess(android.os.Process.myPid());
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

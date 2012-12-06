package com.wordwise;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainGameScreen extends MenuActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main_screen);
         
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    } 
    
    public void startOrContinueGame(View view) {
		//Intent intent = new Intent(this, ConfigurationWizardStep1.class);
		//startActivity(intent);
	}
    public void showHowToPlay(View view) {
		//Intent intent = new Intent(this, ConfigurationWizardStep1.class);
		//startActivity(intent);
	}
    
    public void showSettings(View view) {
		Intent intent = new Intent(this, Settings.class);
		startActivity(intent);
	}
}

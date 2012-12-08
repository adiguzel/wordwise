package com.wordwise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wordwise.activity.AboutActivity;
import com.wordwise.activity.MenuActivity;
import com.wordwise.activity.game.WordEvaluation;
import com.wordwise.activity.setting.ConfigurationWizardStep1;

public class MainActivity extends MenuActivity {
	
	
	Button aboutButton;
	Button exitButton;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        aboutButton = (Button) findViewById(R.id.aboutButton);
        exitButton = (Button) findViewById(R.id.exitButton);
        
        //ASSUMING CHECKS FOR WHETHER THE APP NEEDS
        //TO BE CONFIGURED MADE
        configure();
        
    }
    
    /* It calls the about activity where the user can get some info about the game
     * or learn how to play as well
     */
    public void showAbout(View view) {
    	Intent intent = new Intent(this, AboutActivity.class);
    	startActivity(intent);
    }
    
    /* It is called when the "Exit" button is pressed
     * and it exits from the application to the Home screen
     */
    public void exitApplication(View view) {
    	Intent intent = new Intent(Intent.ACTION_MAIN);
    	intent.addCategory(Intent.CATEGORY_HOME);
    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	startActivity(intent);
    }

    private void configure(){
    	Intent intent = new Intent(this, WordEvaluation.class);
        startActivity(intent);
    }
}

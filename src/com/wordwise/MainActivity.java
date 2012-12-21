package com.wordwise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wordwise.activity.AboutActivity;
import com.wordwise.activity.MainGameScreen;
import com.wordwise.activity.MenuActivity;
import com.wordwise.activity.game.Hangman;
import com.wordwise.activity.game.Memory;
import com.wordwise.activity.game.WordEvaluation;
import com.wordwise.activity.game.Words2Translations;
import com.wordwise.activity.setting.ConfigurationWizardStep1;
import com.wordwise.model.Configuration;

public class MainActivity extends MenuActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        
        if(!Configuration.getInstance().isConfigured())
        	configure();  
        else{
        	loadMainScreen();
        }
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
    	Intent intent = new Intent(this, ConfigurationWizardStep1.class);
        startActivity(intent);
    }
    
    private void loadMainScreen(){
    	Intent intent = new Intent(this, Hangman.class);
        startActivity(intent);
    }
}

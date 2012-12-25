package com.wordwise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wordwise.activity.AboutActivity;
import com.wordwise.activity.MainGameScreen;
import com.wordwise.activity.MenuActivity;
import com.wordwise.activity.setting.ConfigurationWizardStep1;
import com.wordwise.model.Configuration;

public class MainActivity extends MenuActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
       
        if(!Configuration.getInstance(getApplicationContext()).isConfigured())
        	configure();  
        else{
        	loadMainScreen();
        }
        
        /* Setting of the content View should be at the last position in order to receive 
         * configuration screens at first and than avoid having an empty activity if the user 
         * presses back button
         */
        setContentView(R.layout.activity_main);
    }
    
    /* It calls the about activity where the user can get some info about the game
     * or learn how to play as well
     */
    public void showAbout(View view) {
    	Intent intent = new Intent(this, AboutActivity.class);
    	startActivity(intent);
    }
    

    private void configure(){
    	Intent intent = new Intent(this, ConfigurationWizardStep1.class);
        startActivity(intent);
    }
    
    private void loadMainScreen(){
    	Intent intent = new Intent(this, MainGameScreen.class);
        startActivity(intent);
    }
}

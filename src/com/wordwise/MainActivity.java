package com.wordwise;

import android.content.Intent;
import android.os.Bundle;

import com.wordwise.activity.MenuActivity;
import com.wordwise.activity.setting.ConfigurationWizardStep1;

public class MainActivity extends MenuActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //ASSUMING CHECKS FOR WHETHER THE APP NEEDS
        //TO BE CONFIGURED MADE
        configure();
        
    }

    private void configure(){
    	Intent intent = new Intent(this, ConfigurationWizardStep1.class);
        startActivity(intent);
    }
}

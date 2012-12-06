package com.wordwise;

import android.content.Intent;
import android.os.Bundle;

import com.wordwise.activity.MainGameScreen;
import com.wordwise.activity.MenuActivity;

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
    	Intent intent = new Intent(this, MainGameScreen.class);
        startActivity(intent);
    }
}

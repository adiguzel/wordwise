package com.wordwise.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.wordwise.R;
import com.wordwise.view.activity.game.TranslateWord;

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
		Intent intent = new Intent(this, NewGame.class);
		startActivity(intent);
	}
    
    public void showHowToPlay(View view) {
    	
    	//Used by Dragan for testing purpose of TranslateWord activity
    	//To be removed on a later stage ...
		Intent intent = new Intent(this, TranslateWord.class);
		startActivity(intent);
	}
    
}

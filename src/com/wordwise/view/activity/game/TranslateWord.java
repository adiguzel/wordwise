package com.wordwise.view.activity.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wordwise.R;
import com.wordwise.gameengine.Game;
import com.wordwise.model.Configuration;
import com.wordwise.server.model.Language;
import com.wordwise.util.WordwiseUtils;
import com.wordwise.view.activity.WordwiseGameActivity;

//TODO Find out which adtitional information will be necessary
//TODO Check out whether change of the input will be needed and where ?

public class TranslateWord extends WordwiseGameActivity implements Game {

	private final String DIALOG_MESSAGE = "In this screen you will be asked to do a small contribution for this application. Please enter a word on your preffered language and add the translation...";

	private Configuration configuration;
	private HashSet<Language> proficientLanguages;
	private int numberOfProficientLanguages;
	
	private TextView translateWordActivityInfo;
	private EditText wordToBeTranslated;
	private EditText wordTranslation;
	private Button submitTranslation;

	private String wordToBeTranslatedBuffer;
	private String wordTranslationBuffer;

	@Override
	public void performOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.translate_word);

		this.onGameInit();
		this.infoMessageOnStart();

	}

	// Dialog that explains to the user what he will be asked to do in the next
	// screen
	private void infoMessageOnStart() {

		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

		dlgAlert.setMessage(DIALOG_MESSAGE);
		dlgAlert.setTitle("You can contribute :)");
		dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// dismiss the dialog
			}
		});
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}

	public void onGameInit() {
		translateWordActivityInfo = (TextView) this
				.findViewById(R.id.translateWordActivityInfo);
		wordToBeTranslated = (EditText) this
				.findViewById(R.id.wordToBeTranslated);
		wordTranslation = (EditText) this.findViewById(R.id.wordTranslation);
		submitTranslation = (Button) this.findViewById(R.id.submitTranslation);
		
		
		Log.d("Dragan","" + chooseARandomProficientLanguage());
		
	}
	
	private String chooseARandomProficientLanguage() {
		
		configuration = Configuration.getInstance(this);
		proficientLanguages = (HashSet<Language>) configuration.getProficientLanguages();
		numberOfProficientLanguages = proficientLanguages.size();

		
		return "Fuck OFF" + " " + proficientLanguages.size();
	}

	public void onGameStart() {
		// TODO Auto-generated method stub

	}

	public void onGameStop() {
		// TODO Auto-generated method stub

	}

	public void onGamePause() {
		// TODO Auto-generated method stub

	}
	
	public void onGameEnd() {
		// TODO Auto-generated method stub
		
	}

	public void submitTranslation(View v) {
		wordToBeTranslatedBuffer = wordToBeTranslated.getText().toString();
		wordTranslationBuffer = wordTranslation.getText().toString();

		// Remove the words from the edit fields when the data is submitted
		this.wordToBeTranslated.setText("");
		this.wordTranslation.setText("");

		Toast msg = Toast.makeText(this,
				"Your translation was submitted successfully!",
				Toast.LENGTH_SHORT);
		msg.show();

		// TODO Send data to server
	}

	

}

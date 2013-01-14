package com.wordwise.view.activity.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wordwise.R;
import com.wordwise.model.Configuration;
import com.wordwise.server.model.Language;
import com.wordwise.util.LanguageUtils;
import com.wordwise.view.activity.WordwiseGameActivity;

public class TranslateWord extends WordwiseGameActivity {

	private final String DIALOG_MESSAGE = "In this screen you will be asked to do a small contribution for this application. Please enter a word on your preffered language and add the translation...";

	private Configuration configuration;
	private Set<Language> proficientLanguagesSet;
	private List<Language> proficientLanguagesList = new ArrayList<Language>();
	
	private Language randomProficientLanguage;
	private Locale englishLocale;
	private Locale proficientLanguageLocale;
	
	private EditText wordToBeTranslated;
	private EditText wordTranslation;
	private Button submitTranslation;
	private Button continueButton;

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

	@SuppressLint("NewApi")
	public void onGameInit() {
		wordToBeTranslated = (EditText) this
				.findViewById(R.id.wordToBeTranslated);
		wordTranslation = (EditText) this.findViewById(R.id.wordTranslation);
		submitTranslation = (Button) this.findViewById(R.id.submitTranslation);
		continueButton = (Button) this.findViewById(R.id.continueButton);
		
		this.englishLocale = new Locale("en");
		this.wordToBeTranslated.setTextLocale(this.englishLocale);
		
		randomProficientLanguage = chooseRandomProficientLanguage();
		this.proficientLanguageLocale = new Locale(randomProficientLanguage.getCode());
		this.wordTranslation.setTextLocale(this.proficientLanguageLocale);
		
		String translationEditText = wordTranslation.getHint().toString() + " " + randomProficientLanguage.getLanguage();
		wordTranslation.setHint(translationEditText);
		
		Log.d("Dragan","" + chooseRandomProficientLanguage());
		
	}
	
	private Language chooseRandomProficientLanguage() {
		
		configuration = Configuration.getInstance(this);
		proficientLanguagesSet = configuration.getProficientLanguages();
		proficientLanguagesList = LanguageUtils.getProficientLanguages(proficientLanguagesSet);
		
		//removing English since this is the language from which the words are being translated
		if (proficientLanguagesList.contains(LanguageUtils.getByName("English"))) {
			proficientLanguagesList.remove(proficientLanguagesList.indexOf(LanguageUtils.getByName("English")));
		}
		
		Language randomLanguage = LanguageUtils.getRandomProficientLanguage(proficientLanguagesList);
		
		return randomLanguage;
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
		continueButton.setEnabled(true);
	}

	public void submitTranslation(View v) {
		wordToBeTranslatedBuffer = wordToBeTranslated.getText().toString();
		wordTranslationBuffer = wordTranslation.getText().toString();
		
		//check whether the fields are not empty
		if (wordToBeTranslatedBuffer.equalsIgnoreCase("") || wordTranslationBuffer.equalsIgnoreCase("")) {
			Toast msg = Toast.makeText(this,
					"Please fill both: The word and the translation!",
					Toast.LENGTH_SHORT);
			msg.show();
		} else {
			// Remove the words from the edit fields when the data is submitted
			this.wordToBeTranslated.setText("");
			this.wordTranslation.setText("");
			
			//TODO Show feedback while sending ...
			
			//TODO Send data to the server

			Toast msg = Toast.makeText(this,
					"Your translation was submitted successfully!",
					Toast.LENGTH_SHORT);
			msg.show();
			
			this.submitTranslation.setEnabled(false);
			//Tell to the game manager that the translation is submitted 
			this.onGameEnd();
		}
	}

	

}

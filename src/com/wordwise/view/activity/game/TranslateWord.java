package com.wordwise.view.activity.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wordwise.R;
import com.wordwise.gameengine.Game;
import com.wordwise.util.WordwiseUtils;

//TODO Find out which adtitional information will be necessary
//TODO Check out whether change of the input will be needed and where ?

public class TranslateWord extends Activity implements Game {

	private final String DIALOG_MESSAGE = "In this screen you will be asked to do a small contribution for this application. Please enter a word on your preffered language and add the translation...";

	private TextView translateWordActivityInfo;
	private EditText wordToBeTranslated;
	private EditText wordTranslation;
	private Button submitTranslation;

	private String wordToBeTranslatedBuffer;
	private String wordTranslationBuffer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.translate_word);

		this.init();
		this.infoMessageOnStart();

	}
	
	@Override
	public void onBackPressed() {
		WordwiseUtils.makeQuitGameDialog(this);
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

	public void init() {
		translateWordActivityInfo = (TextView) this
				.findViewById(R.id.translateWordActivityInfo);
		wordToBeTranslated = (EditText) this
				.findViewById(R.id.wordToBeTranslated);
		wordTranslation = (EditText) this.findViewById(R.id.wordTranslation);
		submitTranslation = (Button) this.findViewById(R.id.submitTranslation);

	}

	public void start() {
		// TODO Auto-generated method stub

	}

	public void stop() {
		// TODO Auto-generated method stub

	}

	public void pause() {
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

	// called by quit button to quit the game
	public void quit(View v) {

	}
}
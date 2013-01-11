package com.wordwise.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wordwise.R;
import com.wordwise.R.string;
import com.wordwise.gameengine.GameManager;
import com.wordwise.model.GameManagerContainer;

public class WordwiseUtils {

	public static void makeCustomToast(Activity activity, String text) {
		makeCustomToast(activity, text, Toast.LENGTH_SHORT);
	}

	public static void makeCustomToast(Activity activity, String text,
			int duration) {

		LayoutInflater inflater = activity.getLayoutInflater();
		View layout = inflater.inflate(R.layout.custom_toast,
				(ViewGroup) activity.findViewById(R.id.toast_layout_root));

		TextView textView = (TextView) layout.findViewById(R.id.text);
		textView.setText(text);

		Toast toast = new Toast(activity.getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(duration);
		toast.setView(layout);
		toast.show();
	}
	
	public static void makeQuitGameDialog(Activity activity) {

		new AlertDialog.Builder(activity)
	    .setTitle(activity.getString(R.string.exitGameAlertTopic))
	    .setMessage(activity.getString(R.string.exitGameAlert))
	    .setPositiveButton(activity.getString(R.string.exitGamePositiveAnswer), new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with exit
	        	GameManager gManager = GameManagerContainer.getGameManager();
	        	gManager.endGameCycle();
	        }
	     })
	    .setNegativeButton(activity.getString(R.string.exitGameNegativeAnswer), new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })
	     .show();
	}
}

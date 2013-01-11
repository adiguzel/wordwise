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
	    .setTitle(activity.getString(R.string.quitGameAlertTopic))
	    .setMessage(activity.getString(R.string.quitGameAlert))
	    .setPositiveButton(activity.getString(R.string.quitGamePositiveAnswer), new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with exit
	        	GameManager gManager = GameManagerContainer.getGameManager();
	        	gManager.endGameCycle();
	        }
	     })
	    .setNegativeButton(activity.getString(R.string.quitGameNegativeAnswer), new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })
	     .show();
	}
	
	public static void makeQuitApplicationDialog(Activity activity) {
		String appName = activity.getString(R.string.app_name);
		String quitAlert = activity.getString(R.string.quitAppAlertTopic);
		String quitAppAlert = activity.getString(R.string.quitAppAlert);
		new AlertDialog.Builder(activity)
	    .setTitle(String.format(quitAlert, appName))
	    .setMessage(String.format(quitAppAlert, appName))
	    .setPositiveButton(activity.getString(R.string.quitGamePositiveAnswer), new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with exit
	        	quitApplication();
	        }
	     })
	    .setNegativeButton(activity.getString(R.string.quitGameNegativeAnswer), new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })
	     .show();
	}
	
	public static void quitApplication(){
	   	/*
         * Notify the system to finalize and collect all objects of the app
         * on exit so that the virtual machine running the app can be killed
         * by the system without causing issues. NOTE: If this is set to
         * true then the virtual machine will not be killed until all of its
         * threads have closed.
         */
        System.runFinalizersOnExit(true);

        /*
         * Force the system to close the app down completely instead of
         * retaining it in the background. The virtual machine that runs the
         * app will be killed. The app will be completely created as a new
         * app in a new virtual machine running in a new process if the user
         * starts the app again.
         */
        System.exit(0);
	}
}

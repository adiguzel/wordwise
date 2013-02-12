package com.wordwise.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wordwise.R;
import com.wordwise.controller.PreferencesIOManager;
import com.wordwise.gameengine.GameManager;
import com.wordwise.gameengine.level.Level;
import com.wordwise.model.GameManagerContainer;

/**
 * This is a utility class provides methods to do some common operations
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class WordwiseUtils {

	/**
	 * Makes a toast using a custom layout for define Toast short time
	 * */
	public static void makeCustomToast(Activity activity, String text) {
		makeCustomToast(activity, text, Toast.LENGTH_SHORT);
	}

	/**
	 * Makes a toast for a given duration using a custom layout
	 * */
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

	/*
	 * Makes a dialog asking if user wants to quit the game he/she is playing or
	 * not
	 */
	public static void makeQuitGameDialog(final Activity activity) {

		new AlertDialog.Builder(activity)
				.setTitle(activity.getString(R.string.quitGameAlertTopic))
				.setMessage(activity.getString(R.string.quitGameAlert))
				.setPositiveButton(
						activity.getString(R.string.quitGamePositiveAnswer),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// continue with exit
								GameManager gManager = GameManagerContainer
										.getGameManager();
								gManager.endGameCycle();
							}
						})
				.setNegativeButton(
						activity.getString(R.string.quitGameNegativeAnswer),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// do nothing
							}
						}).show();
	}

	/*
	 * Dialog that explains to the user what he will be asked to do in the next
	 * screen
	 */
	public static void infoDialogOnStart(String title, String dialog,
			Context context) {

		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

		dlgAlert.setMessage(dialog);
		dlgAlert.setTitle(title);
		dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// dismiss the dialog
			}
		});
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}

	/*
	 * Updates game top panel with new points and level info
	 */
	public static void updateGameTopPanel(Activity activity) {
		PreferencesIOManager configuration = PreferencesIOManager
				.getInstance(activity);
		TextView pointsView = (TextView) activity.findViewById(R.id.points);
		TextView levelView = (TextView) activity.findViewById(R.id.level);

		String levelString = activity.getResources().getString(
				R.string.levelLabel);
		int points = configuration.getPoints();
		Level level = Level.getByPoint(points);
		if (pointsView != null && levelView != null) {
			pointsView.setText("" + configuration.getPoints());
			levelView.setText(levelString + " " + level.getLevel());
		}
	}
}

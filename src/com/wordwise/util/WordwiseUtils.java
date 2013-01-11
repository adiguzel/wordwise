package com.wordwise.util;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wordwise.R;

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
}

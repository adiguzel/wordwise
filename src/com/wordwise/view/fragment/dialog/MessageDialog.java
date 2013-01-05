package com.wordwise.view.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.wordwise.R;

/**
 * Dialog to inform user blog post result
 */
public class MessageDialog extends DialogFragment {
	// Default message value
	String message = "";

	public static MessageDialog newInstance(String message) {
		// Supply num input as an argument.
		MessageDialog f = new MessageDialog();

		Bundle args = new Bundle();
		// save dialog state
		args.putString("message", message);
		f.setArguments(args);
		return f;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// restore the dialog state upon (re)creation
		message = getArguments().getString("message");
		return new AlertDialog.Builder(getActivity())
				// .setTitle("Location share")
				.setMessage(message)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						}).create();

	}
}
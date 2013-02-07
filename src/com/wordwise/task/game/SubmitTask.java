package com.wordwise.task.game;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import com.wordwise.client.RESTfullServerCommunication;
import com.wordwise.model.SubmitListener;

public abstract class SubmitTask extends AsyncTask<Void, Void, Boolean> {
	private Activity activity;
	private SubmitListener submitListener;
	private ProgressDialog dialog;
	protected RESTfullServerCommunication serverComm;
	
	public SubmitTask(Activity activity, SubmitListener submitListener){
		serverComm = new RESTfullServerCommunication(activity);
		this.activity = activity;
		this.submitListener = submitListener;
	}
	
	@Override
	protected void onPreExecute() {
		dialog = new ProgressDialog(activity) {
			public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
			}
		};
		dialog.setMessage("Submitting...");
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(true);
		// show the progress bar
		dialog.show();
	}
	
	protected void onPostExecute(Boolean result) {
		dialog.cancel();
		// operation successful
		submitListener.onSubmitResult(result);
	}
	
}


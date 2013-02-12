package com.wordwise.task.game;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import com.wordwise.R;
import com.wordwise.client.RESTfullServerCommunication;
import com.wordwise.model.SubmitListener;

/**
 * This class provides the necessary basic implementation to submit data to server
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
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
		//set the message as submitting
		String progressMsg= activity.getResources().getString(R.string.submit_in_progress);
		dialog.setMessage(progressMsg);
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(true);
		// show the progress bar
		dialog.show();
	}
	
	protected void onPostExecute(Boolean result) {
		dialog.cancel();
		// operation successful, call the callback with the result
		submitListener.onSubmitResult(result);
	}
	
}


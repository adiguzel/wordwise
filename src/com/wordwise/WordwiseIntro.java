package com.wordwise;

import com.tekle.oss.android.animation.AnimationFactory;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

public class WordwiseIntro extends Activity {
	private TextView introText;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wordwise_intro);
		getActionBar().hide();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		introText = (TextView) findViewById(R.id.wordwiseIntroText);
		introText.startAnimation(AnimationFactory.inFromLeftAnimation(1000,
				null));
		introText.postDelayed(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				openMainScreen();
			}
		}, 4000);

	}

	public void openMainScreen() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

}

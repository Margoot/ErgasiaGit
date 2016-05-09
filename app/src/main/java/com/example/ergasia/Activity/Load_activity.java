package com.example.ergasia.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.ergasia.R;

public class Load_activity extends Activity {

	private static int SPLASH_SHOW_TIME = 4800;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_load_activity);

		final ImageView zoom = (ImageView) findViewById(R.id.imageView);

		final Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
		zoom.startAnimation(zoomAnimation);

		//new BackgroundSplashTask().execute();
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity
				Intent i = new Intent(Load_activity.this, Post_rec_activity.class);
				startActivity(i);

				// close this activity
				finish();
			}
		}, SPLASH_SHOW_TIME);
	}
}

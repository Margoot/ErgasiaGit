package com.example.ergasia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Inscription_activity extends Activity {

	private Button createButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription_activity);
		addButtonListener();

		TextView  textView1  = (TextView)findViewById(R.id.nameTextView1);
		TextView  textView2  = (TextView)findViewById(R.id.nameEditText1);
		TextView  textView3  = (TextView)findViewById(R.id.firstnameTextView1);
		TextView  textView4  = (TextView)findViewById(R.id.firstNameEditText1);
		TextView  textView5  = (TextView)findViewById(R.id.emailTextView1);
		TextView  textView6  = (TextView)findViewById(R.id.emailEditText1);
		TextView  textView7  = (TextView)findViewById(R.id.passwordTextView1);
		TextView  textView8  = (TextView)findViewById(R.id.passwordEditText1);
		TextView  textView9  = (TextView)findViewById(R.id.confpasswordTextView1);
		TextView  textView10  = (TextView)findViewById(R.id.confpasswordEditText1);
		TextView  textView11  = (TextView)findViewById(R.id.createButton);
		TextView  textView12  = (TextView)findViewById(R.id.inscriptionTextView);

		setFont(textView1, "BigCaslon.ttf");
		setFont(textView2, "BigCaslon.ttf");
		setFont(textView3, "BigCaslon.ttf");
		setFont(textView4, "BigCaslon.ttf");
		setFont(textView5, "BigCaslon.ttf");
		setFont(textView6, "BigCaslon.ttf");
		setFont(textView7, "BigCaslon.ttf");
		setFont(textView8, "BigCaslon.ttf");
		setFont(textView9, "BigCaslon.ttf");
		setFont(textView10, "BigCaslon.ttf");
		setFont(textView11, "BigCaslon.ttf");
		setFont(textView12, "BigCaslon.ttf");
	}

	/**
	 * function setFont which use to customize the font of the view
	 * @param textView
	 * @param fontName
	 */
	private void setFont(TextView textView, String fontName) {
		if(fontName != null){
			try {
				Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + fontName);
				textView.setTypeface(typeface);
			} catch (Exception e) {
				Log.e("FONT", fontName + " not found", e);
			}
		}
	}

	private void addButtonListener () {
		createButton = (Button) findViewById(R.id.createButton);
		createButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Inscription_activity.this, LoginActivity.class);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inscription_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

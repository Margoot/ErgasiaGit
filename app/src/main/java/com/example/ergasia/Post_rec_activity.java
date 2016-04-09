package com.example.ergasia;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Post_rec_activity extends Activity {

	Button jepostButton;
	Button jerecButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_rec_activity);
		addListenerOnButton();

		TextView  textView1  = (TextView)findViewById(R.id.inscriptionTextView);
		TextView  textView2  = (TextView)findViewById(R.id.postButton);
		TextView  textView3  = (TextView)findViewById(R.id.recButton);
		TextView  textView4  = (TextView)findViewById(R.id.sloganTextView);

		setFont(textView1, "BrushScriptMT.ttf");
		setFont(textView2, "BigCaslon.ttf");
		setFont(textView3, "BigCaslon.ttf");
		setFont(textView4, "BigCaslon.ttf");

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

	private void addListenerOnButton() {

		//final Context context = this;

		jepostButton = (Button) findViewById(R.id.postButton);
		jerecButton = (Button) findViewById(R.id.recButton);

		jepostButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0){
				Intent i = new Intent(Post_rec_activity.this, LoginActivity.class);
				startActivity(i);
			}
		});

		jerecButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0){
				Intent i = new Intent(Post_rec_activity.this, LoginActivity.class);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post_rec_activity, menu);
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

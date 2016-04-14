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
	private SessionManager session;
	private SQLiteHandler db;
	public static Boolean isPost;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_rec_activity);


		TextView  textView1  = (TextView)findViewById(R.id.inscriptionTextView);
		TextView  textView2  = (TextView)findViewById(R.id.postButton);
		TextView  textView3  = (TextView)findViewById(R.id.recButton);
		TextView  textView4  = (TextView)findViewById(R.id.sloganTextView);

		setFont(textView1, "BrushScriptMT.ttf");
		setFont(textView2, "BigCaslon.ttf");
		setFont(textView3, "BigCaslon.ttf");
		setFont(textView4, "BigCaslon.ttf");

		// SQlite database handler
		db = new SQLiteHandler(getApplicationContext());
		// Session manager
		session = new SessionManager(getApplicationContext());

		//check if user is already logged in
		if(session.isLoggedIn()) {
			//User is already logged in. Take him to Post/Rec activity
			Intent i = new Intent(Post_rec_activity.this, MainTabbedActivityPost.class );
			startActivity(i);
			finish();
			// A FAIRE: différencier Postulant Recruteur
		}

		addListenerOnButton();

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


		private  void addListenerOnButton() {

			//final Context context = this;

			jepostButton = (Button) findViewById(R.id.postButton);
			jerecButton = (Button) findViewById(R.id.recButton);

			jepostButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					isPost = true;
					Intent i = new Intent(Post_rec_activity.this, LoginActivity.class);
					startActivity(i);


				}
			});

			jerecButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					isPost = false;
					Intent i = new Intent(Post_rec_activity.this, LoginActivity.class);
					startActivity(i);

				}
			});
		}


	public static boolean getIsPost() {
		return isPost;
	}

}

package com.example.ergasia;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.app.ProgressDialog;


import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 * 
 */
public class LoginActivity extends Activity implements LoaderCallbacks<Cursor> {

	private static final String TAG = Inscription_activity.class.getSimpleName();//Tag to identify the request
    private Button btnLogin;
    private Button createAccountButton;
	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	//private UserLoginTask mAuthTask = null;
    private AutoCompleteTextView inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
	// UI references.


	private View mProgressView;
	private View mLoginFormView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

        TextView textView1 = (TextView)findViewById(R.id.inscriptionTextView);
        TextView textView2 = (TextView)findViewById(R.id.email);
        TextView textView3 = (TextView)findViewById(R.id.password);
        TextView textView4 = (TextView)findViewById(R.id.email_sign_in_button);
        TextView textView5 = (TextView)findViewById(R.id.createAccountButton);
        setFont(textView1, "BrushScriptMT.ttf");
        setFont(textView2, "BigCaslon.ttf");
        setFont(textView3, "BigCaslon.ttf");
        setFont(textView4, "BigCaslon.ttf");
        setFont(textView5, "BigCaslon.ttf");
        textView5.setPaintFlags(textView5.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // intialisation of the different inputs and buttons
        inputEmail = (AutoCompleteTextView) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
		btnLogin = (Button) findViewById(R.id.email_sign_in_button);
        createAccountButton = (Button) findViewById(R.id.createAccountButton);

        // intialisation of the progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQlite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        //check if user is already logged in
        if(session.isLoggedIn()) {
            //User is already logged in. Take him to Post/Rec activity
            Intent i = new Intent(LoginActivity.this, MainTabbedActivityPost.class );
            startActivity(i);
            finish();
            // A FAIRE: différencier Postulant Recruteur
        }

        //
		

		// Set up the login form.
		//mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
		populateAutoComplete();


		inputPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
				if (id == R.id.login || id == EditorInfo.IME_NULL) {
					attemptLogin();
					return true;
				}
				return false;
			}
		});



		mLoginFormView = findViewById(R.id.login_form);
		mProgressView = findViewById(R.id.login_progress);

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


    private void addListenerOnButton () {
        btnLogin.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
            attemptLogin();
        }
    });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0){
                Intent i = new Intent(LoginActivity.this, Inscription_activity.class);
                startActivity(i);
                finish();

            }
        });
    }

    private void checkLogin(final String email,final String password) {
        //Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,AppConfig.URL_LOGIN, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    //Check for error node in json
                    if(!error) {
                        //user successfully logged in
                        //create login session
                        session.setLogin(true);

                        //Now store the user in SQLite
                        String uid = jObj.getString("uid");

						JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String firstname = user.getString("firstname");
                        String email = user.getString("email");
                        String created_at = user.getString("created_at");

                        //Inserting row in users table
                        db.addUser(name, firstname, email, uid, created_at);

                        //Launch main activity
                        Intent i = new Intent(LoginActivity.this, MainTabbedActivityPost.class);
                        startActivity(i);
                        finish();
                    } else {
                        //Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    //JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

	private void populateAutoComplete() {
		getLoaderManager().initLoader(0, null, this);
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		/*if (mAuthTask != null) {
			return;
		}*/

		// Reset errors.
		inputEmail.setError(null);
		inputPassword.setError(null);

		// Store values at the time of the login attempt.
		String email = inputEmail.getText().toString().trim();
		String password = inputPassword.getText().toString().trim();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password, if the user entered one.
		if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
			inputPassword.setError(getString(R.string.error_invalid_password));
			focusView = inputPassword;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(email)) {
			inputEmail.setError(getString(R.string.error_field_required));
			focusView = inputEmail;
			cancel = true;
            checkLogin(email, password);
		} else if (!isEmailValid(email)) {
			inputEmail.setError(getString(R.string.error_invalid_email));
			focusView = inputEmail;
			cancel = true;
            Toast.makeText(getApplicationContext(), "Veuillez saisir les champs",Toast.LENGTH_LONG).show();
        }

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			showProgress(true);
			//mAuthTask = new UserLoginTask(email, password);
			//mAuthTask.execute((Void) null);
		}
	}

	private boolean isEmailValid(String email) {
		// TODO: Replace this with your own logic
		return email.contains("@");
	}

	private boolean isPasswordValid(String password) {
		// TODO: Replace this with your own logic
		return password.length() > 4;
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
						}
					});

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this,
				// Retrieve data rows for the device user's 'profile' contact.
				Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
						ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
				ProfileQuery.PROJECTION,

				// Select only email addresses.
				ContactsContract.Contacts.Data.MIMETYPE + " = ?",
				new String[] { ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE },

				// Show primary email addresses first. Note that there won't be
				// a primary email address if the user hasn't specified one.
				ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		List<String> emails = new ArrayList<String>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			emails.add(cursor.getString(ProfileQuery.ADDRESS));
			cursor.moveToNext();
		}

		addEmailsToAutoComplete(emails);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}

	private interface ProfileQuery {
		String[] PROJECTION = { ContactsContract.CommonDataKinds.Email.ADDRESS,
				ContactsContract.CommonDataKinds.Email.IS_PRIMARY, };

		int ADDRESS = 0;
		int IS_PRIMARY = 1;
	}

	private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
		// Create adapter to tell the AutoCompleteTextView what to show in its
		// dropdown list.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginActivity.this,
				android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

		inputEmail.setAdapter(adapter);
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	/*public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

		private final String mEmail;
		private final String mPassword;

		UserLoginTask(String email, String password) {
			mEmail = email;
			mPassword = password;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}

			for (String credential : DUMMY_CREDENTIALS) {
				String[] pieces = credential.split(":");
				if (pieces[0].equals(mEmail)) {
					// Account exists, return true if the password matches.
					return pieces[1].equals(mPassword);
				}
			}

			// TODO: register the new account here.
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				finish();
			} else {
				inputPassword.setError(getString(R.string.error_incorrect_password));
				inputPassword.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}*/
}

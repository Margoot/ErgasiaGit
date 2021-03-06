package com.example.ergasia.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.app.ProgressDialog;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.String;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ergasia.app.AppConfig;
import com.example.ergasia.app.AppController;
import com.example.ergasia.R;
import com.example.ergasia.Helper.SQLiteHandler;
import com.example.ergasia.Helper.SessionManager;
import com.example.ergasia.model.User;

import static com.android.volley.Request.Method.POST;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();//Tag to identify the request
    private Button btnLogin;
    private Button createAccountButton;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //private UserLoginTask mAuthTask = null;
    private EditText inputEmail;
    private EditText inputPassword;
    private TextInputLayout inputLayoutEmail;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView textView1 = (TextView) findViewById(R.id.createAccountButton);
        textView1.setPaintFlags(textView1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // intialisation of the different inputs and buttons
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputEmail = (EditText) findViewById(R.id.email);
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

        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        addListenerOnButton();
    }


    private void addListenerOnButton() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, Inscription_activity.class);
                startActivity(i);
                finish();

            }
        });
    }

    private void checkLoginCandidate(final String email, final String password) {
        //Tag used to cancel the request

        if (!validateEmail()) {
            return;
        }

        String tag_string_req = "req_login";

        pDialog.setMessage("Connection en cours ... ");
        showDialog();

        StringRequest strReq = new StringRequest(POST, AppConfig.URL_LOGIN_CANDIDATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject obj = new JSONObject(response);
                    boolean error = obj.getBoolean("error");

                    //Check for error node in json
                    if (!error) {
                        //user successfully logged in
                        //create login session
                        session.setLogin(true);
                        session.setCandidate(true);
                        session.setRecruiter(false);

                        //Now store the user in SQLite
                        JSONObject userObj = obj.getJSONObject("user");
                        User user = new User(userObj.getString("user_id"), userObj.getString("name"), userObj.getString("email"));

                        //Storing user in shared preferences
                        AppController.getInstance().getPrefManager().storeUser(user);

                        String uidUser = userObj.getString("uidUser");
                        String nameUser = userObj.getString("name");
                        String firstnameUser = userObj.getString("firstname");
                        String email = userObj.getString("email");
                        String created_at_user = userObj.getString("created_at");

                        db.addUser(nameUser, firstnameUser, email, uidUser, created_at_user);


                        JSONObject candidateObj = obj.getJSONObject("candidate");
                        String uidCandidate = candidateObj.getString("uidCandidate");
                        String nameCandidate = candidateObj.getString("name");
                        String firstnameCandidate = candidateObj.getString("firstname");
                        String training = candidateObj.getString("training");
                        String areaActivity = candidateObj.getString("area_activity");
                        String type = candidateObj.getString("type");
                        String language1 = candidateObj.getString("language1");
                        String levelLanguage1 = candidateObj.getString("level_language1");
                        String language2 = candidateObj.getString("language2");
                        String levelLanguage2 = candidateObj.getString("level_language2");
                        String language3 = candidateObj.getString("language3");
                        String levelLanguage3 = candidateObj.getString("level_language3");
                        String skill = candidateObj.getString("skill");
                        String geolocation = candidateObj.getString("geolocation");
                        String created_at_candidate = candidateObj.getString("created_at");

                        //session.setCandidateName(firstnameCandidate);


                        db.addNewCandidate(nameCandidate, firstnameCandidate, training, areaActivity, type,
                                language1, levelLanguage1, language2, levelLanguage2, language3, levelLanguage3,
                                skill, geolocation, uidCandidate, created_at_candidate);


                        //Launch main activity

                        if (session.isCandidate()) {
                            Intent i = new Intent(LoginActivity.this, MainTabbedActivityPost.class);
                            startActivity(i);
                            finish();
                        } else {
                            Intent i = new Intent(LoginActivity.this, New_post_activity.class);
                            startActivity(i);
                            finish();
                        }


                    } else {
                        //Error in login. Get the error message
                        String errorMsg = obj.getString("error_msg");
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

    private void checkLoginRecruiter(final String email, final String password) {

        if (!validateEmail()){
            return;
        }

        //Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Connection en cours ... ");
        showDialog();

        StringRequest strReq = new StringRequest(POST, AppConfig.URL_LOGIN_RECRUITER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject obj = new JSONObject(response);
                    boolean error = obj.getBoolean("error");

                    //Check for error node in json
                    if (!error) {
                        //user successfully logged in
                        //create login session
                        session.setLogin(true);
                        session.setRecruiter(true);
                        session.setCandidate(false);

                        //Now store the user in SQLite
                        JSONObject userObj = obj.getJSONObject("user");
                        User user = new User(userObj.getString("user_id"), userObj.getString("name"), userObj.getString("email"));
                        String uidUser = userObj.getString("uidUser");
                        String nameUser = userObj.getString("name");
                        String firstnameUser = userObj.getString("firstname");
                        String email = userObj.getString("email");
                        String created_at_user = userObj.getString("created_at");

                        db.addUser(nameUser, firstnameUser, email, uidUser, created_at_user);

                        JSONArray recruitersObj = obj.getJSONArray("offer");

                        for (int i = 0; i < recruitersObj.length(); i++) {
                            JSONObject recruiterObj = (JSONObject) recruitersObj.get(i);

                            //JSONObject recruiterObj = obj.getJSONObject("offer");
                            String uidRecruiter = recruiterObj.getString("uidOffer");
                            String company = recruiterObj.getString("company");
                            String jobTitle = recruiterObj.getString("job_title");
                            String areaActivity = recruiterObj.getString("area_activity");
                            String type = recruiterObj.getString("type");
                            String geolocation = recruiterObj.getString("geolocation");
                            String skill = recruiterObj.getString("skill");
                            String created_at_recruiter = recruiterObj.getString("created_at");

                            db.addOffer(company, jobTitle, areaActivity, type, geolocation, skill,
                                    uidRecruiter, created_at_recruiter);
                        }

                        //Storing user in shared preferences
                        AppController.getInstance().getPrefManager().storeUser(user);
                        if (session.isRecruiter()) {
                            Intent i = new Intent(LoginActivity.this, MainTabbedActivityRec.class);
                            startActivity(i);
                            finish();
                        } else {
                            Intent i = new Intent(LoginActivity.this, New_offer_activity.class);
                            startActivity(i);
                            finish();
                        }

                    } else {
                        //Error in login. Get the error message
                        String errorMsg = obj.getString("error_msg");
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

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {

        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        //Check for empty data in form
        if (!email.isEmpty() && !password.isEmpty()) {
            //login user
            if (Post_rec_activity.getIsPost())
                checkLoginCandidate(email, password);
            else
                checkLoginRecruiter(email, password);
        } else {
            //Prompt user to enter credidentials
            Toast.makeText(getApplicationContext(), "Veuillez saisir tous les champs!", Toast.LENGTH_LONG).show();
        }

    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    // Validating email
    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError("Veuillez entrer un email valide");
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {

                case R.id.email:
                    validateEmail();
                    break;
            }
        }
    }


}





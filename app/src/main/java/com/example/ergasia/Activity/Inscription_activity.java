package com.example.ergasia.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ergasia.app.AppConfig;
import com.example.ergasia.app.AppController;
import com.example.ergasia.R;
import com.example.ergasia.Helper.SQLiteHandler;
import com.example.ergasia.Helper.SessionManager;

import static com.android.volley.Request.Method.POST;


public class Inscription_activity extends Activity {

    private static final String TAG = Inscription_activity.class.getSimpleName();
    private Button createButton;
    private EditText inputName;
    private EditText inputFirstName;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = (RelativeLayout) RelativeLayout.inflate(Inscription_activity.this,
                R.layout.activity_inscription_activity, null);


        // Set fonts
        TextView textView1 = (TextView) layout.findViewById(R.id.nameTextView1);
        TextView textView2 = (TextView) layout.findViewById(R.id.nameEditText1);
        TextView textView3 = (TextView) layout.findViewById(R.id.firstnameTextView1);
        TextView textView4 = (TextView) layout.findViewById(R.id.firstNameEditText1);
        TextView textView5 = (TextView) layout.findViewById(R.id.emailTextView1);
        TextView textView6 = (TextView) layout.findViewById(R.id.emailEditText1);
        TextView textView7 = (TextView) layout.findViewById(R.id.passwordTextView1);
        TextView textView8 = (TextView) layout.findViewById(R.id.passwordEditText1);
        TextView textView9 = (TextView) layout.findViewById(R.id.confpasswordTextView1);
        TextView textView10 = (TextView) layout.findViewById(R.id.confpasswordEditText1);
        TextView textView11 = (TextView) layout.findViewById(R.id.createButton);
        TextView textView12 = (TextView) layout.findViewById(R.id.inscriptionTextView);

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

        inputName = (EditText) layout.findViewById(R.id.nameEditText1);
        inputFirstName = (EditText) layout.findViewById(R.id.firstNameEditText1);
        inputEmail = (EditText) layout.findViewById(R.id.emailEditText1);
        inputPassword = (EditText) layout.findViewById(R.id.passwordEditText1);
        createButton = (Button) layout.findViewById(R.id.createButton);

        //Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //Session manager
        session = new SessionManager(getApplicationContext()); //return the context for the entire application

        //SQlite database handler
        db = new SQLiteHandler(getApplicationContext());

        //Check if user is already logged in or not
        if (session.isLoggedIn()) {
            //User is already logged in. Take him to main activity
            Intent intent = new Intent(Inscription_activity.this, MainTabbedActivityPost.class);
            startActivity(intent);
            finish();
        }


        //Create button click event
        createButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputName.getText().toString().trim();
                String firstname = inputFirstName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();


                if (!name.isEmpty() && !firstname.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    registerUser(name, firstname, email, password);
                } else {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer vos coordonnées !",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        setContentView(layout);
    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * firstname, email, password) to register url
     *
     * @param name
     * @param firstname
     * @param email
     * @param password
     */
    private void registerUser(final String name, final String firstname,
                              final String email, final String password) {
        //Tag used to cancel the request
        String tag_string_req = "req_register";
        pDialog.setMessage("Enregistrement...");
        showDialog();

        //Request a string response from the provided URL
        StringRequest strReq = new StringRequest(POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        session.setCandidate(false);
                        session.setRecruiter(false);
                        //User succesfully stored in MySQL
                        //Now store the user in Sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String firstname = user.getString("firstname");
                        String email = user.getString("email");
                        String created_at = user.getString("created_at");


                        //Inserting row in users table
                        db.addUser(name, firstname, email, uid, created_at);
                        Toast.makeText(getApplicationContext(), "Enregistré avec succès ! " +
                                "Connectez-vous maintenant !", Toast.LENGTH_LONG).show();

                        //Launch login activity
                        Intent intent = new Intent(Inscription_activity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        //Error occured in registration. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg,
                                Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("firstname", firstname);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        //Adding request to request queue
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
     * function setFont which use to customize the font of the view
     *
     * @param textView
     * @param fontName
     */
    private void setFont(TextView textView, String fontName) {
        if (fontName != null) {
            try {
                Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + fontName);
                textView.setTypeface(typeface);
            } catch (Exception e) {
                Log.e("FONT", fontName + " not found", e);
            }
        }
    }

}

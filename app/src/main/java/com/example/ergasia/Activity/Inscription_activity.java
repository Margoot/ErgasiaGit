package com.example.ergasia.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.example.ergasia.model.User;

import static com.android.volley.Request.Method.POST;


public class Inscription_activity extends AppCompatActivity {

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

    private TextInputLayout inputLayoutEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = (RelativeLayout) RelativeLayout.inflate(Inscription_activity.this,
                R.layout.activity_inscription_activity, null);

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
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
        /*
        if (session.isLoggedIn()) {
            //User is already logged in. Take him to main activity
            Intent intent = new Intent(Inscription_activity.this, MainTabbedActivityPost.class);
            startActivity(intent);
            finish();
        }
        */


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
                        session.setLogin(true);
                        //User succesfully stored in MySQL
                        //Now store the user in Sqlite
                        //String uidUser = jObj.getString("uidUser");

                        JSONObject userObj = jObj.getJSONObject("user");
                        User user = new User(userObj.getString("user_id"), userObj.getString("name"), userObj.getString("email"));
                        String uidUser = userObj.getString("uidUser");
                        String name = userObj.getString("name");
                        String firstname = userObj.getString("firstname");
                        String email = userObj.getString("email");
                        String created_at = userObj.getString("created_at");

                        AppController.getInstance().getPrefManager().storeUser(user);

                        session.setEmail(email);

                        //Inserting row in users table
                        db.addUser(name, firstname, email, uidUser, created_at);
                        Toast.makeText(getApplicationContext(), "Enregistré avec succès ! " +
                                "Entrez vos coordonées !", Toast.LENGTH_LONG).show();

                        //Launch login activity
                        if(Post_rec_activity.getIsPost()) {
                            Intent intent = new Intent(Inscription_activity.this, New_post_activity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(Inscription_activity.this, New_offer_activity.class);
                            startActivity(intent);
                            finish();
                        }
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




}

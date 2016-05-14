package com.example.ergasia.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ergasia.Helper.SQLiteHandler;
import com.example.ergasia.Helper.SessionManager;
import com.example.ergasia.R;
import com.example.ergasia.app.AppConfig;
import com.example.ergasia.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class View_offer_activity extends AppCompatActivity {

    private static final String TAG = View_offer_activity.class.getSimpleName();
    private Toolbar toolbar;
    private SQLiteHandler db;
    private SessionManager session;

    private static EditText inputCompany;
    private static EditText inputJobTitle;
    private static EditText inputAreaActivity;
    private static EditText inputType;
    private static EditText inputGeolocation;
    private static EditText inputSkill;
    private static Button finishButton;
    private static RadioGroup inputTypeGroup;

    private ProgressDialog pDialog;
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_offer_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        inputCompany = (EditText) findViewById(R.id.viewJobEditText);
        inputJobTitle = (EditText) findViewById(R.id.jobTitleEditText2);
        inputAreaActivity = (EditText) findViewById(R.id.viewAreaEditText);
        inputType = (EditText) findViewById(R.id.viewTypeEditText);
        inputGeolocation = (EditText) findViewById(R.id.viewLocationTextEdit);
        inputSkill = (EditText) findViewById(R.id.viewSkillsTextEdit);
        finishButton = (Button) findViewById(R.id.finishModification);

        //Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //Fetching candidate details from SQlite
        HashMap<String, String> recruiter = db.getOfferDetails();
        String company = recruiter.get("company");
        String jobTitle = recruiter.get("job_title");
        String areaActivity = recruiter.get("area_activity");
        String type = recruiter.get("type");
        String geolocation = recruiter.get("geolocation");
        String skill = recruiter.get("skill");
        uid = recruiter.get("uidOffer");

        inputCompany.setText(company);
        inputJobTitle.setText(jobTitle);
        inputAreaActivity.setText(areaActivity);
        inputType.setText(type);
        inputGeolocation.setText(geolocation);
        inputSkill.setText(skill);

        //Create button click event
        finishButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String company = inputCompany.getText().toString().trim();
                String jobTitle = inputJobTitle.getText().toString().trim();
                String areaActivity = inputAreaActivity.getText().toString().trim();
                String geolocation = inputGeolocation.getText().toString().trim();
                String skill = inputSkill.getText().toString().trim();

                //if condition to check if the user has chosen the type
                if (inputTypeGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Veuillez choisir le type de contrat", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton selectedButton = (RadioButton) findViewById(inputTypeGroup.getCheckedRadioButtonId());
                    String type = selectedButton.getText().toString();

                    //dans le else car !empty is verified

                    if (!company.isEmpty() && !jobTitle.isEmpty() && !areaActivity.isEmpty() &&
                            !skill.isEmpty() && !geolocation.isEmpty()) {
                        updatedNewRecruiter(uid,company, jobTitle, areaActivity, type,
                               geolocation, skill);
                    } else {
                        Toast.makeText(getApplicationContext(), "Veuillez entrer tous les champs !",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        setContentView(R.layout.activity_view_offer_activity);

    }

    private void updatedNewRecruiter(final String uidOffer,final String company, final String jobTitle,
                                     final String areaActivity, final String type,
                                     final String geolocation, final String skill ) {

        //Tag used to cancel the request
        String tag_string_req = "req_update_recruiter";
        pDialog.setMessage("Enregistrement...");
        showDialog();

        //Request a string response from the provided URL
        StringRequest strReq = new StringRequest(POST,
                AppConfig.URL_UPDATE_RECRUITER, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "New Recruiter Updated Response: " + response.toString());
                hideDialog();


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        //the new candidate succesfully stored in MySQL
                        //Now store the candidate in Sqlite

                        JSONObject jUpdateRecruiter = jObj.getJSONObject("offer");

                        String company = jUpdateRecruiter.getString("company");
                        String jobTitle = jUpdateRecruiter.getString("job_title");
                        String areaActivity = jUpdateRecruiter.getString("area_activity");
                        String type = jUpdateRecruiter.getString("type");
                        String geolocation = jUpdateRecruiter.getString("geolocation");
                        String skill = jUpdateRecruiter.getString("skill");


                        //Inserting row in candidates table
                        db.updateRecruiter(company, jobTitle, areaActivity,type,
                                geolocation, skill);
                        Toast.makeText(getApplicationContext(), "Votre offre a été mise à jour avec succès ! "
                                , Toast.LENGTH_LONG).show();

                        //display the new data from the database
                        showModifications(type);

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
                Log.e(TAG, "Update Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to new candidate url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", session.getEmail());
                params.put("unique_id", uidOffer);
                params.put("name", company);
                params.put("firstname", jobTitle);
                params.put("area_activity", areaActivity);
                params.put("type", type);
                params.put("geolocation", geolocation);
                params.put("skill", skill);
                return params;
            }
        };

        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    public static void modifyCandidate() {
        inputType.setVisibility(View.GONE);
        inputTypeGroup.setVisibility(View.VISIBLE);
        finishButton.setVisibility(View.VISIBLE);

        inputCompany.setFocusableInTouchMode(true);
        inputJobTitle.setFocusableInTouchMode(true);
        inputAreaActivity.setFocusableInTouchMode(true);
        inputType.setFocusableInTouchMode(true);
        inputGeolocation.setFocusableInTouchMode(true);
        inputSkill.setFocusableInTouchMode(true);


    }

    public void showModifications(String type) {

        inputType.setVisibility(View.VISIBLE);
        inputType.setText(type);

        finishButton.setVisibility(View.GONE);
        inputTypeGroup.setVisibility(View.GONE);

        inputCompany.setFocusableInTouchMode(false);
        inputJobTitle.setFocusableInTouchMode(false);
        inputAreaActivity.setFocusableInTouchMode(false);
        inputType.setFocusableInTouchMode(false);
        inputGeolocation.setFocusableInTouchMode(false);
        inputSkill.setFocusableInTouchMode(false);

        inputCompany.setFocusable(false);
        inputJobTitle.setFocusable(false);
        inputAreaActivity.setFocusable(false);
        inputType.setFocusable(false);
        inputGeolocation.setFocusable(false);
        inputSkill.setFocusable(false);
    }


    //Logout function
    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Etes-vous sûr de vouloir vous déconnecter ?");
        alertDialogBuilder.setPositiveButton("Oui",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(session.PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(session.KEY_IS_LOGGEDIN, false);

                        //Saving the sharedpreferences
                        editor.commit();

                        db.deleteUsers();
                        db.deleteOffers();

                        //Starting login activity
                        Intent intent = new Intent(View_offer_activity.this, Post_rec_activity.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("Non",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_offer_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
        }
        if (id == R.id.action_modify) {
            modifyCandidate();
        }
        return super.onOptionsItemSelected(item);
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

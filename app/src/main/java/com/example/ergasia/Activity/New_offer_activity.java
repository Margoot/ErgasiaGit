package com.example.ergasia.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ergasia.app.AppConfig;
import com.example.ergasia.app.AppController;
import com.example.ergasia.R;
import com.example.ergasia.Helper.SQLiteHandler;
import com.example.ergasia.Helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class New_offer_activity extends AppCompatActivity {

    private SessionManager session;
    private static final String TAG = New_offer_activity.class.getSimpleName();
    private Button validateButton;
    private EditText inputCompany;
    private EditText inputJobTitle;
    private EditText inputAreaActivity;
    private RadioGroup inputType;
    private EditText inputGeolocation;
    private EditText inputSkill;
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_offer_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textView1  = (TextView)findViewById(R.id.cdiRadioButton);
        TextView  textView2  = (TextView)findViewById(R.id.cddRadioButton);
        TextView  textView3  = (TextView)findViewById(R.id.internRadioButton);
        TextView  textView4  = (TextView)findViewById(R.id.newAreaTextView);
        TextView  textView5  = (TextView)findViewById(R.id.newAreaEditText);
        TextView  textView6  = (TextView)findViewById(R.id.newLocationTextView);
        TextView  textView7  = (TextView)findViewById(R.id.LocationEditText);
        TextView  textView8  = (TextView)findViewById(R.id.newJobTitleTextView);
        TextView  textView9  = (TextView)findViewById(R.id.newJobTitleEditText);
        TextView  textView10  = (TextView)findViewById(R.id.geolocSwitchNewOffer);
        TextView  textView11  = (TextView)findViewById(R.id.newSkillsTextView);
        TextView  textView12  = (TextView)findViewById(R.id.newSkillsEditText);
        TextView  textView15  = (TextView)findViewById(R.id.newTypeTextView);
        TextView  textView13  = (TextView)findViewById(R.id.newTypeTextView);
        TextView textView14  = (TextView)findViewById(R.id.newCompanyTextView);
        TextView textView16  = (TextView)findViewById(R.id.newCompanyEditText);


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
        setFont(textView13, "BigCaslon.ttf");
        setFont(textView14, "BigCaslon.ttf");
        setFont(textView15, "BigCaslon.ttf");
        setFont(textView16, "BigCaslon.ttf");

        inputCompany = (EditText) findViewById(R.id.newCompanyEditText);
        inputJobTitle = (EditText) findViewById(R.id.newJobTitleEditText);
        inputAreaActivity = (EditText) findViewById(R.id.newAreaEditText);
        inputType = (RadioGroup) findViewById(R.id.typeRadioGroup);
        inputGeolocation = (EditText) findViewById(R.id.LocationEditText);
        inputSkill = (EditText) findViewById(R.id.newSkillsEditText);
        validateButton = (Button) findViewById(R.id.createButton);

        //Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //Session manager for a new offer
        session = new SessionManager(getApplicationContext()); //return the context for the entire application

        //SQlite database handler for a new offer
        db = new SQLiteHandler(getApplicationContext());

       /* if (session.isRecruiter()) {
            Intent intent = new Intent(New_offer_activity.this, MainTabbedActivityRec.class);
            startActivity(intent);
            //finish();
        }*/

        /*
        if(session.isRecruiter()){
            Intent intent = new Intent(New_offer_activity.this,MainTabbedActivityRec.class);
            startActivity(intent);
        }
        */

        //validate button click event
        validateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String company = inputCompany.getText().toString().trim();
                String jobTitle = inputJobTitle.getText().toString().trim();
                String areaActivity = inputAreaActivity.getText().toString().trim();

                //if confition to check if the user has chosen the type of the new offer
                if (inputType.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Veuillez choisir le type de contrat", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton selectedButton = (RadioButton) findViewById(inputType.getCheckedRadioButtonId());
                    String type = selectedButton.getText().toString();

                    /**
                     *
                     * Pourquoi laisser tous les autres champs dans le else du radio button?
                     * Le dernier if ne devrait pas etre en dehors de la première condition car on chack que tous les champs sont bien
                     * complet
                     * Comme ça ça doit marcher mais un peu chelou je trouve, ça fait beaucoup de conditions imbriquées
                     */

                    String geolocation = inputGeolocation.getText().toString().trim();
                    String skill = inputSkill.getText().toString().trim();

                    //check if the fields are all filled without the type because it's already checked above
                    if (!company.isEmpty() && !jobTitle.isEmpty() && !areaActivity.isEmpty() && !geolocation.isEmpty() &&
                            !skill.isEmpty()) {
                        registerNewOffer(company, jobTitle, areaActivity, type, geolocation, skill);
                    } else {
                        Toast.makeText(getApplicationContext(), "Veuillez entrer tous les champs !",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
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


    /**
     * Function to store an offer in MySQL database will post params(tag, company,
     * jobTitle, areaActivity, type,geolocation,skill) to new offer url
     * @param company
     * @param jobTitle
     * @param areaActivity
     * @param type
     * @param geolocation
     * @param skill
     */
    private void registerNewOffer(final String company, final String jobTitle,final String areaActivity,
                              final String type, final String geolocation, final String skill ) {
        //Tag used to cancel the request
        String tag_string_req = "req_register";
        pDialog.setMessage("Enregistrement...");
        showDialog();

        //Request a string response from the provided URL
        StringRequest strReq = new StringRequest(POST,
                AppConfig.URL_NEW_OFFER, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        //new offer succesfully stored in MySQL
                        //Now store the offer in Sqlite
                        session.setRecruiter(true);
                        /**
                         *
                         * C'est ici qu'il faut faire appel au sessionmanager,
                         * puisque on ne sauvegarde jamais le fait que l'utillisateur est bien enregistré, one ne
                         * fait qu'afficher un message
                         */
                        String uidOffer = jObj.getString("uidOffer");

                        JSONObject offer = jObj.getJSONObject("offer");
                        int id_users_fk = offer.getInt("id_users_fk");
                        String company = offer.getString("company");
                        String jobTitle = offer.getString("job_title");
                        String areaActivity = offer.getString("area_activity");
                        String type = offer.getString("type");
                        String geolocation = offer.getString("geolocation");
                        String skill = offer.getString("skill");
                        String created_at = offer.getString("created_at");


                        //Inserting row in recruiters table
                        db.addOffer(company, jobTitle, areaActivity, type, geolocation, skill, uidOffer,id_users_fk, created_at);
                        Toast.makeText(getApplicationContext(), "L'offre a été enregistré avec succès ! "
                                , Toast.LENGTH_LONG).show();

                        //Launch mainTabbedActivity activity
                        Intent intent = new Intent(New_offer_activity.this, MainTabbedActivityRec.class);
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
                params.put("email", session.getEmail());
                params.put("company", company);
                params.put("job_title", jobTitle);
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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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

                        //Starting login activity
                        Intent intent = new Intent(New_offer_activity.this, Post_rec_activity.class);
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
        getMenuInflater().inflate(R.menu.menu_new_offer_activity, menu);
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
        return super.onOptionsItemSelected(item);
    }

}
